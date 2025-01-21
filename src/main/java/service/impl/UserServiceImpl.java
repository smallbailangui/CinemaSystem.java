package main.java.service.impl;

import main.java.dao.UserDAO;
import main.java.dao.impl.UserDAOimpl;
import main.java.model.Movie;
import main.java.model.User;
import main.java.service.UserService;
import main.java.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOimpl();

    public void updateAccount(User user){
        try {
            userDAO.updateAccount(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displayUserAccountBalance(int userId) throws SQLException {
        User user = userDAO.getUserById(userId);
        double money = user.getAccountBalance();
        System.out.println(user.getUserName()+ ", " + "您的账户余额为: "+money+"元");
        System.out.println();
    }

    public User addUser(String userName,String userPassword) throws SQLException {
        //这里添加新用户时，没有遍历数据库以得到id编号，所以id为NULL

        List<User> users = userDAO.getAllUsers();
        for(User user: users){
            if(userName.equals(user.getUserName())){
                JOptionPane.showConfirmDialog(new Frame(), "用户名已存在，请重新输入！", "网上电影院购票", JOptionPane.YES_OPTION);

                return null;
            }
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        //用户账户余额初始值为100
        user.setAccountBalance(100);
        try {
            userDAO.addUser(user);
            JOptionPane.showConfirmDialog(new Frame(), "注册成功!", "网上电影院购票", JOptionPane.YES_OPTION);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(new Frame(), "注册失败!", "网上电影院购票", JOptionPane.YES_OPTION);
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setUserPassword(rs.getString("user_password"));
            user.setAccountBalance(rs.getDouble("account_balance"));
            users.add(user);
        }
        return users;
    }
}
