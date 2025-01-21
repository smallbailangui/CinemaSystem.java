package main.java.GUI;

import main.java.controller.AdminController;
import main.java.model.User;
import main.java.service.*;
import main.java.controller.CinemaController;
import main.java.model.Admin;
import main.java.service.impl.LoginServiceImpl;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Main {
    private static Main  m;

    private   Main()
    {    }
    public static Main getM()
    {
        if(m==null)
        {
            m=new Main();
        }
        return m;
    }
    //    public static void main(String[] args) {
//        // 创建 JFrame 对象
    public void main()
    {
        JFrame frame = new JFrame("网上电影院购票");
        frame.setLayout(null);//设置自定义修改(绝对布局）
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //关窗口
        frame.setSize(900,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        // 创建标签
        JLabel label = new JLabel("电影院网上购票系统");

        frame.getContentPane().add(label); // 将标签添加到窗口
//        label.setLayout(null);
        label.setBounds(290, -250, 900, 600);
        Font title = new Font("楷体", Font.BOLD, 30);//字体，叫Title
        Font text = new Font("Text", Font.PLAIN, 20);//字体，叫Text
        label.setFont(title);
        //登录
        JLabel zhangHao = new JLabel("账号：");
//        zhangHao.setLayout(null);
        zhangHao.setFont(text);

        zhangHao.setBounds(200, -150, 900, 600);
        frame.getContentPane().add(zhangHao);

        ////密码
        JLabel miMa = new JLabel("密码：");
        miMa.setLayout(null);
        miMa.setFont(text);
        miMa.setBounds(200, -50, 900, 600);
        frame.getContentPane().add(miMa);
        //输入文本框
        ////账号
        JTextField account = new JTextField(30);
        account.setFont(text);
        account.setBounds(260, 135, 310, 40);

        frame.add(account);
        /////密码
        JPasswordField password = new JPasswordField(30);
        password.setFont(text);
        password.setBounds(260, 235, 310, 40);
        frame.add(password);
        // 创建按钮
        JButton login = new JButton("登入");
        login.setFont(new Font("T", Font.BOLD, 20));
        login.setBounds(280,320,80,50);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                CinemaController t=new CinemaController();;
//                t.handleUserLogin(account.getText(),password.getText());
                String id = account.getText();
                String pwd=new String(password.getPassword());
                User user=CinemaController.getInstance().handleUserLogin(id,pwd);
                if(user!=null)
                {
                    frame.dispose();
//                    Login.getLi().secssLogin();
                    JOptionPane.showConfirmDialog(new Frame(), "登入成功", "网上电影院购票", JOptionPane.YES_OPTION);
                    //登入
                    new UserView(user);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "用户名或密码错误", "网上电影院购票", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        frame.getContentPane().add(login);

        JButton registerIn = new JButton("注册");
        registerIn.setFont(new Font("T", Font.BOLD, 20));
        registerIn.setBounds(440,320,80,50);
        registerIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Register.getRegister().main();//注册
            }
        });
        frame.getContentPane().add(registerIn);
        JButton adminBt = new JButton("管理员登入");
        adminBt.setUI(new BasicButtonUI());
        adminBt.setFont(new Font("Text", Font.BOLD, 15));
        adminBt.setBounds(490,280,80,25);

        adminBt.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                adminBt.setForeground(Color.blue);
            }
            public void mouseExited(MouseEvent e) {
                adminBt.setForeground(Color.black);
            }
        });
        adminBt.setBorder(null) ;
//        adminBt.setBackground(Color.white);
        adminBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                AdminLogIn.getAdminLogIn().main();
            }
        });
        frame.getContentPane().add(adminBt);
        // 设置窗口可见
        frame.setVisible(true);
//    }
    }
//    public static void main(String[] args) {
//        Main.getM().main();
//    }
}

class Register {
    private static Register register;

