package com.blockvader.enchantingtweaks.client.renderer;

import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.blocks.BlockBookshelfContainer;
import com.blockvader.enchantingtweaks.client.model.ModelBookshelf;
import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderBookshelf extends TileEntitySpecialRenderer<TileEntityBookshelf>
{
	protected ModelBookshelf model = new ModelBookshelf();
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID + ":textures/entity/books.png");
	
	@Override
	public void render(TileEntityBookshelf te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x+0.0625F, y+0.0625F, z+0.0625F);
		boolean b1 = te.hasBook1;
		boolean b2 = te.hasBook2;
		boolean b3 = te.hasBook3;
		boolean b4 = te.hasBook4;
		boolean b5 = te.hasBook5;
		boolean b6 = te.hasBook6;
		
		EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockBookshelfContainer.FACING);
		if (facing == EnumFacing.WEST)
		{
			GlStateManager.translate(0.875F, 0F, 0F);
			GlStateManager.rotate(270, 0, 1, 0);
		}
		if (facing == EnumFacing.EAST)
		{
			GlStateManager.translate(0F, 0F, 0.875F);
			GlStateManager.rotate(90, 0, 1, 0);
		}
		if (facing == EnumFacing.NORTH)
		{
			GlStateManager.translate(0.875F, 0F, 0.875F);
			GlStateManager.rotate(180, 0, 1, 0);
		}
		
		this.bindTexture(TEXTURE);
		model.renderBookshelf(b1, b2, b3, b4, b5, b6);
		GlStateManager.popMatrix();
	}
}
