package com.blockvader.enchantingtweaks.blocks;

import javax.annotation.Nullable;

import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.ModGUIHandler;
import com.blockvader.enchantingtweaks.init.ModBlocks;
import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class BlockBookshelfContainer extends Block
{
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockBookshelfContainer() 
	{
		super(Material.WOOD);
		this.setLightOpacity(0);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		if (world.getBlockState(pos).getBlock() == ModBlocks.BLOODWOOD_BOOK_CONTAINER) return 0;
		return 20;
	}
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
		if (face.getAxis() == Axis.Y)
		{
			return BlockFaceShape.SOLID;
		}
        return face == (EnumFacing)state.getValue(FACING) ? BlockFaceShape.BOWL : BlockFaceShape.SOLID;
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
	
	public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
	public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
	
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (worldIn.getBlockState(fromPos).getBlock() == Blocks.FIRE && worldIn.getTileEntity(pos) instanceof TileEntityBookshelf)
		{
			if (state.getBlock() != ModBlocks.BLOODWOOD_BOOK_CONTAINER)
			{
				((TileEntityBookshelf)worldIn.getTileEntity(pos)).burn();
			}
		}
	}
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityBookshelf)
            {
                ((TileEntityBookshelf)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (worldIn.getTileEntity(pos).getClass() == TileEntityBookshelf.class)
		{
			TileEntityBookshelf TE = (TileEntityBookshelf) worldIn.getTileEntity(pos);
			for (int i = 0; i < TE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getSlots(); i++)
			{
				ItemStack stack = TE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(i);
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
			}
		}
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (te instanceof IWorldNameable && ((IWorldNameable)te).hasCustomName())
        {
            player.addStat(StatList.getBlockStats(this));
            player.addExhaustion(0.005F);

            if (worldIn.isRemote)
            {
                return;
            }

            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Item item = this.getItemDropped(state, worldIn.rand, i);

            if (item == Items.AIR)
            {
                return;
            }

            ItemStack itemstack = new ItemStack(item, this.quantityDropped(worldIn.rand));
            itemstack.setStackDisplayName(((IWorldNameable)te).getName());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) 
	{
		return new TileEntityBookshelf();
	}
	
	public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
	
	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos)
	{
		if (world.getTileEntity(pos) instanceof TileEntityBookshelf)
		{
			TileEntityBookshelf bookshelf = (TileEntityBookshelf) world.getTileEntity(pos);
			return ((float) bookshelf.getBookCount()/3.0f);
		}
		return 0;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			playerIn.openGui(Main.instance, ModGUIHandler.ID_BOOKSHELF, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
