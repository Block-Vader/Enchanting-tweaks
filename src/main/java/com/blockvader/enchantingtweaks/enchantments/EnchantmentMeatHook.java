package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentMeatHook extends Enchantment {
	
	public EnchantmentMeatHook()
	{
		super(Rarity.RARE, EnumEnchantmentType.FISHING_ROD, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 15 + 5 * enchantmentLevel;
    }
	
	public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
    
    @Override
    public int getMaxLevel()
    {
    	return 4;
    }
    
    @Override
    public boolean isTreasureEnchantment()
    {
    	return true;
    }
    
    public static void bringInHookedEntity(Entity target, Entity angler, int level)
    {
    	float f = target instanceof EntityItem ? 0.5f : 1;
    	double d0 = angler.posX - target.posX;
        double d1 = Math.max(angler.posY - target.posY, 0) + 2.0d;
        double d2 = angler.posZ - target.posZ;
        double v = Math.sqrt(0.05d * level * d1 * f);
        target.motionX += f * d0 / ((1.5d * d1 + 1.0d) / v);
        target.motionY += v;
        target.motionZ += f * d2 / ((1.5d * d1 + 1.0d) / v);
    }
}
