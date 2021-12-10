package vendingmachine.controller;

import java.util.Map;

import vendingmachine.domain.Change;
import vendingmachine.domain.Coin;
import vendingmachine.view.InputView;

public class MachineController {

	public void run() {
		Change change = new Change(InputView.getHavingMoney());
		Map<Coin, Integer> changes = change.generateChanges();
	}

}
