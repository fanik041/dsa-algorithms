package Graph;
import java.util.*;

public class DFS {

  public static List<Integer> dfs(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> order = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    dfsHelper(graph, start, visited, order);
    return order;
  }

  private static void dfsHelper(
      Map<Integer, List<Integer>> graph,
      int node,
      Set<Integer> visited,
      List<Integer> order
  ) {
    if (!visited.add(node)) return;

    order.add(node);

    for (int neighbor : graph.getOrDefault(node, List.of())) {
      dfsHelper(graph, neighbor, visited, order);
    }
  }

  public static void main(String[] args) {
    Map<Integer, List<Integer>> g = new HashMap<>();
    g.put(1, List.of(2, 3));
    g.put(2, List.of(4, 5));
    g.put(3, List.of(6));
    g.put(4, List.of());
    g.put(5, List.of());
    g.put(6, List.of());

    System.out.println(dfs(g, 1)); // e.g. [1, 2, 4, 5, 3, 6]
  }
}
