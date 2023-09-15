package com.miu.cs.librarysystem.system;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;

public class Util {

  public static Font makeSmallFont(Font f) {
    return new Font(f.getName(), f.getStyle(), (f.getSize() - 2));
  }

  public static void adjustLabelFont(JLabel label, Color color, boolean bigger) {
    if (bigger) {
      Font f =
          new Font(
              label.getFont().getName(),
              label.getFont().getStyle(),
              (label.getFont().getSize() + 2));
      label.setFont(f);
    } else {
      Font f =
          new Font(
              label.getFont().getName(),
              label.getFont().getStyle(),
              (label.getFont().getSize() - 2));
      label.setFont(f);
    }
    label.setForeground(color);
  }

  /** Sorts a list of numeric strings in natural number order */
  public static List<String> numericSort(List<String> list) {
    Collections.sort(list, new NumericSortComparator());
    return list;
  }

  static class NumericSortComparator implements Comparator<String> {
    @Override
    public int compare(String s, String t) {
      if (!isNumeric(s) || !isNumeric(t))
        throw new IllegalArgumentException("Input list has non-numeric characters");
      int sInt = Integer.parseInt(s);
      int tInt = Integer.parseInt(t);
      if (sInt < tInt) return -1;
      else if (sInt == tInt) return 0;
      else return 1;
    }
  }

  public static boolean isNumeric(String s) {
    if (s == null) return false;
    try {
      Integer.parseInt(s);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static void centerFrameOnDesktop(Component f) {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    int height = toolkit.getScreenSize().height;
    int width = toolkit.getScreenSize().width;
    int frameHeight = f.getSize().height;
    int frameWidth = f.getSize().width;
    f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
  }

  public static JButton buttonImage(
      String sourseImage, String btnText, int iconWidth, int iconHeight) {
    ImageIcon icon = new ImageIcon(sourseImage);
    Image scaledImage =
        icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JButton btn = new JButton(btnText, scaledIcon);
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setHorizontalTextPosition(SwingConstants.RIGHT);
    btn.setVerticalTextPosition(SwingConstants.CENTER);
    return btn;
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
