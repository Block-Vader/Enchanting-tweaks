package com.blockvader.enchantingtweaks.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.Main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;

public class GuiFactoryEnchantingTweaks implements IModGuiFactory{

	@Override
	public void initialize(Minecraft minecraftInstance) { }

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new EnchantmentTweakConfigGui(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}
	
	public static class EnchantmentTweakConfigGui extends GuiConfig
    {
        public EnchantmentTweakConfigGui(GuiScreen parentScreen)
        {
            super(parentScreen, getConfigElements(), Main.MOD_ID, false, false, I18n.format(Main.MOD_ID + ".configgui.title"));
        }
        
        private static List<IConfigElement> getConfigElements()
        {
        	 List<IConfigElement> list = new ArrayList<IConfigElement>();
        	 list.add(new DummyCategoryElement("main", Main.MOD_ID + ".configgui.main", MainEntry.class));
        	 return list;
        }
        
        public static class MainEntry extends CategoryEntry
        {
            public MainEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
            {
                super(owningScreen, owningEntryList, prop);
            }

            @Override
            protected GuiScreen buildChildScreen()
            {
            	return new GuiConfig(this.owningScreen,
                        (new ConfigElement(ConfigHandler.getConfig().getCategory(ConfigHandler.CATEGOTY_MAIN))).getChildElements(),
                        this.owningScreen.modID, ConfigHandler.CATEGOTY_MAIN, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
                        this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
                        GuiConfig.getAbridgedConfigPath(ConfigHandler.getConfig().toString()));
            }
        }
    }

}
