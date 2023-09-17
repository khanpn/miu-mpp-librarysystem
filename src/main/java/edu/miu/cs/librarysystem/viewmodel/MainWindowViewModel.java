package edu.miu.cs.librarysystem.viewmodel;

import edu.miu.cs.librarysystem.ui.MenuItem;

public class MainWindowViewModel {
  private final MenuItem selectedMenuItem;

  public MainWindowViewModel(MenuItem selectedMenuItem) {
    this.selectedMenuItem = selectedMenuItem;
  }

  public MenuItem getSelectedMenuItem() {
    return selectedMenuItem;
  }
}
