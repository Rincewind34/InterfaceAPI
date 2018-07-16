package de.rincewind.test;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import de.rincewind.interfaceplugin.listener.InventoryClickListener;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;

@SuppressWarnings("deprecation")
public class TestPlayer implements Player {

	private String name;
	
	private InventoryView openInventory;

	private InventoryCloseListener listenerClose;
	private InventoryClickListener listenerClick;

	public TestPlayer(String name) {
		this(name, (InventoryClickListener) null);
	}
	
	public TestPlayer(String name, InventoryCloseListener listener) {
		this.listenerClose = listener;
		this.name = name;
	}
	
	public TestPlayer(String name, InventoryClickListener listener) {
		this.listenerClick = listener;
		this.name = name;
	}
	
	public void clickInventory(ClickType type, InventoryAction action, int rawSlot) {
		this.listenerClick.onClick(new InventoryClickEvent(this.openInventory, SlotType.CONTAINER, rawSlot, type, action));
	}
	
	public Inventory getSynthInventory() {
		return this.openInventory == null ? null : this.openInventory.getTopInventory();
	}

	@Override
	public void closeInventory() {
		if (this.listenerClose != null) {
			this.listenerClose.onClose(new InventoryCloseEvent(this.openInventory));
		}

		this.openInventory = null;
	}

	@Override
	public void updateInventory() {
		// Supported
	}
	
	@Override
	public InventoryView openInventory(Inventory inventory) {
		return this.openInventory = new InventoryView() {
			
			@Override
			public InventoryType getType() {
				return inventory.getType();
			}
			
			@Override
			public Inventory getTopInventory() {
				return inventory;
			}
			
			@Override
			public HumanEntity getPlayer() {
				return TestPlayer.this;
			}
			
			@Override
			public Inventory getBottomInventory() {
				return null;
			}
		};
	}

