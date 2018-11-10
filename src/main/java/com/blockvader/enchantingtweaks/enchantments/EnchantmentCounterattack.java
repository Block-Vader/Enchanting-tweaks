package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;

public class EnchantmentCounterattack extends Enchantment {
	public EnchantmentCounterattack()
	{
		super(Rarity.RARE, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND});
		this.setRegistryName("counterattack");
		this.setName("counterattack");
	}
	
	/**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 20 + 10 * (enchantmentLevel - 1);
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
    
    @Override
    public int getMaxLevel()
    {
    	return 2;
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
    	return stack.getItem() instanceof ItemShield;
    }

}

