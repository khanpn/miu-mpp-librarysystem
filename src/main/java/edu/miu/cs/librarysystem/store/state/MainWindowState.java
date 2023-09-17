package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.viewmodel.MainWindowViewModel;

public class MainWindowState extends State<MainWindowViewModel> {
  public MainWindowState(MainWindowViewModel data) {
    super(AppStatePath.MAIN_WINDOW, data);
  }
}
