package tw.momocraft.slimechunkplus.handlers;

import tw.momocraft.slimechunkplus.Commands;
import tw.momocraft.slimechunkplus.SlimeChunkPlus;
import tw.momocraft.slimechunkplus.TabComplete;

public class DependHandler {

    public DependHandler() {
        registerEvents();
    }

    private void registerEvents() {
        SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setExecutor(new Commands());
        SlimeChunkPlus.getInstance().getCommand("SlimeChunkPlus").setTabCompleter(new TabComplete());
    }
}
