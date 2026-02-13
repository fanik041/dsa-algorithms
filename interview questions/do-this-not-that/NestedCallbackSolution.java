import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NestedCallbackSolution {

  record User(String id) {}
  record Order(String id) {}

  interface AsyncApi {
    CompletableFuture<User> getUser(String id);
    CompletableFuture<List<Order>> getOrders(String userId);
    CompletableFuture<String> processOrders(List<Order> orders);
  }

  static void run(AsyncApi api, String id) {
    api.getUser(id)
        .thenCompose(user -> api.getOrders(user.id()))          
        .thenCompose(api::processOrders)
        .thenAccept(System.out::println)
        .exceptionally(ex -> {
          System.err.println("Pipeline failed: " + ex.getMessage());
          return null;
        });
  }

  public static void main(String[] args) {
    ExecutorService ioPool = Executors.newFixedThreadPool(8);

    AsyncApi api = new AsyncApi() {
      @Override
      public CompletableFuture<User> getUser(String id) {
        return CompletableFuture.supplyAsync(() -> {
          sleep(150);
          return new User(id);
        }, ioPool);
      }

      @Override
      public CompletableFuture<List<Order>> getOrders(String userId) {
        return CompletableFuture.supplyAsync(() -> {
          sleep(200);
          return List.of(new Order("o1"), new Order("o2"), new Order("o3"));
        }, ioPool);
      }

      @Override
      public CompletableFuture<String> processOrders(List<Order> orders) {
        return CompletableFuture.supplyAsync(() -> {
          sleep(300);
          return "Processed " + orders.size() + " orders";
        }, ioPool);
      }
    };

    run(api, "user-123");


    sleep(1000);
    ioPool.shutdown();
  }

  static void sleep(long ms) {
    try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
  }
}
