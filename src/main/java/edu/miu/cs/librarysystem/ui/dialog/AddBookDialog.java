package edu.miu.cs.librarysystem.ui.dialog;

import edu.miu.cs.librarysystem.business.Author;
import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.action.bookshelf.BookshelfAddBookAction;
import edu.miu.cs.librarysystem.store.action.bookshelf.BookshelfLoadBooksAction;
import edu.miu.cs.librarysystem.store.core.Dispatcher;
import edu.miu.cs.librarysystem.store.core.Store;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.BookshelfState;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.UiUtils;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;

public class AddBookDialog extends JDialog {
  private static final Pattern BOOK_ISBN_FORMAT_PATTER = Pattern.compile("^\\d{2}-\\d{5}$");
  private JPanel contentPane;
  private JLabel headingLabel;
  private JPanel newBookPanel;
  private JTextField isbnField;
  private JTextField titleField;
  private JTextField authorsField;
  private JButton addBookButton;
  private JButton cancelButton;
  private JComboBox maxCheckoutLengthSelect;
  private JSpinner numOfCopiesSpinner;
  private JLabel errorMessageLabel;
  private JList<Author> authorList;
  private JSplitPane splitPane;
  private JPanel authorsPanel;

  public AddBookDialog() {
    initialize();
  }

  private void initialize() {
    setContentPane(contentPane);
    setTitle("New Book");
    setSize(550, 310);
    UiUtils.addButtonHover(addBookButton);
    UiUtils.addButtonHover(cancelButton);
    TypographyUtils.applyHeadingStyle(headingLabel);
    TypographyUtils.applyDangerStyle(errorMessageLabel);
    errorMessageLabel.setText("");

    setModal(true);
    getRootPane().setDefaultButton(addBookButton);

    Dispatcher.dispatch(new BookshelfLoadBooksAction());
    DefaultListModel<Author> authorListModel = new DefaultListModel<>();
    Set<Author> authors =
        Store.getState(AppStatePath.BOOKSHELF, BookshelfState.class).getData().getAuthors();
    authors.forEach(authorListModel::addElement);
    authorList.setModel(authorListModel);

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            onCancel();
          }
        });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            onCancel();
          }
        },
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    cancelButton.addActionListener((event) -> onCancel());
    addBookButton.addActionListener(
        (event) -> {
          errorMessageLabel.setText("");
          try {
            String isbn = isbnField.getText().strip();
            String title = titleField.getText().strip();
            int maxCheckoutLength =
                Integer.parseInt(
                    (String) Objects.requireNonNull(maxCheckoutLengthSelect.getSelectedItem()));
            int numOfCopies = (int) numOfCopiesSpinner.getValue();
            if (isbn.isBlank() || title.isBlank()) {
              errorMessageLabel.setText("All fields must be input");
              return;
            }
            if (!BOOK_ISBN_FORMAT_PATTER.matcher(isbn).matches()) {
              errorMessageLabel.setText(
                  "ISBN must be in format ##-#####. Note that # is represented for a digit");
              return;
            }
            if (numOfCopies < 1) {
              errorMessageLabel.setText("Number of copies must be greater than 0");
              return;
            }
            List<Author> selectedAuthors = authorList.getSelectedValuesList();
            if (selectedAuthors.isEmpty()) {
              errorMessageLabel.setText("You have not selected authors");
              return;
            }
            Book newBook = new Book(isbn, title, maxCheckoutLength, selectedAuthors);
            for (int i = 0; i < numOfCopies; i++) {
              newBook.addCopy();
            }
            Dispatcher.dispatch(new BookshelfAddBookAction(newBook));
            onOK();
          } catch (IllegalArgumentException e) {
            errorMessageLabel.setText("Invalid data input");
          }
        });
  }

  public static void main(String[] args) {
    AddBookDialog addBookDialog = new AddBookDialog();
    UiUtils.centerFrameOnDesktop(addBookDialog);
    addBookDialog.setVisible(true);
  }

  private void onOK() {
    dispose();
  }

  private void onCancel() {
    dispose();
  }
}
