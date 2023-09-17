package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.viewmodel.CheckoutBookViewModel;

public class CheckoutBookState extends State<CheckoutBookViewModel> {
  public CheckoutBookState(CheckoutBookViewModel data) {
    super(AppStatePath.CHECKOUT_BOOK, data);
  }
}
