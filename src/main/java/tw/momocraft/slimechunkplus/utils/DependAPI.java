package tw.momocraft.slimechunkplus.utils;

import org.bukkit.Bukkit;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.handlers.ServerHandler;

public class DependAPI {
    private VaultAPI vaultApi;
    private PlayerPointsAPI playerPointsApi;

    private boolean Vault = false;
    private boolean PlayerPoints = false;
    private boolean PlaceHolderAPI = false;

    public DependAPI() {
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Vault")) {
            this.setVaultStatus(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null);
            if (Vault) {
                setVaultApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.PlayerPoints")) {
            this.setPlayerPointsStatus(Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints") != null);
            if (PlayerPoints) {
                setPlayerPointsApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.PlaceHolderAPI")) {
            this.setPlaceHolderStatus(Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null);
        }

        sendUtilityDepends();
    }

    private void sendUtilityDepends() {
        ServerHandler.sendConsoleMessage("&fHooked [ &e"
                + (VaultEnabled() ? "Vault, " : "")
                + (PlayerPointsEnabled() ? "PlayerPoints, " : "")
                + (PlaceHolderAPIEnabled() ? "PlaceHolderAPI, " : "")
                + "&f]");
    }


    public boolean VaultEnabled() {
        return this.Vault;
    }

    public boolean PlayerPointsEnabled() {
        return this.PlayerPoints;
    }

    public boolean PlaceHolderAPIEnabled() {
        return this.PlaceHolderAPI;
    }


    public void setVaultStatus(boolean bool) {
        this.Vault = bool;
    }

    public void setPlaceHolderStatus(boolean bool) {
        this.PlaceHolderAPI = bool;
    }

    private void setPlayerPointsStatus(boolean bool) {
        this.PlayerPoints = bool;
    }


    public VaultAPI getVaultApi() {
        return this.vaultApi;
    }

    private void setVaultApi() {
        vaultApi = new VaultAPI();
    }

    public PlayerPointsAPI getPlayerPointsApi() {
        return this.playerPointsApi;
    }

    private void setPlayerPointsApi() {
        playerPointsApi = new PlayerPointsAPI();
    }
}
