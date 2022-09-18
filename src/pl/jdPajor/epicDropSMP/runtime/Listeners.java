package pl.jdPajor.epicDropSMP.runtime;

import java.io.IOException;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pl.jdPajor.epicDropSMP.MainReg;
import pl.jdPajor.epicDropSMP.configuration.Config;
import pl.jdPajor.epicDropSMP.include.Colors;
import pl.jdPajor.epicDropSMP.include.Item;
import pl.jdPajor.epicDropSMP.include.RandomUtil;
import pl.jdPajor.epicDropSMP.runtime.cmd.DropCommand;
import pl.jdPajor.epicDropSMP.runtime.cmd.DropEditCommand;
import pl.jdPajor.epicDropSMP.runtime.data.Tombstone;
import pl.jdPajor.epicDropSMP.runtime.data.User;

public class Listeners implements Listener {
	
	@EventHandler
	public void hoin2(PlayerJoinEvent e) {
		e.getPlayer().discoverRecipe(MainReg.nkey);
	}
	
	@EventHandler 
	public void hove(PlayerMoveEvent e) {
		if (e.isCancelled()) return;
		if (!e.getPlayer().isOnGround()) return;
		if (e.getPlayer().getInventory().getBoots() != null && e.getPlayer().getInventory().getBoots().hasItemMeta()) {
			if (e.getPlayer().getInventory().getBoots().getEnchantmentLevel(Enchantment.DURABILITY) == 4) {
				Location loc = e.getTo().clone().add(0, -1, 0);
				for (int i = -3; i < 4; i++) {
					for (int j = -3; j < 4; j++) {
						Block b = loc.clone().add(i, 0, j).getBlock();
						if (b.getLocation().distance(e.getTo()) < 3 && b.getType() == Material.LAVA) {
							b.setType(Material.COBBLESTONE);
						}
					}	
				}
			}
		}
	}
	
