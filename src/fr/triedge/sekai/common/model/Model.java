package src.fr.triedge.sekai.common.model;

import java.util.ArrayList;

public class Model {

	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
}
