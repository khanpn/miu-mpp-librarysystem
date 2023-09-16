package edu.miu.cs.librarysystem.dataaccess;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.LibraryMember;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class DataAccessFacade implements DataAccess {

  enum StorageType {
    BOOKS,
    MEMBERS,
    USERS;
  }

  public static final String APP_DATA_DIR =
      System.getProperty("user.dir") + File.separator + "lib-data";
  public static final String DATE_PATTERN = "MM/dd/yyyy";

  // implement: other save operations
  public void saveNewMember(LibraryMember member) {
    HashMap<String, LibraryMember> mems = readMemberMap();
    String memberId = member.getMemberId();
    mems.put(memberId, member);
    saveToStorage(StorageType.MEMBERS, mems);
  }

  @SuppressWarnings("unchecked")
  public HashMap<String, Book> readBooksMap() {
    // Returns a Map with name/value pairs being
    // isbn -> Book
    return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
  }

  @SuppressWarnings("unchecked")
  public HashMap<String, LibraryMember> readMemberMap() {
    // Returns a Map with name/value pairs being
    // memberId -> LibraryMember
    return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
  }

  @SuppressWarnings("unchecked")
  public HashMap<String, User> readUserMap() {
    // Returns a Map with name/value pairs being
    // userId -> User
    return (HashMap<String, User>) readFromStorage(StorageType.USERS);
  }

  ///// load methods - these place test data into the storage area
  ///// - used just once at startup

  static void loadBookMap(List<Book> bookList) {
    HashMap<String, Book> books = new HashMap<String, Book>();
    for (Book book : bookList) {
      books.put(book.getIsbn(), book);
    }
    // bookList.forEach(book -> books.put(book.getIsbn(), book));
    saveToStorage(StorageType.BOOKS, books);
  }

  static void loadUserMap(List<User> userList) {
    HashMap<String, User> users = new HashMap<String, User>();
    userList.forEach(user -> users.put(user.getId(), user));
    saveToStorage(StorageType.USERS, users);
  }

  static void loadMemberMap(List<LibraryMember> memberList) {
    HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
    memberList.forEach(member -> members.put(member.getMemberId(), member));
    saveToStorage(StorageType.MEMBERS, members);
  }

  @Override
  public void deleteMember(String memberId) {
    HashMap<String, LibraryMember> mems = readMemberMap();
    mems.remove(memberId);
    saveToStorage(StorageType.MEMBERS, mems);
  }

  static void saveToStorage(StorageType type, Object ob) {
    ObjectOutputStream out = null;
    try {
      Path path = getDataPath().resolve(type.toString());
      out = new ObjectOutputStream(Files.newOutputStream(path));
      out.writeObject(ob);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (Exception e) {
        }
      }
    }
  }

  static Object readFromStorage(StorageType type) {
    ObjectInputStream in = null;
    Object retVal = null;
    try {
      Path path = getDataPath().resolve(type.toString());
      in = new ObjectInputStream(Files.newInputStream(path));
      retVal = in.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (Exception e) {
        }
      }
    }
    return retVal;
  }

  public static Path getDataPath() throws IOException {
    Path dataPath = Paths.get(APP_DATA_DIR);
    if (!Files.exists(dataPath)) {
      Files.createDirectories(dataPath);
    }
    return dataPath;
  }

  static final class Pair<S, T> implements Serializable {

    S first;
    T second;

    Pair(S s, T t) {
      first = s;
      second = t;
    }

    @Override
    public boolean equals(Object ob) {
      if (ob == null) return false;
      if (this == ob) return true;
      if (ob.getClass() != getClass()) return false;
      @SuppressWarnings("unchecked")
      Pair<S, T> p = (Pair<S, T>) ob;
      return p.first.equals(first) && p.second.equals(second);
    }

    @Override
    public int hashCode() {
      return first.hashCode() + 5 * second.hashCode();
    }

    @Override
    public String toString() {
      return "(" + first.toString() + ", " + second.toString() + ")";
    }

    private static final long serialVersionUID = 5399827794066637059L;
  }

  @Override
  public void saveBook(Book book) {
    HashMap<String, Book> books = readBooksMap();
    books.put(book.getIsbn(), book);
    saveToStorage(StorageType.BOOKS, books);
  }

  @Override
  public Optional<Book> findBookByIsbn(String isbn) {
    return Optional.ofNullable(readBooksMap().get(isbn));
  }

  @Override
  public Optional<LibraryMember> findMemberById(String memberId) {
    return Optional.ofNullable(readMemberMap().get(memberId));
  }
}
