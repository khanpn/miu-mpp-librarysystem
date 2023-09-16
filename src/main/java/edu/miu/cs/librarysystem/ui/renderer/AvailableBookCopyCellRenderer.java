package edu.miu.cs.librarysystem.ui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AvailableBookCopyCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = -5215765312776674501L;

  @Override
  public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

    Component c =
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    if (table.getColumnName(column).toLowerCase().startsWith("available")) {
      Integer availableCopy = (Integer) table.getValueAt(row, column);
      Integer totalCopy = (Integer) table.getValueAt(row, column + 1);
      if (availableCopy < totalCopy) {
        c.setForeground(Color.YELLOW.darker());
        c.setFont(new Font(c.getFont().getName(), Font.BOLD, c.getFont().getSize()));
      }
      if (availableCopy == 0) {
        c.setForeground(Color.RED.darker());
        c.setFont(new Font(c.getFont().getName(), Font.BOLD, c.getFont().getSize()));
      }
    } else {
      c.setForeground(null);
    }
    return c;
  }
}
