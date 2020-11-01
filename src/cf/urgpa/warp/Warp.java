package cf.urgpa.warp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Warp extends JavaPlugin implements Listener {
	private static Warp instance;
	private Database db;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		db = new Database(this);
	}

	public static Warp getInstance() {
		return instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equals("워프")) {
			if (!(sender instanceof Player))
				return true;
			if (args.length < 1) {
				return false;
			}
			switch (args[0]) {
				case "생성":
					if (args.length < 2) {
						sender.sendMessage("사용법: /워프 생성 <워프명>");
						return true;
					}
					String name = args[1];
					if (db.warpExists(name)) {
						sender.sendMessage("이미 존재하는 워프명입니다. 다른 이름으로 다시 시도해주세요.");
						return true;
					}
					db.createWarp(name, (Player) sender);
					sender.sendMessage("워프가 생성되었습니다.");
					break;
				case "제거":
					if (args.length < 2) {
						sender.sendMessage("사용법: /워프 제거 <워프명>");
						return true;
					}
					name = args[1];
					if (!db.warpExists(name)) {
						sender.sendMessage("존재하지 않는 워프명입니다. 워프명을 확인하신 후 다시 시도해주세요.");
						db.deleteWarp(name);
						return true;
					}
					db.deleteWarp(name);
					sender.sendMessage("워프가 제거되었습니다.");
					break;
				case "목록":
					String warps = "사용 가능한 워프 목록: " + ChatColor.YELLOW.toString();
					for(String warpName : db.getWarps().keySet()){
						warps += warpName + ", ";
					}
					warps = warps.substring(warps.length() - 2, warps.length() - 1);
					sender.sendMessage(warps);
					break;
				default:
					name = args[0];
					if (!db.warpExists(name)) {
						sender.sendMessage("존재하지 않는 워프명입니다. 워프명을 확인하신 후 다시 시도해주세요.");
						return true;
					}
					Warpable warp = db.getWarpByName(name);
					warp.warp((Player) sender);
					break;
			}
		}
		return true;
	}

	@Override
	public void onDisable() {
		db.saveConf();
	}
}
