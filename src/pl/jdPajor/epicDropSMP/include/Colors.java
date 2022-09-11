package pl.jdPajor.epicDropSMP.include;

import net.md_5.bungee.api.ChatColor;

public class Colors {
	public static String fix(String s) {
		return addColors(s.replaceAll("&p", "&aSerwer# "));
	}

	private static String addColors(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
