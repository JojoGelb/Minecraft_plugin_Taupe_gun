package code.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import code.Main;
import code.Object_creator;
import net.md_5.bungee.api.ChatColor;

public class Game_listener implements Listener {
	
	Main main;
	
	//Event qui s'active lorsqu'un joueur se connecte au serveur
		public Game_listener(Main main) {
			this.main = main;
	}

		@EventHandler
		public void onJoint(PlayerJoinEvent event) {
			
			System.out.println("CONNECTION");
			
			Player player = event.getPlayer();
			//player.getInventory().clear();
			
			boolean player_exist = false;
			
			for(Player p : this.main.players){

				if(p.getName().equalsIgnoreCase(player.getName())) {
					player_exist = true;
					break;
				}
			}
			
			if(player_exist == false) {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				//player.getInventory().clear();
				this.main.players.add(player);
				
				//Ce sera ici pour corriger les soucis de redémarage serveur in game et conservation d'équipe
				
				this.main.scoreboard.getTeam("NO_TEAM").addEntry(player.getName());
				player.sendMessage("§6[PLUGIN INFO] You are in no team: use the command /gui_compass to change team");
				player.setDisplayName(ChatColor.YELLOW + player.getName() + ChatColor.WHITE);
				//player.setPlayerListName("§6[NO TEAM] §f" + player.getDisplayName());*/
				
			}
			
			ItemStack gui_compass = Object_creator.create_gui_compass(this.main);
			
			//Ajout à l'inventaire du joueur
			player.getInventory().addItem(gui_compass);
			
			Object_creator.createBoard(player,"00:00",ChatColor.AQUA + "Border: " + ChatColor.DARK_AQUA + "80:00", ChatColor.AQUA + "Taupes: " + ChatColor.DARK_AQUA + "40:00", true);
	
		}
		
