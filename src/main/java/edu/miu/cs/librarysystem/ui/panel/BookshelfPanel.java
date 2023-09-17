package edu.miu.cs.librarysystem.ui.panel;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.action.bookshelf.BookshelfFilterAction;
import edu.miu.cs.librarysystem.store.action.bookshelf.BookshelfLoadBooksAction;
import edu.miu.cs.librarysystem.store.action.bookshelf.BookshelfSelectBookAction;
import edu.miu.cs.librarysystem.store.core.Dispatcher;
import edu.miu.cs.librarysystem.store.core.StateChangeEvent;
import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.Store;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.BookshelfState;
import edu.miu.cs.librarysystem.ui.dialog.AddBookDialog;
import edu.miu.cs.librarysystem.ui.dialog.CopyBookDialog;
import edu.miu.cs.librarysystem.ui.renderer.AvailableBookCopyCellRenderer;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.Util;
import edu.miu.cs.librarysystem.viewmodel.BookshelfViewModel;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class BookshelfPanel extends JPanel implements StateChangeListener<BookshelfState> {
  private JPanel contentPane;
  private JLabel bookshelfLabel;
  private JTextField searchField;
  private JButton searchButton;
  private JButton clearSearchButton;
  private JButton addBookButton;
  private JButton copyBookButton;
  private JScrollPane tableScrollPane;
  private JTable bookTable;
  private JLabel selectedBookLabel;
  private JLabel selectedBookAuthorsLabel;
  private DefaultTableModel bookTableModel;
  private List<Book> books;

  public BookshelfPanel() {
    Store.registerOnStateChange(getListeningStatePath(), this);
    init();
  }

  public static void main(String[] args) {
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(new BookshelfPanel());
    jFrame.setSize(500, 400);
    jFrame.setVisible(true);
  }

  private void init() {
    Util.addButtonHover(searchButton);
    Util.addButtonHover(clearSearchButton);
    Util.addButtonHover(addBookButton);
    Util.addButtonHover(copyBookButton);
    add(contentPane);
    TypographyUtils.applyHeadingStyle(bookshelfLabel);
    selectedBookLabel.setText("");
    bookTableModel =
        new DefaultTableModel() {
          @Override
          public Class getColumnClass(int columnIndex) {
            Class[] types =
                new Class[] {
                  String.class,
                  String.class,
                  Integer.class,
                  Integer.class,
                  Integer.class,
                  String.class
                };
            return types[columnIndex];
          }

          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    bookTableModel.setColumnIdentifiers(
        new String[] {
          "ISBN", "Title", "Maximum checkout", "Available copies", "Total copies", "Authors"
        });
    bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    bookTable.setModel(bookTableModel);
    ((DefaultTableCellRenderer) bookTable.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.LEFT);
    bookTable.setDefaultRenderer(Integer.class, new AvailableBookCopyCellRenderer());

    TableColumnModel colModel = bookTable.getColumnModel();
    colModel.getColumn(0).setPreferredWidth(100);
    colModel.getColumn(1).setPreferredWidth(280);
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
        (e) -> {
          AddBookDialog addBookDialog = new AddBookDialog();
          Util.centerFrameOnDesktop(addBookDialog);
          addBookDialog.setVisible(true);
        });
    copyBookButton.addActionListener(
        (e) -> {
          CopyBookDialog copyBookDialog = new CopyBookDialog();
          Util.centerFrameOnDesktop(copyBookDialog);
          copyBookDialog.setVisible(true);
        });
    Dispatcher.dispatch(new BookshelfLoadBooksAction());
  }

  @Override
  public void onStateChanged(StateChangeEvent<BookshelfState> event) {
    BookshelfViewModel viewModel = event.getNewState().getData();
    copyBookButton.setEnabled(false);
    Book selectedBook = viewModel.getSelectedBook();
    updateSelectedBookText(selectedBook);
    if (viewModel.getSelectedBook() != null) {
      copyBookButton.setEnabled(true);
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
                  book.getAuthors().stream().map(Object::toString).collect(Collectors.joining(", "))
                })
        .forEach(bookTableModel::addRow);
  }

  @Override
  public StatePath getListeningStatePath() {
    return AppStatePath.BOOKSHELF;
  }

  private void updateSelectedBookText(Book selectedBook) {
    String bookInfoFormat = "ISBN: {0} | Title: {1} | Available: {2}";
    String authorsFormat = "Authors: {0}";
    selectedBookLabel.setText(
        MessageFormat.format(
            bookInfoFormat,
            Optional.ofNullable(selectedBook).map(Book::getIsbn).orElse(""),
            Optional.ofNullable(selectedBook).map(Book::getTitle).orElse(""),
            Optional.ofNullable(selectedBook)
                .map(Book::getAvailableBooksLength)
                .map(String::valueOf)
                .orElse("")));
    selectedBookAuthorsLabel.setText(
        MessageFormat.format(
            authorsFormat,
            Optional.ofNullable(selectedBook)
                .map(Book::getAuthors)
                .map(String::valueOf)
                .orElse("")));
  }
}
