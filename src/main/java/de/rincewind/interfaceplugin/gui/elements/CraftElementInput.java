package de.rincewind.interfaceplugin.gui.elements;

import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

import com.google.common.base.Predicates;

import de.rincewind.interfaceapi.exceptions.IllegalSlotContentException;
import de.rincewind.interfaceapi.gui.elements.ElementInput;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementInput extends CraftElementSlot implements ElementInput {

	private Predicate<ItemStack> checker;

	public CraftElementInput(WindowEditor handle) {
		super(handle);

		this.checker = Predicates.alwaysTrue();
		this.getBlocker().unlock();
	}

	@Override
	public void setInputPredicate(Predicate<ItemStack> predicate, boolean force) {
		if (predicate == null) {
			predicate = Predicates.alwaysTrue();
		} else if (!this.isEmpty() && !predicate.test(this.getContent())) {
			if (force) {
				this.setContent(null);
			} else {
				throw new IllegalSlotContentException("Illegal item in slot for new predicate");
			}
		}

		this.checker = predicate;
	}

	@Override
	public ItemStack popItem() {
		if (this.isEmpty()) {
			throw new IllegalSlotContentException("The slot is empty");
		}

		ItemStack result = this.getContent();
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
