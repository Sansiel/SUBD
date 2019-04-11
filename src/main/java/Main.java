import view.MainView;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainView mainView = new MainView();
                mainView.setVisible();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
