package tw.momocraft.slimechunkplus.handlers;

import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateHandler {

    private final String plugin = "slimechunkplus";
    private final int PROJECTID = 85995;

    private final String versionExact = tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().getDescription().getVersion();
    private final String localeVersion = this.versionExact.split("-")[0];
    private String latestVersion;

    private final boolean updatesAllowed = ConfigHandler.getConfig("config.yml").getBoolean("Check-Updates");

    public UpdateHandler() {
        this.checkUpdates(tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().getServer().getConsoleSender());
    }

    public void checkUpdates(final CommandSender sender) {
        if (this.updateNeeded(sender) && this.updatesAllowed) {
            ServerHandler.sendMessage(sender, "&aNew version is available: " + "&ev" + this.latestVersion);
            ServerHandler.sendMessage(sender, "&ehttps://www.spigotmc.org/resources/" + plugin + "." + PROJECTID + "/history");
        } else if (this.updatesAllowed) {
            ServerHandler.sendMessage(sender, "&fYou are up to date!");
        }
    }

    private boolean updateNeeded(final CommandSender sender) {
        if (this.updatesAllowed) {
            ServerHandler.sendMessage(sender, "&fChecking for updates...");
            try {
                String HOST = "https://api.spigotmc.org/legacy/update.php?resource=" + this.PROJECTID;
                URLConnection connection = new URL(HOST + "?_=" + System.currentTimeMillis()).openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String version = reader.readLine();
                reader.close();
                if (version.length() <= 7) {
                    this.latestVersion = version.replaceAll("[a-z]", "").replace("-SNAPSHOT", "").replace("-BETA", "").replace("-ALPHA", "").replace("-RELEASE", "");
                    String[] latestSplit = this.latestVersion.split("\\.");
                    String[] localeSplit = this.localeVersion.split("\\.");
                    if ((Integer.parseInt(latestSplit[0]) > Integer.parseInt(localeSplit[0]) ||
                            Integer.parseInt(latestSplit[1]) > Integer.parseInt(localeSplit[1]) || Integer.parseInt(latestSplit[2]) > Integer.parseInt(localeSplit[2]))) {
                        return true;
                    }
                }
            } catch (Exception e) {
                ServerHandler.sendMessage(sender, "&cFailed to check for updates, connection could not be made.");
                return false;
            }
        }
        return false;
    }
}