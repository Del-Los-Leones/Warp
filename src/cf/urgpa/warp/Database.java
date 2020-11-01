package cf.urgpa.warp;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Database {
	private Warp plugin;
	private YamlConfiguration conf;

	private final String WARPS = "warps";

	private HashMap<String, Warpable> warps = new HashMap<>();

	public Database(Warp plugin) {
		this.plugin = plugin;
		this.conf = (YamlConfiguration) plugin.getConfig();
		loadConf();
	}
	public void createWarp(String name, Player p){
		Location loc = p.getLocation();
		Warpable warp = new Warpable(name, loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
		warps.put(name, warp);
	}
	public HashMap<String, Warpable> getWarps(){
		return warps;
	}

	public void deleteWarp(String name){
		warps.remove(name);
	}

	public boolean warpExists(String name){
		return warps.containsKey(name);
	}

	public Warpable getWarpByName(String name){
		return warps.get(name);
	}

	public void saveConf() {
		conf.set(WARPS, null);
		HashMap<String, String> map = new HashMap<>();
		warps.forEach((s, w) -> map.put(s, w.toString()));
		conf.createSection(WARPS, map);
		try {
			conf.save(plugin.getDataFolder() + "/config.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadConf() {
		if (conf.isSet(WARPS)) {
			Objects.requireNonNull(conf.getConfigurationSection(WARPS)).getValues(false).forEach((s, o) -> {
				warps.put(s, Warpable.getFromString((String) o));
			});
		}
	}
}
