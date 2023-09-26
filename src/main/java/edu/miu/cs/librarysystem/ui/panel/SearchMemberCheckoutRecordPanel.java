package edu.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.store.action.checkoutrecord.CheckoutRecordPrintAction;
import edu.miu.cs.librarysystem.store.action.checkoutrecord.CheckoutRecordSearchAction;
import edu.miu.cs.librarysystem.store.core.Dispatcher;
import edu.miu.cs.librarysystem.store.core.StateChangeEvent;
import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.CheckoutRecordState;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.UiUtils;
import edu.miu.cs.librarysystem.viewmodel.CheckoutRecordViewModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class SearchMemberCheckoutRecordPanel extends JPanel
    implements LibPanel, StateChangeListener<CheckoutRecordState> {

  private JTextField txtFieldFirstName;
  private JTextField txtState;
  private JTextField txtZip;
  private JTextField txtFieldLastName;
  private JTextField txtCity;
  private JTextField txtFieldStreet;
  private JTextField txtFieldId;
  private JTextField txtTelephone;

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
    UiUtils.addButtonHover(searchMemberButton);
    actionPanel.add(searchMemberButton);
    searchMemberButton.setHorizontalAlignment(SwingConstants.RIGHT);

    JButton printRecordButton = new JButton("PRINT RECORD");
    UiUtils.addButtonHover(printRecordButton);
    actionPanel.add(printRecordButton);

    JButton clearFieldsButton = new JButton("CLEAR FIELDS");
    UiUtils.addButtonHover(clearFieldsButton);
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
    controlsPanel.setLayout(new GridLayout(0, 2, 0, 0));

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
          Dispatcher.dispatch(new CheckoutRecordSearchAction(memberId));
        });

    printRecordButton.addActionListener(
        e -> {
          String memberId = txtFieldId.getText();
          if (memberId.isEmpty() || txtFieldId.isEnabled()) {
            JOptionPane.showMessageDialog(
                this, "Please Enter Member ID to search", "", ERROR_MESSAGE);
            return;
          }
          Dispatcher.dispatch(new CheckoutRecordPrintAction(memberId));
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
  public void onStateChanged(StateChangeEvent<CheckoutRecordState> event) {
    CheckoutRecordViewModel viewModel = event.getNewState().getData();
    LibraryMember libraryMember = viewModel.getLibraryMember();
    if (libraryMember == null) {
      JOptionPane.showMessageDialog(
          this, "Member with ID " + viewModel.getMemberId() + " not found", "", ERROR_MESSAGE);
      return;
    }
    updateMemberFields(libraryMember);
    if (viewModel.isPrintCheckoutRecords()) {
      printCheckoutRecords(libraryMember);
    }
  }

  private void updateMemberFields(LibraryMember member) {
    txtFieldId.setEnabled(false);

    txtFieldFirstName.setText(member.getFirstName());
    txtFieldLastName.setText(member.getLastName());
    txtTelephone.setText(member.getTelephone());
    txtFieldStreet.setText(member.getAddress().getStreet());
    txtZip.setText(member.getAddress().getZip());
    txtCity.setText(member.getAddress().getCity());
    txtState.setText(member.getAddress().getState());
  }

  private void printCheckoutRecords(LibraryMember member) {
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
  }

  @Override
  public StatePath getListeningStatePath() {
    return AppStatePath.CHECKOUT_RECORD;
  }
}
