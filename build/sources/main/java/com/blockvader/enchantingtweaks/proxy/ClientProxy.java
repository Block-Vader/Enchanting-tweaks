package com.blockvader.enchantingtweaks.proxy;

import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.client.ClientEventHandler;
import com.blockvader.enchantingtweaks.client.model.ModelRegistry;
import com.blockvader.enchantingtweaks.client.renderer.RenderBookshelf;
import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements CommonProxy {

	@Override
	public void preInit() 
	{
		ConfigHandler.clientPreInit();
		MinecraftForge.EVENT_BUS.register(new ModelRegistry());
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}

	@Override
	public void init()
	{
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBookshelf.class, new RenderBookshelf());
	}

}
