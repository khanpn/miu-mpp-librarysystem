package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.viewmodel.SearchOverdueBookViewModel;

public class SearchOverdueBookState extends State<SearchOverdueBookViewModel> {
  public SearchOverdueBookState(SearchOverdueBookViewModel data) {
    super(AppStatePath.SEARCH_OVERDUE_BOOK, data);
  }
}
