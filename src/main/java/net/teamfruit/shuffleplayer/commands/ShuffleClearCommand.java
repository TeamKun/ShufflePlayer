package net.teamfruit.shuffleplayer.commands;

import net.teamfruit.shuffleplayer.Shuffle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ShuffleClearCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Shuffle.clearAll()
                .thenAccept(b -> {
                    sender.sendMessage("Cleared");
                });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        return completions;
    }
}
