package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.service.LibraryMemberService;
import edu.miu.cs.librarysystem.store.action.checkoutrecord.CheckoutRecordPrintAction;
import edu.miu.cs.librarysystem.store.action.checkoutrecord.CheckoutRecordSearchAction;
import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.state.CheckoutRecordState;
import edu.miu.cs.librarysystem.viewmodel.CheckoutRecordViewModel;
import java.util.Optional;

public class CheckoutRecordReducer implements Reducer<CheckoutRecordState, Action<?>> {

  @Override
  public CheckoutRecordState reduce(Action<?> action) {
    String memberId = null;
    LibraryMember libraryMember = null;
    boolean printCheckoutRecords = false;
    if (action instanceof CheckoutRecordSearchAction searchAction) {
      memberId = searchAction.getData();
      libraryMember = searchMember(memberId).orElse(null);
    } else if (action instanceof CheckoutRecordPrintAction checkoutRecordPrintAction) {
      memberId = checkoutRecordPrintAction.getData();
      libraryMember = searchMember(memberId).orElse(null);
      printCheckoutRecords = true;
    }
    return new CheckoutRecordState(
        new CheckoutRecordViewModel(memberId, libraryMember, printCheckoutRecords));
  }

  private Optional<LibraryMember> searchMember(String memberId) {
    return LibraryMemberService.getInstance().findMemberById(memberId);
  }

  @Override
  public <O extends Action<?>> boolean canReduce(O action) {
    return action instanceof CheckoutRecordSearchAction
        || action instanceof CheckoutRecordPrintAction;
  }
}
