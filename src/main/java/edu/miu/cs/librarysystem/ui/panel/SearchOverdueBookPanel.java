package edu.miu.cs.librarysystem.ui.panel;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.action.searchoverduebook.SearchOverdueBookClearSearchAction;
import edu.miu.cs.librarysystem.store.action.searchoverduebook.SearchOverdueBookSearchAction;
import edu.miu.cs.librarysystem.store.core.Dispatcher;
import edu.miu.cs.librarysystem.store.core.StateChangeEvent;
import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.SearchOverdueBookState;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.UiUtils;
import edu.miu.cs.librarysystem.viewmodel.SearchOverdueBookViewModel;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SearchOverdueBookPanel extends JPanel
    implements LibPanel, StateChangeListener<SearchOverdueBookState> {
  private JTextField searchField;
  DefaultTableModel model;

  public void init() {
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    add(panel, BorderLayout.NORTH);
    panel.setLayout(
        new FlowLayout(FlowLayout.CENTER, 5, TypographyUtils.H_PADDING_FROM_PANEL_HEADER));

    JLabel lblNewLabel = new JLabel("Book Search");
    TypographyUtils.applyHeadingStyle(lblNewLabel);
    panel.add(lblNewLabel);

    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel();
    add(centerPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel();

    JLabel searchLabel = new JLabel("Book ISBN:");
    actionPanel.add(searchLabel);
    searchField = new JTextField();
    searchField.setSize(200, 24);
    actionPanel.add(searchField);

    // empty label for spacing
    actionPanel.add(new JLabel());
    actionPanel.add(new JLabel());

    JButton btnSearch = new JButton("SEARCH");
    UiUtils.addButtonHover(btnSearch);
    actionPanel.add(btnSearch);

    JButton btnClearSearch = new JButton("CLEAR");
    UiUtils.addButtonHover(btnClearSearch);
    actionPanel.add(btnClearSearch);

    actionPanel.setLayout(new GridLayout(0, 6, 8, 0));
    actionPanel.setBounds(5, 5, 750, 40);

    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    actionPanel.setPreferredSize(new Dimension(750, 40));
    actionPanel.setMaximumSize(new Dimension(750, 40));

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
          ;
        };

    Object[] columnsObjects = {"ISBN", "TITLE", "Copy #", "OVERDUE", "DUE DATE", "MEMBER"};
    model = new DefaultTableModel();
    model.setColumnIdentifiers(columnsObjects);

    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setModel(model);
    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.LEFT);
    TableColumnModel colModel = table.getColumnModel();
    colModel.getColumn(5).setPreferredWidth(200);
    colModel.getColumn(4).setPreferredWidth(100);
    colModel.getColumn(3).setPreferredWidth(75);
    colModel.getColumn(2).setPreferredWidth(50);
    colModel.getColumn(1).setPreferredWidth(150);
    colModel.getColumn(0).setPreferredWidth(75);
    JScrollPane jScrollPane = new JScrollPane();
    jScrollPane.setViewportView(table);
    jScrollPane.setPreferredSize(new Dimension(750, 275));
    tablePanel.add(jScrollPane);

    btnSearch.addActionListener(
        event -> {
          String isbn = searchField.getText();
          if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter book ISBN", "", ERROR_MESSAGE);
            return;
          }
          Dispatcher.dispatch(new SearchOverdueBookSearchAction(isbn));
        });

    btnClearSearch.addActionListener(
        (evt) -> Dispatcher.dispatch(new SearchOverdueBookClearSearchAction()));
  }

  void clearText() {
    searchField.setText("");
  }

  @Override
  public void onStateChanged(StateChangeEvent<SearchOverdueBookState> event) {
    SearchOverdueBookViewModel viewModel = event.getNewState().getData();
    model.setRowCount(0);
    if (viewModel.isClearSearch()) {
      clearText();
      return;
    }
    if (viewModel.getBook() == null) {
      JOptionPane.showMessageDialog(
          this,
          "Book with ISBN " + viewModel.getBookId() + " could not be found",
          "",
          ERROR_MESSAGE);
      return;
    }
    viewModel
        .getOverdueCheckoutRecordEntries()
        .forEach(
            entry -> {
              entry.forEach(
                  (member, checkoutRecordEntries) -> {
                    checkoutRecordEntries.forEach(
                        overdueRecord -> {
                          Book overDueBook = overdueRecord.getCopy().getBook();
                          model.addRow(
                              new Object[] {
                                overDueBook.getIsbn(),
                                overDueBook.getTitle(),
                                overdueRecord.getCopy().getCopyNum(),
                                "YES",
                                overdueRecord.getDueDate().format(UiUtils.DATE_FORMATTER),
                                member.getFullName()
                              });
                        });
                  });
            });
  }

  @Override
  public StatePath getListeningStatePath() {
    return AppStatePath.SEARCH_OVERDUE_BOOK;
  }
}
