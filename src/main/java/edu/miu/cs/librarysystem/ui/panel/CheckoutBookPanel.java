package edu.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.CheckoutHistory;
import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.store.action.checkoutbook.CheckoutBookCheckoutBookAction;
import edu.miu.cs.librarysystem.store.action.checkoutbook.CheckoutBookRefreshAction;
import edu.miu.cs.librarysystem.store.core.Dispatcher;
import edu.miu.cs.librarysystem.store.core.StateChangeEvent;
import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.CheckoutBookState;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.UiUtils;
import edu.miu.cs.librarysystem.viewmodel.CheckoutBookViewModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CheckoutBookPanel extends JPanel
    implements LibPanel, StateChangeListener<CheckoutBookState> {

  private JComboBox<String> bookIsbnComboBox;
  private JComboBox<String> memberIdComboBox;
  DefaultTableModel model = new DefaultTableModel();

  public void init() {
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    add(panel, BorderLayout.NORTH);
    panel.setLayout(
        new FlowLayout(FlowLayout.CENTER, 5, TypographyUtils.H_PADDING_FROM_PANEL_HEADER));

    JLabel lblNewLabel = new JLabel("Book Checkout History");
    TypographyUtils.applyHeadingStyle(lblNewLabel);
    panel.add(lblNewLabel);

    panel.add(lblNewLabel);

    Object[] columnsObjects = {
      "CHECKOUT DATE", "DUE DATE", "ISBN", "BOOK TITLE", "BORROWER", "TEL"
    };

    model.setColumnIdentifiers(columnsObjects);

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel();
    add(centerPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel();

    JButton checkoutBookButton = new JButton("CHECKOUT BOOK");
    UiUtils.addButtonHover(checkoutBookButton);
    actionPanel.add(checkoutBookButton, BorderLayout.EAST);
    checkoutBookButton.setHorizontalAlignment(SwingConstants.RIGHT);

    JPanel controlsPanel = new JPanel();
    JLabel memberIdLabel = new JLabel("Member ID:");
    controlsPanel.add(memberIdLabel);

    memberIdComboBox = new JComboBox<>();
    controlsPanel.add(memberIdComboBox);

    JLabel bookIsbnLabel = new JLabel("ISBN:");
    controlsPanel.add(bookIsbnLabel);

    bookIsbnComboBox = new JComboBox<>();
    controlsPanel.add(bookIsbnComboBox);

    actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 6));
    actionPanel.setBounds(5, 200, 460, 40);

    controlsPanel.setLayout(new GridLayout(0, 2, 10, 20));
    controlsPanel.setBounds(5, 5, 460, 100);

    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    controlsPanel.setPreferredSize(new Dimension(460, 100));
    controlsPanel.setMaximumSize(new Dimension(460, 100));

    actionPanel.setPreferredSize(new Dimension(460, 40));
    actionPanel.setMaximumSize(new Dimension(460, 40));

    centerPanel.add(controlsPanel);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    centerPanel.add(actionPanel);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    JPanel tablePanel = new JPanel();
    tablePanel.setLayout(new FlowLayout(0, 0, 0));
    tablePanel.setPreferredSize(new Dimension(750, 275));
    tablePanel.setMaximumSize(new Dimension(750, 275));

    centerPanel.add(tablePanel);

    JTable table =
        new JTable() {
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };

    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setModel(model);
    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.LEFT);
    JScrollPane jScrollPane = new JScrollPane();
    jScrollPane.setViewportView(table);
    jScrollPane.setPreferredSize(new Dimension(750, 275));
    tablePanel.add(jScrollPane);

    checkoutBookButton.addActionListener(
        event -> {
          String memberId = (String) memberIdComboBox.getSelectedItem();
          String bookIsbn = (String) bookIsbnComboBox.getSelectedItem();
          if (memberId == null || memberId.isEmpty() || bookIsbn == null || bookIsbn.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "All fields are required", "", JOptionPane.ERROR_MESSAGE);
            return;
          }

          memberId = memberId.substring(0, memberId.indexOf(" - ")).trim();
          bookIsbn = bookIsbn.substring(0, bookIsbn.indexOf(" - ")).trim();
          Dispatcher.dispatch(new CheckoutBookCheckoutBookAction(memberId + "," + bookIsbn));
        });
    Dispatcher.dispatch(new CheckoutBookRefreshAction());
  }

  void repopulateCheckoutHistoryList(List<CheckoutHistory> checkoutHistories) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    model.setRowCount(0);
    for (CheckoutHistory checkout : checkoutHistories) {
      model.addRow(
          new Object[] {
            checkout.getCheckoutDate().format(formatter),
            checkout.getDueDate().format(formatter),
            checkout.getCopy().getBook().getIsbn(),
            checkout.getCopy().getBook().getTitle(),
            checkout.getMember().getFullName(),
            checkout.getMember().getTelephone(),
          });
    }
  }

  @Override
  public void onStateChanged(StateChangeEvent<CheckoutBookState> event) {
    CheckoutBookViewModel viewModel = event.getNewState().getData();
    if (viewModel.isCheckout()) {
      JOptionPane.showMessageDialog(this, "Book successfully checked out", "", INFORMATION_MESSAGE);
      Dispatcher.dispatch(new CheckoutBookRefreshAction());
      return;
    }
    if (viewModel.getException() != null) {
      JOptionPane.showMessageDialog(
          this, viewModel.getException().getMessage(), "", JOptionPane.ERROR_MESSAGE);
      Dispatcher.dispatch(new CheckoutBookRefreshAction());
      return;
    }
    memberIdComboBox.removeAllItems();
    bookIsbnComboBox.removeAllItems();

    List<LibraryMember> libraryMembers = viewModel.getLibraryMembers();
    List<Book> books = viewModel.getBooks();
    if (libraryMembers != null) {
      for (LibraryMember member : libraryMembers) {
        memberIdComboBox.addItem(member.getMemberId() + " - " + member.getFullName());
      }
    }
    if (books != null) {
      for (Book book : books) {
        bookIsbnComboBox.addItem(book.getIsbn() + " - " + book.getTitle());
      }
    }
    repopulateCheckoutHistoryList(viewModel.getCheckoutHistories());
  }

  @Override
  public StatePath getListeningStatePath() {
    return AppStatePath.CHECKOUT_BOOK;
  }
}
