package tw.momocraft.slimechunkplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.api.CorePlusAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        int length = args.length;
        if (length == 1) {
            if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.use"))
                commands.add("help");
            if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.reload"))
                commands.add("reload");
            if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.version"))
                commands.add("version");
            if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.checkslime"))
                commands.add("checkslime");
        } else {
            switch (args[0].toLowerCase()) {
                case "checkslime":
                    // /scp checkslime [player]
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "slimechunkplus.command.checkslime.other"))
                        commands.addAll(CorePlusAPI.getPlayer().getOnlinePlayerNames());
                    break;
            }
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}