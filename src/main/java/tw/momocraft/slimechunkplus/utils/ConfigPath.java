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
    private final Map<String, String> customCmdProp = new HashMap<>();
    private final Map<String, SoundMap> soundProp = new HashMap<>();
    private final Map<String, ParticleMap> particleProp = new HashMap<>();

    //  ============================================== //
    //         CleanSlot Settings                      //
    //  ============================================== //
    private boolean slimeChunk;
    private boolean sCSucMsg;
    private List<String> sCSucCmds;
    private boolean sCFaiMsg;
    private List<String> sCFaiCmds;
    private boolean sCNearInfo;
    private int sCNearInfoRange;
    private List<String> sCSucParticles;
    private List<String> sCFaiParticles;
    private List<String> sCSucSounds;
    private List<String> sCFaiSounds;

    //  ============================================== //
    //         Setup all configuration.                //
    //  ============================================== //
    private void setUp() {
        setGeneral();
        setupSlimeChunk();
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
        ConfigurationSection particleConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Particles");
        if (particleConfig != null) {
            ParticleMap particleMap;
            for (String group : particleConfig.getKeys(false)) {
                particleMap = new ParticleMap();
                particleMap.setType(ConfigHandler.getConfig("config.yml").getString("General.Particles." + group + ".Type"));
                particleMap.setAmount(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Amount", 1));
                particleMap.setTimes(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Times", 1));
                particleMap.setInterval(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Interval", 20));
                particleProp.put(group, particleMap);
            }
        }
        ConfigurationSection soundConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Sounds");
        if (soundConfig != null) {
            SoundMap soundMap;
            for (String group : soundConfig.getKeys(false)) {
                soundMap = new SoundMap();
                soundMap.setType(ConfigHandler.getConfig("config.yml").getString("General.Sounds." + group + ".Type"));
                soundMap.setVolume(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Volume", 1));
                soundMap.setPitch(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Pitch", 1));
                soundMap.setTimes(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Loop.Times", 1));
                soundMap.setInterval(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Loop.Interval", 20));
                soundProp.put(group, soundMap);
            }
        }
    }

    //  ============================================== //
    //         Slime Chunk Settings                    //
    //  ============================================== //
    private void setupSlimeChunk() {
        slimeChunk = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Enable");
        sCSucMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Message");
        sCSucCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Succeed.Commands");
        sCFaiMsg = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Failed.Message");
        sCFaiCmds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Failed.Commands");
        sCNearInfo = ConfigHandler.getConfig("config.yml").getBoolean("Slime-Chunk.Succeed.Nearby-Information.Enable");
        sCNearInfoRange = ConfigHandler.getConfig("config.yml").getInt("Slime-Chunk.Succeed.Nearby-Information.Range");
        sCSucParticles = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Succeed.Particles");
        sCSucSounds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Succeed.Sounds");
        sCFaiParticles = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Failed.Particles");
        sCFaiSounds = ConfigHandler.getConfig("config.yml").getStringList("Slime-Chunk.Failed.Sounds");
    }

    //  ============================================== //
    //         General Settings                        //
    //  ============================================== //
    public Map<String, String> getCustomCmdProp() {
        return customCmdProp;
    }
    public Map<String, ParticleMap> getParticleProp() {
        return particleProp;
    }
    public Map<String, SoundMap> getSoundProp() {
        return soundProp;
    }

    //  ============================================== //
    //         Slime Chunk Settings                    //
    //  ============================================== //

    public boolean isSlimeChunk() {
        return slimeChunk;
    }

    public boolean isSCFaiMsg() {
        return sCFaiMsg;
    }

    public boolean isSCNearInfo() {
        return sCNearInfo;
    }

    public boolean isSCSucMsg() {
        return sCSucMsg;
    }

    public int getSCNearInfoRange() {
        return sCNearInfoRange;
    }

    public List<String> getSCFaiCmds() {
        return sCFaiCmds;
    }

    public List<String> getSCSucCmds() {
        return sCSucCmds;
    }

    public List<String> getSCSucParticles() {
        return sCSucParticles;
    }

    public List<String> getSCFaiParticles() {
        return sCFaiParticles;
    }

    public List<String> getSCSucSounds() {
        return sCSucSounds;
    }

    public List<String> getSCFaiSounds() {
        return sCFaiSounds;
    }
}
