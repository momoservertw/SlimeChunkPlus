package tw.momocraft.slimechunkplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.utils.SlimeChunk;


public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        switch (args.length) {
            case 0:
                if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.use")) {
                    CorePlusAPI.getMsg().sendMsg("", sender, "");
                    CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgTitle(), sender);
                    CorePlusAPI.getMsg().sendMsg("", sender,
                            "&f " + SlimeChunkPlus.getInstance().getDescription().getName()
                                    + " &ev" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgHelp(), sender);
                    CorePlusAPI.getMsg().sendMsg("", sender, "");
                } else {
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case 1:
                if (args[0].equalsIgnoreCase("help")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.use")) {
                        CorePlusAPI.getMsg().sendMsg("", sender, "");
                        CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgTitle(), sender);
                        CorePlusAPI.getMsg().sendMsg("", sender,
                                "&f " + SlimeChunkPlus.getInstance().getDescription().getName()
                                        + " &ev" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgHelp(), sender);
                        if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.reload"))
                            CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgReload(), sender);
                        if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.version"))
                            CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgVersion(), sender);
                        if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.checkslime.other")) {
                            CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgCheckSlime(), sender);
                            CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgCheckSlimeOther(), sender);
                        } else if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.checkslime")) {
                            CorePlusAPI.getMsg().sendLangMsg("", ConfigHandler.getConfigPath().getMsgCheckSlime(), sender);
                        }
                        CorePlusAPI.getMsg().sendMsg("", sender, "");
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.reload")) {
                        ConfigHandler.generateData(true);
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("version")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.version")) {
                        CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                                "&f " + SlimeChunkPlus.getInstance().getDescription().getName()
                                        + " &ev" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        CorePlusAPI.getUpdate().check(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(), sender,
                                SlimeChunkPlus.getInstance().getName(), SlimeChunkPlus.getInstance().getDescription().getVersion(), false);
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                "Message.noPermission", sender);
                    }
                    return true;
                    // /scp checkslime
                } else if (args[0].equalsIgnoreCase("checkslime")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.checkslime")) {
                        if (ConfigHandler.getConfigPath().isSlimeChunk()) {
                            if (sender instanceof ConsoleCommandSender) {
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                        "Message.onlyPlayer", sender);
                            } else {
                                SlimeChunk.startCheck(sender, null);
                            }
                        } else {
                            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                    "Message.featureDisabled", sender);
                        }
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case 2:
                // /scp checkslime PLAYER
                if (args[0].equalsIgnoreCase("checkslime")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.checkslime.other")) {
                        if (ConfigHandler.getConfigPath().isSlimeChunk()) {
                            Player player = CorePlusAPI.getPlayer().getPlayer(args[1]);
                            String[] placeHolders = CorePlusAPI.getMsg().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            if (player == null) {
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                        "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            SlimeChunk.startCheck(sender, player);
                        } else {
                            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                    "Message.featureDisabled", sender);
                        }
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
        }
        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                "Message.unknownCommand", sender);
        return true;
    }
}