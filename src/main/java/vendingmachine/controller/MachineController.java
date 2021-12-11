package vendingmachine.controller;

import java.util.ArrayList;

import vendingmachine.domain.Beverage;
import vendingmachine.domain.Beverages;
import vendingmachine.domain.Change;
import vendingmachine.domain.Money;
import vendingmachine.domain.VendingMachine;
import vendingmachine.validator.InputValidator;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public class MachineController {
	private static final int itemIndex = 0;
	private static final int priceIndex = 1;
	private static final int stockIndex = 2;
	private static final String PRICE = "금액";
	private static final String STOCK = "수량";

	private VendingMachine vendingMachine;

	public void run() {
		setting();
		progress();
		finishWithReturn();
	}

	private void setting() {
		Change changes = Change.generateChanges(InputView.getHavingMoney());
		OutputView.printHavingMachineCoin();
		ArrayList<String> itemPriceStock = InputView.getItemPriceStock();
		Beverages beverages = splitItem(itemPriceStock);
		Money inputMoney = InputView.getUserInputMoney();
		vendingMachine = new VendingMachine(beverages, changes, inputMoney);
	}

	private void progress() {
		while (vendingMachine.canSell()) {
			OutputView.printInputMoney(vendingMachine.getMoney());
			String itemName = InputView.getItemName();
			Beverage beverage = vendingMachine.findBeverageByName(itemName);
			vendingMachine.sell(beverage);
		}
	}

	private void finishWithReturn() {
		Change calculate = vendingMachine.calculate();
	}

	private Beverages splitItem(ArrayList<String> itemPriceStock) {
		Beverages beverages = new Beverages();
		for (String itemInfoList : itemPriceStock) {
			String[] itemInfo = itemInfoList.split(",");
			String name = itemInfo[itemIndex];
			int price = InputValidator.checkNumberForm(itemInfo[priceIndex], PRICE);
			int stock = InputValidator.checkNumberForm(itemInfo[stockIndex], STOCK);
			beverages.add(new Beverage(name, price), stock);
		}
		return beverages;
	}

}
