package pl.jdPajor.epicDropSMP.external;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import pl.jdPajor.epicDropSMP.runtime.data.User;

public class PlaceholderImpl extends PlaceholderExpansion {
	
	@Override
	public String getAuthor() {
		return "jdPajor";
	}

	@Override
	public String getIdentifier() {
		return "dropsmp";
	}

	@Override
	public String getVersion() {
		return "6.9.0";
	}

	@Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("stat1")) {
        	return "" + User.getUser(player.getName()).getVar1();
        }
        if (params.equalsIgnoreCase("stat2")) {
        	return "" + User.getUser(player.getName()).getVar2();
        }
        if (params.equalsIgnoreCase("stat3")) {
        	return "" + User.getUser(player.getName()).getVar3();
        }
        return null;
    }
}
