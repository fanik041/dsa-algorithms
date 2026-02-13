import java.util.List;
import java.util.function.Consumer;

public class NestedCallbackHell {

  record User(String id) {}
  record Order(String id) {}

  interface AsyncApi {
    void getUser(String id, Consumer<User> cb, Consumer<Throwable> errCb);
    void getOrders(String userId, Consumer<List<Order>> cb, Consumer<Throwable> errCb);
    void processOrders(List<Order> orders, Consumer<String> cb, Consumer<Throwable> errCb);
  }

  static void run(AsyncApi api, String id) {
    api.getUser(id, user -> {
      api.getOrders(user.id(), orders -> {
        api.processOrders(orders, result -> {
          System.out.println(result);
        }, err -> {
          System.err.println("processOrders failed: " + err.getMessage());
        });
      }, err -> {
        System.err.println("getOrders failed: " + err.getMessage());
      });
    }, err -> {
      System.err.println("getUser failed: " + err.getMessage());
    });
  }
}
