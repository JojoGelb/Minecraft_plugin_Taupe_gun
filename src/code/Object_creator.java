package code;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.Scoreboard;

public class Object_creator {
	
	public static ItemStack create_gui_compass( Main main) {
			
			//Créé une boussole
			ItemStack compass = new ItemStack(Material.COMPASS, 1);
			
			//Récupère l'objet gérant les données de l'épé
			ItemMeta custom = compass.getItemMeta();
			
			//Change les propriétés de l'objet: voir la documentation de ItemMeta pour voir ce qu'on peut faire d'autre
			custom.setDisplayName("$4Choix de l'équipe");
			custom.setLore(Arrays.asList("Sélectionne ton équipe"));
			
			//applique les modifications
			compass.setItemMeta(custom);
			
			//on retourne le résultat à la fonction qui l'a appellé
			return compass;
	}
	
	public static void createBoard(Player p, String timer, String line_before1, String line_before2,  boolean pas_commencé) {
		
		int distance_closest_border = distance_to_border(p);
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getMainScoreboard();
		
		Objective obj;
		
		if(board.getObjective("taupe_gun_board") != null) {
			obj = board.getObjective("taupe_gun_board");
			obj.unregister();
		}
		
		obj = board.registerNewObjective("taupe_gun_board", "dummy", ChatColor.DARK_RED + "Taupe Gun");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
		
		Score score = obj.getScore(ChatColor.BLUE + "###############");					 
		score.setScore(3);
		
		Score score2 = obj.getScore(ChatColor.AQUA + "Timer:   " + ChatColor.DARK_AQUA + timer);
		score2.setScore(2);
		
		Score score3 = obj.getScore(line_before2);
		score3.setScore(1);
		
		Score score4 = obj.getScore(line_before1);
		score4.setScore(0);
		
		//Impossible à cause de pbms de compatibilité avec le main scoreboard
		/*Score score4;
		if(pas_commencé == false) {
			score4 = obj.getScore(ChatColor.AQUA + "DistanceBorder: " + ChatColor.DARK_AQUA + distance_closest_border);
		}else {
			score4 = obj.getScore(ChatColor.AQUA + "DistanceBorder: " + ChatColor.DARK_AQUA + 2000);
		}
		score4.setScore(3);*/
		
		p.setScoreboard(board);
	}
	
	private static int distance_to_border(Player p) {
		
		Location pLoc = p.getLocation();
		
		double worldB_size = p.getWorld().getWorldBorder().getSize();
		
		if(Math.abs(pLoc.getX()) > Math.abs(pLoc.getZ())){
			return (int) (worldB_size/2 - Math.abs(pLoc.getX()));
		}else {
			return (int) (worldB_size/2 - Math.abs(pLoc.getZ()));
		}
	}
		
}
