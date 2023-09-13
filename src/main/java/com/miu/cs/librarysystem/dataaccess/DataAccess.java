package com.miu.cs.librarysystem.dataaccess;

import java.util.HashMap;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.LibraryMember;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member); 
}
