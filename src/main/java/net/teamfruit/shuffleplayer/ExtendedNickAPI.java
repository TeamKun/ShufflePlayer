package net.teamfruit.shuffleplayer;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ExtendedNickAPI {
    private static <T> CompletableFuture<T> promisify(Consumer<CompletableFuture<T>> func) {
        CompletableFuture<T> future = new CompletableFuture<>();
        func.accept(future);
        return future;
    }


}
