package main.java.service.impl;




import main.java.dao.ShowingDAO;
import main.java.dao.impl.ShowingDAOimpl;
import main.java.model.Showing;
import main.java.service.ShowingService;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class ShowingServiceImpl implements ShowingService {
    private ShowingDAO showingDAO = new ShowingDAOimpl();


    /**
     * 根据给定的放映场次ID删除放映场次，并删除关联的座位信息。
     * @param removeShowingId 要删除的放映场次的ID
     */
    public void removeShowing(int removeShowingId) throws SQLException {
        List<Showing> showings = showingDAO.getAllShowings();
        if(showings.isEmpty()){
            JOptionPane.showConfirmDialog(new Frame(), "暂无放映场次！", "网上电影院购票", JOptionPane.YES_OPTION);
            System.out.println("暂无放映场次！");
            System.out.println();
            return;
        }
        boolean exist = false;

        for(Showing showing:showings){
            if(removeShowingId == showing.getShowingId()){
                exist = true;
                break;
            }
        }

        if(!exist){
            JOptionPane.showMessageDialog(new Frame(), "您输入的场次id不存在！", "网上电影院购票", JOptionPane.ERROR_MESSAGE);
            System.out.println("您输入的场次id不存在！");
            System.out.println();
            return;
        }
        SeatServiceImpl seatService = new SeatServiceImpl();
        seatService.removeSeatByShowingId(removeShowingId);
        showingDAO.removeShowing(removeShowingId);
         JOptionPane.showConfirmDialog(new Frame(), "场次删除成功", "网上电影院购票", JOptionPane.YES_OPTION);
        System.out.println("场次删除成功！");
        System.out.println();
    }

    public List<Showing> getAllShowings() throws SQLException {
        return showingDAO.getAllShowings();
    }

    /**
     * 添加一个新的放映场次，并为该场次生成座位。
     * @param cinemaId 影院ID
     * @param movieId 电影ID
     * @param startTime 放映开始时间
     * @param price 放映票价
     */
    public void addShowing(int cinemaId, int movieId, Timestamp startTime,double price) {
        try {
            SeatServiceImpl seatService = new SeatServiceImpl();
            int showingId = showingDAO.addShowing(cinemaId, movieId, startTime,price);
            if (showingId != -1) {
                // 生成30个座位，五排六列
                for (int row = 1; row <= 5; row++) {
                    for (int column = 1; column <= 6; column++) {
                        String seatNumber = String.format("%d排%d列", row, column);
                        if(row==1) {
                            seatService.addSeat(showingId, seatNumber, 1);
                        }
                        else
                        {
                            seatService.addSeat(showingId, seatNumber, 0);
                        }
                    }
                }
                System.out.println("放映场次添加成功！");
                JOptionPane.showConfirmDialog(new Frame(), "放映场次添加成功", "网上电影院购票", JOptionPane.YES_OPTION);
            } else {
                System.out.println("添加放映失败。");
                JOptionPane.showConfirmDialog(new Frame(), "添加放映失败", "网上电影院购票", JOptionPane.YES_OPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加放映失败。");
        }
    }

    public int getPriceByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getPriceByShowingId(showingId);
        if (rs.next()) {
            return rs.getInt("price");
        }
        return -1;
    }


    public Timestamp getStartTimeByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getStartTimeByShowingId(showingId);
        if (rs.next()) {
            return rs.getTimestamp("start_time");
        }
        return null;
    }

    public String getMovieNameByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getMovieNameByShowingId(showingId);
        if (rs.next()) {
            return rs.getString("title");
        }
        return null;
    }

    public String getCinemaNameByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getCinemaNameByShowingId(showingId);
        if (rs.next()) {
            return rs.getString("name");
        }
        return null;
    }


}
