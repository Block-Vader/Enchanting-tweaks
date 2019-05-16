package com.blockvader.enchantingtweaks.client;

import com.blockvader.enchantingtweaks.CapabilitySyncMessage;
import com.blockvader.enchantingtweaks.capabilities.CapMomentumProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapSyncMessageHandler implements IMessageHandler<CapabilitySyncMessage, IMessage>{

	@Override
	public IMessage onMessage(CapabilitySyncMessage message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			int m = message.getMomentum();
			player.getCapability(CapMomentumProvider.MOMENTUM, null).setMomentum(m);
		}
		return null;
	}

}
