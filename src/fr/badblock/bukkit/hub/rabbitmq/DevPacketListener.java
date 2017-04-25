package fr.badblock.bukkit.hub.rabbitmq;

import fr.badblock.bukkit.hub.BadBlockHub;
import fr.badblock.bukkit.hub.inventories.selector.dev.DevSelectorInventory;
import fr.badblock.rabbitconnector.RabbitListener;
import fr.badblock.rabbitconnector.RabbitListenerType;

public class DevPacketListener extends RabbitListener {
	public DevPacketListener() {
		super(BadBlockHub.getInstance().getRabbitService(), "hub", false, RabbitListenerType.SUBSCRIBER);
	}

	@Override
	public void onPacketReceiving(String body) {
		if (body == null)
			return;
		
		DevAliveFactory devAliveFactory = BadBlockHub.getInstance().getGson().fromJson(body, DevAliveFactory.class);
		
		if (devAliveFactory == null)
			return;
		
		DevSelectorInventory.Apply(devAliveFactory);
	}
}