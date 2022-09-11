package pl.jdPajor.epicDropSMP.include;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {
	
	public static Location LocationFromString(String s) {
        String[] ss = s.split("_");
        Location l = new Location(Bukkit.getWorlds().get(0), 0.0, 0.0, 0.0, 0.0f, 0.0f);
        l.setWorld(Bukkit.getWorld(ss[0]));
        l.setX(Double.parseDouble(ss[1]));
        l.setY(Double.parseDouble(ss[2]));
        l.setZ(Double.parseDouble(ss[3]));
        l.setYaw(Float.parseFloat(ss[4]));
        l.setPitch(Float.parseFloat(ss[5]));
        return l;
    }
    
    public static String LocationToString(Location l) {
        return l.getWorld().getName() + "_" + l.getX() + "_" + l.getY() + "_" + l.getZ() + "_" + l.getYaw() + "_" + l.getPitch();
    }

}
