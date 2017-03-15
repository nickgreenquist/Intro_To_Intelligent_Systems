/*
Names:
Nick Greenquist
Bernie Cecchini
*/

#include <iostream>

using namespace std;

//Adjacency List code
struct AdjListNode
{
    int dest;
    struct AdjListNode* next;
};

struct AdjList
{
    struct AdjListNode *head;  // pointer to head node of list
};

struct AdjListNode* newAdjListNode(int dest)
{
    AdjListNode *newNode = new AdjListNode;
    newNode->dest = dest;
    newNode->next = NULL;
    return newNode;
}
//end of Adjacency List implementation code

//Graph class code
class Graph
{
    int V;

public:
    Graph(int V);

    struct AdjList* aList;

    void addEdge(int v, int w);
};

Graph::Graph(int V)
{
    this->V = V;

    // Create an array of adjacency lists.  Size of array will be V
    this->aList = new AdjList[V]

    for (int i = 0; i < V; ++i)
    {
        this->aList[i].head = NULL;
    }
}

void Graph::addEdge(int src, int dest)
{
    struct AdjListNode* newNode = newAdjListNode(dest);
    newNode->next = this->aList[src].head;
    this->aList[src].head = newNode;
}

struct node
{
    int s;
    struct node *next;
};

class Queue
{
    private:
        node *rear;
        node *front;

    public:
        Queue();
        void enqueue(int s);
        void dequeue();
        bool isEmpty();
        int top();
};

Queue::Queue()
{
    rear = NULL;
    front = NULL;
}

int Queue::top()
{
    return front->s;
}

bool Queue::isEmpty()
{
    if(front == NULL) {
        return true;
    }
    return false;
}

void Queue::enqueue(int s)
{
    node *temp = new node;
    temp->s = s;
    temp->next = NULL;

    if(front == NULL) {
        front = temp;
    }
    else {
        rear->next = temp;
    }
    rear = temp;
}

void Queue::dequeue()
{
    node *temp = new node;
    if(front == NULL) {
        return;
    }
    else {
        temp = front;
        front = front->next;
        delete temp;
    }
}

int BFS(Graph g, int s, int t, int *v, int *val, int *c)
{
    // Create a queue for BFS
    Queue queue;
    queue.enqueue(s);

    while(!queue.isEmpty())
    {
        s = queue.top();
        queue.dequeue();

        struct AdjListNode* pCrawl = g.aList[s].head;
        while (pCrawl)
        {
            //not visited
            if(v[pCrawl->dest] < 0) {
                val[pCrawl->dest] = val[s]+1;
                c[pCrawl->dest] = c[s];
                v[pCrawl->dest] = 1;
                queue.enqueue(pCrawl->dest);
            }
            else if(val[pCrawl->dest] == val[s] + 1) {
                c[pCrawl->dest] = c[s] + c[pCrawl->dest];
            }
            else if(val[pCrawl->dest] > val[s] + 1) {
                //cout << "Adding edge: " << pCrawl->dest << endl;
                c[pCrawl->dest] = c[s];
                val[pCrawl->dest] = val[s] + 1;
                queue.enqueue(pCrawl->dest);
            }
            pCrawl = pCrawl->next;
        }
    }

    return c[t];
}


int numPaths(Graph g, int n, int s, int t)
{

    int *v = new int[n + 1];
    int *val = new int[n + 1];
    int *c = new int[n +1];
    for(int i = 1; i < n + 1; i++)
    {
        v[i] = -1;
        val[i] = 0;
        c[i] = 0;
    }
    v[s] = 1;
    val[s] = 1;
    c[s] = 1;

    return BFS(g, s, t, v, val, c);
}

int main()
{
    //get the maze from input
    int n;
    cin >> n;
    int m;
    cin >> m;
    int s;
    cin >> s;
    int t;
    cin >> t;

    //Set up Adjacency List - O(n + m)
    Graph g(n+1);
    for(int i = 0; i < m; i++)
    {
        int vs;
        cin >> vs;
        int vd;
        cin >> vd;
        g.addEdge(vs, vd);
        g.addEdge(vd, vs);
    }

    cout << numPaths(g, n, s, t) << endl;

    return 0;
}

