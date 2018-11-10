package com.blockvader.enchantingtweaks;

import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BookshelfItemStackHandler extends  ItemStackHandler {
	
	private TileEntityBookshelf TE;
	
	public BookshelfItemStackHandler(int size, TileEntityBookshelf TE)
	{
		super (size);
		this.TE =TE;
	}
	
	@Override
	protected int getStackLimit(int slot, ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!stack.isEmpty())
		{
			Item item = stack.getItem();
			if (!ConfigHandler.BookItems.contains(item))
			{
				return stack;
			}
		}
		return super.insertItem(slot, stack, simulate);
	}
	
	@Override
	protected void onContentsChanged(int slot)
	{
		if (!TE.getWorld().isRemote)
		{
			TE.getWorld().notifyBlockUpdate(TE.getPos(), TE.getWorld().getBlockState(TE.getPos()), TE.getWorld().getBlockState(TE.getPos()), 3);
		}
	}

}
