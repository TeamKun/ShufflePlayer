package net.teamfruit.shuffleplayer;

import net.teamfruit.shuffleplayer.commands.ShuffleClearCommand;
import net.teamfruit.shuffleplayer.commands.ShuffleSwapCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShufflePlayer extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic

        // イベント登録
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getCommand("shuffle_swap").setExecutor(new ShuffleSwapCommand());
        getCommand("shuffle_clear").setExecutor(new ShuffleClearCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
