package de.rincewind.interfaceplugin.gui.elements;

import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

import com.google.common.base.Predicates;

import de.rincewind.interfaceapi.exceptions.IllegalSlotContentException;
import de.rincewind.interfaceapi.gui.elements.ElementInput;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementStackChangeEvent;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementInput extends CraftElementSlot implements ElementInput {

	private int maxStackSize;

	private Predicate<ItemStack> checker;

	public CraftElementInput(WindowEditor handle) {
		super(handle);

		this.maxStackSize = 64;
		this.checker = Predicates.alwaysTrue();
		this.getBlocker().unlock();

		this.getEventManager().registerListener(ElementStackChangeEvent.class, (event) -> {
			if (!this.checker.test(event.getCourserItem())) {
				event.consume();
			}
		}).addAfter();
	}

	@Override
	public void setInputPredicate(Predicate<ItemStack> predicate, boolean force) {
		if (predicate == null) {
			predicate = Predicates.alwaysTrue();
		} else if (!this.isEmpty() && !predicate.test(this.getCurrentContent())) {
			if (force) {
				this.setContent(null);
			} else {
				throw new IllegalSlotContentException("Illegal item in slot for new predicate");
			}
		}

		this.checker = predicate;
	}

	@Override
	public void setMaxStackSize(int maxStackSize, boolean force) {
		if (!this.isEmpty() && this.getCurrentContent().getAmount() > maxStackSize) {
			if (force) {
				ItemStack item = this.getCurrentContent();
				item.setAmount(maxStackSize);
				this.setContent(item);
			} else {
				throw new IllegalSlotContentException("Illegal item in slot for new max-stack-size");
			}
		}
		
		this.maxStackSize = maxStackSize;
	}

	@Override
	public int getMaxStackSize() {
		return this.maxStackSize;
	}

	@Override
	public ItemStack popItem() {
		if (this.isEmpty()) {
			throw new IllegalSlotContentException("The slot is empty");
		}

		ItemStack result = this.getCurrentContent();
		assert InterfaceUtils.normalize(result) != null : "The slot content is null or AIR";
		assert this.checker.test(result) : "The slot content does not match current predicate";

		this.setContent(null);
		return result;
	}

	@Override
	public Predicate<ItemStack> getInputPredicate() {
		return this.checker;
	}

}
