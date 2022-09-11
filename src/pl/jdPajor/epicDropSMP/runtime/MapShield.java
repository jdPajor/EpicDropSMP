package pl.jdPajor.epicDropSMP.runtime;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class MapShield extends MapRenderer {
	private static BufferedImage dead;
	
	static {
		try {
			dead = ImageIO.read(MapDefault.class.getResourceAsStream("/shield.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(MapView map, MapCanvas canvas, Player p) {
		map.setScale(Scale.FARTHEST);
		map.setCenterX(-dead.getWidth() / 2);
		map.setCenterZ(-dead.getHeight() / 2);
		canvas.drawImage(0, 0, dead);
	}

}
