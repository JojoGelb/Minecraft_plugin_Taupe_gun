package code;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import code.Listener.Game_listener;
import code.chat_commands.Game_controller_commands;


public class Main extends JavaPlugin {
	
	Game_controller_commands commandeBorderListerner;
	
	Game_listener game_listener;
	
	public boolean isLobby = true;
	
	public List<Player> players = new ArrayList<>();
	
	public List<Player> taupes = new ArrayList<>();
	
	public Scoreboard scoreboard;
	
	public WorldBorder world_border;
	
	@Override
	public void onEnable() {
		
		System.out.println("Le plugin vient de démarrer");
		
		//Desactive le pvp dans tous les mondes
		for(World wld : getServer().getWorlds()){
		    wld.setPVP(false);
		}
		
		//Fonction qui vérifie les teams et les instancies
		this.create_scordboard();
		
		//Création world border
		World world = Bukkit.getWorld("world");
		this.world_border = world.getWorldBorder();
		this.world_border.setCenter(0,0);
		this.world_border.setSize(2000);
		
		//lobby controller
		this.game_listener = new Game_listener(this);
		this.getServer().getPluginManager().registerEvents(this.game_listener, this);
		
		//In game controller
		this.commandeBorderListerner = new Game_controller_commands(this);
		this.getCommand("start_game").setExecutor(commandeBorderListerner);
		this.getCommand("msg_taupes").setExecutor(commandeBorderListerner);

	}
	
	public void create_scordboard() {
		//Récupère le scoreboard de tab, ajoute les teams au besoin, change les couleurs
		scoreboard= Bukkit.getScoreboardManager().getMainScoreboard();
		
		// How to delete a team from code
		//scoreboard.getTeam("BLUE").unregister();

		
		//On ajoute des équipes si elles n'existent pas déja
		if(scoreboard.getTeam("NO_TEAM") == null) {
			scoreboard.registerNewTeam("NO_TEAM");
		}
		//Met un préfix devant le pseudo des joueurs
		scoreboard.getTeam("NO_TEAM").setPrefix(ChatColor.YELLOW + "[NO TEAM] ");
		//Change la couleur des joueurs
		scoreboard.getTeam("NO_TEAM").setColor(ChatColor.YELLOW);
		scoreboard.getTeam("NO_TEAM").setAllowFriendlyFire(false);
		
		if(scoreboard.getTeam("NOIR") == null) {
			scoreboard.registerNewTeam("NOIR");
		}
		scoreboard.getTeam("NOIR").setColor(ChatColor.BLACK);
		scoreboard.getTeam("NOIR").setAllowFriendlyFire(true);
		
		if(scoreboard.getTeam("VERT_FONCE") == null) {
			scoreboard.registerNewTeam("VERT_FONCE");
		}
		scoreboard.getTeam("VERT_FONCE").setColor(ChatColor.DARK_GREEN);
		scoreboard.getTeam("VERT_FONCE").setAllowFriendlyFire(true);
		
		if(scoreboard.getTeam("BLEU_CLAIR") == null) {
			scoreboard.registerNewTeam("BLEU_CLAIR");
		}
		scoreboard.getTeam("BLEU_CLAIR").setColor(ChatColor.AQUA);
		scoreboard.getTeam("BLEU_CLAIR").setAllowFriendlyFire(true);
		
		if(scoreboard.getTeam("OR") == null) {
			scoreboard.registerNewTeam("OR");
		}
		scoreboard.getTeam("OR").setColor(ChatColor.GOLD);
		scoreboard.getTeam("OR").setAllowFriendlyFire(true);
		
		if(scoreboard.getTeam("VERT") == null) {
			scoreboard.registerNewTeam("VERT");
		}
		scoreboard.getTeam("VERT").setColor(ChatColor.GREEN);
		scoreboard.getTeam("VERT").setAllowFriendlyFire(true);
		
		if(scoreboard.getTeam("ROUGE") == null) {
			scoreboard.registerNewTeam("ROUGE");
		}
		scoreboard.getTeam("ROUGE").setColor(ChatColor.RED);
		scoreboard.getTeam("ROUGE").setAllowFriendlyFire(false);
		
		if(scoreboard.getTeam("BLEU") == null) {
			scoreboard.registerNewTeam("BLEU");
		}
		scoreboard.getTeam("BLEU").setColor(ChatColor.BLUE);
		scoreboard.getTeam("BLEU").setAllowFriendlyFire(false);
		
		if(scoreboard.getTeam("ROSE") == null) {
			scoreboard.registerNewTeam("ROSE");
		}
		scoreboard.getTeam("ROSE").setColor(ChatColor.LIGHT_PURPLE);
		scoreboard.getTeam("ROSE").setAllowFriendlyFire(false);
		
		if(scoreboard.getTeam("BLANC") == null) {
			scoreboard.registerNewTeam("BLANC");
		}
		scoreboard.getTeam("BLANC").setColor(ChatColor.WHITE);
		scoreboard.getTeam("BLANC").setAllowFriendlyFire(true);
		
	}
	

}