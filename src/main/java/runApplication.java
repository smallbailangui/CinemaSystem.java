package main.java;
import main.java.GUI.*;

public class runApplication {
    public static void main(String[] args) throws Exception {
        //下面是改用单例模式设计的用户界面
//        CinemaController.getInstance().start();
        //new CinemaController().start();
        Main.getM().main();
    }
}
