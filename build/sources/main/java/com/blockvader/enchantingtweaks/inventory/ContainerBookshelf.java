package com.blockvader.enchantingtweaks.inventory;

import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBookshelf extends Container {
	
	public IItemHandler bookshelfInventory;
	private TileEntityBookshelf bookshelf;
	
	public ContainerBookshelf(InventoryPlayer playerInventory, TileEntityBookshelf bookshelf)
	{
		this.bookshelf = bookshelf;
		this.bookshelfInventory = bookshelf.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0; i < bookshelfInventory.getSlots(); i++)
		{
			this.addSlotToContainer(new SlotItemHandler(bookshelfInventory, i, 35 + i * 18, 20)
					{
						public boolean isItemValid(net.minecraft.item.ItemStack stack)
						{
							return ConfigHandler.BookItems.contains(stack.getItem());
						}
						
						@Override
						public int getSlotStackLimit() 
						{
							return 1;
						}
					}
			);
		}
		
		for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 109));
        }
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < this.bookshelfInventory.getSlots())
            {
                if (!this.mergeItemStack(itemstack1, this.bookshelfInventory.getSlots(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.bookshelfInventory.getSlots(), false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
            bookshelf.getWorld().notifyBlockUpdate(bookshelf.getPos(), bookshelf.getWorld().getBlockState(bookshelf.getPos()), bookshelf.getWorld().getBlockState(bookshelf.getPos()), 3);
        }

        return itemstack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

}
