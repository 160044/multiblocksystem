package me.idra.multiblocksystem.managers;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.idra.multiblocksystem.MultiblockSystem;
import me.idra.multiblocksystem.filehandlers.FileHandlerPermanentVariables;
import me.idra.multiblocksystem.filehandlers.FileHandlerPlayerData;
import me.idra.multiblocksystem.filehandlers.FileHandlerWorldMultiblocks;
import me.idra.multiblocksystem.lists.ListAbstractMultiblocks;
import me.idra.multiblocksystem.lists.ListItemGroups;
import me.idra.multiblocksystem.tasks.TaskIncrementTick;
import me.idra.multiblocksystem.tasks.TaskTickScoreboards;



public class ManagerPlugin {

	private ManagerPlugin() {
		// Empty constructor
	}


	
	public static final String VERSION = "0.1.1";
	public static final String AUTHOR = "Idra";

	public static MultiblockSystem plugin;
	public static FileConfiguration config;
	public static FileConfiguration messages;
	
	public static long tick;
	public static int tick_interval;
	private static TaskIncrementTick task_increment_tick;
	private static TaskTickScoreboards task_tick_scoreboards;
	
	
	
	public static void initialize(MultiblockSystem in_plugin) {
		
    	// Set important attributes
		plugin = in_plugin;
    	config = plugin.getConfig();
    	messages = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
    	tick_interval = config.getInt("Ticks.multiblock-tick-interval");
    	
    	// Set up config
    	plugin.saveDefaultConfig();
    	
    	// Ensure default folders exist
		File multiblock_folder = new File(plugin.getDataFolder(), "multiblocks");
		if (!multiblock_folder.exists())
			multiblock_folder.mkdir();
		
		File data_folder = new File(plugin.getDataFolder(), "data");
		if (!data_folder.exists())
			data_folder.mkdir();

		File slimefun_folder = new File(plugin.getDataFolder(), "slimefun");
			if (!slimefun_folder.exists())
				slimefun_folder.mkdir();
		
		FileHandlerPermanentVariables.loadFile();
		FileHandlerPlayerData.loadFile();
		FileHandlerWorldMultiblocks.loadFile();
		
		// Handle ticks
		tick = FileHandlerPermanentVariables.getStoredTick();
		task_increment_tick = new TaskIncrementTick();
		task_increment_tick.runTaskTimer(ManagerPlugin.plugin, 0, tick_interval);

		// Handle scoreboards
		task_tick_scoreboards = new TaskTickScoreboards();
		task_tick_scoreboards.runTaskTimer(ManagerPlugin.plugin, 0, tick_interval);

    	// Initialize classes
		ManagerSlimefunItems.initialize();
		ListItemGroups.initialize();
    	ListAbstractMultiblocks.initialize();
    	ManagerPermissions.initialize();
	}
}
