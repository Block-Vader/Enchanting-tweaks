package com.blockvader.enchantingtweaks.client;

import java.lang.reflect.Field;

import com.blockvader.enchantingtweaks.eventhandler.EnchantmentHandler;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientEventHandler {
	
	@SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) throws NoSuchFieldException, SecurityException
	{
        if (FMLClientHandler.instance().getClient().isGamePaused())
        {
            return;
        }

        EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();
        if (player != null && player.getRidingEntity() instanceof EntityHorse)
        {
            EntityHorse horse = (EntityHorse) player.getRidingEntity();
            if (horse.getHorseArmorType() != HorseArmorType.NONE)
            {
	        	ItemStack armor = EnchantmentHandler.getHorseArmor(horse);
				if (horse.isInWater() && EnchantmentHelper.getEnchantmentLevel(Enchantments.AQUA_AFFINITY, armor) > 0)
	            {
	                horse.addVelocity(0f, 0.0125f, 0f);
	            }
            }
        }
    }

}
