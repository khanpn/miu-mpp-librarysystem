package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.store.action.mainwindow.MainWindowMenuSelectItemAction;
import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.state.MainWindowState;
import edu.miu.cs.librarysystem.ui.MenuItem;
import edu.miu.cs.librarysystem.viewmodel.MainWindowViewModel;

public class MainWindowReducer implements Reducer<MainWindowState, Action<?>> {
  @Override
  public MainWindowState reduce(Action<?> action) {
    MenuItem selectItem = null;
    if (action instanceof MainWindowMenuSelectItemAction selectItemAction) {
      selectItem = selectItemAction.getData();
    }
    return new MainWindowState(new MainWindowViewModel(selectItem));
  }

  @Override
  public <O extends Action<?>> boolean canReduce(O action) {
    return action instanceof MainWindowMenuSelectItemAction;
  }
}
