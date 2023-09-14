package com.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.CheckoutHistory;
import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.LibraryMember;
import com.miu.cs.librarysystem.business.LibrarySystemException;
import com.miu.cs.librarysystem.business.SystemController;
import com.miu.cs.librarysystem.system.LibWindow;
import com.miu.cs.librarysystem.system.TypographyUtils;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CheckoutBookPanel extends JPanel implements LibWindow {

  private static final long serialVersionUID = -6316708968684859855L;
  private boolean isInitialized = false;

  private JComboBox<String> bookIsbnTextField;
  private JComboBox<String> memberIdTextField;

  DefaultTableModel model = new DefaultTableModel();
  ControllerInterface ci = new SystemController();

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
    lblNewLabel.setFont(getFont().deriveFont(24f));
    panel.add(lblNewLabel);

    panel.add(lblNewLabel);

    Object[] columnsObjects = {
      "CHECKOUT DATE", "DUE DATE", "ISBN", "BOOK TITLE", "BORROWER", "TEL"
    };

    model.setColumnIdentifiers(columnsObjects);
    getCheckoutHistoryList();

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel midlePanel = new JPanel();
    add(midlePanel, BorderLayout.CENTER);

    JPanel checkoutPanel = new JPanel(new BorderLayout());
    checkoutPanel.setBounds(5, 130, 460, 40);
    // test
    //    checkoutPanel.setBorder(new LineBorder(null, 1, true));

    JButton checkoutBookButton = new JButton("CHECKOUT BOOK");
    checkoutPanel.add(checkoutBookButton, BorderLayout.EAST);
    checkoutBookButton.setHorizontalAlignment(SwingConstants.RIGHT);

    JPanel inputsPanel = new JPanel();
    inputsPanel.setBounds(5, 5, 460, 100);
    inputsPanel.setLayout(new GridLayout(0, 2, 0, 20));
    // test
    //    inputsPanel.setBorder(new LineBorder(null, 1, true));
    JLabel memberIdLabel = new JLabel("Member ID:");
    inputsPanel.add(memberIdLabel);

    Collection<LibraryMember> members = ci.allLibraryMembers();

    String[] membersStrings = new String[members.size()];
    int counter = 0;
    for (LibraryMember member : members) {
      membersStrings[counter] = member.getMemberId() + " - " + member.getFullName();
      counter++;
    }

    memberIdTextField = new JComboBox<>(membersStrings);
    inputsPanel.add(memberIdTextField);

    JLabel bookIsbnLabel = new JLabel("ISBN:");
    inputsPanel.add(bookIsbnLabel);

    Collection<Book> books = ci.allBooks();
    String[] bookStrings = new String[books.size()];
    int index = 0;
    for (Book book : books) {
      bookStrings[index] = book.getIsbn() + " - " + book.getTitle();
      index++;
    }

    bookIsbnTextField = new JComboBox<>(bookStrings);
    inputsPanel.add(bookIsbnTextField);

    midlePanel.setLayout(null);
    midlePanel.add(checkoutPanel);
    midlePanel.add(inputsPanel);

    JPanel tablePanel = new JPanel();
    tablePanel.setBounds(5, 200, 650, 275);
    midlePanel.add(tablePanel);
    tablePanel.setLayout(new BorderLayout(0, 0));

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
            ci.checkBook(memberId, bookIsbn);
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
    Collection<CheckoutHistory> checkouts = ci.getCheckoutHistory();
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
