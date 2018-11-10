package com.blockvader.enchantingtweaks.eventhandler;

import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.Main;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigEventHandler {
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void OnConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID()==Main.MOD_ID)
		{
			ConfigHandler.syncFromGui();
		}
	}

}
