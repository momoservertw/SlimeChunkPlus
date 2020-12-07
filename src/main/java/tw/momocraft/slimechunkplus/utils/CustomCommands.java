package tw.momocraft.slimechunkplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.handlers.ServerHandler;

import java.util.List;

public class CustomCommands {

    public static void executeMultiCmdsList(CommandSender sender, List<String> input) {
        for (String value : input) {
            if (value.contains(";")) {
                String[] cmds;
                cmds = value.split(";");
                for (String cmd : cmds) {
                    executeCommands(sender, cmd);
                }
            } else {
                executeCommands(sender, value);
            }
        }
    }

    public static void executeMultiCmds(CommandSender sender, String input) {
        if (input.contains(";")) {
            String[] cmds;
            cmds = input.split(";");
            for (String cmd : cmds) {
                executeCommands(sender, cmd);
            }
        } else {
            executeCommands(sender, input);
        }
    }

    public static void executeCommands(CommandSender sender, String input) {
        if (sender != null && !(sender instanceof ConsoleCommandSender)) {
            Player player = (Player) sender;
            if (input.startsWith("log:")) {
                input = input.replace("log: ", "");
                ServerHandler.sendConsoleMessage(Utils.translateLayout(input, player));
                return;
            } else if (input.startsWith("broadcast:")) {
                input = input.replace("broadcast: ", "");
                Bukkit.broadcastMessage(Utils.translateLayout(input, player));
                return;
            } else if (input.startsWith("console:")) {
                input = input.replace("console: ", "");
                dispatchConsoleCommand(player, input, true);
                return;
            } else if (input.startsWith("bungee:")) {
                input = input.replace("bungee: ", "");
                dispatchBungeeCordCommand(player, input, true);
                return;
            } else if (input.startsWith("op:")) {
                input = input.replace("op: ", "");
                dispatchOpCommand(player, input, true);
                return;
            } else if (input.startsWith("player:")) {
                input = input.replace("player: ", "");
                dispatchPlayerCommand(player, input, true);
                return;
            } else if (input.startsWith("chat:")) {
                input = input.replace("chat: ", "");
                dispatchChatCommand(player, input, true);
                return;
            } else if (input.startsWith("message:")) {
                input = input.replace("message: ", "");
                dispatchMessageCommand(player, input, true);
                return;
            } else if (input.startsWith("custom:")) {
                input = input.replace("custom: ", "");
                dispatchCustomCommand(player, input);
                return;
                /*
            } else if (input.startsWith("message-suggestion:")) {
                input = input.replace("message-suggestion: ", "");
                dispatchMessageCommand(player, input, true);
                return;
            } else if (input.startsWith("message-console:")) {
                input = input.replace("message-console: ", "");
                dispatchMessageCommand(player, input, true);
                return;
            } else if (input.startsWith("message-player:")) {
                input = input.replace("message-player: ", "");
                dispatchMessageCommand(player, input, true);
                return;
            } else if (input.startsWith("message-op:")) {
                input = input.replace("message-op: ", "");
                dispatchMessageCommand(player, input, true);
                return;


                 */
            }
            dispatchConsoleCommand(null, input, false);
        } else {
            executeCommands(input);
        }
    }

