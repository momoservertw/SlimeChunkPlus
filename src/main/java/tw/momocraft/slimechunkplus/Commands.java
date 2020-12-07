package tw.momocraft.slimechunkplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.handlers.PermissionsHandler;
import tw.momocraft.slimechunkplus.handlers.PlayerHandler;
import tw.momocraft.slimechunkplus.utils.Language;
import tw.momocraft.slimechunkplus.utils.SlimeChunk;


public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        switch (args.length) {
            case 0:
                if (PermissionsHandler.hasPermission(sender, "slimechunkplus.use")) {
                    Language.dispatchMessage(sender, "");
                    Language.sendLangMessage("Message.SlimeChunkPlus.Commands.title", sender, false);
                    if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.version")) {
                        Language.dispatchMessage(sender, "&d&lSlimeChunkPlus &e&lv" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "&8 - &fby Momocraft");
                    }
                    Language.sendLangMessage("Message.SlimeChunkPlus.Commands.help", sender, false);
                    Language.dispatchMessage(sender, "");
                } else {
                    Language.sendLangMessage("Message.noPermission", sender);
                }
                return true;
            case 1:
                if (args[0].equalsIgnoreCase("help")) {
                    if (PermissionsHandler.hasPermission(sender, "slimechunkplus.use")) {
                        Language.dispatchMessage(sender, "");
                        Language.sendLangMessage("Message.SlimeChunkPlus.Commands.title", sender, false);
                        if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.version")) {
                            Language.dispatchMessage(sender, "&d&lSlimeChunkPlus &e&lv" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "&8 - &fby Momocraft");
                        }
                        Language.sendLangMessage("Message.SlimeChunkPlus.Commands.help", sender, false);
                        if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.reload")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.reload", sender, false);
                        }
                        if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.clear.other")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.clean", sender, false);
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.cleanOther", sender, false);
                        } else if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.clear")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.clean", sender, false);
                        }
                        if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.cleanslot.other")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.cleanSlot", sender, false);
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.cleanSlotOther", sender, false);
                        } else if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.cleanslot")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.cleanSlot", sender, false);
                        }
                        if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.takeoff.other")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.takeOff", sender, false);
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.takeOffOther", sender, false);
                        } else if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.clear")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.clean", sender, false);
                        }
                        if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.takeoffslot.other")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.takeOffSlot", sender, false);
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.takeOffSlotOther", sender, false);
                        } else if (PermissionsHandler.hasPermission(sender, "SlimeChunkPlus.command.takeoffslot")) {
                            Language.sendLangMessage("Message.SlimeChunkPlus.Commands.takeOffSlot", sender, false);
                        }
                        Language.dispatchMessage(sender, "");
                    } else {
                        Language.sendLangMessage("Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (PermissionsHandler.hasPermission(sender, "slimechunkplus.command.reload")) {
                        ConfigHandler.generateData(true);
                        Language.sendLangMessage("Message.configReload", sender);
                    } else {
                        Language.sendLangMessage("Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("version")) {
                    if (PermissionsHandler.hasPermission(sender, "slimechunkplus.command.version")) {
                        Language.dispatchMessage(sender, "&d&lSlimeChunkPlus &e&lv" + SlimeChunkPlus.getInstance().getDescription().getVersion() + "&8 - &fby Momocraft");
                        ConfigHandler.getUpdater().checkUpdates(sender);
                    } else {
                        Language.sendLangMessage("Message.noPermission", sender);
                    }
                    return true;
                    // /scp checkslime
                } else if (args[0].equalsIgnoreCase("checkslime")) {
                    if (PermissionsHandler.hasPermission(sender, "slimechunkplus.command.checkslime")) {
                        if (ConfigHandler.getConfigPath().isSlimeChunk()) {
                            if (sender instanceof ConsoleCommandSender) {
                                Language.sendLangMessage("Message.onlyPlayer", sender);
                            } else {
                                SlimeChunk.startCheck(sender, null);
                            }
                        } else {
                            Language.sendLangMessage("Message.featureDisabled", sender);
                        }
                    } else {
                        Language.sendLangMessage("Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case 2:
                // /scp checkslime PLAYER
                if (args[0].equalsIgnoreCase("checkslime")) {
                    if (PermissionsHandler.hasPermission(sender, "slimechunkplus.command.checkslime.other")) {
                        if (ConfigHandler.getConfigPath().isSlimeChunk()) {
                            if (sender instanceof ConsoleCommandSender) {
                                Language.sendLangMessage("Message.onlyPlayer", sender);
                            } else {
                                Player player = PlayerHandler.getPlayerString(args[1]);
                                String[] placeHolders = Language.newString();
                                placeHolders[2] = args[1];
                                if (player == null) {
                                    Language.sendLangMessage("Message.targetNotFound", sender, placeHolders);
                                    return true;
                                }
                                SlimeChunk.startCheck(sender, player);
                            }
                        } else {
                            Language.sendLangMessage("Message.featureDisabled", sender);
                        }
                    } else {
                        Language.sendLangMessage("Message.noPermission", sender);
                    }
                    return true;
                }
                break;

        }
        Language.sendLangMessage("Message.unknownCommand", sender);
        return true;
    }
}