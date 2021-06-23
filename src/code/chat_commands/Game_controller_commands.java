package code.chat_commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.Main;
import code.Timers.Compte_a_rebours_start;
import code.Timers.Game_controller_timer;
import net.md_5.bungee.api.ChatColor;

public class Game_controller_commands implements CommandExecutor{
	
	public Main main;
	public Game_controller_timer game_timer;

	public Game_controller_commands(Main main) {

		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("start_game") && (sender.getName().equalsIgnoreCase("jejeplusfaim") || sender.getName().equalsIgnoreCase("ccforakalabo"))) {
			
			if(!Bukkit.getScoreboardManager().getMainScoreboard().getTeam("NO_TEAM").getEntries().isEmpty()) {
				Bukkit.broadcastMessage(ChatColor.BOLD + "§6[Plugin info] §fUn joueur n'a pas de team. Annulation de la commande");
				return false;
			}
			
			game_timer = new Game_controller_timer(this.main);
			
			new Compte_a_rebours_start().runTaskTimer(this.main, 0, 20);
			new Game_controller_timer(this.main).runTaskTimer(this.main, 120, 20);
			
			//TP les joueurs par team
			
			//lancer la border
			
			//chrono
		}
		
		//Commande pour envoyer des messages qu'aux taupes
		if(cmd.getName().equalsIgnoreCase("msg_taupes") && sender instanceof Player){
			
			Player p = (Player) sender;
			
			if(!p.getScoreboardTags().contains("TAUPE")) {
				p.sendMessage(ChatColor.BOLD + "§4Seul les taupes peuvent parler avec cette commande");
				return false;
			}
			
			if(args.length == 0) {
				p.sendMessage(ChatColor.BOLD +"§4Empty message");
				return false;
			}
			
			String message = "";
			
			for (String ar : args) {
				message += ar + " ";
			}
			
			//On boucle sur tous les joueurs: on regarde s'ils ont le tag taupe, si oui on envoie le msg
			for( Player pl : Bukkit.getOnlinePlayers()) {
				if(pl.getScoreboardTags().contains("TAUPE")) {
					pl.sendMessage(ChatColor.ITALIC + "§8[Taupe]<" + p.getDisplayName() + "> " + message);
				}
				
			}
			
			return true;
		}
		return false;
	}

}
