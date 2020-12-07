package tw.momocraft.slimechunkplus.utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tw.momocraft.slimechunkplus.handlers.ServerHandler;

import java.util.*;

public class Utils {

    public static String getNearbyPlayer(Player player, int range) {
        try {
            ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
            ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight(null, range);
            ArrayList<Location> sight = new ArrayList<>();
            for (Block block : sightBlock) sight.add(block.getLocation());
            for (Location location : sight) {
                for (Entity entity : entities) {
                    if (Math.abs(entity.getLocation().getX() - location.getX()) < 1.3) {
                        if (Math.abs(entity.getLocation().getY() - location.getY()) < 1.5) {
                            if (Math.abs(entity.getLocation().getZ() - location.getZ()) < 1.3) {
                                if (entity instanceof Player) {
                                    return entity.getName();
                                }
                            }
                        }
                    }
                }
            }
            return "INVALID";
        } catch (NullPointerException e) {
            return "INVALID";
        }
    }

    public static String translateLayout(String input, Player player) {
        if (player != null && !(player instanceof ConsoleCommandSender)) {
            String playerName = player.getName();
            // %player%
            try {
                input = input.replace("%player%", playerName);
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
            // %player_display_name%
            try {
                input = input.replace("%player_display_name%", player.getDisplayName());
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
            // %player_uuid%
            try {
                input = input.replace("%player_uuid%", player.getUniqueId().toString());
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
            // %player_interact%
            try {
                input = input.replace("%player_interact%", getNearbyPlayer(player, 3));
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
            // %player_sneaking%
            try {
                input = input.replace("%player_sneaking%", String.valueOf(player.isSneaking()));
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
            // %player_flying%
            try {
                input = input.replace("%player_flying%", String.valueOf(player.isFlying()));
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
            Location loc = player.getLocation();
            // %player_world%
            if (input.contains("%player_world%")) {
                input = input.replace("%player_world%", loc.getWorld().getName());
            }
            // %player_loc%
            // %player_loc_x%, %player_loc_y%, %player_loc_z%
            // %player_loc_x_NUMBER%, %player_loc_y_NUMBER%, %player_loc_z_NUMBER%
            if (input.contains("%player_loc")) {
                try {
                    String loc_x = String.valueOf(loc.getBlockX());
                    String loc_y = String.valueOf(loc.getBlockY());
                    String loc_z = String.valueOf(loc.getBlockZ());
                    String[] arr = input.split("%");
                    for (int i = 0; i < arr.length; i++) {
                        switch (arr[i]) {
                            case "player_loc_x":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_x%" + arr[i + 1] + "%", loc_x + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                            case "player_loc_y":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_y%" + arr[i + 1] + "%", loc_y + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                            case "player_loc_z":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_z%" + arr[i + 1] + "%", loc_z + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                        }
                    }
                    input = input.replace("%player_loc%", loc_x + ", " + loc_y + ", " + loc_z);
                    input = input.replace("%player_loc_x%", loc_x);
                    input = input.replace("%player_loc_y%", loc_y);
                    input = input.replace("%player_loc_z%", loc_z);
                } catch (Exception e) {
                    ServerHandler.sendDebugTrace(e);
                }
            }
        }
        // %player% => CONSOLE
        if (player == null) {
            try {
                input = input.replace("%player%", "CONSOLE");
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
        }
        // Translate color codes.
        input = ChatColor.translateAlternateColorCodes('&', input);
        return input;
    }

    /**
     * Sort Map keys by values.
     * High -> Low
     *
     * @param map the input map.
     * @param <K> key
     * @param <V> value
     * @return the sorted map.
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Sort Map keys by values.
     * Low -> High
     *
     * @param map the input map.
     * @param <K> key
     * @param <V> value
     * @return the sorted map.
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueLow(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static String translateColorCode(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}