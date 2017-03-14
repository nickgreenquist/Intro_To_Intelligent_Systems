/*
Names: Nick Greenquist and Bernie Cecchini
*/

#include <iostream>

using namespace std;

int INT_MAX = 1000000;

// A utility function to find the vertex with minimum distance value
int minDistance(int dist[], bool visited[], int V)
{
   // Initialize min value
   int min = INT_MAX;
   int min_index;

   for (int v = 0; v < V; v++)
   {
        if (visited[v] == false && dist[v] <= min)
        {
            min = dist[v];
            min_index = v;
        }
   }

   return min_index;
}

//finds shortest path from src vertex to every other vertex
bool dijkstra(int **graph, int src, int V)
{
     int dist[V]; //shortest distance from src to every other vertex

     bool visited[V];

     for (int i = 0; i < V; i++)
     {
        dist[i] = INT_MAX;
        visited[i] = false;
     }

     // Distance of source vertex from itself is always 0
     dist[src] = 0;

     // Find shortest path for all vertices
     for (int count = 0; count < V-1; count++)
     {
       //u will be src in the first iteration because it has been set to 0 while all others are INT_MAX
       int u = minDistance(dist, visited, V);

       visited[u] = true;

       //we want to visit src at the very end so we set this back to false so we end at src
       if(u == src) {
        visited[u] = false;
       }

       // Update dist value of the adjacent vertices of the picked vertex.
       for (int v = 0; v < V; v++)
       {
         if (!visited[v] && graph[u][v] && dist[u] != INT_MAX && dist[u]+graph[u][v] < dist[v])
         {
             dist[v] = dist[u] + graph[u][v];
         }
       }
     }

     if(dist[src] < 0) {
        return true;
     }
     return false;
}

int main()
{
    int V;
    cin >> V;
    V++;
    int E;
    cin >> E;
    int s;

    //set up graph as adjMatrix - O(n^2)
    int **graph;
    graph = new int *[V];
    for(int i = 0; i < V; i++)
    {
        graph[i] = new int[V];
        for(int j = 0; j < V; j++)
        {
            graph[i][j] = 0;
        }
    }

    for(int i = 0; i < E; i++)
    {
        int vs;
        cin >> vs;
        int vd;
        cin >> vd;
        int w;
        cin >> w;
        graph[vs][vd] = w;
        if(w < 0) {
            s = vs;
        }
    }

    if(dijkstra(graph, s, V)) {
        cout << "YES" << endl;
    }
    else {
        cout << "NO" << endl;
    }

    return 0;
}

/*
Corresponding Pseudo Code
Int minDistance(int dist[], bool visited[], int V) - O(n)
Set int min to INT_MAX
Declare int min_index
For v = 0 to V do
If visited[v] is false and dist[v] is less than min then
Min = dist[v]
Min_index = v
Return min_index


Bool dijkstra(int **graph, int src, int V) - O(n^2)
Int dist[V] //shortest distance from src to every other vertex
Bool visited[V] //keep track of visited vertices
For i = 0 to V do
Set dist[i] to INT_MAX
Set visited[i] to false
Dist[src] = 0
For int count = 0 to V-1 do - O(n^2)
Int u = minDistance(dist, visited, V) - O(n)
Visited[u] = true
If u is equal to src then set visited[u] to false //ensures we find cycle from src back to src
For v = 0 to V do - O(n)
If visited[v] is false AND there is an edge from u to v AND dist[u] is not equal to INT_MAX AND dist[u] + graph[u][v] is less than dist[v] ten
Dist[v] = dist[u] + graph[u][v]
If dist[src] is less than 0 then return TRUE
Otherwise return FALSE


Int main()
Read in graph to adjacency matrix **graph - O(n^2)
Capture the src vertex of the negative edge from the input
Call dijkstra function on graph


Informal Description of the Algorithm
	This algorithm uses a trick with dijkstra's algorithm to find all the shortest paths from the source vertex even though there is a negative weight edge in the graph. Recall that dijkstra's cannot work with graphs that contain negative edges because it is greedy. However, this problem was a special case because it involved only ONE negative edge. So, here is how the algorithm is able to use dijkstra’s to solve this problem: check if the graph contains a negative cycle.
	We start by reading in the input and capturing which vertex the negative edge sources from. This is the vertex we are going to use dijkstra's on to find a shortest path. Now, we need to find a cycle back to source, so this is the same as a path from source back to source (but not source to itself directly!). We do this by setting the min distance from source to source (dist[src]) to 0 and all other dist[i] to INT_MAX. Next, we start the dijkstra loop on all the vertices. Because dist[src] will be the minimum at the first iteration, we know we will start at src vertex. The trick here is to set visited[src] back to false at the very beginning so we can find a cycle back to src once we have left it. Other than that, there is no real modification to the standard dijkstra's algorithm. Next, we continue with the algorithm, looping through all the vertices and checking the minimum distances to each and setting our dist[] array accordingly. After the algorithm is finished, we simply return the value that is stored in dist[src] (the minimum distance from src back to src in a cycle).


Reasoning of Correctness
	This algorithm is correct because of a few key facts. The first is that there is only ONE negative edge. Because of this, we know any negative cycle that may exist must contain this edge. Furthermore, we can conclude that any negative cycle will contain the src vertex of this negative edge. Using these two facts, we can conclude that we only need to find the shortest cycle from source back to itself that contains the negative edge and check if the cycle weight is still negative. This means dijkstra’s is the perfect algorithm for this.
	Since we start from the src vertex, and because dijkstra’s is a greedy algorithm, we know that this algorithm will always pick that negative edge from the src because it is the first and shortest option the algorithm will be presented with. We then let the algorithm continue as it normally would. We don’t need to worry about it skipping the negative edge (like it normally might would) because we forced it to pick it first at the start by picking src as the vertex we start from. At the end, the dist[src] value will be the shortest weight cycle from src back to itself, starting with the negative edge as the first edge of that cycle.
	The only other fact to note that proves this works is that we modified dijkstra’s by setting visited[src] back to false after the FIRST iteration so that the cycle from src back to src will actually be checked.


Running Time Estimate (with reasoning) - Constraint: O(n^2)
	Because we use an adjacency matrix to store the graph, reading in the input takes O(n^2) time. Next, dijkstra’s algorithm takes O(n^2) time (where n is the number of vertices in the graph). The only modification we made to the algorithm is setting the visited[src] to false. This does not affect the running time complexity.
Computing the mindistance inside the main loop of dijkstra's takes O(n) time. So, this nested loop takes O(n^2) time. Next, updating the dist[i] value is also O(n) time. Because this is also nested, this entire loop still takes O(n^2) time. Because these two inner O(n) loops occur one after the other, they do not bump up the running time of dijkstra's higher than O(n^2).
	In total, the running time of the entire algorithm is O(n^2).
*/
