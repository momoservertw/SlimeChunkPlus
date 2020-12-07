package tw.momocraft.slimechunkplus.utils;

import javafx.util.Pair;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.slimechunkplus.SlimeChunkPlus;
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
            if (ConfigHandler.getConfigPath().isSCSucMsg()) {
                Language.sendLangMessage("Message.SlimeChunkPlus.slimeChunkFound", player);
            }
            CustomCommands.executeMultiCmdsList(player, ConfigHandler.getConfigPath().getSCSucCmds(), true);

            List<String> sounds = ConfigHandler.getConfigPath().getSCSucSounds();
            if (!sounds.isEmpty()) {
                for (String group : sounds) {
                    SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(group);
                    Sound sound = Sound.valueOf(soundMap.getType());
                    int times = soundMap.getTimes();
                    int interval = soundMap.getInterval();
                    long volume = soundMap.getVolume();
                    long pitch = soundMap.getPitch();
                    new BukkitRunnable() {
                        int i = 1;

                        @Override
                        public void run() {
                            if (i > times) {
                                cancel();
                            } else {
                                ++i;
                                player.playSound(loc, sound, volume, pitch);
                            }
                        }
                    }.runTaskTimer(SlimeChunkPlus.getInstance(), 0, interval);
                }
            }
            List<String> particles = ConfigHandler.getConfigPath().getSCSucParticles();
            if (!particles.isEmpty()) {
                for (String particleType : particles) {
                    ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(particleType);
                    Particle particle = Particle.valueOf(particleMap.getType());
                    int amount = particleMap.getAmount();
                    int times = particleMap.getTimes();
                    int interval = particleMap.getInterval();
                    new BukkitRunnable() {
                        int i = 1;

                        @Override
                        public void run() {
                            if (i > times) {
                                cancel();
                            } else {
                                ++i;
                                player.spawnParticle(particle, loc, amount, 0, 0, 0, 0);
                            }
                        }
                    }.runTaskTimer(SlimeChunkPlus.getInstance(), 0, interval);
                }
                ServerHandler.sendFeatureMessage("Slime-Chunk", player.getName(), "final", "success",
                        new Throwable().getStackTrace()[0]);
            }
        } else {
            if (ConfigHandler.getConfigPath().isSCFaiMsg()) {
                Language.sendLangMessage("Message.SlimeChunkPlus.slimeChunkNotFound", player);
            }
            CustomCommands.executeMultiCmdsList(player, ConfigHandler.getConfigPath().getSCFaiCmds(), true);
            if (ConfigHandler.getConfigPath().isSCNearInfo()) {
                List<Pair<Integer, Integer>> chunkList = getSlimeChunksAround(loc, ConfigHandler.getConfigPath().getSCNearInfoRange());
                if (!chunkList.isEmpty()) {
                    String[] placeHolders = Language.newString();
                    placeHolders[6] = String.valueOf(chunkList.size());
                    placeHolders[7] = String.valueOf(getNearestDistance(chunkList));
                    Language.sendLangMessage("Message.SlimeChunkPlus.slimeChunkNearInfo", player, placeHolders);
                } else {
                    Language.sendLangMessage("Message.SlimeChunkPlus.slimeChunkNearInfoNull", player);
                }
            }
            List<String> sounds = ConfigHandler.getConfigPath().getSCFaiSounds();
            if (!sounds.isEmpty()) {
                for (String group : sounds) {
                    SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(group);
                    Sound sound = Sound.valueOf(soundMap.getType());
                    int times = soundMap.getTimes();
                    int interval = soundMap.getInterval();
                    long volume = soundMap.getVolume();
                    long pitch = soundMap.getPitch();
                    new BukkitRunnable() {
                        int i = 1;

                        @Override
                        public void run() {
                            if (i > times) {
                                cancel();
                            } else {
                                ++i;
                                player.playSound(loc, sound, volume, pitch);
                            }
                        }
                    }.runTaskTimer(SlimeChunkPlus.getInstance(), 0, interval);
                }
            }
            List<String> particles = ConfigHandler.getConfigPath().getSCFaiParticles();
            if (!particles.isEmpty()) {
                for (String particleType : particles) {
                    ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(particleType);
                    Particle particle = Particle.valueOf(particleMap.getType());
                    int amount = particleMap.getAmount();
                    int times = particleMap.getTimes();
                    int interval = particleMap.getInterval();
                    new BukkitRunnable() {
                        int i = 1;

                        @Override
                        public void run() {
                            if (i > times) {
                                cancel();
                            } else {
                                ++i;
                                player.spawnParticle(particle, loc, amount, 0, 0, 0, 0);
                            }
                        }
                    }.runTaskTimer(SlimeChunkPlus.getInstance(), 0, interval);
                }
            }
            ServerHandler.sendFeatureMessage("Slime-Chunk", player.getName(), "final", "fail",
                    new Throwable().getStackTrace()[0]);
        }
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
                    if (chunk.isSlimeChunk()) {
                        chunkList.add(new Pair<>(x, z));
                    }
                }
            }
        } catch (Exception ex) {
            ServerHandler.sendDebugTrace(ex);
        }
        return chunkList;
    }

    /**
     * @param chunkList the slime chunk list.
     * @return the distance of the nearest slime chunk.
     */
    private static long getNearestDistance(List<Pair<Integer, Integer>> chunkList) {
        // Comparing all chunk distance.
        double min = 0;
        double value;
        for (Pair<Integer, Integer> pair : chunkList) {
            value = Math.sqrt(Math.pow(pair.getKey(), 2) + Math.pow(pair.getValue(), 2));
            if (min == 0 || min > value) {
                min = value;
            }
        }
        // To get the chunk distance.
        return Math.round(min * 16);
    }
}
