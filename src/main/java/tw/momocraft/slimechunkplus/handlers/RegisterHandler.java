package tw.momocraft.slimechunkplus.handlers;

import tw.momocraft.slimechunkplus.Commands;
import tw.momocraft.slimechunkplus.utils.TabComplete;


public class RegisterHandler {

    public static void registerEvents() {
        tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setExecutor(new Commands());
        tw.momocraft.slimechunkplus.SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setTabCompleter(new TabComplete());
    }
}
