package me.Fake_Name131.DeathCoords;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	public static final Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");
	static Logger LOGGER = Bukkit.getLogger();

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("deathcoords")) {
			p.sendMessage(format("&4Death&aCoords &61.0.0 by #8f46a3Fake_Name131&6."));
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setMessage(format(e.getMessage()));
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		double x = Math.round(p.getLocation().getX());
		double y = Math.round(p.getLocation().getY());
		double z = Math.round(p.getLocation().getZ());
		String msg = ChatColor.AQUA + p.getName() + ChatColor.GREEN + " has died at X"
				+ ChatColor.AQUA + x + ChatColor.GREEN + " Y"
				+ ChatColor.AQUA + y + ChatColor.GREEN + " Z"
				+ ChatColor.AQUA + z + ChatColor.GREEN + ".";
		if (getConfig().getBoolean("send-all") == true) {
			Bukkit.broadcastMessage(msg);
		} else if (getConfig().getBoolean("send-all") == false) {
			p.sendMessage(msg);
		} else {
			p.sendMessage(format("&cThe boolean value \"send-all\" in the file &4config.yml &cis not set to true or false."));
			LOGGER.warning(("&cThe boolean value \"send-all\" in the file &4config.yml &cis not set to true or false."));
		}
	}

	public static String format(String msg) {
		if ((Bukkit.getVersion().contains("1.16") || (Bukkit.getVersion().contains("1.17")) || (Bukkit.getVersion().contains("1.18")))) {
			Matcher match = pattern.matcher(msg);
			while (match.find()) {
				String color = msg.substring(match.start(), match.end());
				msg = msg.replace(color, ChatColor.of(color) + "");
				match = pattern.matcher(msg);
			}
		}
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
