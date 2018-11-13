package src.fr.triedge.sekai.common.model;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private List<Account> accounts = new ArrayList<>();
	private List<Item> items = new ArrayList<>();
	private List<Charact> onlineCharacters = new ArrayList<>();
	private List<Npc> npcs = new ArrayList<>();

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Charact> getOnlineCharacters() {
		return onlineCharacters;
	}

	public void setOnlineCharacters(List<Charact> onlineCharacters) {
		this.onlineCharacters = onlineCharacters;
	}

	public List<Npc> getNpcs() {
		return npcs;
	}

	public void setNpcs(List<Npc> npcs) {
		this.npcs = npcs;
	}
}
