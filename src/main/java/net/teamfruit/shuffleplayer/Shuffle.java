package net.teamfruit.shuffleplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.inventivetalent.nicknamer.api.NickManager;
import org.inventivetalent.nicknamer.api.NickNamerAPI;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Shuffle {
    private static <T> CompletableFuture<T> promisify(Consumer<CompletableFuture<T>> func) {
        CompletableFuture<T> future = new CompletableFuture<>();
        func.accept(future);
        return future;
    }

    public static CompletableFuture<Boolean> set(String nameA, String nameB) {
        // Bukkit
        Player playerA = Bukkit.getPlayer(nameA);
        if (playerA == null)
            return CompletableFuture.completedFuture(false);

        playerA.setDisplayName(nameB);

        // Nick
        NickManager nickAPI = NickNamerAPI.getNickManager();
        nickAPI.setNick(playerA.getUniqueId(), nameB);
        return promisify(resolve -> nickAPI.setSkin(playerA.getUniqueId(), nameB, () -> resolve.complete(true)));
    }

    public static CompletableFuture<Boolean> clearAll() {
        // Nick
        NickManager nickAPI = NickNamerAPI.getNickManager();
        Set<UUID> nickPlayers = nickAPI.getUsedNicks().stream().flatMap(e -> nickAPI.getPlayersWithNick(e).stream()).collect(Collectors.toSet());
        for (UUID player : nickPlayers) {
            nickAPI.removeNick(player);
            Optional.ofNullable(Bukkit.getPlayer(player)).ifPresent(p -> p.setDisplayName(p.getName()));
        }
        Set<UUID> skinPlayers = nickAPI.getUsedSkins().stream().flatMap(e -> nickAPI.getPlayersWithSkin(e).stream()).collect(Collectors.toSet());
        for (UUID player : skinPlayers)
            nickAPI.removeSkin(player);
        return CompletableFuture.completedFuture(true);
    }

    public static CompletableFuture<Boolean> swap(String playerA, String playerB) {
        CompletableFuture<Boolean> resultA = set(playerA, playerB);
        CompletableFuture<Boolean> resultB = set(playerB, playerA);
        return resultA.thenCombine(resultB, (a, b) -> a && b);
    }
}
