package main.java.controller;


import main.java.service.*;
import main.java.service.impl.*;

import java.sql.SQLException;


public class AdminController {
    private MovieService movieService;
    private ShowingService showingService;
    private SeatService seatService;
    private CinemaService cinemaService;
    public AdminController() {
        movieService = (MovieService)new MovieServiceImpl();
        showingService =(ShowingService) new ShowingServiceImpl();
        seatService = new SeatServiceImpl();
        cinemaService = new CinemaServiceImpl();
    }



    public void removeShowing(int removeShowingId) throws SQLException {
        showingService.removeShowing(removeShowingId);
    }

    public void removeMovieById(int movieId) throws SQLException {
        movieService.removeMovieById(movieId);
    }
    public void alterMovieStatus(int movieId,String alterStatus){
        movieService.alterMovieStatus(movieId,alterStatus);
    }

    public void addCinema(String name,int totalSeats) {
        cinemaService.addCinema(name, totalSeats);
    }


    public void addMovie(String title,String status,int score,int duration,String introduction){
        movieService.addMovie(title, status,score,duration,introduction);
    }

    public void addSeat(int showingId, String seatNumber, int seat_type) throws SQLException {
        seatService.addSeat(showingId, seatNumber,seat_type);
    }

}