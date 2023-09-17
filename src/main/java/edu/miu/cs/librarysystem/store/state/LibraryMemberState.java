package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.viewmodel.LibraryMemberViewModel;

public class LibraryMemberState extends State<LibraryMemberViewModel> {
  public LibraryMemberState(LibraryMemberViewModel data) {
    super(AppStatePath.LIBRARY_MEMBER, data);
  }
}
