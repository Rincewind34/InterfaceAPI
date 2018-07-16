package de.rincewind.test;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class TestInventory implements Inventory {
	
	private String name;
	
	private ItemStack[] content;
	
	public TestInventory(String name, int size) {
		this.name = name;
		this.content = new ItemStack[size];
	}
	
	@Override
	public HashMap<Integer, ItemStack> addItem(ItemStack... arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public HashMap<Integer, ? extends ItemStack> all(Material arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(ItemStack arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.content.length; i++) {
			this.content[i] = null;
		}
	}

	@Override
	public void clear(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Material arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(ItemStack arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Material arg0, int arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(ItemStack arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAtLeast(ItemStack arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int first(Material arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int first(ItemStack arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int firstEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ItemStack[] getContents() {
		return this.content;
	}

	@Override
	public InventoryHolder getHolder() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ItemStack getItem(int slot) {
		return this.content[slot];
	}

	@Override
	public Location getLocation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxStackSize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getSize() {
		return this.content.length;
	}

	@Override
	public ItemStack[] getStorageContents() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTitle() {
		return this.name;
	}

	@Override
	public InventoryType getType() {
		return InventoryType.CHEST;
	}

	@Override
	public List<HumanEntity> getViewers() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<ItemStack> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<ItemStack> iterator(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(Material arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(ItemStack arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public HashMap<Integer, ItemStack> removeItem(ItemStack... arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContents(ItemStack[] content) throws IllegalArgumentException {
		this.content = content;
	}

	@Override
	public void setItem(int slot, ItemStack item) {
		this.content[slot] = item;
	}

	@Override
	public void setMaxStackSize(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStorageContents(ItemStack[] arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

}
