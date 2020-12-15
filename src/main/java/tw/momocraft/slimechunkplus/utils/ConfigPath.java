package tw.momocraft.slimechunkplus.utils;

import tw.momocraft.slimechunkplus.handlers.ConfigHandler;

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
    private boolean sCSucMsg;
    private List<String> sCSucCmds;
    private boolean sCFaiMsg;
    private List<String> sCFaiCmds;
    private boolean sCNearInfo;
    private int sCNearInfoRange;

    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setupMsg();
        setupSlimeChunk();
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
        sCSucMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Message");
        sCSucCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Succeed.Commands");
        sCFaiMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Failed.Message");
        sCFaiCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Failed.Commands");
        sCNearInfo = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Nearby-Information.Enable");
        sCNearInfoRange = ConfigHandler.getConfig("config.yml").getInt("Slime-Chunk.Succeed.Nearby-Information.Range");
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

    public boolean issCSucMsg() {
        return sCSucMsg;
    }

    public List<String> getsCSucCmds() {
        return sCSucCmds;
    }

    public boolean issCFaiMsg() {
        return sCFaiMsg;
    }

    public List<String> getsCFaiCmds() {
        return sCFaiCmds;
    }

    public boolean issCNearInfo() {
        return sCNearInfo;
    }

    public int getsCNearInfoRange() {
        return sCNearInfoRange;
    }
}
