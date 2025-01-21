
package main.java.GUI;

import main.java.dao.CinemaDAO;
import main.java.dao.MovieDAO;
import main.java.dao.ShowingDAO;
import main.java.dao.TicketDAO;
import main.java.dao.impl.*;
import main.java.model.*;
import main.java.service.TicketService;
import main.java.service.impl.SeatServiceImpl;
import main.java.service.impl.ShowingServiceImpl;
import main.java.service.impl.TicketServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UserView extends JFrame {
//    private UserController userController=new UserController();
    private JTextField usernameField, passwordField, quantityField;
    private JTextArea infoArea;
    private User user;
    public UserView(User user) {
        this.user = user;

        setTitle("用户操作界面");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the layout
//        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("电影院用户系统", JLabel.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Create a panel for user input (login, register, etc.)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

//        JLabel usernameLabel = new JLabel("用户名: ");
//        usernameField = new JTextField();
//        JLabel passwordLabel = new JLabel("密码: ");
//        passwordField = new JTextField();
//        JLabel quantityLabel = new JLabel("购买数量: ");
//        quantityField = new JTextField();
//        JButton loginButton = new JButton("登录");
//        JButton registerButton = new JButton("注册");
//
//        inputPanel.add(usernameLabel);
//        inputPanel.add(usernameField);
//        inputPanel.add(passwordLabel);
//        inputPanel.add(passwordField);
//        inputPanel.add(quantityLabel);
//        inputPanel.add(quantityField);
//        inputPanel.add(loginButton);
//        inputPanel.add(registerButton);

//        add(inputPanel, BorderLayout.CENTER);
//
//        // Info area to show balance and other details
//        infoArea = new JTextArea();
//        infoArea.setEditable(false);
//        add(new JScrollPane(infoArea), BorderLayout.SOUTH);
//
//        // Action Listeners for buttons
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String username = usernameField.getText();
//                String password = passwordField.getText();
//                if (userController.login(username, password)) {
//                    User user = userController.getUserInfo(username);
//                    infoArea.setText("登录成功!\n账户余额: " + user.getAccountBalance() + "\n可购买电影票。");
//                } else {
//                    JOptionPane.showMessageDialog(UserView.this, "登录失败！用户名或密码错误");
//                }
//            }
//        });
//
//        registerButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String username = usernameField.getText();
//                String password = passwordField.getText();
//                if (userController.register(username, password)) {
//                    JOptionPane.showMessageDialog(UserView.this, "注册成功！");
//                } else {
//                    JOptionPane.showMessageDialog(UserView.this, "用户名已存在！");
//                }
//            }
//        });

        // Create Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 2, 5, 5));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 100, 5));
        // Menu Buttons
        JButton showCinemasButton = new JButton("查看所有影厅");
        showCinemasButton.setFont(new Font("T", Font.BOLD, 20));
        JButton showMoviesButton = new JButton("查看所有电影");
        showMoviesButton.setFont(new Font("T", Font.BOLD, 20));
        JButton showShowingsButton = new JButton("查看所有放映场次");
        showShowingsButton.setFont(new Font("T", Font.BOLD, 20));
        JButton displaySeatNumberBySeatIdBtn = new JButton("显示场次剩余座位");
        displaySeatNumberBySeatIdBtn.setFont(new Font("T", Font.BOLD, 20));
        JButton buyTicketButton = new JButton("购买电影票");
        buyTicketButton.setFont(new Font("T", Font.BOLD, 20));
        JButton refundTicketBtn=new JButton("退票");
        refundTicketBtn.setFont(new Font("T", Font.BOLD, 20));
        JButton showHistoryButton = new JButton("查看历史订单");
        showHistoryButton.setFont(new Font("T", Font.BOLD, 20));
        JButton manageAccountButton = new JButton("显示账户余额");
        manageAccountButton.setFont(new Font("T", Font.BOLD, 20));
        JButton logoutButton = new JButton("退出登录");
        logoutButton.setFont(new Font("T", Font.BOLD, 20));

        menuPanel.add(buyTicketButton);
        menuPanel.add(refundTicketBtn);
        menuPanel.add(showCinemasButton);
        menuPanel.add(showMoviesButton);
        menuPanel.add(showShowingsButton);
        menuPanel.add(displaySeatNumberBySeatIdBtn);
        menuPanel.add(showHistoryButton);
        menuPanel.add(manageAccountButton);
        menuPanel.add(logoutButton);

        // 添加时间显示
        TimeDisplayUtil.addTimeDisplay(this);

        add(menuPanel);

        // Action Listeners for Menu Buttons
        displaySeatNumberBySeatIdBtn.addActionListener(e ->getShowingID());
        refundTicketBtn.addActionListener(e ->refundTicket());
        showCinemasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllCinemas();
            }
        });

        showMoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllMovies();
            }
        });

        showShowingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllShowings();
            }
        });

        buyTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyTicket();
            }
        });

        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayHistory();
            }
        });

        manageAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageAccount();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        setVisible(true);
    }

    private void refundTicket() {

        JTextField showingIdField = new JTextField();
        JTextField seatNumberField = new JTextField();
        Object[] message = {
                "场次ID:", showingIdField,
                "退选的座位:", seatNumberField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "退票", JOptionPane.OK_CANCEL_OPTION);
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
                JOptionPane.showMessageDialog(this, "电影状态不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            TicketService ticketService=new TicketServiceImpl();
            try {
                ticketService.refundTicket(user, showingId, seatNumber);

            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    // 显示场次剩余座位
    private void getShowingID() {
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
        return ;
    }

    // 显示所有影厅
    private void displayAllCinemas() {
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

    // Method to display all movies
    private void displayAllMovies() {
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

    // Method to display all showings (movie sessions)
    // 显示所有放映场次

    private void displayAllShowings() {
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
        ShowingDAO showingDAO = new ShowingDAOimpl();
        ShowingServiceImpl showingService = new ShowingServiceImpl();
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

    // Method for purchasing tickets
    private void buyTicket() {
        // Implement ticket purchasing functionality
        //
        JFrame frame = new JFrame("观影购票");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        String[] columnNames = {"放映ID", "影院ID", "电影名称","电影简介","开始时间","票价","VIP票价","是否购票"};

       DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        JTable table = new JTable(model);
        table.setFont(new Font("text", Font.PLAIN, 15 ));
        table.setRowHeight(35);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN );
        table.setFont(new Font("text", Font.PLAIN, 12 ));

        TicketService  ticketService=new TicketServiceImpl();
        ShowingServiceImpl showingService = new ShowingServiceImpl();
        ShowingDAO showingDAO = new ShowingDAOimpl();
        try {
            List<Showing> showings = showingDAO.getAllShowings();
            MovieDAO movieDAO = new MovieDAOimpl();
            if (showings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "暂无放映信息！");
                return;
            }
            for (Showing showing : showings) {
                JButton purchse= new JButton("购买");
                table.getColumnModel().getColumn(7).setCellEditor(new MyRender(purchse));//设置编辑器
                table.getColumnModel().getColumn(7).setCellRenderer(new MyRender(purchse) );
                purchse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        JComboBox<String> seatNumber = new JComboBox<>();
                        SeatDAOimpl seatDAO = new SeatDAOimpl();
                        List<Seat> seats=seatDAO.getAvailableSeatsByShowingId(showing.getShowingId());
                        if(seats.isEmpty())
                        {
                            JOptionPane.showMessageDialog(frame, "暂无可选座位！");
                        }
                        //所有座位添加到文本框当中
                        for (Seat seat : seats) {
                            seatNumber.addItem(seat.getSeatNumber());
                        }
                        JTextField  seatTypes=new JTextField();;
//                        seatTypes.setSize(10,15);
                        seatTypes.setEditable(false);
                        JTextField seatValue=new JTextField();
                        seatValue.setEditable(false);
//                        seatValue.setSize(10,15);
                        JTextField seatPrice=new JTextField();
                        seatPrice.setEditable(false);
//                        seatPrice.setSize(10,15);
                        if(!seats.isEmpty())
                        {
                            Seat seat=seats.get(0);
                            seatTypes.setText(seat.getSeat_type()==1?"VIP座位":"普通座位");
                            seatValue.setText(seat.isAvailable()==1?"可选":"不可选");
                            double price=seat.getSeat_type()==1?showing.getPrice()+15:showing.getPrice();
                            seatPrice.setText(String.valueOf(price));
                        }
                        seatNumber.addItemListener(new ItemListener() {
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                // 当选项改变时，更新JLabel的文本
                                if (e.getStateChange() == ItemEvent.SELECTED) {
                                    seatTypes.setText(seats.get(seatNumber.getSelectedIndex()).getSeat_type()==1?"VIP座位":"普通座位");
                                    seatValue.setText(seats.get(seatNumber.getSelectedIndex()).isAvailable()==1?"可选":"不可选");
                                    double price=seats.get(seatNumber.getSelectedIndex()).getSeat_type()==1?showing.getPrice()+15:showing.getPrice();
                                    seatPrice.setText(String.valueOf(price));
                                }
                            }
                        });


                        Object[] message = {
                                "座位号:", seatNumber,
                                "座位属性:", seatTypes,
                                "座位状态",seatValue,
                                "价格",seatPrice
                        };
                        int option = JOptionPane.showConfirmDialog(new Frame(), message, "购买影票", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            ResultSet resultSet = seatDAO.getSeatAvailableBySeatId(seats.get(seatNumber.getSelectedIndex()).getSeatId());
                            int isAvailable=0;
                            try {
                                if (resultSet.next()) {
                                    isAvailable = resultSet.getInt("is_available");
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            if(isAvailable==1)
                            {
                                ticketService.purchaseTicket(showing,user, seats.get(seatNumber.getSelectedIndex()));
                            }
                            else {
                                JOptionPane.showConfirmDialog(new Frame(), "手速太慢，票已经被抢走了~", "网上电影院购票", JOptionPane.YES_OPTION);
                            }
                        }
                    }
                });
                table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (value instanceof JTextArea) {
                            return (JTextArea) value;
                        }
                        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    }
                });
                JTextArea movieIntroductions=new JTextArea(movieDAO.getMovieIntroductionByID(showing.getMovieId()));
                movieIntroductions.setEditable(false);
                movieIntroductions.setLineWrap(true);
                Object[] row = new Object[]{showing.getShowingId(), showing.getCinemaId(),showingService.getMovieNameByShowingId(showing.getShowingId()),
                        movieIntroductions,showing.getStartTime(),showing.getPrice(),showing.getPrice()+15,purchse};
                    model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "获取放映信息失败。");
            return;
        }

        // 创建滚动面板，将表格放入其中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动面板添加到窗口中
        frame.add(scrollPane, BorderLayout.CENTER);

        // 显示窗口
        frame.setVisible(true);

    }

    //渲染 器 编辑器
    class MyRender extends AbstractCellEditor implements TableCellRenderer,ActionListener, TableCellEditor {
        private static final long serialVersionUID = 1L;
        private JButton button = null;

        public MyRender(JButton button) {
            this.button = button;

        }
        public Object getCellEditorValue() {
            return null;
        }

        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
            return button;
        }
        public void actionPerformed(ActionEvent e) {
            button.getAction();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
// TODO Auto-generated method stub
            return button;
        }
    }
    // Method to display user's purchase history
    private void displayHistory() {
        JFrame frame = new JFrame("历史订单");
        frame.setSize(600, 450); // 增加了窗口高度以适应新列
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        try {
            TicketDAO ticketDAO = new TicketDAOimpl();
            List<Ticket> tickets = ticketDAO.getTicketsByUserId(user.getUserId());
            if (tickets.isEmpty()) {
                JOptionPane.showConfirmDialog(this, "当前用户无购票记录", "电影院网上购票系统", JOptionPane.OK_OPTION);
            } else {
                // 添加"支付时间"到列名数组
                String[] columnNames = {"购票编号", "场次ID", "片名", "影厅名", "座位", "开始时间", "支付时间"};
                JTable table = new JTable(new DefaultTableModel(columnNames, 0));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setFont(new Font("text", Font.PLAIN, 15));

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                SeatServiceImpl seatService = new SeatServiceImpl();
                ShowingServiceImpl showingService = new ShowingServiceImpl();

                for (Ticket ticket : tickets) {
                    String seatNumber = seatService.getSeatNumberBySeatId(ticket.getSeatId());
                    String cinemaName = showingService.getCinemaNameByShowingId(ticket.getShowingId());
                    Timestamp startTime = showingService.getStartTimeByShowingId(ticket.getShowingId());
                    String movieName = showingService.getMovieNameByShowingId(ticket.getShowingId());

                    String paymentTime = ticket.getPaymentTime();

                    Object[] row = new Object[] {ticket.getTicketId(), ticket.getShowingId(), movieName, cinemaName, seatNumber, startTime, paymentTime};
                    model.addRow(row);
                }

                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.setVisible(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "查询购票信息时出现错误: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Method to manage user account (view and update balance)
    private void manageAccount() {
        double money = user.getAccountBalance();
        JOptionPane.showConfirmDialog(this, "账户余额为："+money, "账户余额", JOptionPane.YES_NO_OPTION);
    }

    // Logout method
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "是否确认退出登录?", "退出登录", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
           Main.getM().main();
        }
    }
//    public static void main(String[] args) {
//        UserController userController = new UserController();
//        new UserView(new User());
//    }
}
