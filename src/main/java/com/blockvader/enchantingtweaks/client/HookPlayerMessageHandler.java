package com.blockvader.enchantingtweaks.client;

import com.blockvader.enchantingtweaks.HookPlayerMessage;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentMeatHook;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HookPlayerMessageHandler implements IMessageHandler<HookPlayerMessage, IMessage>{

	@Override
	public IMessage onMessage(HookPlayerMessage message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			Entity angler = player.world.getEntityByID(message.getId());
            EnchantmentMeatHook.bringInHookedEntity(player, angler, 4);
		}
		return null;
	}

}
