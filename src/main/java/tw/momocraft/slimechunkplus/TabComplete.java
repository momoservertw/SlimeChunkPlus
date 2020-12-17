package tw.momocraft.slimechunkplus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        Collection<?> playersOnlineNew;
        Player[] playersOnlineOld;
        switch (args.length) {
            case 1:
                if (CorePlusAPI.getPermManager().hasPermission(sender, "slimechunkplus.use")) {
                    commands.add("help");
                }
                if (CorePlusAPI.getPermManager().hasPermission(sender, "slimechunkplus.command.reload")) {
                    commands.add("reload");
                }
                if (CorePlusAPI.getPermManager().hasPermission(sender, "slimechunkplus.command.version")) {
                    commands.add("version");
                }
                if (CorePlusAPI.getPermManager().hasPermission(sender, "slimechunkplus.command.checkslime")) {
                    commands.add("checkslime");
                }
                break;
            case 2:
                // /scp checkslime PLAYER
                if (args[0].equalsIgnoreCase("checkslime") && CorePlusAPI.getPermManager().hasPermission(sender, "slimechunkplus.command.checkslime.other")) {
                    try {
                        if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                                playersOnlineNew = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                                for (Object objPlayer : playersOnlineNew) {
                                    commands.add(((Player) objPlayer).getName());
                                }
                            }
                        } else {
                            playersOnlineOld = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                            for (Player player : playersOnlineOld) {
                                commands.add(player.getName());
                            }
                        }
                    } catch (Exception e) {
                        CorePlusAPI.getLangManager().sendDebugTrace(ConfigHandler.getPrefix(), e);
                    }
                }
                break;
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}