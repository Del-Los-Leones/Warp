package cf.urgpa.warp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Warpable extends Location {
	private String name;
	public Warpable(String name, World world, double x, double y, double z) {
		super(world, x, y, z);
		this.name = name;
	}

	public String toString() {
		return getX() + ":" + getY() + ":" + getZ() + ":" + getWorld().getName() + ":" + name;
	}

	public void warp(Player p){
		p.sendMessage(ChatColor.AQUA + "[MPP] " + ChatColor.GREEN + name + ChatColor.WHITE + "(으)로 이동하셨습니다.");
		p.teleport(this);
	}

	public static Warpable getFromString(String str) {
		String[] xyz = str.split(":");
		double x = Double.parseDouble(xyz[0]);
		double y = Double.parseDouble(xyz[1]);
		double z = Double.parseDouble(xyz[2]);
		World world = Warp.getInstance().getServer().getWorld(xyz[3]);
		return new Warpable(xyz[4], world, x, y, z);
	}
}
