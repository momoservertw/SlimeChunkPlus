package tw.momocraft.slimechunkplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.utils.SlimeChunk;


public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        switch (args.length) {
            case 0:
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.use")) {
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgTitle(), sender);
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + SlimeChunkPlus.getInstance().getDescription().getName()
                            +" &ev" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgHelp(), sender);
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case 1:
                if (args[0].equalsIgnoreCase("help")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.use")) {
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgTitle(), sender);
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + SlimeChunkPlus.getInstance().getDescription().getName()
                                +" &ev" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgHelp(), sender);
                        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.reload")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgReload(), sender);
                        }
                        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.version")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVersion(), sender);
                        }
                        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.checkslime.other")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),ConfigHandler.getConfigPath().getMsgCheckSlime(), sender);
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),ConfigHandler.getConfigPath().getMsgCheckSlimeOther(), sender);
                        } else if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.checkslime")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),ConfigHandler.getConfigPath().getMsgCheckSlime(), sender);
                        }
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.reload")) {
                        ConfigHandler.generateData(true);
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("version")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.version")) {
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + SlimeChunkPlus.getInstance().getDescription().getName()
                                +" &ev" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        CorePlusAPI.getUpdateManager().check(ConfigHandler.getPrefix(), sender,
                                CorePlus.getInstance().getName(), CorePlus.getInstance().getDescription().getVersion(), false);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                    // /scp checkslime
                } else if (args[0].equalsIgnoreCase("checkslime")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.checkslime")) {
                        if (ConfigHandler.getConfigPath().isSlimeChunk()) {
                            if (sender instanceof ConsoleCommandSender) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.onlyPlayer", sender);
                            } else {
                                SlimeChunk.startCheck(sender, null);
                            }
                        } else {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                        }
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case 2:
                // /scp checkslime PLAYER
                if (args[0].equalsIgnoreCase("checkslime")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "slimechunkplus.command.checkslime.other")) {
                        if (ConfigHandler.getConfigPath().isSlimeChunk()) {
                            Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            if (player == null) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            SlimeChunk.startCheck(sender, player);
                        } else {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                        }
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
        }
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
        return true;
    }
}