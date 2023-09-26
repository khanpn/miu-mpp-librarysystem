package edu.miu.cs.librarysystem.util;

import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class UiUtils {

  public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  public static void centerFrameOnDesktop(Component f) {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    int height = toolkit.getScreenSize().height;
    int width = toolkit.getScreenSize().width;
    int frameHeight = f.getSize().height;
    int frameWidth = f.getSize().width;
    f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
  }

  public static JButton buttonImage(
      String srcImage, String btnText, int iconWidth, int iconHeight) {
    ImageIcon icon = new ImageIcon(getImage(srcImage));
    Image scaledImage =
        icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JButton btn = new JButton(btnText, scaledIcon);
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setHorizontalTextPosition(SwingConstants.RIGHT);
    btn.setVerticalTextPosition(SwingConstants.CENTER);
    return btn;
  }

  public static Image getImage(String path) {
    try {
      return ImageIO.read(Objects.requireNonNull(UiUtils.class.getResourceAsStream(path)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void addButtonHover(JButton btn) {
    Color color = btn.getBackground();
    btn.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(Color.decode("#FFC107"));
          }

          // when mouse exit the button will return to previous color
          public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(color);
          }
        });
  }
}
