# Library System Application

This is a demo project for Modern Programing Practice (MPP) course at Maharishi International University.

## Grade Book

| Item          | Grade |
|:--------------|:-----:|
| Assignments   |  10   |
| Mid Term Exam |  40   |
| Project       | 13.5  |
| Final Exam    |  40   |

## Project

### Technologies

The application is a desktop application, based on Java Swing

### Application Architecture

The application follows State Management pattern as below:
![State Management](dist/state-management.png?raw=true "State Management")

### Source Code Structure

<pre>
.
├── README.md
├── pom.xml
├── src
│    └── main
│        ├── java
│        │    └── edu
│        │        └── miu
│        │            └── cs
│        │                └── librarysystem
│        │                    ├── LibrarySystemApplication.java
│        │                    ├── business
│        │                    │    ├── Address.java
│        │                    │    ├── Author.java
│        │                    │    ├── BasicAuthCredentials.java
│        │                    │    ├── Book.java
│        │                    │    ├── BookCopy.java
│        │                    │    ├── CheckoutHistory.java
│        │                    │    ├── CheckoutRecord.java
│        │                    │    ├── CheckoutRecordEntry.java
│        │                    │    ├── LibraryMember.java
│        │                    │    └── Person.java
│        │                    ├── dataaccess
│        │                    │    ├── Auth.java
│        │                    │    ├── DataAccess.java
│        │                    │    ├── DataAccessFacade.java
│        │                    │    ├── TestData.java
│        │                    │    └── User.java
│        │                    ├── exception
│        │                    │    ├── LibrarySystemException.java
│        │                    │    └── LoginException.java
│        │                    ├── service
│        │                    │    ├── BookService.java
│        │                    │    ├── LibraryMemberService.java
│        │                    │    ├── LoginService.java
│        │                    │    └── UserService.java
│        │                    ├── store
│        │                    │    ├── AppStoreModule.java
│        │                    │    ├── action
│        │                    │    │    ├── bookshelf
│        │                    │    │    │    ├── BookshelfAddBookAction.java
│        │                    │    │    │    ├── BookshelfFilterAction.java
│        │                    │    │    │    ├── BookshelfLoadBooksAction.java
│        │                    │    │    │    ├── BookshelfSelectBookAction.java
│        │                    │    │    │    └── BookshelfUpdateBookAction.java
│        │                    │    │    ├── checkoutbook
│        │                    │    │    │    ├── CheckoutBookCheckoutBookAction.java
│        │                    │    │    │    └── CheckoutBookRefreshAction.java
│        │                    │    │    ├── checkoutrecord
│        │                    │    │    │    ├── CheckoutRecordPrintAction.java
│        │                    │    │    │    └── CheckoutRecordSearchAction.java
│        │                    │    │    ├── librarymember
│        │                    │    │    │    ├── LibraryMemberDeleteAction.java
│        │                    │    │    │    ├── LibraryMemberGetAllMemberAction.java
│        │                    │    │    │    └── LibraryMemberSaveAction.java
│        │                    │    │    ├── login
│        │                    │    │    │    └── LoginSubmitAction.java
│        │                    │    │    ├── mainwindow
│        │                    │    │    │    └── MainWindowMenuSelectItemAction.java
│        │                    │    │    └── searchoverduebook
│        │                    │    │        ├── SearchOverdueBookClearSearchAction.java
│        │                    │    │        └── SearchOverdueBookSearchAction.java
│        │                    │    ├── core
│        │                    │    │    ├── ActionLoop.java
│        │                    │    │    ├── ActionQueues.java
│        │                    │    │    ├── Dispatcher.java
│        │                    │    │    ├── Reducers.java
│        │                    │    │    ├── StateChangeEvent.java
│        │                    │    │    ├── StateChangeListener.java
│        │                    │    │    ├── Store.java
│        │                    │    │    ├── StoreModule.java
│        │                    │    │    ├── action
│        │                    │    │    │    └── Action.java
│        │                    │    │    ├── reducer
│        │                    │    │    │    └── Reducer.java
│        │                    │    │    └── state
│        │                    │    │        ├── State.java
│        │                    │    │        └── StatePath.java
│        │                    │    ├── reducer
│        │                    │    │    ├── BookshelfReducer.java
│        │                    │    │    ├── CheckoutBookReducer.java
│        │                    │    │    ├── CheckoutRecordReducer.java
│        │                    │    │    ├── LibraryMemberReducer.java
│        │                    │    │    ├── LoginReducer.java
│        │                    │    │    ├── MainWindowReducer.java
│        │                    │    │    └── SearchOverdueBookReducer.java
│        │                    │    └── state
│        │                    │        ├── AppStatePath.java
│        │                    │        ├── BookshelfState.java
│        │                    │        ├── CheckoutBookState.java
│        │                    │        ├── CheckoutRecordState.java
│        │                    │        ├── LibraryMemberState.java
│        │                    │        ├── LoginState.java
│        │                    │        ├── MainWindowState.java
│        │                    │        └── SearchOverdueBookState.java
│        │                    ├── ui
│        │                    │    ├── LibrarySystem.java
│        │                    │    ├── MenuItem.java
│        │                    │    ├── dialog
│        │                    │    │    ├── AddBookDialog.form
│        │                    │    │    ├── AddBookDialog.java
│        │                    │    │    ├── CopyBookDialog.form
│        │                    │    │    └── CopyBookDialog.java
│        │                    │    ├── listener
│        │                    │    │    └── LoginSubmitActionListener.java
│        │                    │    ├── panel
│        │                    │    │    ├── BookshelfPanel.form
│        │                    │    │    ├── BookshelfPanel.java
│        │                    │    │    ├── CheckoutBookPanel.java
│        │                    │    │    ├── LibPanel.java
│        │                    │    │    ├── LibraryMemberPanel.java
│        │                    │    │    ├── MenuPanel.java
│        │                    │    │    ├── SearchMemberCheckoutRecordPanel.java
│        │                    │    │    └── SearchOverdueBookPanel.java
│        │                    │    ├── renderer
│        │                    │    │    └── AvailableBookCopyCellRenderer.java
│        │                    │    └── window
│        │                    │        ├── LibWindow.java
│        │                    │        ├── LoginWindow.form
│        │                    │        ├── LoginWindow.java
│        │                    │        └── MainWindow.java
│        │                    ├── util
│        │                    │    ├── Colors.java
│        │                    │    ├── TypographyUtils.java
│        │                    │    └── UiUtils.java
│        │                    └── viewmodel
│        │                        ├── AuthenticationContextViewModel.java
│        │                        ├── BookshelfViewModel.java
│        │                        ├── CheckoutBookViewModel.java
│        │                        ├── CheckoutRecordViewModel.java
│        │                        ├── LibraryMemberViewModel.java
│        │                        ├── MainWindowViewModel.java
│        │                        └── SearchOverdueBookViewModel.java
│        └── resources
│            └── images
│                ├── add-book-color.png
│                ├── add-copy-book-color.png
│                ├── add-member-color.png
│                ├── checkout-book-color.png
│                ├── library.jpg
│                ├── logout-color.png
│                ├── search-checkout-color.png
│                └── search-overdue-color.png
</pre>

### Sequence diagram

#### Login Sequence diagram

![Login Sequence Diagram](dist/login-sequence-diagram.png?raw=true "State Management")

### Login credentials

| Username | Password |      Role |
|:---------|:--------:|----------:|
| 101      |   xyz    | LIBRARIAN |
| 102      |   abc    |     ADMIN |
| 103      |   111    |      BOTH |

### To format code using google-java-format with maven spotless plugin

```
mvn spotless:apply
```

### To build application with maven

```
mvn clean package
```

### To run application with Java

```
java -jar library-system-0.1.0-SNAPSHOT-jar-with-dependencies.jar
```

