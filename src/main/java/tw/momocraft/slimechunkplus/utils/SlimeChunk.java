package tw.momocraft.slimechunkplus.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.slimechunkplus.handlers.ConfigHandler;

import java.util.HashMap;
import java.util.Map;

public class SlimeChunk {

    public static void startCheck(CommandSender sender, Player target) {
        Player player;
        if (target != null)
            player = target;
        else
            player = (Player) sender;
        Location loc = player.getLocation();
        if (loc.getChunk().isSlimeChunk()) {
            CorePlusAPI.getCmd().sendCmd(ConfigHandler.getPlugin(), player, player, ConfigHandler.getConfigPath().getSlimeChunkSucceedCmds());
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginPrefix(),
                    "Slime-Chunk", player.getName(), "isSlimeChunk", "succeed",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        CorePlusAPI.getCmd().sendCmd(ConfigHandler.getPlugin(), player, player, ConfigHandler.getConfigPath().getSlimeChunkFailedCmds());
        if (ConfigHandler.getConfigPath().isSlimeChunkNearInfo()) {
            int range = ConfigHandler.getConfigPath().getSlimeChunkNearInfoRange();
            Map<Integer, Integer> chunkMap = getSlimeChunksAround(loc, range);
            if (!chunkMap.isEmpty()) {
                String msg = ConfigHandler.getConfigPath().getMsgSlimeChunkNearInfo();
                msg = msg.replace("%chunks%", String.valueOf(range));

                String[] placeHolders = CorePlusAPI.getMsg().newString();
                placeHolders[6] = String.valueOf(chunkMap.size()); // %amount%
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(), msg, player, placeHolders);
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
                    if (chunk.isSlimeChunk())
                        chunkMap.put(x, z);
                }
            }
        } catch (Exception ex) {
            CorePlusAPI.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPluginPrefix(), ex);
        }
        return chunkMap;
    }
}
