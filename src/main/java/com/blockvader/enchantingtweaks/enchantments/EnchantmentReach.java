package com.blockvader.enchantingtweaks.enchantments;

import java.util.UUID;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentReach extends Enchantment {
	
	public static final UUID modifierUUID = UUID.fromString("e6101345-134f-4c54-a645-75c3ae5c7a27");
	
	public EnchantmentReach()
	{
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 20 + 10 * (enchantmentLevel - 1);
    }
	
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
    
    @Override
    public int getMaxLevel()
    {
    	return 2;
    }
}