	@Override
	public int getCooldown(Material arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Inventory getEnderChest() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getExpToLevel() {
		throw new UnsupportedOperationException();

	}

	@Override
	public GameMode getGameMode() {
		throw new UnsupportedOperationException();

	}

	@Override
	public PlayerInventory getInventory() {
		throw new UnsupportedOperationException();

	}

	@Override
	public ItemStack getItemInHand() {
		throw new UnsupportedOperationException();

	}

	@Override
	public ItemStack getItemOnCursor() {
		throw new UnsupportedOperationException();

	}

	@Override
	public MainHand getMainHand() {
		throw new UnsupportedOperationException();

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public InventoryView getOpenInventory() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Entity getShoulderEntityLeft() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Entity getShoulderEntityRight() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getSleepTicks() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean hasCooldown(Material arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isBlocking() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isHandRaised() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isSleeping() {
		throw new UnsupportedOperationException();

	}

	@Override
	public InventoryView openEnchanting(Location arg0, boolean arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void openInventory(InventoryView arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InventoryView openMerchant(Villager arg0, boolean arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public InventoryView openMerchant(Merchant arg0, boolean arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public InventoryView openWorkbench(Location arg0, boolean arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setCooldown(Material arg0, int arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setGameMode(GameMode arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setItemInHand(ItemStack arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setItemOnCursor(ItemStack arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setShoulderEntityLeft(Entity arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setShoulderEntityRight(Entity arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean setWindowProperty(Property arg0, int arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addPotionEffect(PotionEffect arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addPotionEffect(PotionEffect arg0, boolean arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean getCanPickupItems() {
		throw new UnsupportedOperationException();

	}

	@Override
	public EntityEquipment getEquipment() {
		throw new UnsupportedOperationException();

	}

	@Override
	public double getEyeHeight() {
		throw new UnsupportedOperationException();

	}

	@Override
	public double getEyeHeight(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Location getEyeLocation() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Player getKiller() {
		throw new UnsupportedOperationException();

	}

	@Override
	public double getLastDamage() {
		throw new UnsupportedOperationException();

	}

	@Override
	public List<Block> getLastTwoTargetBlocks(Set<Material> arg0, int arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Entity getLeashHolder() throws IllegalStateException {
		throw new UnsupportedOperationException();

	}

	@Override
	public List<Block> getLineOfSight(Set<Material> arg0, int arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getMaximumAir() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getMaximumNoDamageTicks() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getNoDamageTicks() {
		throw new UnsupportedOperationException();

	}

	@Override
	public PotionEffect getPotionEffect(PotionEffectType arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getRemainingAir() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean getRemoveWhenFarAway() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Block getTargetBlock(Set<Material> arg0, int arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean hasAI() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean hasLineOfSight(Entity arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean hasPotionEffect(PotionEffectType arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isCollidable() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isGliding() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isLeashed() {
		throw new UnsupportedOperationException();

	}

	@Override
	public void removePotionEffect(PotionEffectType arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setAI(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setCanPickupItems(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setCollidable(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setGliding(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setLastDamage(double arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean setLeashHolder(Entity arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaximumAir(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaximumNoDamageTicks(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setNoDamageTicks(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setRemainingAir(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setRemoveWhenFarAway(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public AttributeInstance getAttribute(Attribute arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addPassenger(Entity arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addScoreboardTag(String arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean eject() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getEntityId() {
		throw new UnsupportedOperationException();

	}

	@Override
	public float getFallDistance() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getFireTicks() {
		throw new UnsupportedOperationException();

	}

	@Override
	public double getHeight() {
		throw new UnsupportedOperationException();

	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Location getLocation() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Location getLocation(Location arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getMaxFireTicks() {
		throw new UnsupportedOperationException();

	}

	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Entity getPassenger() {
		throw new UnsupportedOperationException();

	}

	@Override
	public List<Entity> getPassengers() {
		throw new UnsupportedOperationException();

	}

	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getPortalCooldown() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Set<String> getScoreboardTags() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Server getServer() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getTicksLived() {
		throw new UnsupportedOperationException();

	}

	@Override
	public EntityType getType() {
		throw new UnsupportedOperationException();

	}

	@Override
	public UUID getUniqueId() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Entity getVehicle() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Vector getVelocity() {
		throw new UnsupportedOperationException();

	}

	@Override
	public double getWidth() {
		throw new UnsupportedOperationException();

	}

	@Override
	public World getWorld() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean hasGravity() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isCustomNameVisible() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isDead() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isGlowing() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isInsideVehicle() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isInvulnerable() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isOnGround() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isSilent() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isValid() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean leaveVehicle() {
		throw new UnsupportedOperationException();

	}

	@Override
	public void playEffect(EntityEffect arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean removePassenger(Entity arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeScoreboardTag(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCustomNameVisible(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFallDistance(float arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFireTicks(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setGlowing(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setGravity(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setInvulnerable(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean setPassenger(Entity arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPortalCooldown(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSilent(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTicksLived(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setVelocity(Vector arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean teleport(Location arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean teleport(Entity arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean teleport(Location arg0, TeleportCause arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean teleport(Entity arg0, TeleportCause arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasMetadata(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void sendMessage(String arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void sendMessage(String[] arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasPermission(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasPermission(Permission arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void recalculatePermissions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setOp(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCustomName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCustomName(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void damage(double arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void damage(double arg0, Entity arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getHealth() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getMaxHealth() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetMaxHealth() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHealth(double arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMaxHealth(double arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> arg0, Vector arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void abandonConversation(Conversation arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void abandonConversation(Conversation arg0, ConversationAbandonedEvent arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void acceptConversationInput(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean beginConversation(Conversation arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isConversing() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getFirstPlayed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLastPlayed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Player getPlayer() {
		return this;
	}

	@Override
	public boolean hasPlayedBefore() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isBanned() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOnline() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWhitelisted() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWhitelisted(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> serialize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> getListeningPluginChannels() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void awardAchievement(Achievement arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean canSee(Player arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void chat(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic arg0, int arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic arg0, EntityType arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InetSocketAddress getAddress() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AdvancementProgress getAdvancementProgress(Advancement arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getAllowFlight() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Location getBedSpawnLocation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Location getCompassTarget() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDisplayName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getExhaustion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getExp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFlySpeed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFoodLevel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getHealthScale() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getLevel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getLocale() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getPlayerListName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getPlayerTime() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getPlayerTimeOffset() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WeatherType getPlayerWeather() {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getSaturation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Scoreboard getScoreboard() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Entity getSpectatorTarget() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getStatistic(Statistic arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getTotalExperience() {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getWalkSpeed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void giveExp(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void giveExpLevels(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAchievement(Achievement arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void hidePlayer(Player arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void incrementStatistic(Statistic arg0, int arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void incrementStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void incrementStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void incrementStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic arg0, EntityType arg1, int arg2) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFlying() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isHealthScaled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPlayerTimeRelative() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSleepingIgnored() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSneaking() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSprinting() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void kickPlayer(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void loadData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean performCommand(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void playEffect(Location arg0, Effect arg1, int arg2) {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> void playEffect(Location arg0, Effect arg1, T arg2) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void playNote(Location arg0, byte arg1, byte arg2) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void playNote(Location arg0, Instrument arg1, Note arg2) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void playSound(Location arg0, Sound arg1, float arg2, float arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void playSound(Location arg0, String arg1, float arg2, float arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void playSound(Location arg0, Sound arg1, SoundCategory arg2, float arg3, float arg4) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void playSound(Location arg0, String arg1, SoundCategory arg2, float arg3, float arg4) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAchievement(Achievement arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetPlayerTime() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetPlayerWeather() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetTitle() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void saveData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendBlockChange(Location arg0, Material arg1, byte arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean sendChunkChange(Location arg0, int arg1, int arg2, int arg3, byte[] arg4) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendMap(MapView arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void sendRawMessage(String arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void sendSignChange(Location arg0, String[] arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void sendTitle(String arg0, String arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void sendTitle(String arg0, String arg1, int arg2, int arg3, int arg4) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setAllowFlight(boolean arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setBedSpawnLocation(Location arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setBedSpawnLocation(Location arg0, boolean arg1) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setCompassTarget(Location arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setDisplayName(String arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setExhaustion(float arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setExp(float arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFlySpeed(float arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFlying(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFoodLevel(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHealthScale(double arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHealthScaled(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLevel(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPlayerListName(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPlayerTime(long arg0, boolean arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPlayerWeather(WeatherType arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setResourcePack(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setResourcePack(String arg0, byte[] arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSaturation(float arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScoreboard(Scoreboard arg0) throws IllegalArgumentException, IllegalStateException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSleepingIgnored(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSneaking(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSpectatorTarget(Entity arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSprinting(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatistic(Statistic arg0, int arg1) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatistic(Statistic arg0, EntityType arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTexturePack(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTotalExperience(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWalkSpeed(float arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showPlayer(Player arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void spawnParticle(Particle arg0, Location arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, T arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, T arg5) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5, T arg6) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5, double arg6) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6, double arg7) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5, double arg6, T arg7) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6, double arg7, T arg8) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6, double arg7, double arg8) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6, double arg7, double arg8,
			T arg9) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Spigot spigot() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stopSound(Sound arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stopSound(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stopSound(Sound arg0, SoundCategory arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stopSound(String arg0, SoundCategory arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSwimming() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSwimming(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void hidePlayer(Plugin arg0, Player arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendBlockChange(Location arg0, BlockData arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showPlayer(Plugin arg0, Player arg1) {
		throw new UnsupportedOperationException();
	}

}
