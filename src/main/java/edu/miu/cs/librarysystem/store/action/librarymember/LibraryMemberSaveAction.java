package edu.miu.cs.librarysystem.store.action.librarymember;

import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.store.core.action.Action;

public class LibraryMemberSaveAction extends Action<LibraryMember> {
  public LibraryMemberSaveAction(LibraryMember data) {
    super(data);
  }
}
