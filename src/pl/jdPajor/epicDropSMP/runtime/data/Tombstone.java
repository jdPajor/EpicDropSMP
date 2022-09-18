package pl.jdPajor.epicDropSMP.runtime.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

import pl.jdPajor.epicDropSMP.MainReg;

public class Tombstone {
	public static final File dir = new File(MainReg.getPlugin(MainReg.class).getDataFolder(), "tombstones");
	public static List<Tombstone> Tombstones = new ArrayList<Tombstone>();
	
	public static Tombstone getTombstone(int x, int y, int z) {
		for (Tombstone u : Tombstones) {
			if (u.x == x && u.y == y && u.z == z) {
				return u;
			}
		}
		return null;
	}

	public static Tombstone getTombstone(String name) {
		for (Tombstone u : Tombstones) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}
	
	private String name;
	public int x;
	public int y;
	public int z;
	public String world;
	public long time;
	
	public Block getBlock() {
		return new Location(Bukkit.getWorld(this.world), x, y, z).getBlock();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Tombstone(String name, int v1, int v2, int v3, long t, String w) {
		this.name = name;
		this.x = v1;
		this.y = v2;
		this.z = v3;
		this.time = t;
		this.world = w;
	}
	
	public void delete() {
		File fil = new File(dir, this.getName());
		if (fil.exists()) fil.delete();
		Tombstones.remove(this);
	}
	
	public void save() throws IOException {
		File fil = new File(dir, this.getName());
		if (!fil.exists()) fil.createNewFile();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(fil);
		yml.set("name", this.name);
		yml.set("x", this.x);
		yml.set("y", this.y);
		yml.set("z", this.z);
		yml.set("time", this.time);
		yml.set("world", this.world);
		yml.save(fil);
	}
}