    public static void executeCommands(String input) {
        if (input.startsWith("log:")) {
            input = input.replace("log: ", "");
            ServerHandler.sendConsoleMessage(Utils.translateLayout(input, null));
            return;
        } else if (input.startsWith("broadcast:")) {
            input = input.replace("broadcast: ", "");
            Bukkit.broadcastMessage(Utils.translateLayout(input, null));
            return;
        } else if (input.startsWith("console:")) {
            input = input.replace("console: ", "");
            dispatchConsoleCommand(null, input, true);
            return;
        } else if (input.startsWith("bungee:")) {
            dispatchBungeeCordCommand(null, input, true);
            return;
        } else if (input.startsWith("op:")) {
            ServerHandler.sendErrorMessage("&cThere is an error while execute command \"&eop: " + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("player:")) {
            ServerHandler.sendErrorMessage("&cThere is an error while execute command \"&eplayer:" + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("chat:")) {
            ServerHandler.sendErrorMessage("&cThere is an error while execute command \"&echat:" + input + "&c\" &8- &cCan not find the execute target.");
            return;
            /*
        } else if (input.startsWith("message-suggestion:")) {
            ServerHandler.sendErrorMessage("&cThere is an error while execute command \"&emessage-suggestion: " + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("message-console:")) {
            ServerHandler.sendErrorMessage("&cThere is an error while execute command \"&emessage-console: " + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("message-player:")) {
            ServerHandler.sendErrorMessage("&cThere is an error while execute command \"&emessage-player: " + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("message-op:")) {
            ServerHandler.sendErrorMessage("&cThere is an error while execute command \"&emessage-op: " + input + "&c\" &8- &cCan not find the execute target.");
            return;

             */
        }
        dispatchConsoleCommand(null, input, true);
    }

    /**
     * To execute custom command.
     */
    // custom: money, %random_number%1000%
    private static void dispatchCustomCommand(CommandSender sender, String input) {
        String[] placeHolderArr = input.split(", ");
        // money, %random_number%1000%
        String newCmd = ConfigHandler.getConfigPath().getCustomCmdProp().get(placeHolderArr[0]);
        if (newCmd == null) {
            ServerHandler.sendErrorMessage("&cCan not find the custom command type: " + placeHolderArr[0]);
        }
        for (int i = 1; i < +placeHolderArr.length; i++) {
            newCmd = newCmd.replace("%cmd_arg" + i + "%", placeHolderArr[i]);
        }
        // money: "console: cmi money give %player% %cmd_arg1%"
        executeCommands(sender, newCmd);
    }

    /**
     * To execute console command.
     *
     */

    private static void dispatchConsoleCommand(Player player, String command, boolean placeholder) {
        if (player != null && !(player instanceof ConsoleCommandSender)) {
            try {
                if (placeholder) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Utils.translateLayout(command, player));
                } else {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            } catch (Exception e) {
                ServerHandler.sendErrorMessage("&cThere was an issue executing a console command, if this continues please report it to the developer!");
                ServerHandler.sendDebugTrace(e);
            }
        } else {
            try {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            } catch (Exception e) {
                ServerHandler.sendErrorMessage("&cThere was an issue executing a console command, if this continues please report it to the developer!");
                ServerHandler.sendDebugTrace(e);
            }
        }
    }

    /**
     * To execute operator command.
     *
     */
    private static void dispatchOpCommand(Player player, String command, boolean placeholder) {
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            if (placeholder) {
                player.chat("/" + Utils.translateLayout(command, player));
            } else {
                player.chat("/" + command);
            }
        } catch (Exception e) {
            ServerHandler.sendDebugTrace(e);
            player.setOp(isOp);
            ServerHandler.sendErrorMessage("&cAn error has occurred while setting " + player.getName() + " status on the OP list, to ensure server security they have been removed as an OP.");
        } finally {
            player.setOp(isOp);
        }
    }

    /**
     * To execute player command.
     *
     */
    private static void dispatchPlayerCommand(Player player, String command, boolean placeholder) {
        try {
            if (placeholder) {
                player.chat("/" + Utils.translateLayout(command, player));
            } else {
                player.chat("/" + command);
            }
        } catch (Exception e) {
            ServerHandler.sendErrorMessage("&cThere was an issue executing a player command, if this continues please report it to the developer!");
            ServerHandler.sendDebugTrace(e);
        }
    }

    /**
     * To send message form player.
     *
     */
    private static void dispatchChatCommand(Player player, String command, boolean placeholder) {
        try {
            if (placeholder) {
                player.chat(Utils.translateLayout(command, player));
            } else {
                player.chat(command);
            }
        } catch (Exception e) {
            ServerHandler.sendErrorMessage("&cThere was an issue executing a player command, if this continues please report it to the developer!");
            ServerHandler.sendDebugTrace(e);
        }
    }

    /**
     * To send message to player.
     *
     */
    private static void dispatchMessageCommand(Player player, String command, boolean placeholder) {
        try {
            if (placeholder) {
                player.sendMessage(Utils.translateLayout(command, player));
            } else {
                player.sendMessage(command);
            }
        } catch (Exception e) {
            ServerHandler.sendErrorMessage("&cThere was an issue executing a command to send a message, if this continues please report it to the developer!");
            ServerHandler.sendDebugTrace(e);
        }
    }

    /**
     * To execute BungeeCord command.
     *
     */
    private static void dispatchBungeeCordCommand(Player player, String command, boolean placeholder) {
        try {
            if (placeholder) {
                BungeeCord.ExecuteCommand(player, Utils.translateLayout(command, player));
            } else {
                BungeeCord.ExecuteCommand(player, command);
            }
        } catch (Exception e) {
            ServerHandler.sendErrorMessage("&cThere was an issue executing an item's command to BungeeCord, if this continues please report it to the developer!");
            ServerHandler.sendDebugTrace(e);
        }
    }
}
