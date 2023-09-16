package edu.miu.cs.librarysystem.util;

import java.awt.*;
import javax.swing.*;

public final class TypographyUtils {

  public static final int HEADING_1 = 16;

  public static final int H_PADDING_FROM_PANEL_HEADER = 20;

  private TypographyUtils() {}

  public static void applyHeadingStyle(JComponent component) {
    applyStyle(component, HEADING_1, Colors.PRIMARY);
  }

  public static void applyDangerStyle(JComponent component) {
    applyStyle(component, component.getFont().getSize(), Colors.DANGER);
  }

  private static void applyStyle(JComponent component, int size, Color color) {
    Font font = new Font(component.getFont().getName(), component.getFont().getStyle(), size);
    component.setFont(font);
    component.setForeground(color);
  }
}
