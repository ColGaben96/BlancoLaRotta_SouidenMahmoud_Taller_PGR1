package co.edu.unbosque.view;

import javax.swing.*;

public class Alerts {
    public void output(String message, String title, int AlertType) {
        JOptionPane.showMessageDialog(null,message,title,AlertType,null);
    }
}