/*
Corresponding Pseudo Code
Struct AdjListNode
1. Int dest
2. AdjListNode *next
Struct AdjList
1. AdjListNode *head
Struct AdjListNode* newAdjListNode(int dest)
1. create s a newNode and hooks up dest and next attributes and returns
Graph::Graph(int V)
1. Set this->V to V
2. Iniitialize a new AdjList of size V and set to this->aList
3. Loop i from 0 to V
1. Set every this->aList[i].head to NULL
Graph::addEdge(int src, int dest)
1. AdjListNode* newNode = newAdjListNode(dest)
2. Set newNode->next to this->aList[src].head
3. Set this->aList[src].head to newNode
Queue implementation
1. Enqueue, dequeue, isEmpty, and top functions
BFS(Graph g, int s, int t, int *v, int *val, int *c)
1. Create queue and push s
2. While queue is not empty do
1. S = queue.top
2. Queue.dequeue
3. Initialize AdjListNode pCrawl to the head of the given s in the graph ‘g’ aList
 4. While pCrawl
1. If v[pCrawl->dest] < 0
1. val[pCrawl->dest] = val[s]+1
2. c[pCrawl->dest] = c[s]
3. v[pCrawl->dest] = 1
4. queue.enqueue(pCrawl->dest)
 2. Else if val[pCrawl->dest] == val[s] + 1
 1. c[pCrawl->dest] = c[s] + c[pCrawl->dest]
 3. Else if val[pCrawl->dest] > val[s] + 1
 1. c[pCrawl->dest] = c[s]
 2. val[pCrawl->dest] = val[s] + 1
 3. queue.enqueue(pCrawl->dest)
 4. pCrawl = pCrawl->next
 3. return c[t]
numPaths(Graph g, int n, int s, int t)
 1. Initialize an int array ‘v’ of size n+1
 2. Initialize an int array ‘val’ of size n+1
 3. Initialize an int array ‘c’ of size n+1
 4. Loop i from 1 to n-1
1. v[i] = -1
2. val[i] = 0
3. c[i] = 0
 5. v[s] = 1
 6. val[s] = 1
 7. c[s] = 1
 8. return BFS(g, s, t, v, val, c)
main()
1. Read in number of vertices and edges and set to int n and int m
2. Initialize graph ‘g’ with n+1 vertices
3. Read in each edge one at a time, setting int vs to the start vertex and int vd to the
destination vertex
4. Add the edge from vs to vd to the graph
5. Add the edge from vd to vs to the graph
6. Call numPaths(g, n, s, t)
7. Return 0
Informal Description of the Algorithm
This algorithm calculates the number of paths from a given source to a given destination
in a given graph that are of the minimum length. With each vertex x we associate two values
count and val, count is the number of shortest path from the given source to x, and val is the
shortest distance from the given source to x. We also maintain a visited variable telling whether
a node is arrived at for the first time of not. We then apply the usual BFS algorithm. If a node is
visited first time it has only one path from the source until now via u, so the shortest path to v is
1 plus the shortest path to u, and the number of ways to reach v via the shortest path is same
as count[u]. If v has already been visited, which means there was some other path to v, then we
have three possible conditions. If the current val[v] which is the distance to v by some other path
is equal to val[u]+1, then the shortest distance to v remains same, but the number of path
increases by number of paths reaching u. If the current path of reaching v is smaller than the
previous value of val[v], then val[v] is stored, and the current path and count[v] are also
updated. The final case is that if the current path has a distance greater than the previous path,
then there is no need to change the values of val[v] and count[v]. We will do this algorithm until
BFS is complete. After completion, val[dest] will contain the shortest distance from the given
source, and count[dest] will contain the number of ways to get from the given source to the
given destination.
Reasoning of Correctness
Using the BFS algorithm with modifications is the key to solving this problem. Because
we are using the BFS algorithm, we know that given the modifications we are always
guaranteed to find the shortest path from a source to a destination. An example of a more
sophisticated version of BFS is Dijkstra's algorithm. On our track to find the shortest
path between two nodes using our modified BFS, we must examine all possible paths to
ensure we have found the one that gives the shortest path. During our search, we
simply maintain associated arrays that store if a node was visited, the length of the path
to get there, and how many ways there are to get there. By checking conditions along
the way, we can ensure that we only store relevant information. This means that even
though there might be multiple ways to get to a node, we only want to store the number
of way to get to the node that are of the minimum length. By the time we finish out BFS,
the final node’s corresponding values will be stored in the associated arrays at the given
node’s corresponding index.
Running Time Estimate (with reasoning)​ - Constraint: O(n+m)
This algorithm is O(n+m). Because the heart of solving this problem lies within a BFS
which is O(n+m), this implementation is subject to the constraint of O(n+m). The only addition to
our search is maintaining associated arrays that we can check conditions as we go to verify that
the optimal result that we are storing at each corresponding index is correct. This way at the end
when we are looking to find our result, we only need to reference a node’s corresponding index
to retrieve the information. Maintaining the arrays and checking the conditions to maintain the
arrays, are operations that take constant time. Getting input is O(n+m) because we read in a
single number for each vertex and each edge. Setting up the Graph’s aList is also O(n+m)
because we call addEdge for every edge. Finally the BFS takes O(n+m) time, making the total
running time no greater than O(n+m).

*/
