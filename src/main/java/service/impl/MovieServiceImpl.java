package main.java.service.impl;

import main.java.dao.MovieDAO;
import main.java.dao.ShowingDAO;
import main.java.dao.impl.MovieDAOimpl;
import main.java.dao.impl.ShowingDAOimpl;
import main.java.model.Movie;
import main.java.model.Showing;
import main.java.service.MovieService;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * MovieServiceImpl 类实现了 MovieService 接口，提供了电影相关的操作方法。
 */
public class MovieServiceImpl implements MovieService {
    private MovieDAOimpl movieDAO = new MovieDAOimpl();

    /**
     * 根据电影ID删除电影记录，并删除所有相关场次。
     * @param movieId 要删除的电影的ID
     * @throws SQLException 数据库操作异常
     */
    public void removeMovieById(int movieId) throws SQLException {
        List<Movie> movies =  movieDAO.getAllMovies();
        boolean exist = false;
        if(movies.isEmpty()){
            JOptionPane.showConfirmDialog(new Frame(), "您输入的电影id不存在！", "网上电影院购票", JOptionPane.YES_OPTION);

            return;
        }
        for(Movie movie:movies){
            if(movie.getId() == movieId){
                exist  = true;
                break;
            }
        }
        if(!exist){
            JOptionPane.showConfirmDialog(new Frame(), "您输入的电影id不存在！", "网上电影院购票", JOptionPane.YES_OPTION);

            System.out.println();
            return;
        }
        ShowingDAOimpl showingDAO = new ShowingDAOimpl();
        try {
            List<Showing> showings = showingDAO.getShowingsByMovieId(movieId);
            if(!showings.isEmpty()){
                for(Showing showing: showings){
                    showingDAO.removeShowing(showing.getShowingId());
                }
            }
            movieDAO.removeMovie(movieId);
            JOptionPane.showConfirmDialog(new Frame(), "电影下架成功！ 相应场次已经删除！", "网上电影院购票", JOptionPane.YES_OPTION);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个新的电影记录。
     * @param title 电影的标题
     * @param status 电影的状态
     */
    public void addMovie(String title, String status,int score,int duration,String introduction) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setStatus(status);
        movie.setScore(score);
        movie.setDuration(duration);
        movie.setIntroduction(introduction);

        try {
            movieDAO.addMovie(movie);
            JOptionPane.showConfirmDialog(new Frame(), "电影添加成功!", "网上电影院购票", JOptionPane.YES_OPTION);
            System.out.println("电影添加成功！");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new Frame(), "添加电影失败", "网上电影院购票", JOptionPane.ERROR_MESSAGE);
            System.out.println("添加电影失败。");
            System.out.println();
        }
    }


    /**
     * 获取所有电影的列表。
     * @return 包含所有电影的列表
     * @throws SQLException 数据库操作异常
     */
    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    /**
     * 修改指定电影的状态。
     * @param movie_id 要修改状态的电影ID
     * @param status 新的电影状态
     */
    public void alterMovieStatus(int movie_id, String status) {

        try {
            movieDAO.alterMovieStatus(status,movie_id);
            System.out.println("电影状态修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("电影状态修改成功失败。");
        }
    }


}
