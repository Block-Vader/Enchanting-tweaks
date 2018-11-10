package com.blockvader.enchantingtweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCounterattack extends Potion {
	
	public static ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID + ":textures/misc/potions.png");

	public PotionCounterattack(boolean isBadEffectIn, int liquidColorIn, int iconIndex) {
		super(isBadEffectIn, liquidColorIn);
		setIconIndex(iconIndex % 8, iconIndex / 8);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		return super.getStatusIconIndex();
	}

}
