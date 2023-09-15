package com.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import com.miu.cs.librarysystem.business.Address;
import com.miu.cs.librarysystem.business.LibraryMember;
import com.miu.cs.librarysystem.controller.ControllerInterface;
import com.miu.cs.librarysystem.controller.SystemController;
import com.miu.cs.librarysystem.system.LibWindow;
import com.miu.cs.librarysystem.system.TypographyUtils;
import com.miu.cs.librarysystem.system.Util;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class AddMemberPanel extends JPanel implements LibWindow {
  @Serial private static final long serialVersionUID = 7863919163615773327L;
  private boolean isInitialized = false;

  private JTextField txtFieldFirstName;
  private JTextField txtState;
  private JTextField txtZip;
  private JTextField txtFieldLastName;
  private JTextField txtCity;
  private JTextField txtFieldStreet;
  private JTextField txtFieldId;
  private JTextField txtTelephone;
  private JButton btnAdd;
  private JButton btnDelete;
  private JButton btnUpdate;

  private JFrame frame;
  private JTable table;
  ControllerInterface controller = new SystemController();
  private int selectedRow = -1;

  public AddMemberPanel() {
    init();
  }

  @Override
  public void init() {
    setLayout(new BorderLayout());
    JPanel titlePanel = new JPanel();
    add(titlePanel, BorderLayout.NORTH);
    titlePanel.setLayout(
        new FlowLayout(FlowLayout.CENTER, 5, TypographyUtils.H_PADDING_FROM_PANEL_HEADER));

    JLabel lblNewLabel = new JLabel("Table of Library Members");
    TypographyUtils.applyHeadingStyle(lblNewLabel);
    titlePanel.add(lblNewLabel);
    Object[] columnsObjects = {"ID", "First Name", "Last Name", "TEL", "Address"};
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(columnsObjects);
    Collection<LibraryMember> members = controller.allLibraryMembers();
    for (LibraryMember member : members) {
      model.addRow(
          new Object[] {
            member.getMemberId(),
            member.getFirstName(),
            member.getLastName(),
            member.getTelephone(),
            member.getAddress()
          });
    }

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel();
    add(centerPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel();

    btnAdd = new JButton("ADD");
    Util.addButtonHover(btnAdd);
    actionPanel.add(btnAdd, BorderLayout.EAST);
    actionPanel.add(btnAdd);

    btnDelete = new JButton("DELETE");
    Util.addButtonHover(btnDelete);
    actionPanel.add(btnDelete);

    btnUpdate = new JButton("UPDATE");
    Util.addButtonHover(btnUpdate);
    actionPanel.add(btnUpdate);

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

    actionPanel.setBounds(134, 235, 460, 40);
    actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 5));

    controlsPanel.setBounds(5, 5, 460, 219);
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
    centerPanel.add(actionPanel);

    JPanel tablePanel = new JPanel();
    tablePanel.setLayout(new FlowLayout(0, 0, 0));
    tablePanel.setPreferredSize(new Dimension(750, 275));
    tablePanel.setMaximumSize(new Dimension(750, 275));

    centerPanel.add(tablePanel);

    table =
        new JTable() {
          private static final long serialVersionUID = -5795502418632762890L;

          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setModel(model);
    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.LEFT);
    TableColumnModel colModel = table.getColumnModel();
    colModel.getColumn(4).setPreferredWidth(300);
    colModel.getColumn(3).setPreferredWidth(200);
    colModel.getColumn(2).setPreferredWidth(100);
    colModel.getColumn(1).setPreferredWidth(100);
    colModel.getColumn(0).setPreferredWidth(50);
    JScrollPane jScrollPane = new JScrollPane();
    jScrollPane.setViewportView(table);
    jScrollPane.setPreferredSize(new Dimension(750, 275));
    tablePanel.add(jScrollPane);

    // Delete button
    btnDelete.addActionListener(
        e -> {
          int count = table.getSelectedRowCount();
          if (count == 1) {
            selectedRow = table.getSelectedRow();

            String memberIdString = (String) table.getValueAt(selectedRow, 0);
            model.removeRow(selectedRow);
            controller.deleteMember(memberIdString);
            selectedRow = -1;
            clearText();
          } else if (count > 1) {
            JOptionPane.showMessageDialog(frame, "Please select single row", "", ERROR_MESSAGE);
          } else {
            JOptionPane.showMessageDialog(frame, "There is no row to delete", "", ERROR_MESSAGE);
          }
        });

    // on select table row
    table.addMouseListener(
        new MouseAdapter() {

          @Override
          public void mousePressed(MouseEvent e) {
            int count = table.getSelectedRowCount();
            if (count == 1) {
              selectedRow = table.getSelectedRow();
              System.out.println(model.getValueAt(selectedRow, 0));
              LibraryMember member =
                  controller.getLibraryMemberById((String) model.getValueAt(selectedRow, 0));
              txtCity.setText(member.getAddress().getCity());
              txtFieldFirstName.setText(member.getFirstName());
              txtFieldId.setText(member.getMemberId());
              txtFieldLastName.setText(member.getLastName());
              txtFieldStreet.setText(member.getAddress().getStreet());
              txtState.setText(member.getAddress().getState());
              txtTelephone.setText(member.getTelephone());
              txtZip.setText(member.getAddress().getZip());
            } else {
              clearText();
            }
            super.mouseClicked(e);
          }
        });

    // Add button
    btnAdd.addActionListener(
        (evt) -> {
          String idString = txtFieldId.getText();
          String firstNameString = txtFieldFirstName.getText();
          String lastNameString = txtFieldLastName.getText();
          String telephoneString = txtTelephone.getText() == null ? "N/A" : txtTelephone.getText();
          String streetString = txtFieldStreet.getText() == null ? "N/A" : txtFieldStreet.getText();
          String cityString = txtCity.getText() == null ? "N/A" : txtCity.getText();
          String stateString = txtState.getText() == null ? "N/A" : txtState.getText();
          String zipString = txtZip.getText() == null ? "N/A" : txtZip.getText();
          if (firstNameString.isEmpty() || lastNameString.isEmpty() || idString.isEmpty()) {
            JOptionPane.showMessageDialog(
                frame, "Invalid id or first name or last name", "", ERROR_MESSAGE);
            System.out.println("Invalid id or first name or last name");
            return;
          }
          List<String> memberStrings = controller.allMemberIds();
          if (memberStrings.contains(idString)) {
            JOptionPane.showMessageDialog(frame, "exist member id", "", ERROR_MESSAGE);
            System.out.println("exist member id");
            return;
          }
          Address newAddress = new Address(streetString, cityString, stateString, zipString);
          LibraryMember member =
              new LibraryMember(
                  idString, firstNameString, lastNameString, telephoneString, newAddress);
          controller.saveMember(member);
          JOptionPane.showMessageDialog(
              frame,
              "Add member successfully",
              "",
              INFORMATION_MESSAGE,
              new ImageIcon(System.getProperty("user.dir") + "/src/librarysystem/success.png"));
          Object[] rowData = {
            member.getMemberId(),
            member.getFirstName(),
            member.getLastName(),
            member.getTelephone(),
            member.getAddress()
          };
          model.addRow(rowData);
        });

    // Update button
    btnUpdate.addActionListener(
        (ActionEvent evt) -> {
          String idString = txtFieldId.getText();
          String firstNameString = txtFieldFirstName.getText();
          String lastNameString = txtFieldLastName.getText();
          String telephoneString = txtTelephone.getText() == null ? "N/A" : txtTelephone.getText();
          String streetString = txtFieldStreet.getText() == null ? "N/A" : txtFieldStreet.getText();
          String cityString = txtCity.getText() == null ? "N/A" : txtCity.getText();
          String stateString = txtState.getText() == null ? "N/A" : txtState.getText();
          String zipString = txtZip.getText() == null ? "N/A" : txtZip.getText();
          if (firstNameString.isEmpty() || lastNameString.isEmpty() || idString.isEmpty()) {
            JOptionPane.showMessageDialog(
                frame, "Invalid id or first name or last name", "", ERROR_MESSAGE);
            System.out.println("Invalid id or first name or last name");
            return;
          }

          Address newAddress = new Address(streetString, cityString, stateString, zipString);
          LibraryMember member =
              new LibraryMember(
                  idString, firstNameString, lastNameString, telephoneString, newAddress);
          controller.saveMember(member);
          JOptionPane.showMessageDialog(
              frame,
              "Update member successfully",
              "",
              INFORMATION_MESSAGE,
              new ImageIcon(System.getProperty("user.dir") + "/src/librarysystem/success.png"));
          model.setValueAt(member.getMemberId(), selectedRow, 0);
          model.setValueAt(member.getFirstName(), selectedRow, 1);
          model.setValueAt(member.getLastName(), selectedRow, 2);
          model.setValueAt(member.getTelephone(), selectedRow, 3);
          model.setValueAt(member.getAddress(), selectedRow, 4);
          clearText();
        });
  }

  void clearText() {
    txtCity.setText("");
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
