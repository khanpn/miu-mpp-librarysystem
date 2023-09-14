package com.miu.cs.librarysystem.ui.panel;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.BookshelfViewModel;
import com.miu.cs.librarysystem.store.AppStateChangeEvent;
import com.miu.cs.librarysystem.store.AppStateChangeListener;
import com.miu.cs.librarysystem.store.AppStore;
import com.miu.cs.librarysystem.store.Dispatcher;
import com.miu.cs.librarysystem.store.action.bookshelf.BookshelfFilterAction;
import com.miu.cs.librarysystem.store.action.bookshelf.BookshelfLoadBooksAction;
import com.miu.cs.librarysystem.store.action.bookshelf.BookshelfSelectBookAction;
import com.miu.cs.librarysystem.store.state.AppStatePath;
import com.miu.cs.librarysystem.store.state.BookshelfState;
import com.miu.cs.librarysystem.system.TypographyUtils;
import com.miu.cs.librarysystem.system.Util;
import com.miu.cs.librarysystem.ui.dialog.AddBookDialog;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class BookshelfPanel extends JPanel implements AppStateChangeListener<BookshelfState> {
  private JPanel contentPane;
  private JPanel topPanel;
  private JPanel centerPanel;
  private JPanel headingTopPanel;
  private JPanel headingCenterPanel;
  private JLabel bookshelfLabel;
  private JPanel toolBarPanel;
  private JTextField searchField;
  private JButton searchButton;
  private JButton clearSearchButton;
  private JButton addBookButton;
  private JButton copyBookButton;
  private JPanel bookTablePanel;
  private JScrollPane tableScrollPane;
  private JTable bookTable;
  private DefaultTableModel bookTableModel;
  private List<Book> books;

  public BookshelfPanel() {
    AppStore.registerOnStateChange(getListeningStatePath(), this);
    init();
  }

  public static void main(String[] args) {
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(new BookshelfPanel());
    jFrame.setSize(500, 400);
    jFrame.setVisible(true);
  }

  private void init() {
    add(contentPane);
    TypographyUtils.applyHeadingStyle(bookshelfLabel);
    bookTableModel = new DefaultTableModel();
    bookTableModel.setColumnIdentifiers(
        new String[] {
          "ISBN", "Title", "Maximum checkout", "Available copies", "Total copies", "Authors"
        });
    bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    bookTable.setModel(bookTableModel);
    ((DefaultTableCellRenderer) bookTable.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.LEFT);
    TableColumnModel colModel = bookTable.getColumnModel();
    colModel.getColumn(0).setPreferredWidth(100);
    colModel.getColumn(1).setPreferredWidth(180);
    colModel.getColumn(2).setPreferredWidth(110);
    colModel.getColumn(3).setPreferredWidth(90);
    colModel.getColumn(4).setPreferredWidth(70);
    colModel.getColumn(5).setPreferredWidth(180);
    bookTable
        .getSelectionModel()
        .addListSelectionListener(
            (e) -> {
              if (e.getValueIsAdjusting() || bookTable.getSelectedRow() < 0) {
                return;
              }
              Book selectedBook = books.get(bookTable.getSelectedRow());
              Dispatcher.dispatch(new BookshelfSelectBookAction(selectedBook));
            });

    searchButton.addActionListener(
        (e) -> Dispatcher.dispatch(new BookshelfFilterAction(searchField.getText())));
    clearSearchButton.addActionListener(
        (e) -> {
          Dispatcher.dispatch(new BookshelfLoadBooksAction());
          searchField.setText("");
        });
    addBookButton.addActionListener(
        e -> {
          AddBookDialog addBookDialog = new AddBookDialog();
          addBookDialog.setSize(500, 400);
          Util.centerFrameOnDesktop(addBookDialog);
          addBookDialog.setVisible(true);
        });
    Dispatcher.dispatch(new BookshelfLoadBooksAction());
  }

  @Override
  public void onStateChanged(AppStateChangeEvent<BookshelfState> event) {
    BookshelfViewModel viewModel = event.getNewState().getData();
    if (viewModel.getSelectedBook() != null) {
      return;
    }
    bookTableModel.setRowCount(0);
    books = viewModel.getBooks();
    if (books == null || books.isEmpty()) {
      return;
    }
    books.stream()
        .map(
            book ->
                new Object[] {
                  book.getIsbn(),
                  book.getTitle(),
                  book.getMaxCheckoutLength(),
                  book.getAvailableBooksLength(),
                  book.getCopies().length,
                  book.getAuthors().toString()
                })
        .forEach(bookTableModel::addRow);
  }

  @Override
  public AppStatePath getListeningStatePath() {
    return AppStatePath.BOOKSHELF;
  }
}
