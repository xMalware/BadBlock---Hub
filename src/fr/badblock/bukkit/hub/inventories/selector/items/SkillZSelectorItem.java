package fr.badblock.bukkit.hub.inventories.selector.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.google.gson.Gson;

import fr.badblock.bukkit.hub.BadBlockHub;
import fr.badblock.bukkit.hub.inventories.abstracts.actions.ItemAction;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.run.BadblockGame;
import fr.badblock.rabbitconnector.RabbitPacketType;
import fr.badblock.rabbitconnector.RabbitService;
import fr.badblock.sentry.SEntry;
import fr.badblock.utils.Encodage;

public class SkillZSelectorItem extends GameSelectorItem {

	public SkillZSelectorItem() {
		super("hub.items.skillzselectoritem", Material.DIAMOND_SWORD, "hub.items.skillzselectoritem.lore");
		this.setFakeEnchantment(true);
	}

	@Override
	public List<ItemAction> getActions() {
		return Arrays.asList(ItemAction.INVENTORY_DROP, ItemAction.INVENTORY_LEFT_CLICK, ItemAction.INVENTORY_RIGHT_CLICK, ItemAction.INVENTORY_WHEEL_CLICK);
	}

	@Override
	public List<String> getGames() {
		return Arrays.asList("skillz");
	}

	@Override
	public void onClick(BadblockPlayer player, ItemAction itemAction, Block clickedBlock) {
		BadBlockHub instance = BadBlockHub.getInstance();
		RabbitService service = instance.getRabbitService();
		Gson gson = instance.getGson();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				service.sendAsyncPacket("networkdocker.sentry.join", gson.toJson(new SEntry(player.getName(), "skillz", false)), Encodage.UTF8, RabbitPacketType.PUBLISHER, 5000, false);
			}
		};
		if (player.hasPermission("matchmaking.priority")) runnable.run();
		else {
			runnable.run();
		}
		player.closeInventory();
	}

	@Override
	public BadblockGame getGame() {
		return BadblockGame.BRAIN;
	}

	@Override
	public boolean isMiniGame() {
		return true;
	}

	@Override
	public String getGamePrefix() {
		return "skillz";
	}

}
