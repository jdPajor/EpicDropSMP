package pl.jdPajor.epicDropSMP.include;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class ACmd extends Command {
	private  String name;
    
    public ACmd(String name) {
        super(name);
        this.name = name;
    }
    
    public boolean execute(CommandSender sender, String label, String[] args) {
        this.onExecute(sender, args);
        return true;
    }
    
    public abstract void onExecute(CommandSender sender, String[] args);
    
    public String getName() {
        return this.name;
    }
}
