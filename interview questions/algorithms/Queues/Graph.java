import java.util.*;

class Graph {

    //declare variables
    private int vertices;
    private List<List<Integer>> adjList;

    public Graph(int vertices){
        this.vertices = vertices;
        adjList = new ArrayList<>();
        for(int i=0; i<vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination) {
        adjList.get(source).add(destination);
        adjList.get(destination).add(source);
    }


    public void bfs(int start) {
    boolean[] visited = new boolean[vertices];
    Queue<Integer> queue = new LinkedList<>();
    
    visited[start] = true;
    queue.add(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();

        System.out.println(node);

        for (int neighbor : adjList.get(node)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.add(neighbor);
                
            }
        }

    }
}
}

/*

Graph representation:

Vertices = {0,1,2,3,4}

Edges = {(0,1), (0,2), (1,3), (1,4)}

0 -> 1 2
1 -> 0 3 4
2 -> 0
3 -> 1
4 -> 1

0 ---- 1
|      |
2      3
       |
       4

*/