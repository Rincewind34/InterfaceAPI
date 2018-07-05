package de.rincewind.interfaceapi.selectors.window;

import java.util.Collection;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowSizeable;
import de.rincewind.interfaceapi.handling.element.MapChangeSelectEvent;
import de.rincewind.interfaceapi.util.HeadsDatabase;

public class WindowMapSelector<T> extends WindowSelector<T> {

	protected final ElementItem barrier;

	protected final ElementItem buttonNext;
	protected final ElementItem buttonPrevious;

	protected final ElementMap typeMap;

	public WindowMapSelector(Plugin plugin, Consumer<T> onSelect, Collection<T> typeElements, boolean defaultSet, T defaultValue, Class<T> typeClass) {
		super(plugin, onSelect, typeElements, defaultSet, defaultValue, typeClass);

		this.buttonNext = this.elementCreator().newItem();
		this.buttonNext.setPoint(Point.of(8, 0));
		this.buttonNext.setIcon(HeadsDatabase.arrowWoodRight());
		this.buttonNext.setDisabledIcon(HeadsDatabase.arrowStoneRight());
		this.buttonPrevious = this.elementCreator().newItem();
		this.buttonPrevious.setIcon(HeadsDatabase.arrowWoodLeft());
		this.buttonPrevious.setDisabledIcon(HeadsDatabase.arrowStoneLeft());
		this.buttonPrevious.setPoint(Point.NULL);
		this.barrier = this.elementCreator().newBarrier(Point.NULL, 9);

		this.typeMap = this.elementCreator().newMap();
		this.typeMap.registerNextPageFliper(this.buttonNext);
		this.typeMap.registerPreviousPageFliper(this.buttonPrevious);
		this.typeMap.getEventManager().registerListener(MapChangeSelectEvent.class, (event) -> {
			this.select(event.getClicked());
		}).monitor();

		for (T element : typeElements) {
			this.typeMap.addItem(this.displayableOf(element));
		}

		int height = WindowSizeable.calculateHeight(this.typeMap.size());

		this.setSize(9, Math.min(height, 6));
		this.typeMap.setComponentValue(Element.WIDTH, this.getWidth());

		this.showControlStrip(height > 6);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);

		this.updateControlStrip();
	}

	public void updateControlStrip() {
		this.showControlStrip(this.isShowControlStrip());
	}

	public void showControlStrip(boolean value) {
		// Do not check if the control strip is currently visible so that it
		// works as update method

		this.buttonNext.setVisible(value);
		this.buttonPrevious.setVisible(value);
		this.barrier.setVisible(value);

		if (!value) {
			this.typeMap.setComponentValue(Element.HEIGHT, this.getHeight());
		} else {
			this.typeMap.setComponentValue(Element.HEIGHT, this.getHeight() - 2);

			this.barrier.setPoint(Point.of(0, this.getHeight() - 2));
			this.buttonNext.setPoint(Point.of(this.buttonNext.getPoint().getX(), this.getHeight() - 1));
			this.buttonPrevious.setPoint(Point.of(this.buttonPrevious.getPoint().getX(), this.getHeight() - 1));
		}
	}

	public boolean isShowControlStrip() {
		return this.barrier.isVisible();
	}

	protected Displayable displayableOf(T value) {
		return Displayable.of(value);
	}

}
