package tw.momocraft.slimechunkplus.utils;

import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigPath {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         General Settings                        //
    //  ============================================== //
    private Map<String, String> customCmdProp = new HashMap<>();;
    private boolean logDefaultNew;
    private boolean logDefaultZip;
    private boolean logCustomNew;
    private boolean logCustomZip;
    private String logCustomPath;
    private String logCustomName;

    //  ============================================== //
    //         CleanSlot Settings                      //
    //  ============================================== //
    private boolean slimeChunk;
    private boolean slimeChunkSucMsg;
    private List<String> slimeChunkSucCmds;
    private boolean slimeChunkFaiMsg;
    private List<String> slimeChunkFaiCmds;
    private boolean slimeChunkNearInfo;
    private int slimeChunkNearInfoRange;

    //  ============================================== //
    //         Setup all configuration.                //
    //  ============================================== //
    private void setUp() {
        setGeneral();
        setupCleanSlot();
    }

    //  ============================================== //
    //         Setup General.                          //
    //  ============================================== //
    private void setGeneral() {
        ConfigurationSection cmdConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Custom-Commands");
        if (cmdConfig != null) {
            for (String group : cmdConfig.getKeys(false)) {
                customCmdProp.put(group, ConfigHandler.getConfig("config.yml").getString("General.Custom-Commands." + group));
            }
        }
    }

    //  ============================================== //
    //         Slime Chunk Settings                    //
    //  ============================================== //
    private void setupCleanSlot() {
        slimeChunk = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Enable");
        slimeChunkSucMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Message");
        slimeChunkSucCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Succeed.Commands");
        slimeChunkFaiMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Failed.Message");
        slimeChunkFaiCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Failed.Commands");
        slimeChunkNearInfo = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Nearby-Information.Enable");
        slimeChunkNearInfoRange = ConfigHandler.getConfig("config.yml").getInt("Slime-Chunk.Succeed.Nearby-Information.Range");
    }

    //  ============================================== //
    //         General Settings                        //
    //  ============================================== //
    public Map<String, String> getCustomCmdProp() {
        return customCmdProp;
    }

    public boolean isLogDefaultNew() {
        return logDefaultNew;
    }

    public boolean isLogDefaultZip() {
        return logDefaultZip;
    }

    public boolean isLogCustomNew() {
        return logCustomNew;
    }

    public boolean isLogCustomZip() {
        return logCustomZip;
    }

    public String getLogCustomName() {
        return logCustomName;
    }

    public String getLogCustomPath() {
        return logCustomPath;
    }

    //  ============================================== //
    //         Slime Chunk Settings                    //
    //  ============================================== //

    public boolean isSlimeChunk() {
        return slimeChunk;
    }

    public boolean isSlimeChunkFaiMsg() {
        return slimeChunkFaiMsg;
    }

    public boolean isSlimeChunkNearInfo() {
        return slimeChunkNearInfo;
    }

    public boolean isSlimeChunkSucMsg() {
        return slimeChunkSucMsg;
    }

    public int getSlimeChunkNearInfoRange() {
        return slimeChunkNearInfoRange;
    }

    public List<String> getSlimeChunkFaiCmds() {
        return slimeChunkFaiCmds;
    }

    public List<String> getSlimeChunkSucCmds() {
        return slimeChunkSucCmds;
    }
}
