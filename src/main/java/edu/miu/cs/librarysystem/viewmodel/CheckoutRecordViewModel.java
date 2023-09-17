package edu.miu.cs.librarysystem.viewmodel;

import edu.miu.cs.librarysystem.business.LibraryMember;

public class CheckoutRecordViewModel {
  private final String memberId;
  private final LibraryMember libraryMember;
  private final boolean printCheckoutRecords;

  public CheckoutRecordViewModel(
      String memberId, LibraryMember libraryMember, boolean printCheckoutRecord) {
    this.memberId = memberId;
    this.libraryMember = libraryMember;
    this.printCheckoutRecords = printCheckoutRecord;
  }

  public String getMemberId() {
    return memberId;
  }

  public LibraryMember getLibraryMember() {
    return libraryMember;
  }

  public boolean isPrintCheckoutRecords() {
    return printCheckoutRecords;
  }
}