		@EventHandler
		public void onInteract(PlayerInteractEvent event) {
			//S'occupe des actions de click de l'utilisateur
			Player p = event.getPlayer();
			Action a = event.getAction();
			
			ItemStack it = event.getItem();
			
			//Si rien en main lors de l'action : rien ne se passe
			if (it == null) return;
			
			//Vérifie si click droit
			if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)  {
				if(this.main.isLobby && it.getType().equals(Material.COMPASS)) {
					//Créé un inventaire, paramètres: type d'inventaire: null, nombre de cases: 1 (max = 54)
					Inventory inv = Bukkit.createInventory(null, 9, "§8Menu choix des teams");
					
					populate_GUI_inventory(inv);
					
					p.openInventory(inv);
				}
			}
		}
		
		public void onDrop(PlayerDropItemEvent event) {
			
			if(this.main.isLobby == true) {
				Item item = event.getItemDrop();
				
				if(item.getType().equals(Material.COMPASS)) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Ne drop pas ta bousolle pour choisir les teams stp");
				}
			}
			
		}
		
		@EventHandler
		public void on_click_inventory(InventoryClickEvent event) {
			//Check les clicks dans un inventaire
			
			//Inventory inv = event.getInventory();
			Player p = (Player) event.getWhoClicked();
			ItemStack current = event.getCurrentItem();
			
			//Click dans le vide
			if(current == null) return;
			
			/* Petit problème: l'utilisateur peut bouger l'objet avec shift
			 * if(p.getOpenInventory().getTitle().equalsIgnoreCase("Crafting") ) {
				if (current.hasItemMeta() && current.getItemMeta().getDisplayName().equalsIgnoreCase("TEAM")){
					event.setCancelled(true);
					return;
				}
			}*/
			
			System.out.println(p.getOpenInventory().getTitle());
			
			if(p.getOpenInventory().getTitle().equalsIgnoreCase("§8Menu choix des teams") ) {
				
				//On evite qu'il garde l'item en main
				event.setCancelled(true);
				p.closeInventory();
				
				//Ici on vérifie la couleur de la laine selectionné dans l'inventaire
				switch(current.getType()) {
					case BLACK_WOOL:
						this.main.scoreboard.getTeam("BLACK").addEntry(p.getName());
						//Change la couleur quand ça tappe dans le chat
						p.setDisplayName(ChatColor.BLACK + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team noir");
						break;
					case GREEN_WOOL:
						this.main.scoreboard.getTeam("VERT_FONCE").addEntry(p.getName());
						//Change la couleur quand ça tappe dans le chat
						p.setDisplayName(ChatColor.DARK_GREEN + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team vert foncé");
						break;
					case LIGHT_BLUE_WOOL:
						this.main.scoreboard.getTeam("BLEU_CLAIR").addEntry(p.getName());
						//Change la couleur quand ça tappe dans le chat
						p.setDisplayName(ChatColor.AQUA + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team bleu clair");
						break;
					case YELLOW_WOOL:
						this.main.scoreboard.getTeam("OR").addEntry(p.getName());
						//Change la couleur quand ça tappe dans le chat
						p.setDisplayName(ChatColor.GOLD + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team or");
						break;
					case LIME_WOOL:
						this.main.scoreboard.getTeam("VERT").addEntry(p.getName());
						//Change la couleur quand ça tappe dans le chat
						p.setDisplayName(ChatColor.GREEN + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team verte");
						break;
					case BLUE_WOOL:
						this.main.scoreboard.getTeam("BLEU").addEntry(p.getName());
						p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team bleu");
						break;
					case RED_WOOL:
						this.main.scoreboard.getTeam("ROUGE").addEntry(p.getName());
						p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO]  Tu as rejoint la team rouge");
						break;
					case WHITE_WOOL:
						this.main.scoreboard.getTeam("WHITE").addEntry(p.getName());
						p.setDisplayName(ChatColor.WHITE + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team blanche");
						break;
					case PINK_WOOL:
						this.main.scoreboard.getTeam("PINK").addEntry(p.getName());
						p.setDisplayName(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.WHITE);
						p.sendMessage("§6[PLUGIN INFO] Tu as rejoint la team rose");
						break;
					default:
						p.sendMessage("§1WTF elle sort d'ou cette laine?");
						break;
				}
				
			}
		}
		
		@EventHandler
		public void on_death(PlayerDeathEvent event) {
			Player p = (Player) event.getEntity();
			int num_team_alive = 0;
			boolean taupe = false;
			
			Team store_team_alive = null;
			
			Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
			
			//Pour chaque team: ajouter aléatoirement une taupe
			for(Team t: s.getTeams()) {
				if(!t.getEntries().isEmpty()) {
					
					boolean team_alive = false;
					
					for(String name : t.getEntries()) {
						
						Player pl = (Player) Bukkit.getPlayer(name);
						if(pl != p && pl.getGameMode().equals(GameMode.SURVIVAL)) {
							
							if(pl.getScoreboardTags().contains("TAUPE")) {
								if(!taupe) {
									taupe = true;
									num_team_alive += 1;
								}
								
							}else {
								if(team_alive == false) {
									team_alive = true;
									num_team_alive +=1; 
									store_team_alive = t;
								}
							}
							
						}
						
					}
				}		
			}
			
			if(num_team_alive == 1) {
				if(taupe) {
					Bukkit.broadcastMessage(ChatColor.BOLD + "TEAM TAUPE WIN");
				}else {
					Bukkit.broadcastMessage(ChatColor.BOLD + "TEAM " + store_team_alive.getDisplayName() +" WIN");
				}
			}
			
		}
			
		
		
		@EventHandler
		public void on_respawn(PlayerRespawnEvent event) {

			Player p = (Player) event.getPlayer();

			if(this.main.isLobby) {
				p.setGameMode(GameMode.SURVIVAL);
				event.setRespawnLocation((new Location(p.getWorld(), 0, 200, 0)));
				new BukkitRunnable() {
					  public void run() {
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,200,255));
					  }
					}.runTaskLater(this.main, 20);
			}else {
				p.setGameMode(GameMode.SPECTATOR);
			}
		}
		
		
		//--------------------------Non Listerners------------------------------------
		
		
		public void populate_GUI_inventory(Inventory inv) {
			
			
			ItemStack blackWool = create_itemStack(Material.BLACK_WOOL, "§aEquipe noir");
			inv.setItem(0, blackWool);
			
			ItemStack greenWool = create_itemStack(Material.GREEN_WOOL, "§aEquipe verte foncé");
			inv.setItem(1, greenWool);
			
			ItemStack light_blueWool = create_itemStack(Material.LIGHT_BLUE_WOOL, "§aEquipe bleu claire");
			inv.setItem(2, light_blueWool);
			
			ItemStack gold_wool = create_itemStack(Material.YELLOW_WOOL, "§aEquipe or");
			inv.setItem(3, gold_wool);
			
			ItemStack green_wool = create_itemStack(Material.GREEN_WOOL, "§aEquipe verte");
			inv.setItem(4, green_wool);
			
			ItemStack red_wool = create_itemStack(Material.RED_WOOL, "§aEquipe rouge");
			inv.setItem(5, red_wool);
			
			ItemStack blue_wool = create_itemStack(Material.BLUE_WOOL, "§aEquipe bleu");
			inv.setItem(6, blue_wool);
			
			ItemStack pinkWool = create_itemStack(Material.PINK_WOOL, "§aEquipe Rose");
			inv.setItem(7, pinkWool);
			
			ItemStack white_Wool = create_itemStack(Material.WHITE_WOOL, "§aEquipe Blanche");
			inv.setItem(7, white_Wool);
		
		
		}
		
		public ItemStack create_itemStack(Material mat, String name) {
			ItemStack item = new ItemStack(mat, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			item.setItemMeta(meta);
			return item;
		}
}
