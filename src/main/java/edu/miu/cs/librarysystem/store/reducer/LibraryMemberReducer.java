package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.service.LibraryMemberService;
import edu.miu.cs.librarysystem.store.action.librarymember.LibraryMemberDeleteAction;
import edu.miu.cs.librarysystem.store.action.librarymember.LibraryMemberGetAllMemberAction;
import edu.miu.cs.librarysystem.store.action.librarymember.LibraryMemberSaveAction;
import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.state.LibraryMemberState;
import edu.miu.cs.librarysystem.viewmodel.LibraryMemberViewModel;
import java.util.ArrayList;
import java.util.List;

public class LibraryMemberReducer implements Reducer<LibraryMemberState, Action<?>> {
  @Override
  public LibraryMemberState reduce(Action<?> action) {
    if (action instanceof LibraryMemberGetAllMemberAction) {
      return reloadState();
    } else if (action instanceof LibraryMemberDeleteAction deleteAction) {
      String memberId = deleteAction.getData();
      LibraryMemberService.getInstance().deleteMember(memberId);
      return reloadState();
    } else if (action instanceof LibraryMemberSaveAction saveAction) {
      LibraryMember member = saveAction.getData();
      LibraryMemberService.getInstance().save(member);
      return reloadState();
    }
    return new LibraryMemberState(new LibraryMemberViewModel(new ArrayList<>()));
  }

  private LibraryMemberState reloadState() {
    List<LibraryMember> allMembers = LibraryMemberService.getInstance().getAllMembers();
    LibraryMemberViewModel viewModel = new LibraryMemberViewModel(allMembers);
    return new LibraryMemberState(viewModel);
  }

  @Override
  public <O extends Action<?>> boolean canReduce(O action) {
    return action instanceof LibraryMemberGetAllMemberAction
        || action instanceof LibraryMemberDeleteAction
        || action instanceof LibraryMemberSaveAction;
  }
}
