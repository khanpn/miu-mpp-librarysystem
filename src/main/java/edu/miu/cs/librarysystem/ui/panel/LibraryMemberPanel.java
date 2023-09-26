package edu.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import edu.miu.cs.librarysystem.business.Address;
import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.store.action.librarymember.LibraryMemberDeleteAction;
import edu.miu.cs.librarysystem.store.action.librarymember.LibraryMemberGetAllMemberAction;
import edu.miu.cs.librarysystem.store.action.librarymember.LibraryMemberSaveAction;
import edu.miu.cs.librarysystem.store.core.Dispatcher;
import edu.miu.cs.librarysystem.store.core.StateChangeEvent;
import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.LibraryMemberState;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.UiUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class LibraryMemberPanel extends JPanel
    implements LibPanel, StateChangeListener<LibraryMemberState> {
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
  private int selectedRow = -1;

  private DefaultTableModel tableModel;

  private List<LibraryMember> libraryMembers;

  public void init() {
    setLayout(new BorderLayout());
    JPanel titlePanel = new JPanel();
    add(titlePanel, BorderLayout.NORTH);
    titlePanel.setLayout(
        new FlowLayout(FlowLayout.CENTER, 5, TypographyUtils.H_PADDING_FROM_PANEL_HEADER));

    JLabel lblNewLabel = new JLabel("Table of Library Members");
    TypographyUtils.applyHeadingStyle(lblNewLabel);
    titlePanel.add(lblNewLabel);

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel();
    add(centerPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel();

    btnAdd = new JButton("ADD");
    UiUtils.addButtonHover(btnAdd);
    actionPanel.add(btnAdd, BorderLayout.EAST);
    actionPanel.add(btnAdd);

    btnDelete = new JButton("DELETE");
    UiUtils.addButtonHover(btnDelete);
    actionPanel.add(btnDelete);

    btnUpdate = new JButton("UPDATE");
    UiUtils.addButtonHover(btnUpdate);
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

    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    controlsPanel.setPreferredSize(new Dimension(460, 219));
    controlsPanel.setMaximumSize(new Dimension(460, 219));

    actionPanel.setPreferredSize(new Dimension(460, 40));
    actionPanel.setMaximumSize(new Dimension(460, 40));

    centerPanel.add(controlsPanel);
    centerPanel.add(actionPanel);

    initLibraryMemberTable(centerPanel);

    // Delete button
    btnDelete.addActionListener(
        e -> {
          int count = table.getSelectedRowCount();
          if (count == 1) {
            selectedRow = table.getSelectedRow();

            String memberIdString = (String) table.getValueAt(selectedRow, 0);
            tableModel.removeRow(selectedRow);
            Dispatcher.dispatch(new LibraryMemberDeleteAction(memberIdString));
            selectedRow = -1;
            clearText();
          } else if (count > 1) {
            JOptionPane.showMessageDialog(frame, "Please select single row", "", ERROR_MESSAGE);
          } else {
            JOptionPane.showMessageDialog(frame, "There is no row to delete", "", ERROR_MESSAGE);
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
          boolean alreadyExist =
              Optional.ofNullable(libraryMembers).orElse(new ArrayList<>()).stream()
                  .anyMatch(m -> m.getMemberId().equals(idString));
          if (alreadyExist) {
            JOptionPane.showMessageDialog(frame, "exist member id", "", ERROR_MESSAGE);
            System.out.println("exist member id");
            return;
          }
          Address newAddress = new Address(streetString, cityString, stateString, zipString);
          LibraryMember member =
              new LibraryMember(
                  idString, firstNameString, lastNameString, telephoneString, newAddress);
          Dispatcher.dispatch(new LibraryMemberSaveAction(member));
          JOptionPane.showMessageDialog(frame, "Add member successfully", "", INFORMATION_MESSAGE);
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
          Dispatcher.dispatch(new LibraryMemberSaveAction(member));
          JOptionPane.showMessageDialog(
              frame, "Update member successfully", "", INFORMATION_MESSAGE);
          clearText();
        });
    Dispatcher.dispatch(new LibraryMemberGetAllMemberAction());
  }

  private void initLibraryMemberTable(JPanel container) {
    Object[] columnsObjects = {"ID", "First Name", "Last Name", "TEL", "Address"};
    tableModel = new DefaultTableModel();
    tableModel.setColumnIdentifiers(columnsObjects);
    table =
        new JTable() {
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setModel(tableModel);
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
    JPanel tablePanel = new JPanel();
    tablePanel.setLayout(new FlowLayout(0, 0, 0));
    tablePanel.setPreferredSize(new Dimension(750, 275));
    tablePanel.setMaximumSize(new Dimension(750, 275));
    tablePanel.add(jScrollPane);
    container.add(tablePanel);
    table.addMouseListener(
        new MouseAdapter() {

          @Override
          public void mousePressed(MouseEvent e) {
            int count = table.getSelectedRowCount();
            if (count == 1) {
              selectedRow = table.getSelectedRow();
              System.out.println(tableModel.getValueAt(selectedRow, 0));
              String memberId = (String) tableModel.getValueAt(selectedRow, 0);
              LibraryMember member =
                  libraryMembers.stream()
                      .filter(m -> m.getMemberId().equals(memberId))
                      .findFirst()
                      .orElseThrow();
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
  public void onStateChanged(StateChangeEvent<LibraryMemberState> event) {
    tableModel.setRowCount(0);
    libraryMembers = event.getNewState().getData().getMembers();
    if (libraryMembers == null) {
      return;
    }

    for (LibraryMember member : libraryMembers) {
      tableModel.addRow(
          new Object[] {
            member.getMemberId(),
            member.getFirstName(),
            member.getLastName(),
            member.getTelephone(),
            member.getAddress()
          });
    }
  }

  @Override
  public StatePath getListeningStatePath() {
    return AppStatePath.LIBRARY_MEMBER;
  }
}
