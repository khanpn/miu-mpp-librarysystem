package com.miu.cs.librarysystem.system;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TestFrame extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JTextField textField;

  /** Launch the application. */
  public static void main(String[] args) {
    EventQueue.invokeLater(
        new Runnable() {
          public void run() {
            try {
              TestFrame frame = new TestFrame();
              frame.setVisible(true);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }

  /** Create the frame. */
  public TestFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(new GridLayout(0, 1, 0, 0));

    JPanel panel_1 = new JPanel();
    contentPane.add(panel_1);
    panel_1.setLayout(new BorderLayout(5, 5));

    JPanel panel = new JPanel();
    panel_1.add(panel);
    panel.setLayout(new GridLayout(0, 1, 1, 1));

    JPanel panel_2 = new JPanel();
    panel.add(panel_2);
    panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    textField = new JTextField();
    textField.setColumns(45);
    panel_2.add(textField);

    JPanel panel_3 = new JPanel();
    panel.add(panel_3);
    panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    JLabel lblNewLabel = new JLabel("New label");
    panel_3.add(lblNewLabel);
  }
}
