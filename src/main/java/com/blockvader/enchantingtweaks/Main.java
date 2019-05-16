package com.blockvader.enchantingtweaks;

import com.blockvader.enchantingtweaks.capabilities.CapArrowProperties;
import com.blockvader.enchantingtweaks.capabilities.CapMomentum;
import com.blockvader.enchantingtweaks.eventhandler.ConfigEventHandler;
import com.blockvader.enchantingtweaks.eventhandler.EnchantmentHandler;
import com.blockvader.enchantingtweaks.eventhandler.OpenCustomGUI;
import com.blockvader.enchantingtweaks.init.ModBlocks;
import com.blockvader.enchantingtweaks.init.ModEnchantments;
import com.blockvader.enchantingtweaks.init.ModItems;
import com.blockvader.enchantingtweaks.proxy.IProxy;
import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Main.MOD_ID, version = Main.VERSION, guiFactory = "com.blockvader.enchantingtweaks.client.gui.GuiFactoryEnchantingTweaks", acceptedMinecraftVersions = Main.MC_VERSION)
public class Main
{
	@Instance
	public static Main instance;
	
    public static final String MOD_ID = "enchanting_tweaks";
    public static final String VERSION = "1.2.1";
    public static final String MC_VERSION = "[1.12, 1.13)";
    public static boolean iscsb_ench_tableLoaded;
    public static boolean isNaturaLoaded;
    public static boolean isThaumcraftLoaded;
    public static boolean isBoPLoaded;
    
    
    @SidedProxy(clientSide = "com.blockvader.enchantingtweaks.proxy.ClientProxy", serverSide = "com.blockvader.enchantingtweaks.proxy.ServerProxy")
	public static IProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	CapMomentum.register();
    	CapArrowProperties.register();
    	
		MinecraftForge.EVENT_BUS.register(new ModBlocks());
		MinecraftForge.EVENT_BUS.register(new ModItems());
		MinecraftForge.EVENT_BUS.register(new OpenCustomGUI());
		MinecraftForge.EVENT_BUS.register(new ModEnchantments());
		MinecraftForge.EVENT_BUS.register(new EnchantmentHandler());
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
		
		iscsb_ench_tableLoaded = Loader.isModLoaded("csb_ench_table");
		isNaturaLoaded = Loader.isModLoaded("natura");
		isThaumcraftLoaded = Loader.isModLoaded("thaumcraft");
		isBoPLoaded = Loader.isModLoaded("biomesoplenty");
		
		proxy.preInit();
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
    	GameRegistry.registerTileEntity(TileEntityBookshelf.class, new ResourceLocation(MOD_ID + ":bookshelf"));
    	NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new ModGUIHandler());
        
		ConfigHandler.Inti();
		proxy.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
}
