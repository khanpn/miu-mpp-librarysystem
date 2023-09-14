package com.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.CheckoutRecord;
import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.LibraryMember;
import com.miu.cs.librarysystem.business.SystemController;
import com.miu.cs.librarysystem.system.LibWindow;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SearchOverDuePanel extends JPanel implements LibWindow {
  private static final long serialVersionUID = -8785710954048069875L;
  private boolean isInitialized = false;

  private final List<String> defaultList = new ArrayList<>();
  private final DefaultListModel<String> listModel = new DefaultListModel<>();
  private JTextField searchField;
  DefaultTableModel model;

  ControllerInterface ci = new SystemController();

  private JList<String> createJList() {
    JList<String> ret = new JList<>(listModel);
    ret.setVisibleRowCount(4);
    return ret;
  }

  private void initializeDefaultList() {
    defaultList.add("Red");
    defaultList.add("Blue");
    defaultList.add("Yellow");
  }

  public SearchOverDuePanel() {
    init();
  }

  @Override
  public void init() {
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    add(panel, BorderLayout.NORTH);
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    JLabel lblNewLabel = new JLabel("Book Search");
    panel.add(lblNewLabel);

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel panel_2 = new JPanel();
    add(panel_2, BorderLayout.CENTER);

    JPanel panel_3 = new JPanel();
    panel_3.setLayout(new GridLayout(0, 6, 0, 0));
    panel_3.setBounds(5, 5, 800, 39);

    JLabel searchLabel = new JLabel("Book ISBN:");
    panel_3.add(searchLabel);
    searchField = new JTextField();
    searchField.setSize(200, 24);
    panel_3.add(searchField);

    JButton btnSearch = new JButton("SEARCH");
    panel_3.add(btnSearch);

    JButton btnClearSearch = new JButton("CLEAR");
    panel_3.add(btnClearSearch);

    JList<String> mainList = createJList();
    mainList.setFixedCellWidth(70);
    JScrollPane mainScroll = new JScrollPane(mainList);

    initializeDefaultList();
    panel_2.add(mainScroll);

    panel_2.setLayout(null);
    panel_2.add(panel_3);

    JPanel panel_4 = new JPanel();
    panel_4.setBounds(5, 46, 580, 275);
    panel_2.add(panel_4);
    panel_4.setLayout(new BorderLayout(0, 0));
    JTable table =
        new JTable() {
          private static final long serialVersionUID = 1L;

          public boolean isCellEditable(int row, int column) {
            return false;
          }
          ;
        };

    Object[] columnsObjects = {"ISBN", "TITLE", "Copy #", "OVERDUE", "DUE DATE", "MEMBER"};
    model = new DefaultTableModel();
    model.setColumnIdentifiers(columnsObjects);

    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setModel(model);
    TableColumnModel colModel = table.getColumnModel();
    colModel.getColumn(5).setPreferredWidth(200);
    colModel.getColumn(4).setPreferredWidth(100);
    colModel.getColumn(3).setPreferredWidth(75);
    colModel.getColumn(2).setPreferredWidth(50);
    colModel.getColumn(1).setPreferredWidth(150);
    colModel.getColumn(0).setPreferredWidth(75);
    JScrollPane jScrollPane = new JScrollPane();
    jScrollPane.setViewportView(table);
    panel_4.add(jScrollPane);

    btnSearch.addActionListener(
        event -> {
          String isbn = searchField.getText();
          if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter book ISBN", "", ERROR_MESSAGE);
            System.out.println("exist member id");
            return;
          }

          Book theBook = ci.getBookByISBN(isbn);
          if (theBook == null) {
            JOptionPane.showMessageDialog(
                this, "Book with ISBN " + isbn + " could not be found", "", ERROR_MESSAGE);
            return;
          }

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

          List<CheckoutRecord> records =
              ci.allLibraryMembers().stream()
                  .map(LibraryMember::getCheckoutRecords)
                  .filter(checkoutRecords -> checkoutRecords.size() > 0)
                  .flatMap(List::stream)
                  .toList();

          records.forEach(
              record -> {
                record
                    .getEntries()
                    .forEach(
                        entry -> {
                          Book book = entry.getCopy().getBook();
                          if (entry.isOverdue() && isbn.equalsIgnoreCase(book.getIsbn())) {
                            clearTable();
                            model.addRow(
                                new Object[] {
                                  book.getIsbn(),
                                  book.getTitle(),
                                  entry.getCopy().getCopyNum(),
                                  entry.isOverdue() ? "YES" : "NO",
                                  entry.getDueDate().format(formatter),
                                  record.getMember().getFullName()
                                });
                          }
                        });
              });
        });

    btnClearSearch.addActionListener(
        (evt) -> {
          clearText();
          //          model.setRowCount(0);
          clearTable();
        });
  }

  void clearTable() {
    while (model.getRowCount() > 0) {
      model.removeRow(0);
    }
  }

  void clearText() {
    searchField.setText("");
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
