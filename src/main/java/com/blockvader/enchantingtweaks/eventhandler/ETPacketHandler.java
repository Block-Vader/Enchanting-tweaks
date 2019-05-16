 package com.blockvader.enchantingtweaks.eventhandler;

import com.blockvader.enchantingtweaks.CapabilitySyncMessage;
import com.blockvader.enchantingtweaks.HookPlayerMessage;
import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.client.CapSyncMessageHandler;
import com.blockvader.enchantingtweaks.client.HookPlayerMessageHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ETPacketHandler {
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MOD_ID);
	
	public static void register()
	{
		NETWORK.registerMessage(CapSyncMessageHandler.class, CapabilitySyncMessage.class, 0, Side.CLIENT);
		NETWORK.registerMessage(HookPlayerMessageHandler.class, HookPlayerMessage.class, 1, Side.CLIENT);
	}
}
