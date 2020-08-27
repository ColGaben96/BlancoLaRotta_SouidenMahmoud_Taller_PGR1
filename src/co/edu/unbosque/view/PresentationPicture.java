package co.edu.unbosque.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Gabriel Blanco
 * @version 1.0
 */
public class PresentationPicture extends JPanel{
    private BufferedImage image = null;
    private JLabel picLabel = null;

    /**
     * @author Gabriel Blanco
     */
    public PresentationPicture() {
        load();
        addComponents();
    }

    /**
     * @author Gabriel Blanco
     */
    private void load() {
        try {
            image = ImageIO.read(new File("./sysfiles/imgs/PresentationPicPGR1.png"));
        } catch (IOException io) {
          System.err.println("Can't find file!");
        }
        picLabel = new JLabel(new ImageIcon(image));
        setLayout(new BorderLayout());
    }

    /**
     * @author Gabriel Blanco
     */
    private void addComponents() {
        add(picLabel, BorderLayout.CENTER);
    }
}
