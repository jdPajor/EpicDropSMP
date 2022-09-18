package pl.jdPajor.epicDropSMP.runtime.cmd;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import pl.jdPajor.epicDropSMP.MainReg;
import pl.jdPajor.epicDropSMP.configuration.Config;
import pl.jdPajor.epicDropSMP.include.ACmd;
import pl.jdPajor.epicDropSMP.include.Chat;
import pl.jdPajor.epicDropSMP.include.Colors;
import pl.jdPajor.epicDropSMP.include.Item;
import pl.jdPajor.epicDropSMP.runtime.MapDagger;
import pl.jdPajor.epicDropSMP.runtime.MapDead;
import pl.jdPajor.epicDropSMP.runtime.MapDefault;
import pl.jdPajor.epicDropSMP.runtime.MapHeart;
import pl.jdPajor.epicDropSMP.runtime.MapShield;
import pl.jdPajor.epicDropSMP.runtime.MapSpeed;
import pl.jdPajor.epicDropSMP.runtime.data.Tombstone;
import pl.jdPajor.epicDropSMP.runtime.data.User;

public class DropCommand extends ACmd {
	public static ItemStack civicBoots = new Item(Material.NETHERITE_BOOTS)
			.addLore(Colors.fix("&fHonda Civic™ IV 1.4 Boots")).addLore(Colors.fix("&cStop hybrid, go VTEC!")).addLore(Colors.fix("&bTDI killer")).addLore(Colors.fix("&bZamienia lawe w cobblestone"))
			//.setAttribute(Attribute.GENERIC_MOVEMENT_SPEED, "GENERIC_MOVEMENT_SPEED", 0.05, EquipmentSlot.FEET)
			//.setAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE, "GENERIC_KNOCKBACK_RESISTANCE", 0.1, EquipmentSlot.FEET)
			//.setAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS, "GENERIC_ARMOR_TOUGHNESS", 3, EquipmentSlot.FEET)
			//.setAttribute(Attribute.GENERIC_ARMOR, "GENERIC_ARMOR", 3, EquipmentSlot.FEET)
			.addEnchant(Enchantment.DURABILITY, 4)
			.toIs();
	public static ItemStack mendingIS = new Item(Material.ENCHANTED_BOOK).toIs();
	
	static {
		EnchantmentStorageMeta im = (EnchantmentStorageMeta) mendingIS.getItemMeta();
		im.addStoredEnchant(Enchantment.MENDING, 1, true);
		mendingIS.setItemMeta(im);
	}
	
	public static ItemStack setMapColor(ItemStack sis, Color c, int flag) {
		ItemStack is = sis;
		MapMeta im = (MapMeta) is.getItemMeta();
		if (im == null) im = (MapMeta) Bukkit.getItemFactory().getItemMeta(Material.FILLED_MAP);
		im.setColor(c);
		if (!im.hasMapView()) im.setMapView(Bukkit.createMap(Bukkit.getWorlds().get(0)));
		MapView m = im.getMapView();
		for (MapRenderer r : m.getRenderers()) {
			m.removeRenderer(r);
		}
		switch (flag) {
		default:
			m.addRenderer(new MapDefault());
			break;
		case 0:
			m.addRenderer(new MapDagger());
			break;
		case 1:
			m.addRenderer(new MapSpeed());
			break;
		case 2:
			m.addRenderer(new MapShield());
			break;
		case 3:
			m.addRenderer(new MapDead());
			break;
		case 4:
			m.addRenderer(new MapHeart());
			break;
					
		}
		
		im.setMapView(m);
		is.setItemMeta(im);
		return is;
	}
	
	public DropCommand() {
		super("drop");
	}

