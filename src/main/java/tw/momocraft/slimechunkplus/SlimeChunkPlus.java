package tw.momocraft.slimechunkplus;

import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.handlers.RegisterHandler;
import tw.momocraft.slimechunkplus.handlers.ServerHandler;

public class SlimeChunkPlus extends JavaPlugin {
    private static tw.momocraft.slimechunkplus.SlimeChunkPlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        RegisterHandler.registerEvents();
        ServerHandler.sendConsoleMessage("&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        ServerHandler.sendConsoleMessage("&fhas been Disabled.");
    }

    public static tw.momocraft.slimechunkplus.SlimeChunkPlus getInstance() {
        return instance;
    }
}