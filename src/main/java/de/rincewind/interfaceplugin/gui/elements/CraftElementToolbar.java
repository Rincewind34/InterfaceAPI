package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.ElementToolbar;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.SelectModifiers;
import de.rincewind.interfaceapi.gui.util.Direction;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.gui.windows.util.Toolbar;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementToolbar extends CraftElement implements ElementToolbar {

	public static String INSTRUCTIONS_SELECT = "§7§lLK: §7§oDiesen Tab auswählen";
	public static String INSTRUCTIONS_SELECTED = "§7§oTab ist ausgewählt";
	public static String INSTRUCTIONS_UNSELECT = "§7§lLK: §7§oDiesen Tab abwählen";

	private boolean canUnselect;

	private Direction direction;
	private Displayable disabledIcon;

	private final Toolbar toolbar;
	
	private UnaryOperator<Icon> modifier;
	private final List<Button> buttons;

	public CraftElementToolbar(WindowEditor handle) {
		super(handle);

		this.direction = Direction.HORIZONTAL;
		this.disabledIcon = DisplayableDisabled.default_icon;
		this.modifier = SelectModifiers.CYAN_GLASS;

		this.buttons = new ArrayList<>();
		this.toolbar = new ToolbarWrapper();

		this.getComponent(Element.ENABLED).setEnabled(true);
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
		this.getComponent(Element.INSTRUCTIONS).setEnabled(true);
	}

	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledIcon = Displayable.checkNull(icon);

		if (!this.isEnabled()) {
			this.update();
		}
	}

	@Override
	public void addButton(String toolset, Displayable icon) {
		Validate.notNull(toolset, "The toolset cannot be null");

		this.buttons.add(new Button(toolset, Displayable.checkNull(icon)));
		this.updateEnabled();
	}

	@Override
	public void removeButton(String toolset) {
		Validate.notNull(toolset, "The toolset cannot be null");

		if (this.buttons.removeIf((button) -> {
			return button.toolset.equals(toolset);
		})) {
			this.update();
		}
	}

	@Override
	public void setCanUnselect(boolean value) {
		this.canUnselect = value;
		this.updateEnabled();
	}

	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
		this.updateEnabled();
	}
	
	@Override
	public void setSelectModifyer(UnaryOperator<Icon> modifier) {
		this.modifier = modifier;
		
		if (this.isEnabled() && this.toolbar.isToolsetActive()) {
			this.update();
		}
	}
	
	@Override
	public boolean canUnselect() {
		return this.canUnselect;
	}
	
	@Override
	public Direction getDirection() {
		return this.direction;
	}
	
	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon.getIcon();
	}

	@Override
	public Toolbar getToolbar() {
		return this.toolbar;
	}
	
	@Override
	public UnaryOperator<Icon> getSelectModifier() {
		return this.modifier;
	}

	@Override
	public Set<String> getSelectableToolsets() {
		return Collections.unmodifiableSet(this.buttons.stream().map(Button::toolset).collect(Collectors.toSet()));
	}

	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			int index = point.getCoord(this.direction);

			if (index < this.buttons.size()) {
				String instructions = CraftElementToolbar.INSTRUCTIONS_SELECT;
				Button button = this.buttons.get(index);

				if (this.toolbar.isToolsetActive() && this.toolbar.getActiveToolset().equals(button.toolset)) {
					instructions = this.canUnselect ? CraftElementToolbar.INSTRUCTIONS_UNSELECT : CraftElementToolbar.INSTRUCTIONS_SELECTED;
				}
				
				return this.updateInstructions(this.modifier.apply(button.icon.getIcon()), instructions);
			} else {
				return Icon.AIR; // TODO Error icon
			}
		} else {
			return this.getDisabledIcon();
		}
	}

	private class ToolbarWrapper implements Toolbar {

		private Toolbar handler;

		public ToolbarWrapper() {
			this.handler = InterfaceAPI.createToolbar();
		}

		@Override
		public void addElement(Element element, String... toolsets) {
			this.handler.addElement(element, toolsets);
		}

		@Override
		public void addElements(Iterable<Element> elements, String... toolsets) {
			this.handler.addElements(elements, toolsets);
		}

		@Override
		public void removeElement(Element element) {
			this.handler.removeElement(element);
		}

		@Override
		public void removeElements(Iterable<Element> elements) {
			this.handler.removeElements(elements);
		}

		@Override
		public void activateToolSet(String toolset) {
			this.handler.activateToolSet(toolset);
			CraftElementToolbar.this.update();
		}

		@Override
		public void deactivateToolset() {
			this.handler.deactivateToolset();
			CraftElementToolbar.this.update();
		}

		@Override
		public boolean isToolsetActive() {
			return this.handler.isToolsetActive();
		}

		@Override
		public String getActiveToolset() {
			return this.handler.getActiveToolset();
		}

		@Override
		public Runnable actiovationListener(String toolset) {
			return this.handler.actiovationListener(toolset);
		}

		@Override
		public Set<Element> getElements() {
			return this.handler.getElements();
		}

		@Override
		public Set<Element> getElements(String toolset) {
			return this.handler.getElements(toolset);
		}

	}

	private static class Button {

		private final String toolset;

		private Displayable icon;

		public Button(String toolset, Displayable icon) {
			this.toolset = toolset;
			this.icon = icon;
		}

		public String toolset() {
			return this.toolset;
		}

	}

}
