package de.rincewind.api.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.google.common.annotations.Beta;

@Beta
public interface ItemClickListener {
	
	void onClick(Player player, Entity ent);
	
}
