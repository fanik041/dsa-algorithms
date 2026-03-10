import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class DFS {
    public static void dfs(List<List<Integer>> adjList, int start) {
        boolean[] visited = new boolean[adjList.size()];
        
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        while(!stack.isEmpty()) {
            int node = stack.pop();

            if(!visited[node]) {
                visited[node] = true;
                System.out.println(node + " ");
                for (int neighbor: adjList.get(node)) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }



    }
}
