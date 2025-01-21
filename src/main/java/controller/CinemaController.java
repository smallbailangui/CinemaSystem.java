package main.java.controller;


import main.java.model.*;
import main.java.service.*;

import java.awt.*;
import java.sql.SQLException;

import main.java.service.impl.*;

import javax.swing.*;

public class CinemaController {

    //
    private static CinemaController instance;

    private MovieService movieService;

    private LoginService loginService;

    private UserService userService;
    private CinemaController() {

        loginService = new LoginServiceImpl();

        userService = new UserServiceImpl();
    }

    public static CinemaController getInstance() {
        if (instance == null) {
            instance = new CinemaController();
        }
        return instance;
    }


    public User userRegister(String userName,String userPassword) throws SQLException {
        return userService.addUser(userName,userPassword);
    }
    public boolean handleAdminLogin(String adminName,String adminPassword) {

        try {
            Admin admin = loginService.adminLogin(adminName, adminPassword);
            if (admin != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public User handleUserLogin(String userName, String userPassword) {
        try {
            User user = loginService.userLogin(userName, userPassword);
            return user;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new Frame(), "用户登录时出现错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }


}
