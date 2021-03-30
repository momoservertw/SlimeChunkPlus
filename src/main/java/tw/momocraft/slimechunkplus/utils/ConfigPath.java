package tw.momocraft.slimechunkplus.utils;

import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.slimechunkplus.SlimeChunkPlus;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConfigPath {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         Message Variables                       //
    //  ============================================== //
    private String msgTitle;
    private String msgHelp;
    private String msgReload;
    private String msgVersion;
    private String msgCheckSlime;
    private String msgCheckSlimeOther;
    private String msgSlimeChunkFound;
    private String msgSlimeChunkNotFound;
    private String msgSlimeChunkNearInfo;
    private String msgSlimeChunkNearInfoNull;

    //  ============================================== //
    //         Slime Chunk Variables                   //
    //  ============================================== //
    private boolean slimeChunk;
    private boolean slimeChunkSucceedMsg;
    private List<String> slimeChunkSucceedCmds;
    private boolean slimeChunkFailedMsg;
    private List<String> slimeChunkFailedCmds;
    private boolean slimeChunkNearInfo;
    private int slimeChunkNearInfoRange;

    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setupMsg();
        setupSlimeChunk();

        sendSetupMsg();
    }

    private void sendSetupMsg() {
        List<String> list = new ArrayList<>(SlimeChunkPlus.getInstance().getDescription().getDepend());
        list.addAll(SlimeChunkPlus.getInstance().getDescription().getSoftDepend());
        UtilsHandler.getLang().sendHookMsg(ConfigHandler.getPluginPrefix(), "plugins", list);

        list = Arrays.asList((
                "spawnbypass" + ","
                        + "spawnerbypass" + ","
                        + "damagebypass"
        ).split(","));
        CorePlusAPI.getLang().sendHookMsg(ConfigHandler.getPluginPrefix(), "Residence flags", list);
    }

    //  ============================================== //
    //         Message Setter                          //
    //  ============================================== //
    private void setupMsg() {
        msgTitle = ConfigHandler.getConfig("config.yml").getString("Message.Commands.title");
        msgHelp = ConfigHandler.getConfig("config.yml").getString("Message.Commands.help");
        msgReload = ConfigHandler.getConfig("config.yml").getString("Message.Commands.reload");
        msgVersion = ConfigHandler.getConfig("config.yml").getString("Message.Commands.version");
        msgCheckSlime = ConfigHandler.getConfig("config.yml").getString("Message.Commands.checkslime");
        msgCheckSlimeOther = ConfigHandler.getConfig("config.yml").getString("Message.Commands.checkslimeOther");
        msgSlimeChunkFound = ConfigHandler.getConfig("config.yml").getString("Message.slimeChunkFound");
        msgSlimeChunkNotFound = ConfigHandler.getConfig("config.yml").getString("Message.slimeChunkNotFound");
        msgSlimeChunkNearInfo = ConfigHandler.getConfig("config.yml").getString("Message.slimeChunkNearInfo");
        msgSlimeChunkNearInfoNull = ConfigHandler.getConfig("config.yml").getString("Message.slimeChunkNearInfoNull");
    }

    //  ============================================== //
    //         Slime Chunk Setter                      //
    //  ============================================== //
    private void setupSlimeChunk() {
        slimeChunk = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Enable");
        slimeChunkSucceedMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Message");
        slimeChunkSucceedCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Succeed.Commands");
        slimeChunkFailedMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Failed.Message");
        slimeChunkFailedCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Failed.Commands");
        slimeChunkNearInfo = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Nearby-Information.Enable");
        slimeChunkNearInfoRange = ConfigHandler.getConfig("config.yml").getInt("Slime-Chunk.Succeed.Nearby-Information.Range");
    }

    //  ============================================== //
    //         Message Getter                          //
    //  ============================================== //
    public String getMsgTitle() {
        return msgTitle;
    }

    public String getMsgHelp() {
        return msgHelp;
    }

    public String getMsgReload() {
        return msgReload;
    }

    public String getMsgVersion() {
        return msgVersion;
    }

    public String getMsgCheckSlime() {
        return msgCheckSlime;
    }

    public String getMsgCheckSlimeOther() {
        return msgCheckSlimeOther;
    }

    public String getMsgSlimeChunkFound() {
        return msgSlimeChunkFound;
    }

    public String getMsgSlimeChunkNotFound() {
        return msgSlimeChunkNotFound;
    }

    public String getMsgSlimeChunkNearInfo() {
        return msgSlimeChunkNearInfo;
    }

    public String getMsgSlimeChunkNearInfoNull() {
        return msgSlimeChunkNearInfoNull;
    }

    //  ============================================== //
    //         Slime Chunk Getter                      //
    //  ============================================== //
    public boolean isSlimeChunk() {
        return slimeChunk;
    }

    public boolean isSlimeChunkSucceedMsg() {
        return slimeChunkSucceedMsg;
    }

    public List<String> getSlimeChunkSucceedCmds() {
        return slimeChunkSucceedCmds;
    }

    public boolean isSlimeChunkFailedMsg() {
        return slimeChunkFailedMsg;
    }

    public List<String> getSlimeChunkFailedCmds() {
        return slimeChunkFailedCmds;
    }

    public boolean isSlimeChunkNearInfo() {
        return slimeChunkNearInfo;
    }

    public int getSlimeChunkNearInfoRange() {
        return slimeChunkNearInfoRange;
    }
}
