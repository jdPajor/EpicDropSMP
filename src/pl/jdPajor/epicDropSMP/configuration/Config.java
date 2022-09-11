package pl.jdPajor.epicDropSMP.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import pl.jdPajor.epicDropSMP.MainReg;
import pl.jdPajor.epicDropSMP.include.Colors;
import pl.jdPajor.epicDropSMP.include.Item;

public enum Config {
	SWORD_EFFECT_TIME,
	SWORD_EFFECT_AMF,
	TOMBSTONE_BANTIME,
	TOMBSTONE_ENABLED,
	PICKAXE33_ENABLED,
	STAT1_MAX_VAL,
	STAT2_MAX_VAL,
	STAT3_MAX_VAL,
	STAT1_BAN,
	STAT2_BAN,
	STAT3_BAN,
	STAT1_START_VAL,
	STAT2_START_VAL,
	STAT3_START_VAL,
	STAT1_RESTORE_VAL,
	STAT2_RESTORE_VAL,
	STAT3_RESTORE_VAL,
	ITEM_MYSTERY,
	ITEM_MYSTERY_CHANCE,
	;
	
	public static File FILE = new File(MainReg.getPlugin(MainReg.class).getDataFolder(), "config.yml");
	
	public static Map<Config, Object> cache = new HashMap<Config, Object>();
	
	public static int getValue_INT(Config c) {
		if (cache.containsKey(c)) {
			return (int) cache.get(c);
		} else {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
			cache.put(c, yml.getInt(c.name()));
			return (int) cache.get(c);
		}
	}
	
	public static boolean getValue_BOOL(Config c) {
		if (cache.containsKey(c)) {
			return (boolean) cache.get(c);
		} else {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
			cache.put(c, yml.getBoolean(c.name()));
			return (boolean) cache.get(c);
		}
	}
	
	public static double getValue_DOUBLE(Config c) {
		if (cache.containsKey(c)) {
			return (double) cache.get(c);
		} else {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
			cache.put(c, yml.getDouble(c.name()));
			return (double) cache.get(c);
		}
	}
	
	public static String getValue_STRING(Config c) {
		if (cache.containsKey(c)) {
			return (String) cache.get(c);
		} else {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
			cache.put(c, yml.getString(c.name()));
			return (String) cache.get(c);
		}
	}
	
	public static ItemStack getValue_BUKKIT_ITEMSTACK(Config c) {
		if (cache.containsKey(c)) {
			return (ItemStack) cache.get(c);
		} else {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
			cache.put(c, yml.getItemStack(c.name()));
			return (ItemStack) cache.get(c);
		}
	}
	
	public static void init() {
		cache.clear();
		if (!MainReg.getPlugin(MainReg.class).getDataFolder().exists()) {
			MainReg.getPlugin(MainReg.class).getDataFolder().mkdir();
		}
		if (!FILE.exists()) {
			try {
				FILE.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(FILE);
			yml.set(TOMBSTONE_BANTIME.name(), 60.0);
			yml.set(SWORD_EFFECT_AMF.name(), 1);
			yml.set(SWORD_EFFECT_TIME.name(), 69);
			yml.set(PICKAXE33_ENABLED.name(), true);
			yml.set(TOMBSTONE_ENABLED.name(), true);
			yml.set(STAT1_MAX_VAL.name(), 200);
			yml.set(STAT2_MAX_VAL.name(), 200);
			yml.set(STAT3_MAX_VAL.name(), 200);
			yml.set(STAT1_BAN.name(), true);
			yml.set(STAT2_BAN.name(), true);
			yml.set(STAT3_BAN.name(), true);
			yml.set(STAT1_START_VAL.name(), 200);
			yml.set(STAT2_START_VAL.name(), 200);
			yml.set(STAT3_START_VAL.name(), 200);
			yml.set(STAT1_RESTORE_VAL.name(), 100);
			yml.set(STAT2_RESTORE_VAL.name(), 100);
			yml.set(STAT3_RESTORE_VAL.name(), 100);
			yml.set(ITEM_MYSTERY_CHANCE.name(), 60.0);
			yml.set(ITEM_MYSTERY.name(), new Item(Material.BOOK).setName(Colors.fix("&a&lMYSTERY BOOK")).addLore(Colors.fix("&aJAKIES LORE #1")).addLore(Colors.fix("&bJAKIES LORE #2")).toIs());
			try {
				yml.save(FILE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
