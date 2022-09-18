package pl.jdPajor.epicDropSMP.runtime.cmd;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import pl.jdPajor.epicDropSMP.MainReg;
import pl.jdPajor.epicDropSMP.include.APermCmd;
import pl.jdPajor.epicDropSMP.include.Colors;
import pl.jdPajor.epicDropSMP.include.Item;

public class DropEditCommand extends APermCmd implements Listener {
	private static File FILE = new File(MainReg.getPlugin(MainReg.class).getDataFolder(), "wad.yml");

	public DropEditCommand() {
		super("dropedit", "cmd.dropedit");
	}
	
	public static ItemStack getIS(int i) {
		if (!FILE.exists()) {
			try {
				FILE.createNewFile();
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
				yml.set("customitemid0", new Item(Material.ELYTRA).toIs());
				yml.set("customitemid1", new Item(Material.SHULKER_SHELL).setAmount(3).toIs());
				yml.set("customitemid2", DropCommand.mendingIS);
				yml.set("customitemid3", new Item(Material.ENCHANTED_GOLDEN_APPLE).setAmount(2).toIs());
				yml.save(FILE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
		return yml.getItemStack("customitemid" + i);
	}
	
	public static void saveIS(int i, ItemStack is) {
		if (!FILE.exists()) {
			try {
				FILE.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
		yml.set("customitemid" + i, is);
		try {
			yml.save(FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void hlose(InventoryCloseEvent e) {
		if (e.getView().getTitle().equalsIgnoreCase(Colors.fix("&b&lDROPEDIT v6.9"))) {
			int h = -1;
			for (int i = 0; i < 27; i++) { 
				ItemStack is = e.getInventory().getItem(i);
				if (is != null) {
					h++;
					saveIS(h, is);
				}
			}
		}
	}
	
	@Override
	public void onExecuteWithPerm(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Inventory inv = Bukkit.createInventory(p, 27, Colors.fix("&b&lDROPEDIT v6.9"));
		for (int i = 0; i < 27; i++) { 
			ItemStack is = getIS(i);
			if (is != null) {
				inv.setItem(i, is);
			} else break;
		}
		p.openInventory(inv);
	}
	
	
	
}
