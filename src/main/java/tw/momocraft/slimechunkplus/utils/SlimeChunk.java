package tw.momocraft.slimechunkplus.utils;

import javafx.util.Pair;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

public class SlimeChunk {

    public static void startCheck(CommandSender sender, Player target) {
        Player player;
        if (target != null)
            player = target;
        else
            player = (Player) sender;
        Location loc = player.getLocation();
        if (loc.getChunk().isSlimeChunk()) {
            if (ConfigHandler.getConfigPath().isSlimeChunkSucceedMsg())
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgSlimeChunkFound(), player);
            CorePlusAPI.getCmd().sendCmd(ConfigHandler.getPlugin(), player, player, ConfigHandler.getConfigPath().getSlimeChunkSucceedCmds());
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginPrefix(),
                    "Slime-Chunk", player.getName(), "isSlimeChunk", "succeed",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        if (ConfigHandler.getConfigPath().isSlimeChunkFailedMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgSlimeChunkNotFound(), player);
        CorePlusAPI.getCmd().sendCmd(ConfigHandler.getPlugin(), player, player, ConfigHandler.getConfigPath().getSlimeChunkFailedCmds());
        if (ConfigHandler.getConfigPath().isSlimeChunkNearInfo()) {
            List<Pair<Integer, Integer>> chunkList = getSlimeChunksAround(loc, ConfigHandler.getConfigPath().getSlimeChunkNearInfoRange());
            if (!chunkList.isEmpty()) {
                String[] placeHolders = CorePlusAPI.getMsg().newString();
                placeHolders[6] = String.valueOf(chunkList.size()); // %amount%
                placeHolders[12] = String.valueOf(getNearestDistance(loc, chunkList)); // %distance%
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgSlimeChunkNearInfo(), player, placeHolders);
            } else {
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgSlimeChunkNearInfoNull(), player);
            }
        }
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginPrefix(),
                "Slime-Chunk", player.getName(), "isSlimeChunk", "failed",
                new Throwable().getStackTrace()[0]);
    }

    /**
     * @param loc   the center location.
     * @param range the checking range. Unit: Chunk
     * @return All slime chunks around. (relatively distance. Unit: chunk)
     */
    private static List<Pair<Integer, Integer>> getSlimeChunksAround(Location loc, int range) {
        World world = loc.getWorld();
        int baseX = loc.getChunk().getX();
        int baseZ = loc.getChunk().getZ();
        Chunk chunk;
        List<Pair<Integer, Integer>> chunkList = new ArrayList<>();
        try {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    chunk = world.getChunkAt(baseX + x, baseZ + z);
                    if (chunk.isSlimeChunk())
                        chunkList.add(new Pair<>(x, z));
                }
            }
        } catch (Exception ex) {
            CorePlusAPI.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPluginPrefix(), ex);
        }
        return chunkList;
    }

    /**
     * @param chunkList the slime chunk list.
     * @return the distance of the nearest slime chunk.
     */
    private static double getNearestDistance(Location loc, List<Pair<Integer, Integer>> chunkList) {
        if (chunkList == null || chunkList.isEmpty())
            return -1;
        // Comparing all chunk distance.
        double min = 0;
        double chunkValue;
        Chunk chunk = null;
        for (Pair<Integer, Integer> pair : chunkList) {
            chunkValue = Math.sqrt(Math.pow(pair.getKey(), 2) + Math.pow(pair.getValue(), 2));
            if (min == 0 || min > chunkValue) {
                min = chunkValue;
                chunk = loc.getWorld().getChunkAt(pair.getKey(), pair.getValue());
            }
        }
        return CorePlusAPI.getUtils().getDistanceXZ(loc,
                new Location(chunk.getWorld(), chunk.getX() << 4, 64, chunk.getZ() << 4).add(7, 0, 7));
    }
}
