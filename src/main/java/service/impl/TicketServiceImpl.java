package main.java.service.impl;


import main.java.dao.*;
import main.java.dao.impl.*;
import main.java.model.Seat;
import main.java.model.Showing;
import main.java.model.Ticket;
import main.java.model.User;
import main.java.service.SeatService;
import main.java.service.ShowingService;
import main.java.service.TicketService;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class TicketServiceImpl implements TicketService {
    private TicketDAOimpl ticketDAO = new TicketDAOimpl();
    private MovieDAOimpl movieDAO = new MovieDAOimpl();
    private SeatDAOimpl seatDAO = new SeatDAOimpl();
    private ShowingDAOimpl showingDAO = new ShowingDAOimpl();
    private ShowingServiceImpl showingService = new ShowingServiceImpl();
    private UserDAOimpl userDAO = new UserDAOimpl();



    public void refundTicket(User user,int showingId,String seatNumber) throws SQLException {
        List<Ticket> userTickets = getTicketsByUserId(user.getUserId());
        if(userTickets.isEmpty()){
            JOptionPane.showConfirmDialog(new Frame(), "您似乎还没有买过票，快去买一张吧！", "网上电影院购票", JOptionPane.YES_OPTION);
            return;
        }
        SeatServiceImpl seatService = new SeatServiceImpl();
        for(Ticket ticket: userTickets){
            if(ticket.getShowingId() == showingId &&
                    seatService.getSeatNumberBySeatId(ticket.getSeatId()).equals(seatNumber)){
                double ticketPrice = ticket.getTicketPrice();
                user.setAccountBalance(user.getAccountBalance()+ticketPrice);
                UserServiceImpl userService = new UserServiceImpl();
                userService.updateAccount(user);
                ticketDAO.refundTicket(showingId,ticket.getSeatId(),user.getUserId());
                JOptionPane.showConfirmDialog(new Frame(), "退票成功！ 票价: "+ticketPrice+"元已退回您的账户余额！", "网上电影院购票", JOptionPane.YES_OPTION);
                return;
            }
        }
        JOptionPane.showConfirmDialog(new Frame(), "您想要退的这张票不存在", "网上电影院购票", JOptionPane.YES_OPTION);
    }

    public void purchaseTicket(Showing showing, User user,Seat seat)  {
        try {
            double ticketPrice = showing.getPrice() + (seat.getSeat_type() == 1 ? 15 : 0);
            if (ticketPrice > user.getAccountBalance()) {
                JOptionPane.showConfirmDialog(new Frame(), "购票失败！您的账户余额不足\n"+
                                "票价为：" +showingService.getPriceByShowingId(showing.getShowingId())+" 元， 您的账户余额为："
                        +user.getAccountBalance()+" 元", "网上电影院购票", JOptionPane.YES_OPTION);

                return;
            }
            ticketDAO.addTicket(showing.getShowingId(), seat.getSeatId(), user.getUserId(), ticketPrice);
            ticketDAO.updateSeatAvailability(seat.getSeatId(), 0);
            double leftMoney = user.getAccountBalance() - ticketPrice;
            user.setAccountBalance(leftMoney);
            userDAO.updateAccount(user);
            int cinemaName = showing.getCinemaId();
            String seatNumber = seat.getSeatNumber();
            String seatType=(seat.getSeat_type() == 1 ? "VIP座位" : "普通座位");
            JOptionPane.showConfirmDialog(new Frame(), "购票成功！  您的座位在:"+cinemaName+"号影厅 "+seatNumber+" "+seatType , "网上电影院购票", JOptionPane.YES_OPTION);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new Frame(), "购票失败", "网上电影院购票", JOptionPane.ERROR_MESSAGE);

        }

    }
    public List<Ticket> getTicketsByUserId(int userId) throws SQLException {
        return ticketDAO.getTicketsByUserId(userId);
    }
}