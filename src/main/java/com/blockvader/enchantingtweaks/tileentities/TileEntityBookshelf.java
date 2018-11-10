package com.blockvader.enchantingtweaks.tileentities;

import java.util.Map;

import com.blockvader.enchantingtweaks.BookshelfItemStackHandler;
import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.inventory.ContainerBookshelf;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityBookshelf extends TileEntity implements ICapabilityProvider, IInteractionObject {
	
	private BookshelfItemStackHandler handler;
	private String customName;
	
	public boolean hasBook1;
	public boolean hasBook2;
	public boolean hasBook3;
	public boolean hasBook4;
	public boolean hasBook5;
	public boolean hasBook6;
	
	public TileEntityBookshelf()
	{
		this.handler = new BookshelfItemStackHandler(6, this);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("Inventory", handler.serializeNBT());
		if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		super.readFromNBT(compound);
		if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) this.handler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public boolean canRenderBreaking() {
		return true;
	}
	
	public void burn()
	{
		boolean spawnParticles = false;
		for (int i = 0; i < 6; i++)
		{
			if (!handler.getStackInSlot(i).isEmpty())
			{
				spawnParticles = true;
				this.handler.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
		if (this.world instanceof WorldServer && spawnParticles)
        {
			((WorldServer)this.world).spawnParticle(EnumParticleTypes.FLAME, this.pos.getX()+0.5d, this.pos.getY()+0.5d, this.pos.getZ()+0.5d, 5, 0.125d, 0.125d, 0.125d, 0.01d, 0);
			((WorldServer)this.world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+0.5d, this.pos.getY()+0.5d, this.pos.getZ()+0.5d, 20, 0.125d, 0.125d, 0.125d, 0.01d, 0);
        }
	}
	
	public int getBookCount()
	{
		int i = 0;
		for (int j = 0; j < handler.getSlots(); j++)
		{
			if (handler.getStackInSlot(j) != ItemStack.EMPTY)
			{
				i++;
			}
		}
		return i;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		readUpdateTag(compound);
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		readUpdateTag(tag);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeUpdateTag(compound);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		writeUpdateTag(compound);
		return compound;
	}
	
	public void readUpdateTag (NBTTagCompound compound)
	{
		int j = compound.getInteger("hasBooks");
		this.hasBook1 = ((j & 1) == 1);
		this.hasBook2 = (((j >> 1) & 1) == 1);
		this.hasBook3 = (((j >> 2) & 1) == 1);
		this.hasBook4 = (((j >> 3) & 1) == 1);
		this.hasBook5 = (((j >> 4) & 1) == 1);
		this.hasBook6 = (((j >> 5) & 1) == 1);
	}
	
	public void writeUpdateTag (NBTTagCompound compound)
	{
		int j = 0;
		for (int i = 0; i < handler.getSlots(); i++)
		{
			if (!handler.getStackInSlot(i).isEmpty())
			{
				j += 1 << i;
			}
		}
		compound.setInteger("hasBooks", j);
	}
	
	public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.bookshelf";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String customNameIn)
    {
        this.customName = customNameIn;
    }
	
    @Override
    public ITextComponent getDisplayName()
    {
    	return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }
    
    public Map<Enchantment, Integer> getEnchantBonus(Map<Enchantment, Integer> map)
	{
    	
		for (int j = 0; j < handler.getSlots(); j++)
		{
			ItemStack stack = handler.getStackInSlot(j);
			if (stack != ItemStack.EMPTY)
			{
				if (stack.getItem() == Items.ENCHANTED_BOOK)
				{
					NBTTagList nbttaglist = ItemEnchantedBook.getEnchantments(stack);

					for (int i = 0; i < nbttaglist.tagCount(); ++i)
					{
						NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
						Enchantment enchantment = Enchantment.getEnchantmentByID(nbttagcompound.getShort("id"));
						int l = nbttagcompound.getShort("lvl");
						map.merge(enchantment, Integer.valueOf(l), (x, y) -> {return x + y;});
					}
				}
			}
		}
		return map;
	}
    
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
    	return true;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		
		return new ContainerBookshelf(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		// TODO Auto-generated method stub
		return Main.MOD_ID + ":bookshelf";
	}
}