    private Register() {
    }
    public static Register getRegister() {
        if (register == null) {
            register = new Register();
        }
        return register;
    }
    public void main()
    {
        JFrame frame = new JFrame("网上电影院购票");
        frame.setLayout(null);//设置自定义修改(绝对布局）
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //关窗口
        frame.setSize(900,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        // 创建标签
        JLabel label = new JLabel("电影院网上购票系统");

        frame.getContentPane().add(label); // 将标签添加到窗口
//        label.setLayout(null);
        label.setBounds(290, -250, 900, 600);
        Font title = new Font("楷体", Font.BOLD, 30);//字体，叫Title
        Font text = new Font("Text", Font.PLAIN, 20);//字体，叫Text
        label.setFont(title);
        //登录
        JLabel zhangHao = new JLabel("账号：");
//        zhangHao.setLayout(null);
        zhangHao.setFont(text);

        zhangHao.setBounds(210, -150, 900, 600);
        frame.getContentPane().add(zhangHao);

        ////密码
        JLabel miMa1 = new JLabel("密码：");
        miMa1.setLayout(null);
        miMa1.setFont(text);
        miMa1.setBounds(210, -50, 900, 600);
        frame.getContentPane().add(miMa1);
        JLabel miMa2 = new JLabel("再次输入密码：");
        miMa2.setLayout(null);
        miMa2.setFont(text);
        miMa2.setBounds(140, 50, 900, 600);
        frame.getContentPane().add(miMa2);
        //输入文本框
        ////账号
        JTextField account = new JTextField(30);
        account.setFont(text);
        account.setBounds(270, 135, 310, 40);

        frame.add(account);
        /////密码
        JPasswordField password1 = new JPasswordField(30);
        password1.setFont(text);
        password1.setBounds(270, 235, 310, 40);
        frame.add(password1);
        JPasswordField password2 = new JPasswordField(30);
        password2.setFont(text);
        password2.setBounds(270, 335, 310, 40);
        frame.add(password2);
        // 创建按钮
        JButton registerIn = new JButton("注册");
        registerIn.setFont(new Font("T", Font.BOLD, 20));
        registerIn.setBounds(370,400,80,50);
        registerIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!password1.getText().equals(password2.getText()))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "两次密码输入不一致！", "网上电影院购票", JOptionPane.ERROR_MESSAGE);
                }
                else if(account.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(new JFrame(), "账号不能为空！", "网上电影院购票", JOptionPane.ERROR_MESSAGE);
                }
                else if(password1.getText().equals(password2.getText())&&password1.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(new JFrame(), "密码不能为空！", "网上电影院购票", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String id=account.getText();
                    String pwd=new String(password1.getPassword());
                    try {
                        User user=CinemaController.getInstance().userRegister(id,pwd);
                        if(user!=null)
                        {
                            frame.dispose();
                            //注册
                            new UserView(user);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        frame.getContentPane().add(registerIn);
        // 设置窗口可见
        frame.setVisible(true);
//    }
    }
}
class AdminLogIn
{
    private static AdminLogIn adminLogIn;

    private AdminLogIn() {
    }
    public static AdminLogIn getAdminLogIn() {
        if (adminLogIn == null) {
            adminLogIn = new AdminLogIn();
        }
        return adminLogIn;
    }
    public void main()
    {
        JFrame frame = new JFrame("网上电影院购票");
        frame.setLayout(null);//设置自定义修改(绝对布局）
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //关窗口
        frame.setSize(900,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        // 创建标签
        JLabel label = new JLabel("管理员登入");

        frame.getContentPane().add(label); // 将标签添加到窗口
        label.setBounds(380, -250, 900, 600);
        Font title = new Font("Title", Font.BOLD, 30);//字体，叫Title
        Font text = new Font("Text", Font.PLAIN, 20);//字体，叫Text
        label.setFont(title);

        JLabel zhangHao = new JLabel("账号：");
        zhangHao.setFont(text);

        zhangHao.setBounds(250, -150, 900, 600);
        frame.getContentPane().add(zhangHao);

        ////密码
        JLabel miMa = new JLabel("密码：");
        miMa.setLayout(null);
        miMa.setFont(text);
        miMa.setBounds(250, -50, 900, 600);
        frame.getContentPane().add(miMa);
        //输入文本框
        ////账号
        JTextField account = new JTextField(30);
        account.setFont(text);
        account.setBounds(310, 135, 310, 40);

        frame.add(account);
        /////密码
        JPasswordField password = new JPasswordField(30);
        password.setFont(text);
        password.setBounds(310, 235, 310, 40);
        frame.add(password);
        // 创建按钮
        JButton login = new JButton("登入");
        login.setFont(new Font("T", Font.BOLD, 20));
        login.setBounds(410,320,80,50);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = account.getText();
                String pwd=new String(password.getPassword());
                if(CinemaController.getInstance().handleAdminLogin(id,pwd))
                {
                    LoginService loginService=new LoginServiceImpl();
                    try {
                        Admin admin = loginService.adminLogin(id, pwd);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.dispose();
                    JOptionPane.showConfirmDialog(new Frame(), "登入成功", "网上电影院购票", JOptionPane.YES_OPTION);
                    AdminController adminController = new AdminController();
                    new AdminView(adminController);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "用户名或密码错误", "网上电影院购票", JOptionPane.ERROR_MESSAGE);

                }
                //登入
            }
        });
        frame.getContentPane().add(login);
        frame.setVisible(true);
    }
}