package pl.jdPajor.epicDropSMP;

import java.io.File;

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
import pl.jdPajor.epicDropSMP.runtime.data.Tombstone;
import pl.jdPajor.epicDropSMP.runtime.data.User;
import pl.jdPajor.epicDropSMP.runtime33.Kilof3X3;

public class MainReg extends JavaPlugin {
	public static ShapedRecipe deadRecipe;
	public static NamespacedKey nkey;
	private static PlaceholderImpl placeholderExtendsion;
	
	@Override 
	public void onEnable() {
		if (!this.getDataFolder().exists()) this.getDataFolder().mkdir(); 
		this.saveDefaultConfig();
		Config.init();
		nkey = new NamespacedKey(this, this.getDescription().getName());
		if (!User.dir.exists()) User.dir.mkdir();
		if (!Tombstone.dir.exists()) Tombstone.dir.mkdir();
		new ActionBarTask().runTaskTimer(this, 45, 45);
		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		for (File f : User.dir.listFiles()) {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			User.users.add(new User(yml.getString("name"), yml.getInt("var1"), yml.getInt("var2"), yml.getInt("var3")));
		}
		for (File f : Tombstone.dir.listFiles()) {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			Tombstone.Tombstones.add(new Tombstone(yml.getString("name"), yml.getInt("x"), yml.getInt("y"), yml.getInt("z"), yml.getLong("time"), yml.getString("world")));
		}
		CmdMan.register(new DropCommand());
		Kilof3X3 kf = new Kilof3X3();
		Bukkit.getPluginManager().registerEvents(kf, this);
		kf.runTaskTimer(this, 5, 5);
		deadRecipe = new ShapedRecipe(nkey, DropCommand.setMapColor(new Item(Material.FILLED_MAP).addEnchant(Enchantment.DURABILITY, 10).setName(Colors.fix("&cInstygnia œmierci")).addLore(Colors.fix("&7Przywraca wybranego gracza do ¿ycia")).toIs(), Color.GREEN, 3));
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
