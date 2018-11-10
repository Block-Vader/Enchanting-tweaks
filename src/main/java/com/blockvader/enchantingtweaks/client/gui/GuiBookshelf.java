package com.blockvader.enchantingtweaks.client.gui;

import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.inventory.ContainerBookshelf;
import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBookshelf extends GuiContainer{
	
	private TileEntityBookshelf bookshelfInventory;
	private InventoryPlayer playerInventory;
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID + ":textures/gui/bookshelf.png");

	public GuiBookshelf(InventoryPlayer playerInventory, TileEntityBookshelf bookshelfInventory)
	{
		super(new ContainerBookshelf(playerInventory, bookshelfInventory));
		this.playerInventory = playerInventory;
        this.bookshelfInventory = bookshelfInventory;
        this.allowUserInput = false;
        this.ySize = 133;
	}

	/**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.bookshelfInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

}
