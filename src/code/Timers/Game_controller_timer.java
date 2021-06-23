package code.Timers;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import code.Main;
import code.Object_creator;
import net.md_5.bungee.api.ChatColor;

public class Game_controller_timer extends BukkitRunnable{
	
	Main main;
	
	public int timer = 0;
	
	public static int taupe_assign = 2400; //40 minutes en tick
	
	public static int start_border = 4800; //80 minutes en tick
	
	public boolean moove_border = true;
	
	public Random rand = new Random();

	public Game_controller_timer(Main main) {
		System.out.println("CLASSE CREEE");
		this.main = main;
	}
	
	

	@Override
	public void run() {
		
		this.timer += 1;
		
		if(timer < taupe_assign) {
			for(Player p: Bukkit.getOnlinePlayers()) {
				Object_creator.createBoard(p, this.getTimer(),ChatColor.AQUA + "Border: " + ChatColor.DARK_AQUA + this.getTimerbefore(start_border), ChatColor.AQUA + "Taupes: " + ChatColor.DARK_AQUA + this.getTimerbefore(taupe_assign),false);
			}
		}else if(timer < start_border) {
			for(Player p: Bukkit.getOnlinePlayers()) {
				Object_creator.createBoard(p, this.getTimer(),ChatColor.AQUA + "Border: " + ChatColor.DARK_AQUA + this.getTimerbefore(start_border),ChatColor.AQUA + "Les taupes sont désignés" ,false);
			}
		}else {
			for(Player p: Bukkit.getOnlinePlayers()) {
				Object_creator.createBoard(p, this.getTimer(),ChatColor.AQUA + "Taille Border: " + ChatColor.AQUA + (int) p.getWorld().getWorldBorder().getSize(), ChatColor.AQUA + "Les taupes sont désignés", false);
			}
		}
		
		
		
		if(this.timer == taupe_assign) {
			
			//Récupère le scoreboard
			Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
			
			int nb_taupes = 0;
			
			//Pour chaque team: ajouter aléatoirement une taupe
			for(Team t: s.getTeams()) {
				if(!t.getEntries().isEmpty()) {
					int random = this.rand.nextInt(t.getEntries().size());
					
					for(String name : t.getEntries()) {
						if(random == 0) {
							Player p = (Player) Bukkit.getPlayer(name);
							p.addScoreboardTag("TAUPE");
							this.main.taupes.add(p);
							p.sendMessage(ChatColor.BOLD + "§6[Plugin info] §fVous êtes désigné comme l'une des taupes. §6/msg_taupes §fpour discuter avec ta nouvelle équipe.");
							nb_taupes +=1;
						}
						random -=1;
					}		
				}
			}
			
			for(Player p: Bukkit.getOnlinePlayers()) {
				p.sendTitle("", ChatColor.BOLD + "§4Apparition de taupes", 0, 60, 20);
			}
			
			Bukkit.broadcastMessage(ChatColor.BOLD + "§4" + nb_taupes + " taupes viennent d'être désignés");
		}
		
		if(this.timer >= start_border) {
			// 1 bloc every 2s
			this.main.world_border.setSize(this.main.world_border.getSize()- 1);
			/*if(this.main.world_border.getSize() >50 ) {
				if(this.moove_border) {
					this.main.world_border.setSize(this.main.world_border.getSize()- 1);
					this.moove_border = false;
				}else {
					this.moove_border = true;
				}
			}*/
			
				
		}
		
	}
	
	public String getTimer() {
		
		String secondes = String.valueOf(this.timer % 60);
		String minutes = String.valueOf((this.timer - Integer.valueOf(secondes)) / 60);
		
		if(minutes.length()<2) {
			minutes = "0" + minutes;
		}
		
		if(secondes.length() < 2) {
			secondes = "0" + secondes;
		}
		return minutes + ":" + secondes;
	}
	
	public String getTimerbefore(int number) {
		//Number sera ici taupe_assign et start_border
	
		int time = number - this.timer;
		
		String secondes = String.valueOf(time % 60);
		String minutes = String.valueOf((time - Integer.valueOf(secondes)) / 60);
		
		if(minutes.length()<2) {
			minutes = "0" + minutes;
		}
		
		if(secondes.length() < 2) {
			secondes = "0" + secondes;
		}
		return minutes + ":" + secondes;
	}
	
	
	
	
	

}
