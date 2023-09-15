package com.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.LibraryMember;
import com.miu.cs.librarysystem.controller.ControllerInterface;
import com.miu.cs.librarysystem.controller.SystemController;
import com.miu.cs.librarysystem.system.LibWindow;
import com.miu.cs.librarysystem.system.TypographyUtils;
import com.miu.cs.librarysystem.system.Util;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchMemberCheckoutRecordPanel extends JPanel implements LibWindow {
  private static final long serialVersionUID = -4264885214167193462L;
  private boolean isInitialized = false;

  private JTextField txtFieldFirstName;
  private JTextField txtState;
  private JTextField txtZip;
  private JTextField txtFieldLastName;
  private JTextField txtCity;
  private JTextField txtFieldStreet;
  private JTextField txtFieldId;
  private JTextField txtTelephone;

  DefaultTableModel model = new DefaultTableModel();
  ControllerInterface ci = new SystemController();

  public SearchMemberCheckoutRecordPanel() {
    init();
  }

  @Override
  public void init() {
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    add(panel, BorderLayout.NORTH);
    panel.setLayout(
        new FlowLayout(FlowLayout.CENTER, 5, TypographyUtils.H_PADDING_FROM_PANEL_HEADER));

    JLabel lblNewLabel = new JLabel("Member Search");
    TypographyUtils.applyHeadingStyle(lblNewLabel);
    panel.add(lblNewLabel);

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel();
    add(centerPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel();
    actionPanel.setBounds(154, 231, 430, 39);

    JButton searchMemberButton = new JButton("SEARCH");
    Util.addButtonHover(searchMemberButton);
    actionPanel.add(searchMemberButton);
    searchMemberButton.setHorizontalAlignment(SwingConstants.RIGHT);

    JButton printRecordButton = new JButton("PRINT RECORD");
    Util.addButtonHover(printRecordButton);
    actionPanel.add(printRecordButton);

    JButton clearFieldsButton = new JButton("CLEAR FIELDS");
    Util.addButtonHover(clearFieldsButton);
    actionPanel.add(clearFieldsButton);

    JPanel controlsPanel = new JPanel();
    JLabel lblMemberId = new JLabel("ID:");
    controlsPanel.add(lblMemberId);

    txtFieldId = new JTextField();
    controlsPanel.add(txtFieldId);
    txtFieldId.setColumns(10);

    JLabel lblFirstName = new JLabel("First Name:");
    controlsPanel.add(lblFirstName);

    txtFieldFirstName = new JTextField();
    controlsPanel.add(txtFieldFirstName);
    txtFieldFirstName.setColumns(10);

    JLabel lblLastName = new JLabel("Last Name:");
    controlsPanel.add(lblLastName);

    txtFieldLastName = new JTextField();
    controlsPanel.add(txtFieldLastName);
    txtFieldLastName.setColumns(10);

    JLabel lblStreet = new JLabel("Street:");
    controlsPanel.add(lblStreet);

    txtFieldStreet = new JTextField();
    controlsPanel.add(txtFieldStreet);
    txtFieldStreet.setColumns(10);

    JLabel lblCity = new JLabel("City:");
    controlsPanel.add(lblCity);

    txtCity = new JTextField();
    controlsPanel.add(txtCity);
    txtCity.setColumns(10);

    JLabel lblState = new JLabel("State:");
    controlsPanel.add(lblState);

    txtState = new JTextField();
    controlsPanel.add(txtState);
    txtState.setColumns(10);

    JLabel lblZip = new JLabel("Zip:");
    controlsPanel.add(lblZip);

    txtZip = new JTextField();
    controlsPanel.add(txtZip);
    txtZip.setColumns(10);

    JLabel lblTelephone = new JLabel("Telephone:");
    controlsPanel.add(lblTelephone);

    txtTelephone = new JTextField();
    controlsPanel.add(txtTelephone);
    txtTelephone.setColumns(10);

    actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 4));

    //    controlsPanel.setBounds(5, 5, 460, 219);
    controlsPanel.setLayout(new GridLayout(0, 2, 0, 0));

    //    centerPanel.setLayout(null);
    //    centerPanel.add(actionPanel);
    //    centerPanel.add(controlsPanel);

    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    controlsPanel.setPreferredSize(new Dimension(460, 219));
    controlsPanel.setMaximumSize(new Dimension(460, 219));

    actionPanel.setPreferredSize(new Dimension(460, 40));
    actionPanel.setMaximumSize(new Dimension(460, 40));

    centerPanel.add(controlsPanel);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    centerPanel.add(actionPanel);

    searchMemberButton.addActionListener(
        (evt) -> {
          String memberId = txtFieldId.getText();
          if (memberId.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please Enter Member ID to search", "", ERROR_MESSAGE);
            return;
          }

          LibraryMember member = ci.findMemberById(memberId);

          if (member == null) {
            JOptionPane.showMessageDialog(
                this, "Member with ID " + memberId + " not found", "", ERROR_MESSAGE);
            return;
          }

          txtFieldId.setEnabled(false);

          txtFieldFirstName.setText(member.getFirstName());
          txtFieldLastName.setText(member.getLastName());
          txtTelephone.setText(member.getTelephone());
          txtFieldStreet.setText(member.getAddress().getStreet());
          txtZip.setText(member.getAddress().getZip());
          txtCity.setText(member.getAddress().getCity());
          txtState.setText(member.getAddress().getState());
        });

    printRecordButton.addActionListener(
        e -> {
          String memberId = txtFieldId.getText();
          if (memberId.isEmpty() || txtFieldId.isEnabled()) {
            JOptionPane.showMessageDialog(
                this, "Please Enter Member ID to search", "", ERROR_MESSAGE);
            return;
          }

          LibraryMember member = ci.findMemberById(memberId);

          if (member == null) {
            JOptionPane.showMessageDialog(
                this, "Member with ID " + memberId + " not found", "", ERROR_MESSAGE);
            return;
          }

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
          System.out.printf(
              "%-10s\t%-10s\t%-8s\t%-6s\t%-20s\t%s\n",
              "CHECKOUT", "DUE", "ISBN", "COPIES", "TITLE", "MEMBER");
          member
              .getCheckoutRecords()
              .forEach(
                  record -> {
                    record
                        .getEntries()
                        .forEach(
                            entry -> {
                              Book b = entry.getCopy().getBook();
                              LibraryMember m = record.getMember();
                              String checkoutDate = entry.getCheckoutDate().format(formatter);
                              String dueDate = entry.getDueDate().format(formatter);
                              System.out.printf(
                                  "%-10s\t%-10s\t%-8s\t%-6s\t%-20s\t%s\n",
                                  checkoutDate,
                                  dueDate,
                                  b.getIsbn(),
                                  b.getNumCopies(),
                                  b.getTitle(),
                                  m.getFullName());
                            });
                  });
        });

    clearFieldsButton.addActionListener((evt) -> clearText());
  }

  void clearText() {
    txtCity.setText("");
    txtFieldId.setEnabled(true);
    txtFieldFirstName.setText("");
    txtFieldId.setText("");
    txtFieldLastName.setText("");
    txtFieldStreet.setText("");
    txtState.setText("");
    txtTelephone.setText("");
    txtZip.setText("");
  }

  @Override
  public boolean isInitialized() {
    return isInitialized;
  }

  @Override
  public void setInitialized(boolean val) {
    isInitialized = val;
  }
}
