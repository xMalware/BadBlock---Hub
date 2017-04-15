package fr.badblock.bukkit.hub.inventories.selector.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import fr.badblock.bukkit.hub.inventories.abstracts.actions.ItemAction;
import fr.badblock.bukkit.hub.inventories.abstracts.items.CustomItem;
import fr.badblock.bukkit.hub.inventories.settings.settings.LightBlueStainedGlassPaneItem;
import fr.badblock.gameapi.players.BadblockPlayer;

public class BuildSelectorItem extends CustomItem {

	public BuildSelectorItem() {
		super("hub.items.buildselectoritem", Material.COBBLESTONE, "hub.items.buildselectoritem.lore");
		this.setNeededPermission("hub.staffroom");
		this.setNoPermissionItem(new LightBlueStainedGlassPaneItem());
	}

	@Override
	public List<ItemAction> getActions() {
		return Arrays.asList(ItemAction.INVENTORY_DROP, ItemAction.INVENTORY_LEFT_CLICK, ItemAction.INVENTORY_RIGHT_CLICK, ItemAction.INVENTORY_WHEEL_CLICK);
	}

	@Override
	public void onClick(BadblockPlayer player, ItemAction itemAction, Block clickedBlock) {
		player.sendPlayer("build");
	}

}
