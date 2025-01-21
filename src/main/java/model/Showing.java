package main.java.model;


import main.java.dao.MovieDAO;
import main.java.dao.impl.MovieDAOimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import main.java.dao.impl.MovieDAOimpl;
public class Showing {
    private int showingId;
    private int cinemaId;
    private int movieId;
    private Timestamp startTime;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getters and Setters
    public int getShowingId() {
        return showingId;
    }

    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
//    public String getMovieName(int id){
//        MovieDAOimpl movieDAO = new MovieDAOimpl();
//        List<Movie> movies=movieDAO.getAllMovies();
//        String t="";
//        for(Movie movie : movies)
//        {
//            if(movie.getId()==id)
//            {
//                t=movie.getTitle();
//                break;
//            }
//        }
//        return  t;
//    }
}
