package tw.momocraft.slimechunkplus.handlers;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.Commands;
import tw.momocraft.slimechunkplus.SlimeChunkPlus;
import tw.momocraft.slimechunkplus.TabComplete;

public class DependHandler {

    public void setup(boolean reload) {
        if (!reload) {
            registerEvents();
            checkUpdate();
        }
    }

    public void checkUpdate() {
        if (!ConfigHandler.isCheckUpdates())
            return;
        CorePlusAPI.getUpdate().check(ConfigHandler.getPluginName(),
                ConfigHandler.getPluginPrefix(), Bukkit.getConsoleSender(),
                SlimeChunkPlus.getInstance().getDescription().getName(),
                SlimeChunkPlus.getInstance().getDescription().getVersion(), true);
    }

    private void registerEvents() {
        SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setExecutor(new Commands());
        SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setTabCompleter(new TabComplete());
    }
}
