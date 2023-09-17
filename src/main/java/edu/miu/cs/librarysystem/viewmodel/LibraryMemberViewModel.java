package edu.miu.cs.librarysystem.viewmodel;

import edu.miu.cs.librarysystem.business.LibraryMember;
import java.util.List;

public class LibraryMemberViewModel {
  private List<LibraryMember> members;

  public LibraryMemberViewModel(List<LibraryMember> members) {
    this.members = members;
  }

  public List<LibraryMember> getMembers() {
    return members;
  }
}
