package pl.jdPajor.epicDropSMP.include;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public abstract class APermCmd extends ACmd {
	private String permission;
	
	public String SrvErrPerm = "&aSerwer# &fbrak permisji! (&a\"{PERM}&f)";
	
	public APermCmd(String name, String permission) {
		super(name);
		this.permission = permission;
	}
	
	public abstract void onExecuteWithPerm(CommandSender sender, String[] args);

	public void onExecuteWithoutPerm(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SrvErrPerm.replaceAll("{PERM}", this.permission)));	
	}
	
	@Override
	public void onExecute(CommandSender sender, String[] args) {
		if (sender.hasPermission(this.permission)) {
			this.onExecuteWithPerm(sender, args);
		} else {
			this.onExecuteWithoutPerm(sender, args);
		}
	}

}
