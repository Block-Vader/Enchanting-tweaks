package com.blockvader.enchantingtweaks.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBookshelf extends ModelBase {
	
	private final ModelRenderer book1;
	private final ModelRenderer book2;
	private final ModelRenderer book3;
	private final ModelRenderer book4;
	private final ModelRenderer book5;
	private final ModelRenderer book6;
	
	public ModelBookshelf()
	{
		this.textureHeight = 64;
		this.textureWidth = 128;
		book1 = new ModelRenderer(this, 0, 0);
		book2 = new ModelRenderer(this, 32, 0);
		book3 = new ModelRenderer(this, 64, 0);
		book4 = new ModelRenderer(this, 0, 32);
		book5 = new ModelRenderer(this, 32, 32);
		book6 = new ModelRenderer(this, 64, 32);
		
		book1.addBox(0, 0, 0, 3, 13, 11);
		book2.addBox(3, 0, 0, 2, 13, 12);
		book3.addBox(5, 0, 0, 2, 14, 12);
		book4.addBox(7, 0, 0, 3, 13, 11);
		book5.addBox(10, 0, 0, 2, 12, 13);
		book6.addBox(12, 0, 0, 2, 13, 12);
	}
	
	public void renderBookshelf(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6)
	{
		if (b1) book1.render(0.0625F);
		if (b2) book2.render(0.0625F);
		if (b3) book3.render(0.0625F);
		if (b4) book4.render(0.0625F);
		if (b5) book5.render(0.0625F);
		if (b6) book6.render(0.0625F);
	}

}
