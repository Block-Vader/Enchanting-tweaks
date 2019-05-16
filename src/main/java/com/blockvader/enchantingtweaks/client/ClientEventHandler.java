package com.blockvader.enchantingtweaks.client;

import java.util.List;

import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentWeightless;
import com.blockvader.enchantingtweaks.eventhandler.EnchantmentHandler;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
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
	                horse.addVelocity(0f, 0.025f, 0f);
	            }
            }
        }
    }
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onFOVChange(FOVUpdateEvent event)
	{
		if (event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(EnchantmentWeightless.modifierUUID) != null)
		{
			float f = 1.0F;
	
	        if (event.getEntity().capabilities.isFlying)
	        {
	            f *= 1.1F;
	        }
	
	        IAttributeInstance iattributeinstance = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
	        double value = (double) iattributeinstance.getAttributeValue() / 5;
	        f = (float)((double)f * ((value / (double)event.getEntity().capabilities.getWalkSpeed() + 1.0D) / 2.0D));
	
	        if (event.getEntity().capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
	        {
	            f = 1.0F;
	        }
	
	        if (event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() == Items.BOW)
	        {
	            int i = event.getEntity().getItemInUseMaxCount();
	            float f1 = (float)i / 20.0F;
	
	            if (f1 > 1.0F)
	            {
	                f1 = 1.0F;
	            }
	            else
	            {
	                f1 = f1 * f1;
	            }
	
	            f *= 1.0F - f1 * 0.15F;
	        }
	        
	        event.setNewfov(f);
		}
	}
	
	@SubscribeEvent
    public void onTooltipRender(ItemTooltipEvent event)
	{
		if (ConfigHandler.ColourfulEnchantments)
		{
			List<String> list = event.getToolTip();
			NBTTagList nbttaglist = new NBTTagList();
			if (event.getItemStack().getItem() == Items.ENCHANTED_BOOK)
			{
				nbttaglist = ItemEnchantedBook.getEnchantments(event.getItemStack());
			}
			else nbttaglist = event.getItemStack().getEnchantmentTagList();
			
	        for (int j = 0; j < nbttaglist.tagCount(); ++j)
	        {
	            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
	            int k = nbttagcompound.getShort("id");
	            int l = nbttagcompound.getShort("lvl");
	            Enchantment enchantment = Enchantment.getEnchantmentByID(k);
	            Rarity rarity = enchantment.getRarity();
	
	            if (enchantment != null)
	            {
	                String s = enchantment.getTranslatedName(l);
	                if  (list.contains(s))
	                {
	                	int i = list.indexOf(s);
	                	list.remove(i);
	                	switch(rarity)
	                	{
	                	case COMMON: s = "§f" + s;
	                	case UNCOMMON: s = "§e" + s;
	                	case RARE: s = "§b" + s;
	                	case VERY_RARE: s = "§d" + s;
	                	}
	                	list.add(i, s);
	                }
	            }
	        }
		}
	}

}
