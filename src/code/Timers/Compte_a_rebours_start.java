package code.Timers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Compte_a_rebours_start extends BukkitRunnable {

	private int timer = 5;
	

	@Override
	public void run() {
		
		if(timer == 0) {
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.getInventory().clear();
				player.setGameMode(GameMode.SURVIVAL);
		        player.sendTitle("",ChatColor.YELLOW + "" + ChatColor.BOLD +  "Bonne chance à tous", 0, 60, 10);
			}
			
			for(World wld : Bukkit.getServer().getWorlds()){
			    wld.setPVP(true);
			}
			
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "spreadplayers 0 0 300 900 true @a");
			
			cancel();
			return;
		}
		
		for (Player player : Bukkit.getOnlinePlayers()) {
	        player.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + timer, "", 0, 20, 0);
		}
		
		timer -= 1;
		
	}

}
