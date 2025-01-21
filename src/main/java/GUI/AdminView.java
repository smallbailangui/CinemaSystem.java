package main.java.GUI;

import main.java.controller.AdminController;
import main.java.dao.* ;
import main.java.dao.impl.CinemaDAOimpl;
import main.java.dao.impl.MovieDAOimpl;
import main.java.dao.impl.SeatDAOimpl;
import main.java.dao.impl.ShowingDAOimpl;
import main.java.model.*;
import main.java.service.* ;
import main.java.service.impl.ShowingServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AdminView extends JFrame {
    private AdminController adminController;

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
        setTitle("管理员操作界面");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("电影院网上购票系统");

        getContentPane().add(label, BorderLayout.NORTH); // 将标签添加到窗口
        Font title = new Font("楷体", Font.BOLD, 30);//字体，叫Title
        Font text = new Font("Text", Font.PLAIN, 20);//字体，叫Text
        label.setFont(title);
        // 主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // 输出区域
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        mainPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // 菜单按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 100, 5));
        // 按钮
        Font font = new Font("T", Font.BOLD, 20);
        JButton addCinemaBtn = new JButton("添加影厅");
        addCinemaBtn.setFont(font);
        JButton displayCinemasBtn = new JButton("显示所有影厅");
        displayCinemasBtn.setFont(font);
        JButton addMovieBtn = new JButton("添加电影");
        addMovieBtn.setFont(font);
        JButton displayMoviesBtn = new JButton("显示所有电影");
        displayMoviesBtn.setFont(font);
        JButton addSeatBtn = new JButton("添加座位");
        addSeatBtn.setFont(font);
        JButton displaySeatNumberBySeatIdBtn = new JButton("显示场次剩余座位");
        displaySeatNumberBySeatIdBtn.setFont(font);
        JButton alterShowingsBtn = new JButton("添加放映场次");
        alterShowingsBtn.setFont(font);
        JButton displayShowingsBtn = new JButton("显示所有放映场次");
        displayShowingsBtn.setFont(font);
        JButton alterMovieStatusBtn = new JButton("修改电影状态");
        alterMovieStatusBtn.setFont(font);
        JButton removeMovieBtn = new JButton("删除电影");
        removeMovieBtn.setFont(font);
        JButton removeShowingBtn = new JButton("删除场次");
        removeShowingBtn.setFont(font);
        JButton returnMain = new JButton("退出登录");
        returnMain.setFont(font);

        // 按钮操作
        addCinemaBtn.addActionListener(e -> showAddCinemaDialog(outputArea));
        addMovieBtn.addActionListener(e -> showAddMovieDialog(outputArea));
        addSeatBtn.addActionListener(e -> showAddSeatDialog(outputArea));
        displaySeatNumberBySeatIdBtn.addActionListener(e ->getShowingID(outputArea));
        displayCinemasBtn.addActionListener(e -> displayAllCinemas(outputArea));
        displayMoviesBtn.addActionListener(e -> displayAllMovies(outputArea));
        displayShowingsBtn.addActionListener(e -> displayAllShowings(outputArea));
        alterMovieStatusBtn.addActionListener(e -> showAlterMovieStatusDialog(outputArea));
        alterShowingsBtn.addActionListener(e ->showAlterShowingDialog(outputArea));
        removeMovieBtn.addActionListener(e -> showRemoveMovieDialog(outputArea));
        removeShowingBtn.addActionListener(e -> showRemoveShowingDialog(outputArea));
        returnMain.addActionListener(e-> logOutDialog(outputArea));
        buttonPanel.add(addCinemaBtn);
        buttonPanel.add(displayCinemasBtn);
        buttonPanel.add(addMovieBtn);
        buttonPanel.add(displayMoviesBtn);
        buttonPanel.add(addSeatBtn);
        buttonPanel.add(displaySeatNumberBySeatIdBtn);
        buttonPanel.add(alterShowingsBtn);
        buttonPanel.add(displayShowingsBtn);
        buttonPanel.add(alterMovieStatusBtn);
        buttonPanel.add(removeMovieBtn);
        buttonPanel.add(removeShowingBtn);
        buttonPanel.add(returnMain);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        // 添加时间显示
        TimeDisplayUtil.addTimeDisplay(this);

        setVisible(true);
    }

    // 显示所有影厅
    private void displayAllCinemas(JTextArea outputArea) {
        outputArea.setText("");  // 清空输出区域
        JFrame frame = new JFrame("影厅信息");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        String[] columnNames = {"影厅ID", "影厅名称", "总座位数"};
        JTable table = new JTable();
        table.setModel(new DefaultTableModel(columnNames, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );
        table.setFont(new Font("text", Font.PLAIN, 15 ));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        CinemaDAO cinemaDAO = new CinemaDAOimpl();
        try {
            List<Cinema> cinemas = cinemaDAO.getAllCinemas();
            if (cinemas.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "暂无影厅信息！");
                return;
            }
            for (Cinema cinema : cinemas) {
                Object[] row = new Object[]{cinema.getId(), cinema.getName(), cinema.getTotalSeats()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "获取影院信息失败。");
            return;
        }

        // 创建滚动面板，将表格放入其中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动面板添加到窗口中
        frame.add(scrollPane, BorderLayout.CENTER);

        // 显示窗口
        frame.setVisible(true);
    }

    // 显示所有电影
    private void displayAllMovies(JTextArea outputArea) {
        outputArea.setText("");  // 清空输出区域
//        adminController.displayAllMovies();
        JFrame frame = new JFrame("电影信息");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        String[] columnNames = {"电影ID", "电影名称", "电影状态","电影评分","电影时长","电影简介"};
        JTable table = new JTable();
        table.setModel(new DefaultTableModel(columnNames, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );
        table.setFont(new Font("text", Font.PLAIN, 15 ));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        MovieDAOimpl movieDAO = new MovieDAOimpl();
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            if (movies.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "暂无电影信息！");
                return;
            }
            for (Movie movie : movies) {
                Object[] row = new Object[]{movie.getId(), movie.getTitle(), movie.getStatus(),movie.getScore(),movie.getDuration(),movie.getIntroduction()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "获取电影信息失败。");
            return;
        }

        // 创建滚动面板，将表格放入其中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动面板添加到窗口中
        frame.add(scrollPane, BorderLayout.CENTER);

        // 显示窗口
        frame.setVisible(true);
    }

    // 显示场次剩余座位
    private void getShowingID(JTextArea outputArea) {
        JTextField IdField = new JTextField();
        Object[] message = {
                "场次ID:", IdField,
        };
        int option = JOptionPane.showConfirmDialog(this, message, "场次剩余座位", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = IdField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "场次剩余座位不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            displaySeatNumberBySeatId(IdField.getText());
        }
    }

    private void displaySeatNumberBySeatId(String showingId) {

//        adminController.displayAllMovies();
        JFrame frame = new JFrame("放映场 "+showingId+" 剩余座位");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        String[] columnNames = { "座位ID", "座位号","座位种类","是否可用"};
        JTable table = new JTable();
        table.setModel(new DefaultTableModel(columnNames, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );
        table.setFont(new Font("text", Font.PLAIN, 15 ));

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        SeatDAOimpl seatDAO = new SeatDAOimpl();
        try {
            List<Seat> seats = seatDAO.getSeatsByShowingId(Integer.parseInt(showingId));
            if (seats.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "暂无场次信息！");
                return;
            }
            for (Seat seat : seats) {
                Object[] row = new Object[]{seat.getSeatId(),  seat.getSeatNumber(), seat.getSeat_type()==1 ? "VIP座位":"普通座位" , seat.isAvailable()==1 ?"是":"否"};
//                System.out.println(seat.getSeat_tpye());
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "获取座位信息失败。");
            return;
        }

        // 创建滚动面板，将表格放入其中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动面板添加到窗口中
        frame.add(scrollPane, BorderLayout.CENTER);

        // 显示窗口
        frame.setVisible(true);
    }

    // 显示所有放映场次

    private void displayAllShowings(JTextArea outputArea) {
        outputArea.setText("");  // 清空输出区域
        JFrame frame = new JFrame("放映信息");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        String[] columnNames = {"放映ID", "影院ID", "电影名称","开始时间","票价"};
        JTable table = new JTable();
        table.setModel(new DefaultTableModel(columnNames, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN );
        table.setFont(new Font("text", Font.PLAIN, 12 ));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ShowingServiceImpl showingService = new ShowingServiceImpl();
        ShowingDAO showingDAO = new ShowingDAOimpl();
        try {
            List<Showing> showings = showingDAO.getAllShowings();
            if (showings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "暂无放映信息！");
                return;
            }
            for (Showing showing : showings) {
                Object[] row = new Object[]{showing.getShowingId(), showing.getCinemaId(),  showingService.getMovieNameByShowingId(showing.getShowingId()),showing.getStartTime(),showing.getPrice()};
                model.addRow(row);//ok
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "获取电影信息失败。");
            return;
        }

        // 创建滚动面板，将表格放入其中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动面板添加到窗口中
        frame.add(scrollPane, BorderLayout.CENTER);

        // 显示窗口
        frame.setVisible(true);
    }

    // 添加影厅
    private void showAddCinemaDialog(JTextArea outputArea) {
        JTextField nameField = new JTextField();
        JTextField totalSeatsField = new JTextField();
        Object[] message = {
                "影厅名称:", nameField,
                "总座位数:", totalSeatsField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "添加影厅", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "影厅名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int totalSeats = 0;
            try {
                totalSeats = Integer.parseInt(totalSeatsField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "总座位数必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            adminController.addCinema(name, Integer.parseInt(String.valueOf(totalSeats)));

        }
    }

    // 添加电影
    private void showAddMovieDialog(JTextArea outputArea) {
        JTextField titleField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField scoreField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField introductionField = new JTextField();
        Object[] message = {
                "电影名称:", titleField,
                "电影状态:", statusField,
                "电影评分:",scoreField,
                "电影时长:",durationField,
                "电影简介:",introductionField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "添加电影", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "电影名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String status = statusField.getText();
            if (status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "电影状态不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String score1 = scoreField.getText();
            if (score1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "电影评分不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String duration1 = durationField.getText();
            if (duration1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "电影时长不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String introduction = introductionField.getText();
            if (introduction.isEmpty()) {
                JOptionPane.showMessageDialog(this, "电影简介不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int score =Integer.parseInt(score1);
            int duration = Integer.parseInt(duration1);
            adminController.addMovie(title,status,score,duration,introduction);

        }
    }
    //添加放映厅
    private  void showAlterShowingDialog(JTextArea outputArea) {
        JTextField cinemaID = new JTextField();
        JTextField movieIDForShowing = new JTextField();
        JTextField startTimeStr1 = new JTextField("(yyyy-mm-dd hh:mm:ss)");
        JTextField Price = new JTextField();
        Object[] message = {
                "影院ID",cinemaID,
                "电影ID",movieIDForShowing,
                "开始时间",startTimeStr1,
                "票价",Price
        };
        int option = JOptionPane.showConfirmDialog(this, message, "添加座位", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int cinemaId = 0;
            try {
                cinemaId = Integer.parseInt(cinemaID.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "影院ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int movieIdForShowing = 0;
            try {
                movieIdForShowing = Integer.parseInt(movieIDForShowing.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "电影ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String startTimeStr = startTimeStr1.getText();
            if (startTimeStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "开始时间不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Timestamp startTime = Timestamp.valueOf(startTimeStr);
            String price = Price.getText();
            if (price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "票价不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ShowingService showingService =(ShowingService) new ShowingServiceImpl();

            showingService.addShowing(cinemaId, movieIdForShowing, startTime, Double.parseDouble(price));

        }
    }
    // 添加座位
    private void showAddSeatDialog(JTextArea outputArea) {
        JTextField showingIdField = new JTextField();
        JTextField seatNumberField = new JTextField();
        JTextField seattypeField = new JTextField();
        // 创建一个选项列表
        Object[] options = {"VIP座位", "普通座位"};
        JComboBox<String> seatType = new JComboBox<>();

        seatType.addItem("普通座位");
        seatType.addItem("VIP座位");
        Object[] message = {
                "场次ID:", showingIdField,
                "座位号:", seatNumberField,
                "座位种类: ",seatType
        };
        int option = JOptionPane.showConfirmDialog(this, message, "添加座位", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int showingId = 0;
            try {
                showingId = Integer.parseInt(showingIdField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "场次ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String seatNumber = seatNumberField.getText();
            if (seatNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "座位号不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int seat_type = seatType.getSelectedIndex();

            try {
                adminController.addSeat(Integer.parseInt(String.valueOf(showingId)),seatNumber,seat_type);
                JOptionPane.showConfirmDialog(new Frame(), "座位添加成功", "网上电影院购票", JOptionPane.YES_OPTION);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "添加座位失败", "错误", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);


            }
        }
    }

    // 修改电影状态
    private void showAlterMovieStatusDialog(JTextArea outputArea) {
        JTextField movieIdField = new JTextField();
        JTextField statusField = new JTextField();
        Object[] message = {
                "电影ID:", movieIdField,
                "新状态:", statusField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "修改电影状态", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int movieId = 0;
            try {
                movieId = Integer.parseInt(movieIdField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "电影ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String status = statusField.getText();
            if (status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "电影状态不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            adminController.alterMovieStatus(Integer.parseInt(String.valueOf(movieId)), status);
            JOptionPane.showConfirmDialog(new Frame(), "电影状态修改成功", "网上电影院购票", JOptionPane.YES_OPTION);
        }
    }

    // 删除电影
    private void showRemoveMovieDialog(JTextArea outputArea) {
        JTextField movieIdField = new JTextField();
        Object[] message = {
                "电影ID:", movieIdField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "删除电影", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int movieId = 0;
            try {
                movieId = Integer.parseInt(movieIdField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "电影ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                adminController.removeMovieById(Integer.parseInt(String.valueOf(movieId)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 删除场次
    private void showRemoveShowingDialog(JTextArea outputArea) {
        JTextField showingIdField = new JTextField();
        Object[] message = {
                "场次ID:", showingIdField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "删除放映场次", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int showingId = 0;
            try {
                showingId = Integer.parseInt(showingIdField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "场次ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                adminController.removeShowing(Integer.parseInt(String.valueOf(showingId)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    //登出
    private void logOutDialog(JTextArea outputArea)
    {
        outputArea.setText("");
        dispose();
        Main.getM().main();
    }

//    // 主方法，启动界面
//    public static void main(String[] args) {
//        AdminController adminController = new AdminController();
//        new AdminView(adminController);
//    }
}
