package mate.academy;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class AsyncRequestProcessor {
    private final Executor executor;
    private Map<String, UserData> cache = new ConcurrentHashMap<>();

    public AsyncRequestProcessor(Executor executor) {
        this.executor = executor;
    }

    public CompletableFuture<UserData> processRequest(String userId) {
        if (cache.containsKey(userId)) {
            return CompletableFuture.supplyAsync(() -> (cache.get(userId)), executor);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return CompletableFuture.supplyAsync(() -> cache.computeIfAbsent(userId,
                id -> new UserData(userId,
                        "details=Details for " + userId)), executor);
    }
}


