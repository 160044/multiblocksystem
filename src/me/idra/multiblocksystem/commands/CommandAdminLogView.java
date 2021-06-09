package me.idra.multiblocksystem.commands;



import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.idra.multiblocksystem.bases.BaseCommand;
import me.idra.multiblocksystem.helpers.Logger;
import me.idra.multiblocksystem.helpers.MessageHandler;
import me.idra.multiblocksystem.lists.ListChatListers;
import me.idra.multiblocksystem.objects.ChatLister;
import net.md_5.bungee.api.chat.ComponentBuilder;



public class CommandAdminLogView extends BaseCommand{
	

	public CommandAdminLogView() {
		super();
		
		name = new String[] {"admin", "log", "view"};
		description = ChatColor.DARK_AQUA + "View the warnings generated since last restart.";
		arguments = new String[] {};
		hidden = false;
		console = true;
		
		addPermission();
		addName();
	}
	
	@Override
	public boolean commandFunction(CommandSender sender, Command command, String label, String[] args) {
		
		// Generate top text
		ArrayList<String> top_text = new ArrayList<String> ();
		top_text.add(MessageHandler.getInfo("admin-log-title"));
		
		// Array of error times + messages
		ArrayList<ComponentBuilder> formatted_errors = new ArrayList<ComponentBuilder> ();
				
		// Send logs
		for (int ID : Logger.log_messages.keySet())
			formatted_errors.add(new ComponentBuilder("").append(
					ChatColor.translateAlternateColorCodes('&', MessageHandler.getInfo("admin-log-format")
					.replace("%time%", Logger.log_times.get(ID))
					.replace("%error%", Logger.log_messages.get(ID)))));
		
		// Set chatlister
		ListChatListers.chat_listers.put(sender, new ChatLister(
				sender,
				top_text,
				formatted_errors,
				MessageHandler.getError("admin-log-none")));
				
		// Display chat lister
		ListChatListers.chat_listers.get(sender).display(1);
		
		// Successful execution
		return true;
	}
}