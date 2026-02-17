package Graph;
import java.util.*;

public class BFS {
  
  public static List<Integer> bfs(Map<Integer, List<Integer>> graph, int start) {
    List<Integer> order = new ArrayList<>();
    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();

    visited.add(start);
    queue.add(start);

    while(!queue.isEmpty()){
        //.poll() removes and returns the head of the queue 
        // for example in [1, 2, 3], .poll() 
        // will return 1 and the queue will become [2, 3]
        int node = queue.poll();
        order.add(node);

        //getOrDefault() returns the value for the specified key if it exists, 
        // otherwise it returns the default value provided as the second argument.
        // for example, graph.getOrDefault(1, List.of()) 
        // will return [2, 3] if key 1 exists in the graph,
        // but if key 1 does not exist in the graph,
        // it will return an empty list List.of()
        for(int neighbor : graph.getOrDefault(node, List.of())){
            if(visited.add(neighbor)){
                queue.add(neighbor);
            }
        }
    }
    return order;
  }

  /*
          1
      /   \
     2     3
   /  \     \
  4    5     6
  This is an example of BFS traversal of the graph starting from node 1.
  The BFS order will be: [1, 2, 3, 4, 5, 6]

  */

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
