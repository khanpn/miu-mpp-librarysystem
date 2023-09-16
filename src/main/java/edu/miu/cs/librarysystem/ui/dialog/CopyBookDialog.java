package edu.miu.cs.librarysystem.ui.dialog;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.AppStore;
import edu.miu.cs.librarysystem.store.Dispatcher;
import edu.miu.cs.librarysystem.store.action.bookshelf.BookshelfUpdateBookAction;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.BookshelfState;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import java.awt.event.*;
import javax.swing.*;

public class CopyBookDialog extends JDialog {
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JSpinner spinnerNumOfCopies;
  private JLabel errorMessage;
  private JLabel headingLabel;

  public CopyBookDialog() {
    setContentPane(contentPane);
    setModal(true);
    TypographyUtils.applyHeadingStyle(headingLabel);
    TypographyUtils.applyDangerStyle(errorMessage);
    getRootPane().setDefaultButton(buttonOK);
    setSize(400, 300);
    errorMessage.setText("");

    buttonOK.addActionListener(
        e -> {
          errorMessage.setText("");
          int numOfCopies = (int) spinnerNumOfCopies.getValue();
          if (numOfCopies < 1) {
            errorMessage.setText("Number of copies must be at least 1");
            return;
          }
          Book selectedBook =
              AppStore.getState(AppStatePath.BOOKSHELF, BookshelfState.class)
                  .getData()
                  .getSelectedBook();
          if (selectedBook == null) {
            errorMessage.setText("Selected book is null");
            return;
          }
          for (int i = 0; i < numOfCopies; i++) {
            selectedBook.addCopy();
          }
          Dispatcher.dispatch(new BookshelfUpdateBookAction(selectedBook));
          onOK();
        });

    buttonCancel.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            onCancel();
          }
        });

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
  }

  private void onOK() {
    dispose();
  }

  private void onCancel() {
    dispose();
  }

  public static void main(String[] args) {
    CopyBookDialog dialog = new CopyBookDialog();
    dialog.pack();
    dialog.setVisible(true);
    System.exit(0);
  }
}
