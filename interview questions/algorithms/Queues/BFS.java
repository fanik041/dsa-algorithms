import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class BFS {
    public static void bfs(List<List<Integer>> adj, int start) {

        boolean[] visited = new boolean[adj.size()];

        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {

            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : adj.get(node)) {

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }
}