	@Override
	public void onExecute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Player p = (Player) sender;
			Inventory inv = Bukkit.createInventory(p, 36, Colors.fix("&a&lINFORMACJE O DROPIE"));
			inv.setItem(0, new Item(Material.NETHERITE_SWORD).addEnchant(Enchantment.DURABILITY, 4).addLore(Colors.fix("&bNaklada efekt mdłości na przeciwnika")).toIs());
			inv.setItem(1, new Item(Material.NETHERITE_PICKAXE).addEnchant(Enchantment.DURABILITY, 4).addLore(Colors.fix("&bKilof 3x3")).toIs());
			inv.setItem(2, civicBoots);
			inv.setItem(3, setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój mocy")).addLore(Colors.fix("&7Przywraca &c10% 🗡")).toIs(), Color.RED, 0));
			inv.setItem(4, setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój szybkości")).addLore(Colors.fix("&7Przywraca &b10% ☄")).toIs(), Color.AQUA, 1));
			inv.setItem(5, setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój wytrzymałości")).addLore(Colors.fix("&7Przywraca &610% 🛡")).toIs(), Color.ORANGE, 2));
			inv.setItem(6, setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&cInstygnia śmierci")).addLore(Colors.fix("&7Przywraca wszystkie statystyki")).toIs(), Color.GREEN, 3));
			inv.setItem(7, setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&eZwój życia")).addLore(Colors.fix("&7Dodaje &c1 serce")).toIs(), Color.BLACK, 4));
			for (int i = 8; i < 8 + 27; i++) {
				ItemStack is = DropEditCommand.getIS(i - 8);
				if (is != null) {
					inv.setItem(i, is);
				}
			}
			p.openInventory(inv);
			return;
		}
		if (!sender.hasPermission("dropsmp.dropcmd")) return;
		if (args.length == 4) {
			if (args[0].equalsIgnoreCase("userset")) {
				User u = User.getUser(args[2]);
				if (!Chat.isInteger(args[3])) {
					sender.sendMessage(Colors.fix("&b/drop &fuserset [var1|var2|var3] [nick] [liczba]"));
					return;
				}
				int i = Integer.parseInt(args[3]);
				if (args[1].equalsIgnoreCase("var1")) {
					u.setVar1(i);
					return;
				}
				if (args[1].equalsIgnoreCase("var2")) {
					u.setVar2(i);
					return;
				}
				if (args[1].equalsIgnoreCase("var3")) {
					u.setVar3(i);
					return;
				}
			}
		}
		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("tombstone")) {
				Tombstone t = Tombstone.getTombstone(args[2]);
				if (t == null) {
					sender.sendMessage(Colors.fix("&cTaki grob nie istnieje!"));
					return;
				}
				if (args[1].equalsIgnoreCase("get")) {
					sender.sendMessage(Colors.fix("&bGrob gracza " + t.getName()));
					sender.sendMessage(Colors.fix("&bSWIAT: " + t.world));
					sender.sendMessage(Colors.fix("&bX: " + t.x));
					sender.sendMessage(Colors.fix("&bY: " + t.y));
					sender.sendMessage(Colors.fix("&bZ: " + t.z));
					long time = t.time - System.currentTimeMillis();
					long sec = (time / 1000) % 60;
					long min = (time / (1000 * 60)) % 60;
					sender.sendMessage(Colors.fix("&bMIN: " + min));
					sender.sendMessage(Colors.fix("&bSEC: " + sec));	
					return;
				}
				if (args[1].equalsIgnoreCase("remove")) {
					Block b = t.getBlock();
					b.setType(Material.AIR);
					b.getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
					User u = User.getUser(t.getName());
					u.setVar1(Config.getValue_INT(Config.STAT1_RESTORE_VAL));
					u.setVar2(Config.getValue_INT(Config.STAT2_RESTORE_VAL));
					u.setVar3(Config.getValue_INT(Config.STAT3_RESTORE_VAL));
					try {
						u.save();
					} catch (IOException e1) {}
					try {
						t.delete();
					} catch (Exception e1) {}
					return;
				}
 			}
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				MainReg.getPlugin(MainReg.class).saveDefaultConfig();
				Config.cache.clear();
				return;
			}
		}
		sender.sendMessage(Colors.fix("&b/drop &fuserset [var1|var2|var3] [nick] [liczba]"));
		sender.sendMessage(Colors.fix("&b/drop &ftombstone [remove|get] [nick]"));
		sender.sendMessage(Colors.fix("&b/drop &freload"));
	}
}