	@EventHandler
	public void hlick(InventoryClickEvent e) {
		if (e.getView().getTitle().equals(Colors.fix("&a&lINFORMACJE O DROPIE"))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void hlickBook(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.hasItem()) {
			Block b = e.getClickedBlock().getLocation().add(0, 1, 0).getBlock();
			Tombstone t = Tombstone.getTombstone(b.getX(), b.getY(), b.getZ()); 
			if (t != null) {
				e.setCancelled(true);
			}
		}
		if (e.hasItem() && e.getItem().hasItemMeta() && (e.getItem().getType() == Material.FILLED_MAP || e.getItem().getType() == Material.BOOK) && e.getItem().getItemMeta().hasDisplayName() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (e.getItem().getItemMeta().getDisplayName().equals(Config.getValue_BUKKIT_ITEMSTACK(Config.ITEM_MYSTERY).getItemMeta().getDisplayName())) {
				e.setCancelled(true);
				if (e.getItem().getAmount() > 1) {
					e.getItem().setAmount(e.getItem().getAmount() - 1);
				} else {
					e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				}
				int r = RandomUtil.getRandInt(0, 100);
				if (r < 15) { //15%
					switch (RandomUtil.getRandInt(0, 4)) {
					case 1:
						e.getPlayer().getInventory().addItem(DropCommand.setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój mocy")).addLore(Colors.fix("&7Przywraca &c10% 🗡")).toIs(), Color.RED, 0));
						break;
					case 2:
						e.getPlayer().getInventory().addItem(DropCommand.setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój szybkości")).addLore(Colors.fix("&7Przywraca &b10% ☄")).toIs(), Color.AQUA, 1));
						break;
					case 3:
						e.getPlayer().getInventory().addItem(DropCommand.setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój wytrzymałości")).addLore(Colors.fix("&7Przywraca &610% 🛡")).toIs(), Color.ORANGE, 2));
						break;
					case 4:
						e.getPlayer().getInventory().addItem(DropCommand.setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&cInstygnia śmierci")).addLore(Colors.fix("&7Przywraca wszystkie statystyki")).toIs(), Color.GREEN, 3));
						break;
					default:
						e.getPlayer().getInventory().addItem(DropCommand.setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój życia")).addLore(Colors.fix("&7Dodaje &c1 serce")).toIs(), Color.BLACK, 3));
						break;
					}
					
				} else
				if (r >= 15 && r < 35) { // 20%
					switch (RandomUtil.getRandInt(0, 2)) {
					case 1:
						e.getPlayer().getInventory().addItem(new Item(Material.NETHERITE_PICKAXE).addEnchant(Enchantment.DURABILITY, 4).addLore(Colors.fix("&bKilof 3x3")).toIs());
						break;
					case 2:
						e.getPlayer().getInventory().addItem(new Item(Material.NETHERITE_SWORD).addEnchant(Enchantment.DURABILITY, 4).addLore(Colors.fix("&bNaklada efekt mdłości na przeciwnika")).toIs());
						break;
					default:
						e.getPlayer().getInventory().addItem(DropCommand.civicBoots);
						break;
					}
					
				} else { 
					int i = 0;
					for (; i < 27; i++) {
						if (DropEditCommand.getIS(i) == null) break;
					}
					ItemStack is = DropEditCommand.getIS(RandomUtil.getRandInt(0, i - 1));
					e.getPlayer().getInventory().addItem(is);
				}
			}
			if (e.getItem().getItemMeta().getDisplayName().equals(Colors.fix("&eZwój mocy"))) {
				e.setCancelled(true);
				if (e.getItem().getAmount() > 1) {
					e.getItem().setAmount(e.getItem().getAmount() - 1);
				} else {
					e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				}
				User u = User.getUser(e.getPlayer().getName());
				u.setVar1(u.getVar1() + 10);
				try {
					u.save();
				} catch (IOException e1) { e1.printStackTrace(); }
			}
			if (e.getItem().getItemMeta().getDisplayName().equals(Colors.fix("&eZwój szybkości"))) {
				e.setCancelled(true);
				if (e.getItem().getAmount() > 1) {
					e.getItem().setAmount(e.getItem().getAmount() - 1);
				} else {
					e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				}
				User u = User.getUser(e.getPlayer().getName());
				u.setVar2(u.getVar2() + 10);
				try {
					u.save();
				} catch (IOException e1) { e1.printStackTrace(); }
			}
			if (e.getItem().getItemMeta().getDisplayName().equals(Colors.fix("&eZwój wytrzymałości"))) {
				e.setCancelled(true);
				if (e.getItem().getAmount() > 1) {
					e.getItem().setAmount(e.getItem().getAmount() - 1);
				} else {
					e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				}
				User u = User.getUser(e.getPlayer().getName());
				u.setVar3(u.getVar3() + 10);
				try {
					u.save();
				} catch (IOException e1) { e1.printStackTrace(); }
			}
			if (e.getItem().getItemMeta().getDisplayName().equals(Colors.fix("&cInstygnia śmierci"))) {
				e.setCancelled(true);
				if (e.getItem().getAmount() > 1) {
					e.getItem().setAmount(e.getItem().getAmount() - 1);
				} else {
					e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				}
				User u = User.getUser(e.getPlayer().getName());
				u.setVar1(Config.getValue_INT(Config.STAT1_INS_VAL));
				u.setVar2(Config.getValue_INT(Config.STAT2_INS_VAL));
				u.setVar3(Config.getValue_INT(Config.STAT3_INS_VAL));
				try {
					u.save();
				} catch (IOException e1) { e1.printStackTrace(); }
			} 
			if (e.getItem().getItemMeta().getDisplayName().equals(Colors.fix("&eZwój życia"))) {
				e.setCancelled(true);
				if (e.getItem().getAmount() > 1) {
					e.getItem().setAmount(e.getItem().getAmount() - 1);
				} else {
					e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				}
				e.getPlayer().setMaxHealth(e.getPlayer().getMaxHealth() + 2);
			}
		}
	}
	
	@EventHandler
	public void hater2(BlockFromToEvent e) {
		if (e.getBlock().getType() == Material.WATER) {
			if (e.getToBlock().getType() == Material.PLAYER_HEAD) {
				e.setCancelled(true);
			}
		}
 	}
	
	@EventHandler
	public void hoin(PlayerLoginEvent e) {
		Tombstone t = Tombstone.getTombstone(e.getPlayer().getName());
		if (t != null) {
			long time = t.time - System.currentTimeMillis();
			long sec = (time / 1000) % 60;
			long min = (time / (1000 * 60)) % 60;
			e.setResult(Result.KICK_BANNED);
			if (t.y == -256) {
				e.setKickMessage(Colors.fix("&aRegeneracja konczy sie za &f" + min + " minut, " + sec + " sekund!"));
	
			} else
			e.setKickMessage(Colors.fix("&7Twoj grob znajduje sie na kordach: &fX:" + t.x + " Y:" + t.y + " Z:" + t.z + "\n&aRegeneracja konczy sie za &f" + min + " minut, " + sec + " sekund!"));
		}
	}
	
	@EventHandler
	public void heath(PlayerDeathEvent e) {
		if (e.isAsynchronous()) return;
		User u = User.getUser(e.getEntity().getName());
		if (u.getVar1() >= 10) u.setVar1(u.getVar1() - 10);
		if (u.getVar2() >= 10) u.setVar2(u.getVar2() - 10);
		if (u.getVar3() >= 10) u.setVar3(u.getVar3() - 10);
		if (RandomUtil.getRandInt(0, 100) <= Config.getValue_DOUBLE(Config.ITEM_MYSTERY_CHANCE)) e.getDrops().add(Config.getValue_BUKKIT_ITEMSTACK(Config.ITEM_MYSTERY));
		if ((Config.getValue_BOOL(Config.STAT1_BAN) && u.getVar1() <= 0) || (Config.getValue_BOOL(Config.STAT2_BAN) && u.getVar2() <= 0) || (Config.getValue_BOOL(Config.STAT3_BAN) && u.getVar3() <= 0)) {
			Block tb;
			if (!Config.getValue_BOOL(Config.TOMBSTONE_ENABLED)) {
				tb = e.getEntity().getWorld().getBlockAt(0, -256, 0);
			} else {
				e.getEntity().getLocation().getBlock().setType(Material.BEDROCK);
				tb = e.getEntity().getLocation().add(0, 1, 0).getBlock();
				tb.setType(Material.PLAYER_HEAD);
				Skull sk = (Skull) tb.getState();
				sk.setOwner(e.getEntity().getName());
				sk.update();
			}
			Tombstone t = new Tombstone(e.getEntity().getName(), tb.getX(), tb.getY(), tb.getZ(), System.currentTimeMillis() + (1000 * 60 * Config.getValue_INT(Config.TOMBSTONE_BANTIME)), e.getEntity().getLocation().getWorld().getName());
			Tombstone.Tombstones.add(t);
			try {
				t.save();
			} catch (IOException e1) { e1.printStackTrace(); }
			long time = t.time - System.currentTimeMillis();
			long sec = (time / 1000) % 60;
			long min = (time / (1000 * 60)) % 60;
			e.getEntity().kickPlayer(Colors.fix("&aRegeneracja konczy sie za &f" + min + " minut, " + sec + " sekund!"));
		}
	}
	
	@EventHandler
	public void hamage2(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			float f = 100 - User.getUser(p.getName()).getVar3();
			double ff = (Math.abs(f) / 100.f) * e.getDamage();
			double dmg = e.getDamage() + ff;
			dmg = Double.parseDouble("" + dmg);
			if (e.getDamage() > p.getHealth()) {
				e.setDamage(p.getHealth());
			} else
			e.setDamage(dmg);
		}
	}
	
	@EventHandler
	public void hamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			float f = User.getUser(p.getName()).getVar1() / 100.f;
			e.setDamage(e.getDamage() * f);
			if (e.getEntity() instanceof LivingEntity)
			if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DURABILITY) == 4 && p.getInventory().getItemInMainHand().getType() == Material.NETHERITE_SWORD) {
				((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Config.getValue_INT(Config.SWORD_EFFECT_TIME), Config.getValue_INT(Config.SWORD_EFFECT_AMF)));
			}
		}
	}

	@EventHandler
	public void hreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.PLAYER_HEAD) {
			Tombstone t = Tombstone.getTombstone(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ());
			if (t != null) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR);
				e.getBlock().getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
				User u = User.getUser(t.getName());
				u.setVar1(Config.getValue_INT(Config.STAT1_RESTORE_VAL));
				u.setVar2(Config.getValue_INT(Config.STAT2_RESTORE_VAL));
				u.setVar3(Config.getValue_INT(Config.STAT3_RESTORE_VAL));
				try {
					u.save();
				} catch (IOException e1) { e1.printStackTrace(); }
				t.delete();
			}
		}
		
	}
}
