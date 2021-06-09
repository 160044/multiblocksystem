package me.idra.multiblocksystem;



import org.bukkit.plugin.java.JavaPlugin;

import me.idra.multiblocksystem.filehandlers.FileHandlerWorldMultiblocks;
import me.idra.multiblocksystem.managers.ManagerCommands;
import me.idra.multiblocksystem.managers.ManagerPlugin;
import me.idra.multiblocksystem.objects.JoinListener;
import me.idra.multiblocksystem.objects.MultiblockListener;
import me.idra.multiblocksystem.objects.TabHandler;



public final class MultiblockSystem extends JavaPlugin {
	
	
    @Override
    public void onEnable() {
		
    	// Perform initialization (outsourced to Chi- I mean, HelperPlugin) 
    	ManagerPlugin.initialize(this);
    	
    	// Set up commands
    	getCommand("multiblock").setExecutor(new ManagerCommands());
    	getCommand("multiblock").setTabCompleter(new TabHandler());
    	
    	// Set up listeners
    	getServer().getPluginManager().registerEvents(new MultiblockListener(), this);
    	getServer().getPluginManager().registerEvents(new JoinListener(), this);
    	
    	// Load world multiblocks
    	FileHandlerWorldMultiblocks.loadMultiblocks();
    }
    
    
    
    @Override
    public void onDisable() {
    	
    	FileHandlerWorldMultiblocks.saveMultiblocks();
    }
}