package de.rincewind.interfaceapi.gui.windows;

import de.rincewind.interfaceplugin.gui.windows.CraftWindowSizeable;

@Deprecated
public class WindowFileBrowser extends CraftWindowSizeable {
	
//	private DebugScene cache;
//	
//	private ElementItem item;
//	private ElementItem itemInfo;
//	private ElementInfo infoPanel;
//	private ElementButton btnDirup;
//	
//	private List<ElementButton> files;
//	
//	private ElementButton btnNewFile;
//	private ElementButton btnNewFolder;
//	private ElementButton btnPaste;
//	
//	private ElementButton btnDelete;
//	private ElementButton btnRename;
//	private ElementButton btnCopy;
//	private ElementButton btnOpen;
//	private ElementButton btnOpenWith;
//	private ElementButton btnUnselect;
//	
//	private FileBrowser browser;
//	
//	private Toolbar toolbar;
//	
//	public WindowFileBrowser(FileBrowser browser) {
//		this.browser = browser;
//		this.files = new ArrayList<>();
//		
//		this.setName("§8Filebrowser");
//		this.setSize(9, 6);
//		this.setColor(Color.BLACK);
//		
//		this.toolbar = new Toolbar();
//		
//		this.item = this.elementCreator().newItem();
//		this.item.setIcon(new Icon(Material.IRON_FENCE, 0, " "));
//		this.item.setPoint(new Point(0, 4));
//		this.item.setComponentValue(Element.WIDTH, 9);
//		this.item.setComponentValue(Element.HEIGHT, 1);
//		
//		this.btnDirup = this.elementCreator().newButton();
//		this.btnDirup.setIcon(new Icon(Material.IRON_DOOR, 0, "§7Folder up"));
//		this.btnDirup.setPoint(new Point(8, 5));
//		this.btnDirup.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.itemInfo = this.elementCreator().newItem();
//		this.itemInfo.setIcon(new Icon(Material.SIGN).rename("§6Information:"));
//		this.itemInfo.setPoint(new Point(6, 5));
//		
//		this.infoPanel = this.elementCreator().newInfoPanel();
//		this.infoPanel.setPoint(new Point(7, 5));
//		
//		this.btnNewFile = this.elementCreator().newButton();
//		this.btnNewFile.setIcon(new Icon(Material.PAPER, 0, "§a+§7File"));
//		this.btnNewFile.setPoint(new Point(0, 5));
//		this.btnNewFile.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnNewFolder = this.elementCreator().newButton();
//		this.btnNewFolder.setIcon(new Icon(Material.CHEST, 0, "§a+§7Directory"));
//		this.btnNewFolder.setPoint(new Point(1, 5));
//		this.btnNewFolder.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnPaste = this.elementCreator().newButton();
//		this.btnPaste.setIcon(new Icon(Material.NETHER_STAR, 0, "§bPaste clipboard"));
//		this.btnPaste.setPoint(new Point(2, 5));
//		this.btnPaste.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnDelete = this.elementCreator().newButton();
//		this.btnDelete.setIcon(new Icon(Material.BARRIER, 0, "§cDelete"));
//		this.btnDelete.setPoint(new Point(0, 5));
//		this.btnDelete.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnRename = this.elementCreator().newButton();
//		this.btnRename.setIcon(new Icon(Material.ANVIL, 0, "§8Rename"));
//		this.btnRename.setPoint(new Point(1, 5));
//		this.btnRename.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnCopy = this.elementCreator().newButton();
//		this.btnCopy.setIcon(new Icon(Material.NETHER_STAR, 0, "§bCopy file"));
//		this.btnCopy.setPoint(new Point(2, 5));
//		this.btnCopy.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnOpen = this.elementCreator().newButton();
//		this.btnOpen.setIcon(new Icon(Material.ENDER_CHEST, 0, "§dOpen"));
//		this.btnOpen.setPoint(new Point(3, 5));
//		this.btnOpen.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnOpenWith = this.elementCreator().newButton();
//		this.btnOpenWith.setIcon(new Icon(Material.ENDER_CHEST, 0, "§dOpen with"));
//		this.btnOpenWith.setPoint(new Point(4, 5));
//		this.btnOpenWith.getEventManager().registerListener(new Handler()).addAfter();
//		
//		this.btnUnselect = this.elementCreator().newButton();
//		this.btnUnselect.setIcon(new Icon(Material.ARROW, 0, "§7Unselect"));
//		this.btnUnselect.setPoint(new Point(8, 5));
//		this.btnUnselect.getEventManager().registerListener(new Handler()).addAfter(); 
//		
//		this.toolbar.addElement(this.btnDirup, "default");
//		this.toolbar.addElement(this.btnNewFile, "default");
//		this.toolbar.addElement(this.btnNewFolder, "default");
//		this.toolbar.addElement(this.btnPaste, "default");
//		this.toolbar.addElement(this.btnDelete, "file", "folder");
//		this.toolbar.addElement(this.btnRename, "file", "folder");
//		this.toolbar.addElement(this.btnCopy, "file", "folder");
//		this.toolbar.addElement(this.btnOpen, "file", "folder");
//		this.toolbar.addElement(this.btnOpenWith, "file");
//		this.toolbar.addElement(this.btnUnselect, "file", "folder");
//		
//		this.render();
//		this.getEventManager().registerListener(new WindowMaximizeListener() {
//			
//			@Override
//			public void onFire(WindowMaximizeEvent event) {
//				WindowFileBrowser.this.browser.select(null);
//				WindowFileBrowser.this.render();
//				
//				if (WindowFileBrowser.this.cache != null) {
//					WindowFileBrowser.this.infoPanel.debug(WindowFileBrowser.this.cache.getDebug(), WindowFileBrowser.this.cache.isPositiv());
//					WindowFileBrowser.this.cache = null;
//				}
//			}
//		}).addAfter();
//	}
//	
//	private void render() {
//		for (Element element : this.files) {
//			this.removeElement(element);
//		}
//		
//		this.files.clear();
//		
//		int pointerX = 0;
//		int pointerY = 0;
//		
//		for (File target : this.browser.getMatchingDirectory().listFiles()) {
//			if (target.getName().startsWith(".")) {
//				continue;
//			}
//			
//			boolean selected = this.browser.getSelectedFile() != null && this.browser.getSelectedFile().equals(target);
//			
//			Point point = new Point(pointerX, pointerY);
//			ElementButton btn = this.elementCreator().newButton();
//			btn.setPoint(point);
//			btn.getEventManager().registerListener(new SelectHandler()).addAfter();
//			
//			if (target.isFile()) {
//				boolean flag = false;
//				
//				for (FileType type : this.browser.getTypes()) {
//					if (type.isMatching(target)) {
//						Icon icon = type.getIconCreator().get();
//						icon = icon.rename(icon.getName().replace("${name}", target.getName())).unenchant();
//						
//						btn.setIcon(icon);
//						flag = true;
//						break;
//					}
//				}
//				
//				if (!flag) {
//					btn.setIcon(new Icon(selected ? Material.EMPTY_MAP : Material.PAPER).rename("§f" + target.getName()));
//				}
//			} else if (target.isDirectory()) {
//				btn.setIcon(new Icon(selected ? Material.ENDER_CHEST : Material.CHEST).rename("§6" + target.getName()));
//			}
//			
//			this.files.add(btn);
//			
//			pointerX = pointerX + 1;
//			
//			if (pointerX == 9) {
//				pointerX = 0;
//				pointerY = pointerY + 1;
//			}
//			
//			if (pointerY == 4) {
//				break; // TODO
//			}
//		}
//		
//		this.renderToolbar();
//		
//		Lore lore = new Lore()
//				.add("§8====================")
//				.add("  §7Current Dir: §6" + this.browser.getMatchingDirectory().getName())
//				.add("  §7Filecount: §6" + this.browser.getMatchingDirectory().listFiles().length)
//				.add("§8====================");
//		
//		this.itemInfo.setIcon(this.itemInfo.getIcon().describe(lore));
//	}
//	
//	private void renderToolbar() {
//		this.infoPanel.reset();
//		this.toolbar.activateToolSet(this.browser.getSelectedFile() == null ? "default" : (this.browser.getSelectedFile().isFile() ? "file" : "folder"));
//		this.update();
//	}
//	
//	private void delete(File file) {
//		if (file.exists()) {
//			if (file.isFile()) {
//				file.delete();
//			} else {
//				for (File target : file.listFiles()) {
//					this.delete(target);
//				}
//				
//				file.delete();
//			}
//		}
//	}
//	
//	
//	private class Handler extends ButtonPressListener {
//		
//		@Override
//		public void onFire(ButtonPressEvent event) {
//			if (event.getElement() == WindowFileBrowser.this.btnNewFile || event.getElement() == WindowFileBrowser.this.btnNewFolder) {
//				boolean btnFile = event.getElement() == WindowFileBrowser.this.btnNewFile;
//
//				WindowAnvil window = new WindowAnvil();
//				window.setDisplay(new Icon(btnFile ? Material.PAPER : Material.CHEST).rename(">"));
//				window.getEventManager().registerListener(new AnvilNameListener() {
//					
//					@Override
//					public void onFire(AnvilNameEvent event) {
//						String name = event.getName().startsWith(">") ? event.getName().substring(1, event.getName().length()) : event.getName();
//						
//						File file = new File(WindowFileBrowser.this.browser.getMatchingDirectory(), name);
//						
//						if (!file.exists()) {
//							if (btnFile) {
//								try {
//									file.createNewFile();
//								} catch (IOException e) {
//									WindowFileBrowser.this.cache = new DebugScene(Arrays.asList("§7" + e.getMessage()), false);
//								}
//							} else {
//								file.mkdir();
//							}
//						} else {
//							WindowFileBrowser.this.cache = new DebugScene(Arrays.asList("§7The file or folder", "§7already exists!"), false);
//						}
//					}
//				}).addAfter();
//				
//				InterfaceAPI.getSetup(event.getPlayer()).open(window);
//			} else if (event.getElement() == WindowFileBrowser.this.btnDirup) {
//				if (!WindowFileBrowser.this.browser.isTopDirectory()) {
//					WindowFileBrowser.this.browser.navigateUp();
//					WindowFileBrowser.this.render();
//				} else {
//					WindowFileBrowser.this.infoPanel.debug("§7Already topdirectory", false);
//				}
//			} else if (event.getElement() == WindowFileBrowser.this.btnDelete) {
//				WindowConfirm window = new WindowConfirm("§8Do you realy want to do that?", (result) -> {
//					if (result) {
//						WindowFileBrowser.this.delete(WindowFileBrowser.this.browser.getSelectedFile());
//						WindowFileBrowser.this.browser.select(null);
//						WindowFileBrowser.this.render();
//					}
//				});
//				
//				InterfaceAPI.getSetup(event.getPlayer()).open(window);
//			} else if (event.getElement() == WindowFileBrowser.this.btnRename) {
//				WindowAnvil window = new WindowAnvil();
//				window.setDisplay(new Icon(WindowFileBrowser.this.browser.getSelectedFile().isFile() ? Material.PAPER : Material.CHEST)
//						.rename(WindowFileBrowser.this.browser.getSelectedFile().getName()));
//				window.getEventManager().registerListener(new AnvilNameListener() {
//					
//					@Override
//					public void onFire(AnvilNameEvent event) {
//						String name = WindowFileBrowser.this.browser.getSelectedFile().getAbsolutePath();
//						File file = null;
//						
//						if (name.contains("/")) {
//							file = new File(name.substring(0, name.lastIndexOf("/")), event.getName());
//						} else {
//							file = new File(event.getName());
//						}
//						
//						if (!file.exists()) {
//							WindowFileBrowser.this.browser.getSelectedFile().renameTo(file);
//							WindowFileBrowser.this.browser.select(null);
//						} else {
//							WindowFileBrowser.this.cache = new DebugScene(Arrays.asList("§7The file or folder", "§7already exists!"), false);
//						}
//					}
//				}).addAfter();
//				
//				InterfaceAPI.getSetup(event.getPlayer()).open(window);
//			} else if (event.getElement() == WindowFileBrowser.this.btnCopy) {
//				
//			} else if (event.getElement() == WindowFileBrowser.this.btnOpen) {
//				if (WindowFileBrowser.this.browser.getSelectedFile().isDirectory()) {
//					WindowFileBrowser.this.browser.navigate(WindowFileBrowser.this.browser.getSelectedFile().getName());
//					WindowFileBrowser.this.render();
//				} else if (WindowFileBrowser.this.browser.getSelectedFile().isFile()) {
//					for (FileType type : WindowFileBrowser.this.browser.getTypes()) {
//						if (type.isMatching(WindowFileBrowser.this.browser.getSelectedFile())) {
//							if (WindowFileBrowser.this.browser.getPrograms(type.getName()).size() != 0) {
//								Program program = WindowFileBrowser.this.browser.getPrograms(type.getName()).get(0);
//								program.execute(WindowFileBrowser.this.browser.getSelectedFile());
//								return;
//							}
//						}
//					}
//					
//					WindowFileBrowser.this.infoPanel.debug("§7No program found", false);
//				}
//			} else if (event.getElement() == WindowFileBrowser.this.btnOpenWith) {
//				if (WindowFileBrowser.this.browser.getSelectedFile().isFile()) {
//					for (FileType type : WindowFileBrowser.this.browser.getTypes()) {
//						if (type.isMatching(WindowFileBrowser.this.browser.getSelectedFile())) {
//							int x = 0;
//							
//							List<ElementButton> btns = new ArrayList<>();
//							
//							ElementButton btnBack = WindowFileBrowser.this.elementCreator().newButton();
//							btnBack.setPoint(new Point(8, 5));
//							btnBack.setIcon(new Icon(Material.ARROW, 0, "§7Unselect"));
//							btnBack.getEventManager().registerListener(new ButtonPressListener() {
//								
//								@Override
//								public void onFire(ButtonPressEvent event) {
//									for (ElementButton btn : btns) {
//										WindowFileBrowser.this.removeElement(btn);
//									}
//									
//									WindowFileBrowser.this.removeElement(btnBack);
//									WindowFileBrowser.this.browser.select(null);
//									WindowFileBrowser.this.render();
//								}
//							});
//							
//							for (Program program : WindowFileBrowser.this.browser.getPrograms(type.getName())) {
//								ElementButton btn = WindowFileBrowser.this.elementCreator().newButton();
//								btn.setIcon(new Icon(Material.ENDER_CHEST, 0, "§7Open with: §d" + program.getName()));
//								btn.setPoint(new Point(x, 5));
//								btn.getEventManager().registerListener(new ButtonPressListener() {
//									
//									@Override
//									public void onFire(ButtonPressEvent event) {
//										for (ElementButton btn : btns) {
//											WindowFileBrowser.this.removeElement(btn);
//										}
//										
//										WindowFileBrowser.this.removeElement(btnBack);
//										
//										program.execute(WindowFileBrowser.this.browser.getSelectedFile());
//									}
//								});
//								
//								btns.add(btn);
//							}
//							
//							WindowFileBrowser.this.toolbar.activateToolSet("null");
//							return;
//						}
//					}
//					
//					WindowFileBrowser.this.infoPanel.debug("§7No program found", false);
//				}
//			} else if (event.getElement() == WindowFileBrowser.this.btnUnselect) {
//				WindowFileBrowser.this.browser.select(null);
//				WindowFileBrowser.this.render();
//			}
//		}
//		
//	}
//	
//	private class SelectHandler extends ButtonPressListener {
//
//		@Override
//		public void onFire(ButtonPressEvent event) {
//			FileBrowser browser = WindowFileBrowser.this.browser;
//			
//			if (browser.getSelectedFile() != null) {
//				if (browser.getSelectedFile().getName().equals(ChatColor.stripColor(event.getElement().getIcon().getName()))) {
//					if (browser.getSelectedFile().isDirectory()) {
//						browser.navigate(browser.getSelectedFile().getName());
//						WindowFileBrowser.this.render();
//					} else if (browser.getSelectedFile().isFile()) {
//						for (FileType type : browser.getTypes()) {
//							if (type.isMatching(browser.getSelectedFile())) {
//								Program program = browser.getPrograms(type.getName()).get(0);
//								program.execute(browser.getSelectedFile());
//								return;
//							}
//						}
//						
//						WindowFileBrowser.this.infoPanel.debug("§7No program found", false);
//					}
//					
//					return;
//				}
//			}
//			
//			WindowFileBrowser.this.browser.select(ChatColor.stripColor(event.getElement().getIcon().getName()));
//			WindowFileBrowser.this.render();
//		}
//
//	}
	
}
