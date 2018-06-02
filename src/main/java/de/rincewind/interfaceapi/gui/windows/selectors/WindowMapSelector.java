package de.rincewind.interfaceapi.gui.windows.selectors;

import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowSizeable;
import de.rincewind.interfaceapi.handling.element.MapChangeSelectEvent;
import de.rincewind.interfaceapi.util.HeadsDatabase;

public class WindowMapSelector<T> extends WindowSelector<T> {

	protected final ElementItem barrier;

	protected final ElementItem buttonNext;
	protected final ElementItem buttonPrevious;

	protected final ElementMap typeMap;

	public WindowMapSelector(Plugin plugin, Consumer<T> onSelect, Iterable<T> typeElements, Class<T> typeClass) {
		super(plugin, onSelect, typeElements, typeClass);

		this.buttonNext = this.elementCreator().newItem();
		this.buttonNext.setPoint(new Point(8, 0));
		this.buttonNext.setIcon(HeadsDatabase.arrowWoodRight());
		this.buttonNext.setDisabledIcon(HeadsDatabase.arrowStoneRight());
		this.buttonPrevious = this.elementCreator().newItem();
		this.buttonPrevious.setIcon(HeadsDatabase.arrowWoodLeft());
		this.buttonPrevious.setDisabledIcon(HeadsDatabase.arrowStoneLeft());
		this.buttonPrevious.setPoint(new Point(0, 0));
		this.barrier = this.elementCreator().newItem();
		this.barrier.setIcon(new Icon(Material.BARRIER));
		this.barrier.setPoint(new Point(0, 0));
		this.barrier.setComponentValue(Element.WIDTH, 9);

		this.typeMap = this.elementCreator().newMap();
		this.typeMap.registerNextPageFliper(this.buttonNext);
		this.typeMap.registerPreviousPageFliper(this.buttonPrevious);
		this.typeMap.getEventManager().registerListener(MapChangeSelectEvent.class, (event) -> {
			this.select(event.getClicked());
		}).monitor();

		for (T element : typeElements) {
			this.typeMap.addItem(Displayable.of(element));
		}

		int height = WindowSizeable.calculateHeight(this.typeMap.size());

		this.setSize(9, Math.min(height, 6));
		this.typeMap.setComponentValue(Element.WIDTH, this.getWidth());

		this.showControlStrip(height > 6);
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		
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
			this.typeMap.setComponentValue(Element.HEIGHT, this.getHeight() - 3);

			this.barrier.setPoint(new Point(0, this.getHeight() - 2));
			this.buttonNext.setPoint(new Point(this.buttonNext.getPoint().getX(), this.getHeight() - 1));
			this.buttonPrevious.setPoint(new Point(this.buttonPrevious.getPoint().getX(), this.getHeight() - 1));
		}
	}

	public boolean isShowControlStrip() {
		return this.barrier.isVisible();
	}

}
