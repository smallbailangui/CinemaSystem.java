package main.java.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
*
*
*
*/
public class TimeDisplayUtil {

    public static void addTimeDisplay(JFrame frame) {
        // 创建时间标签并设置字体
        JLabel timeLabel = new JLabel("", SwingConstants.RIGHT);
        timeLabel.setFont(new Font("Text", Font.PLAIN, 20));

        // 创建时间面板，并设置为右对齐布局
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        timePanel.add(timeLabel);

        // 添加时间面板到主面板的南部
        frame.add(timePanel, BorderLayout.SOUTH);

        // 使用 Timer 定期更新时间
        Timer timer = new Timer(1, (ActionEvent e) -> {
            String dateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            timeLabel.setText(dateTimeString);
        });
        timer.start();
    }
}