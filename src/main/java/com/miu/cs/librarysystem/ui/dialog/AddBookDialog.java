package com.miu.cs.librarysystem.ui.dialog;

import com.miu.cs.librarysystem.system.TypographyUtils;
import com.miu.cs.librarysystem.system.Util;

import java.awt.event.*;
import javax.swing.*;

public class AddBookDialog extends JDialog {
  private JPanel contentPane;
  private JPanel topPanel;
  private JLabel headingLabel;
  private JPanel centerPanel;
  private JPanel formPanel;
  private JPanel isbnPanel;
  private JLabel isbnLabel;
  private JTextField isbnField;
  private JPanel titlePanel;
  private JLabel titleLabel;
  private JTextField titleField;
  private JPanel authorsPanel;
  private JLabel authorsLabel;
  private JTextField authorsField;
  private JPanel maxCheckoutLengthPanel;
  private JLabel maxCheckoutLengthLabel;
  private JPanel numOfCopiesPanel;
  private JLabel numOfCopiesLabel;
  private JTextField numOfCopiesField;
  private JPanel buttonsPanel;
  private JButton addBookButton;
  private JButton cancelButton;
  private JComboBox maxCheckoutLengthSelect;
  private JPanel headingTopPanel;
  private JPanel headingCenterPanel;

  public AddBookDialog() {
    initialize();
  }

  private void initialize() {
    Util.addButtonHover(addBookButton);
    Util.addButtonHover(cancelButton);
    TypographyUtils.applyHeadingStyle(headingLabel);
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(addBookButton);

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            onCancel();
          }
        });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            onCancel();
          }
        },
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    cancelButton.addActionListener((event) -> onCancel());
  }

  private void onOK() {
    // add your code here
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }
}
