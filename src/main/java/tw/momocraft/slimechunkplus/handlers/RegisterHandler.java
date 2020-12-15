package tw.momocraft.slimechunkplus.handlers;

import tw.momocraft.slimechunkplus.Commands;
import tw.momocraft.slimechunkplus.SlimeChunkPlus;
import tw.momocraft.slimechunkplus.utils.TabComplete;


public class RegisterHandler {

    public static void registerEvents() {
        SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setExecutor(new Commands());
        SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setTabCompleter(new TabComplete());
    }
}
