package tw.momocraft.slimechunkplus.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;
import tw.momocraft.slimechunkplus.handlers.ServerHandler;

import java.util.*;

public class SlimeChunk {
    public static void startCheck(CommandSender sender, Player target) {
        Player player;
        if (target != null) {
            player = target;
        } else {
            player = (Player) sender;
        }
        Location loc = player.getLocation();
        if (loc.getChunk().isSlimeChunk()) {
            if (ConfigHandler.getConfigPath().isSlimeChunkSucMsg()) {
                Language.sendLangMessage("Message.SlimeChunkPlus.slimeChunkFound", player);
            }
            CustomCommands.executeMultiCmdsList(player, ConfigHandler.getConfigPath().getSlimeChunkSucCmds());
        } else {
            if (ConfigHandler.getConfigPath().isSlimeChunkFaiMsg()) {
                Language.sendLangMessage("Message.SlimeChunkPlus.slimeChunkNotFound", player);
            }
            CustomCommands.executeMultiCmdsList(player, ConfigHandler.getConfigPath().getSlimeChunkFaiCmds());
            if (ConfigHandler.getConfigPath().isSlimeChunkNearInfo()) {
                Map<Integer, Integer> chunkMap = getSlimeChunksAround(loc, ConfigHandler.getConfigPath().getSlimeChunkNearInfoRange());
                String[] placeHolders = Language.newString();
                placeHolders[6] = String.valueOf(chunkMap.keySet().size());
                placeHolders[7] = String.valueOf(getNearestDistance(chunkMap));
                Language.sendLangMessage("Message.SlimeChunkPlus.slimeChunkNearInfo", player, placeHolders);
            }
        }
    }

    private static Map<Integer, Integer> getSlimeChunksAround(Location loc, int range) {
        World world = loc.getWorld();
        int baseX = loc.getChunk().getX();
        int baseZ = loc.getChunk().getZ();
        Chunk chunk;
        Map<Integer, Integer> chunkMap = new HashMap<>();
        try {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    chunk = world.getChunkAt(baseX + x, baseZ + z);
                    if (chunk.isSlimeChunk()) {
                        chunkMap.put(x, z);
                    }
                }
            }
        } catch (Exception ex) {
            ServerHandler.sendDebugTrace(ex);
        }
        return chunkMap;
    }

    private static int getNearestDistance(Map<Integer, Integer> chunkMap) {
        List<Integer> distanceList = new ArrayList<>();
        for (int x : chunkMap.keySet()) {
            distanceList.add(x * chunkMap.get(x));
        }
        Collections.sort(distanceList);
        return distanceList.get((distanceList.size() / 2) + 1);
    }
}
