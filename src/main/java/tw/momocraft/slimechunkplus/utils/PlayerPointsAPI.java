package tw.momocraft.slimechunkplus.utils;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class PlayerPointsAPI {

    private PlayerPoints pp;

    public PlayerPointsAPI() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
        if (plugin != null) {
            pp = ((PlayerPoints) plugin);
        }
    }

    public double setPoints(OfflinePlayer offlinePlayer, double points) {
        pp.getAPI().set(offlinePlayer.getUniqueId(), (int) points);
        return points;
    }

    public double takePoints(OfflinePlayer offlinePlayer, double points) {
        pp.getAPI().take(offlinePlayer.getUniqueId(), (int) points);
        return getBalance(offlinePlayer);
    }

    public double givePoints(OfflinePlayer offlinePlayer, double points) {
        pp.getAPI().give(offlinePlayer.getUniqueId(), (int) points);
        return getBalance(offlinePlayer);
    }

    public boolean usesDoubleValues() {
        return false;
    }

    public double getBalance(OfflinePlayer offlinePlayer) {
        return pp.getAPI().look(offlinePlayer.getUniqueId());
    }
}
