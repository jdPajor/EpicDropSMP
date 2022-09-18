package pl.jdPajor.epicDropSMP.runtime;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import pl.jdPajor.epicDropSMP.configuration.Config;
import pl.jdPajor.epicDropSMP.include.TextUtil;
import pl.jdPajor.epicDropSMP.runtime.data.Tombstone;
import pl.jdPajor.epicDropSMP.runtime.data.User;

public class ActionBarTask extends BukkitRunnable {
	
	@Override
	public void run() {
		for (Player o : Bukkit.getOnlinePlayers()) {
			User u = User.getUser(o.getName());
			float f = u.getVar2() / 500.f;
			o.setWalkSpeed(f > 0.125f ? f : 0.125f);
			o.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextUtil("&c" + 
					u.getVar1() + "%🗡 &b" + 
					u.getVar2() + "%☄ &6" + 
					u.getVar3() + "%🛡"
			).toTextComponent());
		}
		for (int i = 0; i < Tombstone.Tombstones.size(); i++) {
			Tombstone t = Tombstone.Tombstones.get(i);
			if (t.time < System.currentTimeMillis()) {
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
			}
		}
	}

}
