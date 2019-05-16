package com.blockvader.enchantingtweaks.items;

import java.util.List;

import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemBookshelfContainer extends ItemBlock{
	
	public boolean isAvalible;

	public ItemBookshelfContainer(Block block) {
		super(block);
		this.isAvalible = this.getIsAvalible(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getItem() == Item.getItemFromBlock(ModBlocks.BLOODWOOD_BOOK_CONTAINER))
		{
			String s = I18n.translateToLocal("bloodwood_book_container.desc").trim();
			tooltip.add(s);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	//Bookshelves from modded wood won't show in creative menu if that mod is not loaded
	@Override
	public CreativeTabs getCreativeTab()
	{
		return this.isAvalible ? CreativeTabs.DECORATIONS : null;
	}
	
	public boolean getIsAvalible(Block block)
	{
		return ((block == ModBlocks.ACACIA_BOOK_CONTAINER ||
				block == ModBlocks.BIRCH_BOOK_CONTAINER ||
				block == ModBlocks.DARK_OAK_BOOK_CONTAINER || 
				block == ModBlocks.JUNGLE_BOOK_CONTAINER ||
				block == ModBlocks.OAK_BOOK_CONTAINER ||
				block == ModBlocks.SPRUCE_BOOK_CONTAINER)
				||
				(Main.isThaumcraftLoaded && (
				block == ModBlocks.GREATWOOD_BOOK_CONTAINER ||
				block == ModBlocks.BIRCH_BOOK_CONTAINER))
		 		||
		 		(Main.isNaturaLoaded && (
				block == ModBlocks.AMARANTH_BOOK_CONTAINER ||
				block == ModBlocks.BLOODWOOD_BOOK_CONTAINER ||
				block == ModBlocks.DARKWOOD_BOOK_CONTAINER ||
				block == ModBlocks.FUSEWOOD_BOOK_CONTAINER ||
				block == ModBlocks.GHOSTWOOD_BOOK_CONTAINER ||
				block == ModBlocks.HOPSEED_BOOK_CONTAINER ||
				block == ModBlocks.SAKURA_BOOK_CONTAINER ||
				block == ModBlocks.SILVERBELL_BOOK_CONTAINER ||
				block == ModBlocks.TIGERWOOD_BOOK_CONTAINER))
		 		||
		 		(Main.isBoPLoaded &&(
				block == ModBlocks.SACRED_OAK_BOOK_CONTAINER ||
				block == ModBlocks.CHERRY_BOOK_CONTAINER ||
				block == ModBlocks.UMBRAN_BOOK_CONTAINER ||
				block == ModBlocks.FIR_BOOK_CONTAINER ||
				block == ModBlocks.ETHEREAL_BOOK_CONTAINER ||
				block == ModBlocks.MAGIC_BOOK_CONTAINER ||
				block == ModBlocks.MANGROVE_BOOK_CONTAINER ||
				block == ModBlocks.PALM_BOOK_CONTAINER ||
				block == ModBlocks.PINE_BOOK_CONTAINER ||
				block == ModBlocks.HELLBARK_BOOK_CONTAINER ||
				block == ModBlocks.JACARANDA_BOOK_CONTAINER ||
				block == ModBlocks.MAHAGONY_BOOK_CONTAINER ||
				block == ModBlocks.EBONY_BOOK_CONTAINER))
		 		||	
		 		(Main.isNaturaLoaded || Main.isBoPLoaded) && (
				block == ModBlocks.EUCALYPTUS_BOOK_CONTAINER ||
				block == ModBlocks.REDWOOD_BOOK_CONTAINER ||
				block == ModBlocks.WILLOW_BOOK_CONTAINER));
	}

}
