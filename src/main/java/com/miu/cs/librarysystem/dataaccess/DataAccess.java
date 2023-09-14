package com.miu.cs.librarysystem.dataaccess;

import java.util.HashMap;
import java.util.Optional;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.LibraryMember;

public interface DataAccess {
	HashMap<String, Book> readBooksMap();

	HashMap<String, User> readUserMap();

	HashMap<String, LibraryMember> readMemberMap();

	void saveNewMember(LibraryMember member);

	void deleteMember(String memberId);
	
	void saveBook(Book book);

	Optional<Book> findBookByIsbn(String isbn);

	Optional<LibraryMember> findMemberById(String memberId);
}
