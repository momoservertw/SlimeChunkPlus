package tw.momocraft.slimechunkplus;

import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.handlers.RegisterHandler;

public class SlimeChunkPlus extends JavaPlugin {
    private static SlimeChunkPlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        RegisterHandler.registerEvents();
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(),"&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(),"&fhas been Disabled.");
    }

    public static SlimeChunkPlus getInstance() {
        return instance;
    }
}