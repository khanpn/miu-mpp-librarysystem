package edu.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.CheckoutHistory;
import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.exception.LibrarySystemException;
import edu.miu.cs.librarysystem.service.BookService;
import edu.miu.cs.librarysystem.service.LibraryMemberService;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.Util;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CheckoutBookPanel extends JPanel implements LibWindow {

  private static final long serialVersionUID = -6316708968684859855L;
  private boolean isInitialized = false;

  private JComboBox<String> bookIsbnTextField;
  private JComboBox<String> memberIdTextField;

  DefaultTableModel model = new DefaultTableModel();

  public CheckoutBookPanel() {
    init();
  }

  @Override
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
    getCheckoutHistoryList();

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel();
    add(centerPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel();
    // test
    //    checkoutPanel.setBorder(new LineBorder(null, 1, true));

    JButton checkoutBookButton = new JButton("CHECKOUT BOOK");
    Util.addButtonHover(checkoutBookButton);
    actionPanel.add(checkoutBookButton, BorderLayout.EAST);
    checkoutBookButton.setHorizontalAlignment(SwingConstants.RIGHT);

    JPanel controlsPanel = new JPanel();
    // test
    //    inputsPanel.setBorder(new LineBorder(null, 1, true));
    JLabel memberIdLabel = new JLabel("Member ID:");
    controlsPanel.add(memberIdLabel);

    Collection<LibraryMember> members = LibraryMemberService.getInstance().getAllMembers();

    String[] membersStrings = new String[members.size()];
    int counter = 0;
    for (LibraryMember member : members) {
      membersStrings[counter] = member.getMemberId() + " - " + member.getFullName();
      counter++;
    }

    memberIdTextField = new JComboBox<>(membersStrings);
    controlsPanel.add(memberIdTextField);

    JLabel bookIsbnLabel = new JLabel("ISBN:");
    controlsPanel.add(bookIsbnLabel);

    Collection<Book> books = BookService.getInstance().getAllBooks();
    String[] bookStrings = new String[books.size()];
    int index = 0;
    for (Book book : books) {
      bookStrings[index] = book.getIsbn() + " - " + book.getTitle();
      index++;
    }

    bookIsbnTextField = new JComboBox<>(bookStrings);
    controlsPanel.add(bookIsbnTextField);

    actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 6));
    actionPanel.setBounds(5, 200, 460, 40);

    controlsPanel.setLayout(new GridLayout(0, 2, 10, 20));
    controlsPanel.setBounds(5, 5, 460, 100);

    //    centerPanel.setLayout(null);
    //    centerPanel.add(checkoutPanel);
    //    centerPanel.add(inputsPanel);

    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    controlsPanel.setPreferredSize(new Dimension(460, 100));
    controlsPanel.setMaximumSize(new Dimension(460, 100));

    actionPanel.setPreferredSize(new Dimension(460, 40));
    actionPanel.setMaximumSize(new Dimension(460, 40));

    centerPanel.add(controlsPanel);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    centerPanel.add(actionPanel);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    //    JPanel tablePanel = new JPanel();
    //    tablePanel.setBounds(5, 200, 650, 275);
    //    tablePanel.setLayout(new BorderLayout(0, 0));
    //    centerPanel.add(tablePanel);

    JPanel tablePanel = new JPanel();
    tablePanel.setLayout(new FlowLayout(0, 0, 0));
    tablePanel.setPreferredSize(new Dimension(750, 275));
    tablePanel.setMaximumSize(new Dimension(750, 275));

    centerPanel.add(tablePanel);

    JTable table =
        new JTable() {
          private static final long serialVersionUID = 1L;

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
          String memberId = (String) memberIdTextField.getSelectedItem();
          String bookIsbn = (String) bookIsbnTextField.getSelectedItem();
          if (memberId == null || memberId.isEmpty() || bookIsbn == null || bookIsbn.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "All fields are required", "", JOptionPane.ERROR_MESSAGE);
            return;
          }

          memberId = memberId.substring(0, memberId.indexOf(" - ")).trim();
          bookIsbn = bookIsbn.substring(0, bookIsbn.indexOf(" - ")).trim();

          try {
            clearText();
            BookService.getInstance().checkBook(memberId, bookIsbn);
            JOptionPane.showMessageDialog(
                this,
                "Book successfully checked out",
                "",
                INFORMATION_MESSAGE,
                new ImageIcon(System.getProperty("user.dir") + "/src/librarysystem/success.png"));
            getCheckoutHistoryList();
          } catch (LibrarySystemException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
          }
        });
  }

  void clearText() {
    memberIdTextField.setSelectedItem(null);
    bookIsbnTextField.setSelectedItem(null);
  }

  void getCheckoutHistoryList() {
    Collection<CheckoutHistory> checkouts = BookService.getInstance().getCheckoutHistory();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    model.setRowCount(0);
    for (CheckoutHistory checkout : checkouts) {
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
  public boolean isInitialized() {
    return isInitialized;
  }

  @Override
  public void setInitialized(boolean val) {
    isInitialized = val;
  }
}
