package pl.jdPajor.epicDropSMP.runtime.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import pl.jdPajor.epicDropSMP.MainReg;
import pl.jdPajor.epicDropSMP.configuration.Config;

public class User {
	public static final File dir = new File(MainReg.getPlugin(MainReg.class).getDataFolder(), "users");
	public static List<User> users = new ArrayList<User>();
	
	public static User getUser(String name) {
		for (User u : users) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return new User(name);
	}

	
	private String name;
	private int var1;
	private int var2;
	private int var3;
	
	public int getVar1() {
		return var1;
	}
	
	public int getVar2() {
		return var2;
	}
	
	public int getVar3() {
		return var3;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setVar1(int i) {
		this.var1 = i;
		if (this.var1 > Config.getValue_INT(Config.STAT1_MAX_VAL)) this.var1 = Config.getValue_INT(Config.STAT1_MAX_VAL);
		try {
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void setVar2(int i) {
		this.var2 = i;
		if (this.var2 > Config.getValue_INT(Config.STAT2_MAX_VAL)) this.var2 = Config.getValue_INT(Config.STAT2_MAX_VAL);
		try {
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void setVar3(int i) {
		this.var3 = i;
		if (this.var3 > Config.getValue_INT(Config.STAT3_MAX_VAL)) this.var3 = Config.getValue_INT(Config.STAT3_MAX_VAL);
		try {
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public User(String name) {
		this.name = name;
		this.var1 = Config.getValue_INT(Config.STAT1_START_VAL);
		this.var2 = Config.getValue_INT(Config.STAT2_START_VAL);
		this.var3 = Config.getValue_INT(Config.STAT3_START_VAL);
		try {
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public User(String name, int v1, int v2, int v3) {
		this.name = name;
		this.var1 = v1;
		this.var2 = v2;
		this.var3 = v3;
	}
	
	public void save() throws IOException {
		File fil = new File(dir, this.getName());
		if (!fil.exists()) fil.createNewFile();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(fil);
		yml.set("name", this.getName());
		yml.set("var1", this.getVar1());
		yml.set("var2", this.getVar2());
		yml.set("var3", this.getVar3());
		yml.save(fil);
	}
}
