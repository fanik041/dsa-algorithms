package Graph;
import java.util.*;

public class BFS {
    /*
    // Returns the BFS visit order starting from `start`
    public static List<Integer> bfs(Map<Integer, List<Integer>> graph, int start) {
        List<Integer> order = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
        int node = queue.poll();
        order.add(node);

        for (int nei : graph.getOrDefault(node, List.of())) {
            if (visited.add(nei)) {        // add() returns false if already present
            queue.add(nei);
            }
        }
        }
        return order;
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> g = new HashMap<>();
        g.put(1, List.of(2, 3));
        g.put(2, List.of(4, 5));
        g.put(3, List.of(6));
        g.put(4, List.of());
        g.put(5, List.of());
        g.put(6, List.of());

        System.out.println(bfs(g, 1)); // [1, 2, 3, 4, 5, 6]
    }
    */
  
  public static List<Integer> bfs(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> order = new ArrayList<>();
    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();

    visited.add(start);
    queue.add(start);

    while(!queue.isEmpty()){
        int node = queue.poll();
        order.add(node);

        for(int neighbor : graph.getOrDefault(node, List.of())){
            if(visited.add(neighbor)){
                queue.add(neighbor);
            }
        }
    }
    return order;
  }

  public static void main(String[] args) {
    Map<Integer, List<Integer>> g = new HashMap<>();
    g.put(1, List.of(2, 3));
    g.put(2, List.of(4, 5));
    g.put(3, List.of(6));
    g.put(4, List.of());
    g.put(5, List.of());
    g.put(6, List.of());

    System.out.println(bfs(g, 1)); // [1, 2, 3, 4, 5, 6]
  }
}
