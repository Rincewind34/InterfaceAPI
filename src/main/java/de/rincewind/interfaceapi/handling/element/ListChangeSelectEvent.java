package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementList;

/**
 * Does not guarantee that the event always fires if
 * {@link ElementList#getSelectedIndex()} changes (e.g.
 * {@link ElementList#addItem(int, Displayable)})
 *
 */
public class ListChangeSelectEvent extends ElementValueChangeEvent<ElementList> {

	private int newIndex;

	public ListChangeSelectEvent(ElementList element, int newIndex) {
		super(element, element.getSelected());

		this.newIndex = newIndex;
	}

	public boolean wasUnselect() {
		return this.newIndex == -1;
	}

	public int getNewIndex() {
		return this.newIndex;
	}

	public <T> T getClicked() {
		return this.getElement().get(this.newIndex);
	}

	public <T> T getClicked(Class<T> cls) {
		return this.getElement().get(cls, this.newIndex);
	}

}
