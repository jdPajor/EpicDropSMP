package pl.jdPajor.epicDropSMP;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import pl.jdPajor.epicDropSMP.configuration.Config;
import pl.jdPajor.epicDropSMP.external.PlaceholderImpl;
import pl.jdPajor.epicDropSMP.include.CmdMan;
import pl.jdPajor.epicDropSMP.include.Colors;
import pl.jdPajor.epicDropSMP.include.Item;
import pl.jdPajor.epicDropSMP.runtime.ActionBarTask;
import pl.jdPajor.epicDropSMP.runtime.Listeners;
import pl.jdPajor.epicDropSMP.runtime.cmd.DropCommand;
import pl.jdPajor.epicDropSMP.runtime.cmd.DropEditCommand;
import pl.jdPajor.epicDropSMP.runtime.data.Tombstone;
import pl.jdPajor.epicDropSMP.runtime.data.User;
import pl.jdPajor.epicDropSMP.runtime33.Kilof3X3_V1_16_R3;
import pl.jdPajor.epicDropSMP.runtime33.Kilof3X3_V1_17_R1;
import pl.jdPajor.epicDropSMP.runtime33.Kilof3X3_V1_18_R2;
import pl.jdPajor.epicDropSMP.runtime33.Kilof3X3_V1_19_R1;

public class MainReg extends JavaPlugin {
	public static ShapedRecipe deadRecipe;
	public static NamespacedKey nkey;
	private static PlaceholderImpl placeholderExtendsion;
	
	@Override 
	public void onEnable() {
		try {
            URLConnection connection = new URL("http://167.235.8.41/dropsmp.txt").openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.contains("6.9.0")) {
                	getLogger().log(Level.SEVERE, "\n\n\n\n\n\n\n\n\nPRZESTARZALA WERSJA PLUGINU!!!\nhttps://www.spigotmc.org/resources/epic-drop-smp-⚔%EF%B8%8F-custom-items-✅-mystery-box-stats-⚡.105188/\n\n\n\n\n\n\n\n\n\n");
                	return;
                }
            }
        } catch (IOException e) { }
		if (!this.getDataFolder().exists()) this.getDataFolder().mkdir();
		Config.init();
		String v = Bukkit.getServer().getClass().getPackage().getName();
        String vnms = v.substring(v.lastIndexOf('.') + 1);
        if (Config.getValue_BOOL(Config.PICKAXE33_ENABLED)) {
	        switch (vnms) {
	        case "v1_19_R1":
		        Kilof3X3_V1_19_R1 kf = new Kilof3X3_V1_19_R1(this);
				Bukkit.getPluginManager().registerEvents(kf, this);
				kf.runTaskTimer(this, 5, 5);
				break;
	        case "v1_16_R3":
	        	Kilof3X3_V1_16_R3 kf2 = new Kilof3X3_V1_16_R3(this);
				Bukkit.getPluginManager().registerEvents(kf2, this);
				kf2.runTaskTimer(this, 5, 5);
				break;
	        case "v1_18_R2":
	        	Kilof3X3_V1_18_R2 kf3 = new Kilof3X3_V1_18_R2(this);
				Bukkit.getPluginManager().registerEvents(kf3, this);
				kf3.runTaskTimer(this, 5, 5);
				break;
	        case "v1_17_R1":
	        	Kilof3X3_V1_17_R1 kf4 = new Kilof3X3_V1_17_R1(this);
				Bukkit.getPluginManager().registerEvents(kf4, this);
				kf4.runTaskTimer(this, 5, 5);
				break;
	        default:
	        	getLogger().log(Level.SEVERE, "\n\n\n\n\n\n\n\n\nTA WERSJA SERWERA (" + vnms + ") NIE JEST WSPIERANA!!!\n\n\n\n\n\n\n\n\n\n");
	        	Bukkit.getPluginManager().disablePlugin(this);
	        	return;
	        }
        }
        nkey = new NamespacedKey(this, this.getDescription().getName());
		if (!User.dir.exists()) User.dir.mkdir();
		if (!Tombstone.dir.exists()) Tombstone.dir.mkdir();
		new ActionBarTask().runTaskTimer(this, 45, 45);
		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		DropEditCommand dec = new DropEditCommand();
		CmdMan.register(dec);
		Bukkit.getPluginManager().registerEvents(dec, this);
		for (File f : User.dir.listFiles()) {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			User.users.add(new User(yml.getString("name"), yml.getInt("var1"), yml.getInt("var2"), yml.getInt("var3")));
		}
		for (File f : Tombstone.dir.listFiles()) {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			Tombstone.Tombstones.add(new Tombstone(yml.getString("name"), yml.getInt("x"), yml.getInt("y"), yml.getInt("z"), yml.getLong("time"), yml.getString("world")));
		}
		CmdMan.register(new DropCommand());
		deadRecipe = new ShapedRecipe(nkey, DropCommand.setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&cInstygnia śmierci")).addLore(Colors.fix("&7Przywraca wybranego gracza do życia")).toIs(), Color.GREEN, 3));
		deadRecipe.shape(" A ", "ABA", " A ");
		deadRecipe.setIngredient('A', Material.GOLD_BLOCK);
		deadRecipe.setIngredient('B', Material.GHAST_TEAR);
		Bukkit.addRecipe(deadRecipe);
		//
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			getLogger().warning("Znaleziono PlaceholderAPI - rejestruje placeholdery!");
			placeholderExtendsion = new PlaceholderImpl();
			placeholderExtendsion.register();
		}
	}
	
	@Override
	public void onDisable() {
		if (placeholderExtendsion != null) {
			placeholderExtendsion.unregister();
		}
	}
	
}
