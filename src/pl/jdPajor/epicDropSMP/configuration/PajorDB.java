package pl.jdPajor.epicDropSMP.configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import pl.jdPajor.epicDropSMP.MainReg;

public class PajorDB {
	private static Map<String, String> pajordb = new HashMap<String, String>();
	private static int delta = 0;
	
	private static File FILE = new File(MainReg.getPlugin(MainReg.class).getDataFolder(), "db.jdp");
	
	public static void saveAll() {
		List<String> ls = new ArrayList<String>();
		for (String h : pajordb.keySet()) {
			ls.add(h);
			ls.add(pajordb.get(h));
		}
		try {
			FileWriter fw = new FileWriter(FILE);
			for (String s : ls) {
				fw.write(s);
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void set(String s, String w) {
		delta++;
		if (pajordb.containsKey(s)) {
			pajordb.replace(s, w);
		} else {
			pajordb.put(s, w);
		}
		if (delta > 10) {
			delta = 0;
			new BukkitRunnable() {
				@Override
				public void run() {
					saveAll();
				}
			}.runTaskAsynchronously(MainReg.getPlugin(MainReg.class));
		}
	}
	
	public static String get(String s) {
		if (pajordb.containsKey(s)) {
			return pajordb.get(s);
		} 
		return null;
	}
	
	public static void init() throws Exception {
		pajordb.clear();
		if (!FILE.exists()) {
			FILE.createNewFile();
		} else {
			List<String> ff = Files.readAllLines(FILE.toPath());
			for (int i = 0; i < ff.size(); i += 2) {
				pajordb.put(ff.get(i), ff.get(i + 1));
			}
		}
	}

}
