package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.viewmodel.CheckoutRecordViewModel;

public class CheckoutRecordState extends State<CheckoutRecordViewModel> {
  public CheckoutRecordState(CheckoutRecordViewModel data) {
    super(AppStatePath.CHECKOUT_RECORD, data);
  }
}
