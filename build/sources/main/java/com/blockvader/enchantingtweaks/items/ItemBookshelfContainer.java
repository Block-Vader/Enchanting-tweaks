package com.blockvader.enchantingtweaks.items;

import java.util.List;

import com.blockvader.enchantingtweaks.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemBookshelfContainer extends ItemBlock{

	public ItemBookshelfContainer(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getItem() == ModItems.bloodwood_book_container)
		{
			String s = I18n.translateToLocal("bloodwood_book_container.desc").trim();
			tooltip.add(s);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
