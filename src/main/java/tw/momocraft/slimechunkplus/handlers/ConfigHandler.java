package tw.momocraft.slimechunkplus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.SlimeChunkPlus;
import tw.momocraft.slimechunkplus.utils.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigHandler {
    private static YamlConfiguration configYAML;
    private static Dependence depends;
    private static ConfigPath configPath;

    public static void generateData(boolean reload) {
        genConfigFile("config.yml");
        setConfigPath(new ConfigPath());
        if (!reload) {
            CorePlusAPI.getUpdateManager().check(getPluginName(), getPrefix(), Bukkit.getConsoleSender(),
                    SlimeChunkPlus.getInstance().getDescription().getName(),
                    SlimeChunkPlus.getInstance().getDescription().getVersion(), true);
        }
    }

    public static FileConfiguration getConfig(String fileName) {
        File filePath = tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().getDataFolder();
        File file;
        switch (fileName) {
            case "config.yml":
                if (configYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            default:
                break;
        }
        file = new File(filePath, fileName);
        return getPath(fileName, file, false);
    }

    private static void getConfigData(File filePath, String fileName) {
        File file = new File(filePath, fileName);
        if (!(file).exists()) {
            try {
                tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().saveResource(fileName, false);
            } catch (Exception e) {
                CorePlusAPI.getLangManager().sendErrorMsg(ConfigHandler.getPrefix(),
                        "&cCannot save " + fileName + " to disk!");
                return;
            }
        }
        getPath(fileName, file, true);
    }

    private static YamlConfiguration getPath(String fileName, File file, boolean saveData) {
        switch (fileName) {
            case "config.yml":
                if (saveData) {
                    configYAML = YamlConfiguration.loadConfiguration(file);
                }
                return configYAML;
        }
        return null;
    }

    private static void genConfigFile(String fileName) {
        String[] fileNameSlit = fileName.split("\\.(?=[^\\.]+$)");
        int configVer = 0;
        File filePath = tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().getDataFolder();
        switch (fileName) {
            case "config.yml":
                configVer = 2;
                break;
        }
        getConfigData(filePath, fileName);
        File File = new File(filePath, fileName);
        if (File.exists() && getConfig(fileName).getInt("Config-Version") != configVer) {
            if (tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().getResource(fileName) != null) {
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
                String currentTime = currentDate.format(formatter);
                String newGen = fileNameSlit[0] + " " + currentTime + "." + fileNameSlit[0];
                File newFile = new File(filePath, newGen);
                if (!newFile.exists()) {
                    File.renameTo(newFile);
                    File configFile = new File(filePath, fileName);
                    configFile.delete();
                    getConfigData(filePath, fileName);
                    CorePlusAPI.getLangManager().sendConsoleMsg(getPrefix(),
                            "&4The file \"" + fileName + "\" is out of date, generating a new one!");
                }
            }
        }
        getConfig(fileName).options().copyDefaults(false);
    }

    private static void setConfigPath(ConfigPath configPath) {
        ConfigHandler.configPath = configPath;
    }

    public static ConfigPath getConfigPath() {
        return configPath;
    }

    public static Dependence getDepends() {
        return depends;
    }

    private static void setDepends(Dependence depend) {
        depends = depend;
    }

    public static String getPrefix() {
        return getConfig("config.yml").getString("Message.prefix");
    }

    public static String getPlugin() {
        return "[" + SlimeChunkPlus.getInstance().getDescription().getName() + "] ";
    }

    public static String getPluginName() {
        return CorePlus.getInstance().getDescription().getName();
    }

    public static boolean isDebugging() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }

}