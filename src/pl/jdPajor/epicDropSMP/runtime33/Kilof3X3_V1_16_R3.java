package pl.jdPajor.epicDropSMP.runtime33;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityShulker;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Kilof3X3_V1_16_R3 extends BukkitRunnable implements Listener {
	private Plugin registry;
	
	public Kilof3X3_V1_16_R3(Plugin p) {
		this.registry = p;
	}
	
	public static Map<String, Integer> users = new HashMap<String, Integer>();
	
	public static int getType(String u) {
		for (String s : users.keySet()) {
			if (s.contains(u)) {
				return users.get(s);
			}
		}
		return -1;
	}
	
	public static void setType(String s, int i) {
		if (users.containsKey(s)) {
			users.replace(s, i);
		} else {
			users.put(s, i);
		}
	}
	
	@EventHandler
	public void brk(BlockBreakEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().hasItemMeta() && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.NETHERITE_PICKAXE && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(Enchantment.DURABILITY) == 4) {
			if (getType(e.getPlayer().getName()) == 2) {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0) continue;
						Block b = e.getBlock().getLocation().add(i, 0, j).getBlock();
						if (b.getType() == Material.BEDROCK) {
							continue;
						}
						b.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
					}
				}
			}
			if (getType(e.getPlayer().getName()) == 0) {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0) continue;
						Block b = e.getBlock().getLocation().add(i, j, 0).getBlock();
						if (b.getType() == Material.BEDROCK) {
							continue;
						}
						b.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
					}
				}
			}
			if (getType(e.getPlayer().getName()) == 1) {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0) continue;
						Block b = e.getBlock().getLocation().add(0, i, j).getBlock();
						if (b.getType() == Material.BEDROCK) {
							continue;
						}
						b.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
					}
				}
			}
		}
	}
	
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			String s = p.getName();
			if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getType() == Material.NETHERITE_PICKAXE && p.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(Enchantment.DURABILITY) == 4) {
				RayTraceResult rr = p.rayTraceBlocks(5);
				if (rr != null && rr.getHitBlock() != null) {
					if (rr.getHitBlockFace() == BlockFace.UP || rr.getHitBlockFace() == BlockFace.DOWN) {
						setType(s, 2);
					} else
					if (rr.getHitBlockFace() == BlockFace.WEST || rr.getHitBlockFace() == BlockFace.EAST) {
						setType(s, 1);
					} else {
						setType(s, 0);
					}
					WorldServer world = ((CraftWorld) (p.getWorld())).getHandle();
					PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
					if (getType(s) == 2) {
						for (int i = -1; i < 2; i++) {
							for (int j = -1; j < 2; j++) {
								if (i == 0 && j == 0) continue;
								EntityShulker shulker = new EntityShulker(EntityTypes.SHULKER, world);
								shulker.glowing = true;
								shulker.setInvisible(true);
								shulker.teleportTo(world, new BlockPosition(rr.getHitBlock().getX() + i, rr.getHitBlock().getY(), rr.getHitBlock().getZ() + j));
								connection.sendPacket(new PacketPlayOutSpawnEntity(shulker));
								connection.sendPacket(new PacketPlayOutEntityMetadata(shulker.getBukkitEntity().getEntityId(), shulker.getDataWatcher(), false));
								new BukkitRunnable() {
									@Override
									public void run() {
										if (connection != null) {
											connection.sendPacket(new PacketPlayOutEntityDestroy(shulker.getBukkitEntity().getEntityId()));
										}
									}
								}.runTaskLater(registry, 5);
							}
						}
					}
					if (getType(s) == 0) {
						for (int i = -1; i < 2; i++) {
							for (int j = -1; j < 2; j++) {
								if (i == 0 && j == 0) continue;
								EntityShulker shulker = new EntityShulker(EntityTypes.SHULKER, world);
								shulker.glowing = true;
								shulker.setInvisible(true);
								shulker.teleportTo(world, new BlockPosition(rr.getHitBlock().getX() + i, rr.getHitBlock().getY() + j, rr.getHitBlock().getZ()));
								connection.sendPacket(new PacketPlayOutSpawnEntity(shulker));
								connection.sendPacket(new PacketPlayOutEntityMetadata(shulker.getBukkitEntity().getEntityId(), shulker.getDataWatcher(), false));
								new BukkitRunnable() {
									@Override
									public void run() {
										if (connection != null) {
											connection.sendPacket(new PacketPlayOutEntityDestroy(shulker.getBukkitEntity().getEntityId()));
										}
									}
								}.runTaskLater(registry, 5);
							}
						}
					}
					if (getType(s) == 1) {
						for (int i = -1; i < 2; i++) {
							for (int j = -1; j < 2; j++) {
								if (i == 0 && j == 0) continue;
								EntityShulker shulker = new EntityShulker(EntityTypes.SHULKER, world);
								shulker.glowing = true;
								shulker.setInvisible(true);
								shulker.teleportTo(world, new BlockPosition(rr.getHitBlock().getX(), rr.getHitBlock().getY() + i, rr.getHitBlock().getZ() + j));
								connection.sendPacket(new PacketPlayOutSpawnEntity(shulker));
								connection.sendPacket(new PacketPlayOutEntityMetadata(shulker.getBukkitEntity().getEntityId(), shulker.getDataWatcher(), false));
								new BukkitRunnable() {
									@Override
									public void run() {
										if (connection != null) {
											connection.sendPacket(new PacketPlayOutEntityDestroy(shulker.getBukkitEntity().getEntityId()));
										}
									}
								}.runTaskLater(registry, 5);
							}
						}
					}
				}
			}
		}
	}

}
