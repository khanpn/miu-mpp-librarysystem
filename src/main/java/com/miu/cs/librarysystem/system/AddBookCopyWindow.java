package com.miu.cs.librarysystem.system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
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

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.SystemController;

public class AddBookCopyWindow extends JPanel implements LibWindow {

	private static final long serialVersionUID = -8717602939009496185L;
	private boolean isInitialized = false;

	private JTextField txtIsbn;
	private JTextField txtAvailability;
	private JTextField txtTitle;
	private JTable table;
	private final ControllerInterface ci = new SystemController();
	private final List<String> defaultList = new ArrayList<>();
	private final DefaultListModel<String> listModel = new DefaultListModel<>();
	private JTextField searchField;
	DefaultTableModel model;

	private int selectedRow = -1;

	public AddBookCopyWindow() {
		init();
	}

	@Override
	public void init() {
		setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblNewLabel = new JLabel("Book shelf");
		topPanel.add(lblNewLabel);
		
		Object[] columnsObjects = { "ISBN", "Title", "Maximum checkout", "Available copies", "Total copies",
				"Authors" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columnsObjects);
		updateJtable();

		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(0, 5, 10, 10));
		searchPanel.setBounds(5, 231, 700, 39);

		searchField = new JTextField();
		searchField.setSize(200, 24);
		searchPanel.add(searchField);
		JButton btnSearch = new JButton("ISBN SEARCH");
		searchPanel.add(btnSearch);

		JButton btnClearSearch = new JButton("CLEAR SEARCH");
		searchPanel.add(btnClearSearch);

		JButton btnCopy = new JButton("COPY");
		searchPanel.add(btnCopy);

		JPanel middlePanel = new JPanel();
		middlePanel.setBounds(5, 5, 460, 219);
		middlePanel.setLayout(new GridLayout(0, 2, 0, 20));

		JLabel lblIsbn = new JLabel("ISBN:");
		middlePanel.add(lblIsbn);

		txtIsbn = new JTextField();
		middlePanel.add(txtIsbn);
		txtIsbn.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title:");
		middlePanel.add(lblTitle);

		txtTitle = new JTextField();
		middlePanel.add(txtTitle);
		txtTitle.setColumns(10);

		JLabel lblAvailability = new JLabel("Availability:");
		middlePanel.add(lblAvailability);

		txtAvailability = new JTextField("");
		middlePanel.add(txtAvailability);
		txtAvailability.setColumns(10);
		
		// add empty element to fill gap
		middlePanel.add(new JLabel(""));
		middlePanel.add(new JLabel(""));


		JList<String> mainList = createJList();
		mainList.setFixedCellWidth(70);
		JScrollPane mainScroll = new JScrollPane(mainList);

		initializeDefaultList();
		centerPanel.add(mainScroll);

		centerPanel.setLayout(null);
		centerPanel.add(searchPanel);
		centerPanel.add(middlePanel);

		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(5, 282, 580, 275);
		centerPanel.add(tablePanel);
		tablePanel.setLayout(new BorderLayout(0, 0));
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(model);
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(5).setPreferredWidth(200);
		colModel.getColumn(4).setPreferredWidth(50);
		colModel.getColumn(3).setPreferredWidth(50);
		colModel.getColumn(2).setPreferredWidth(50);
		colModel.getColumn(1).setPreferredWidth(100);
		colModel.getColumn(0).setPreferredWidth(75);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(table);
		tablePanel.add(jScrollPane);

		btnCopy.addActionListener(e -> {
			int count = table.getSelectedRowCount();
			if (count == 1) {
				selectedRow = table.getSelectedRow();
				String isbn = (String) table.getValueAt(selectedRow, 0);
				Book book = ci.getBookByISBN(isbn);
				book.addCopy();
				ci.saveBook(book);

				model.setValueAt(book.getAvailableBooksLength(), selectedRow, 3);
				model.setValueAt(book.getCopies().length, selectedRow, 4);

				clearText();
				JOptionPane.showMessageDialog(this, "Copy a book successfully.", "", JOptionPane.INFORMATION_MESSAGE);
				table.clearSelection();

			} else if (count > 1) {
				JOptionPane.showMessageDialog(this, "Please select single a book.", "", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "There is no book to copy", "", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnSearch.addActionListener(e -> {
			String isbn = searchField.getText();
			if (isbn.isEmpty()) {
				updateJtable();
			} else {
				updateJtableByIsbn(isbn);
				if (table.getRowCount() > 0) {
					table.setRowSelectionInterval(0, 0);
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int count = table.getSelectedRowCount();
				if (count == 1) {
					selectedRow = table.getSelectedRow();
					Book book = ci.getBookByISBN((String) model.getValueAt(selectedRow, 0));
					txtTitle.setText(book.getTitle());
					txtIsbn.setText(book.getIsbn());
					txtAvailability.setText(String.valueOf(book.getNumCopies()));
				} else {
					clearText();
				}
				super.mouseClicked(e);
			}
		});

		btnClearSearch.addActionListener((evt) -> {
			searchField.setText("");
			updateJtable();
		});
	}

	void clearText() {
		txtTitle.setText("");
		txtIsbn.setText("");
		txtAvailability.setText("");
	}

	void updateJtable() {
		model.setRowCount(0);
		Collection<Book> books = ci.allBooks();
		for (Book book : books) {
			Object[] objects = { book.getIsbn(), book.getTitle(), book.getMaxCheckoutLength(),
					book.getAvailableBooksLength(), book.getCopies().length, book.getAuthors().toString() };
			model.addRow(objects);
		}
	}

	void updateJtableByIsbn(String isbn) {
		model.setRowCount(0);
		Collection<Book> books = ci.allBooks();
		for (Book book : books) {
			if (book.getIsbn().equals(isbn)) {
				Object[] objects = { book.getIsbn(), book.getTitle(), book.getMaxCheckoutLength(),
						book.getAvailableBooksLength(), book.getCopies().length, book.getAuthors().toString() };
				model.addRow(objects);
				break;
			}
		}
	}

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

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void setInitialized(boolean val) {
		isInitialized = val;
	}
}