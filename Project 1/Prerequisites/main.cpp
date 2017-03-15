/*
Names:
Nick Greenquist
Bernie Cecchini
*/

#include<iostream>

using namespace std;

//custom stack implementation code
struct StackElement {
    int digit;
    StackElement* predecessor;
};

StackElement *stack0 = 0;

void push(int x) {
    StackElement *stack1 = new StackElement;
    stack1->digit = x;
    stack1->predecessor = stack0;
    stack0 = stack1;
}

void pop() {
    if(stack0) {
        StackElement *tmp = stack0;
        stack0 = stack0->predecessor;
        delete tmp;
    }
}

int top() {
    if(stack0) {
        int t = stack0->digit;
        return t;
    }
    return 0;
}

bool isEmpty() {
    if(stack0) {
        return false;
    }
    return true;
}
//end of stack implementation code

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

    struct AdjList* aList;

    void topologicalSortUtil(int v, bool visited[]);
public:
    Graph(int V);

    void addEdge(int v, int w);

    int* topologicalSort();

    int findLongestChain(int inOrder[]);
};

Graph::Graph(int V)
{
    this->V = V;

    // Create an array of adjacency lists.  Size of array will be V
    this->aList = new AdjList[V];

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

// Recursive TopSort helper function
void Graph::topologicalSortUtil(int v, bool visited[])
{
    visited[v] = true;

    struct AdjListNode* pCrawl = this->aList[v].head;
    while (pCrawl)
    {
        if(!visited[pCrawl->dest]) {
            topologicalSortUtil(pCrawl->dest, visited);
        }
        pCrawl = pCrawl->next;
    }

    push(v);
}

//Top level sort function - returns the array of the in order vertices - O(n + m)
int* Graph::topologicalSort()
{
    int V = this->V;
    bool *visited = new bool[V];
    for (int i = 0; i < V; i++)
    {
        visited[i] = false;
    }

    for (int i = 0; i < V; i++)
    {
        if (visited[i] == false)
        {
            topologicalSortUtil(i, visited);
        }
    }

    //Create an array that will store the vertices in bottom-up order of how
    //they are stored in the Stack
    int *inOrder = new int[V];
    for(int i = 1; i <= V; i++)
    {
        inOrder[V-i] = top();
        pop();
    }

    return inOrder;
}

//given a topologically sorted graph, will return the diameter of the graph
int Graph::findLongestChain(int *inOrder)
{
    //find longest path
    int S[V];
    for(int j = 0; j < V; j++)
    {
        int v = inOrder[j];

        S[v] = 1;

        struct AdjListNode* pCrawl = this->aList[v].head;
        while (pCrawl)
        {
            if(S[v] <= S[pCrawl->dest] + 1) {
                S[v] = S[pCrawl->dest] + 1;
            }
            pCrawl = pCrawl->next;
        }
    }

    //find max
    int m = 0;
    for(int i = 0; i < V; i++)
    {
        if(S[i] > m) {
            m = S[i];
        }
    }
    return m;
}

//main function that handles creation of the Graph and also calling the helper methods in the
//correct order in order to find the length of the longest prerequisite chain
int prerequisites(int **C, int n)
{
    //Set up Adjacency List - O(n + m)
    Graph g(n+1);
    for(int i = 0; i < n; i++)
    {
        int j = 0;
        while(true)
        {
            if(C[i][j] == 0)
            {
                break;
            }
            g.addEdge(i+1, C[i][j]);
            j++;
        }
    }


    //Topological Sort the Graph - O(n + m)
    int* inOrder = g.topologicalSort();

    //Find Diameter of a DAG when in Top Sorted order - O(n + m)
    int longestChain = g.findLongestChain(inOrder);

    return longestChain;
}


int main()
{
    //get number of classes
    int n;
    cin >> n;

    //set up each classes prereqs and store in a 2D array
    //end of prereqs is signalled by a '0' - O(n + m)
    int **C;
    int line[n];
    int i = 0;
    int temp = -1;
    C = new int *[n];
    for(int j = 0; j < n; j++)
    {
        i = 0;
        while(true)
        {
            cin >> temp;
            line[i] = temp;
            i++;
            if(temp == 0)
            {
                break;
            }
        }

        C[j] = new int[i];
        for(int x = 0; x < i; x++)
        {
            C[j][x] = line[x];
        }
    }


    //call method for intervals with break times
    cout << prerequisites(C, n) << endl;

    return 0;
}

/*
Corresponding Pseudo Code
//Stack implementation - code not shown because redundant
StackElement
Has push, pop, top, and isEmpty functions


//Initialize first stack element
StackElement *stack0 = 0;


Struct AdjListNode
Int dest
AdjListNode *next
Struct AdjList
AdjListNode *head
Struct AdjListNode* newAdjListNode(int dest)
create s a newNode and hooks up dest and next attributes and returns


Graph::Graph(int V)
Set this->V to V
Iniitialize a new AdjList of size V and set to this->aList
Loop i from 0 to V
Set every this->aList[i].head to NULL


Graph::addEdge(int src, int dest)
AdjListNode* newNode = newAdjListNode(dest)
Set newNode->next to this->aList[src].head
Set this->aList[src].head to newNode


Graph::topologicalSortUtil(int v, bool visited[])
Visted[v] = true;
AdjListNode* pCrawl = aList[v].head
While pCrawl is not Null do
If visited[pCrawl->dest] is not true then
topologicalSortUtil(pCrawl->dest, visited)
pCrawl = pCrawl->next
push(v) to Stack


int* Graph::topologicalSort
Int V = this->V
Init new bool *visted array of size V
Set all visited[i] to false from i = 0 to V
For i = 0 to V do
If visited[i] is false then
topologicalSortUtil(i, visited)
Int *inOrder is new int array of size V
Reverse the order of the stack by doing For i = 1 to V do
inOrder[V-i] = top()
pop()
Return inOrder


Int Graph::findLongestChain(int *inOrder)
Int S[V]
For j = 0 to V do
Int v = inOrder[j]
S[v] = 1
AdjListNode* pCrawl = sList[v].head
While pCrawl is not NULL do
If S[v] is < S[pCrawl->dest] + 1 then
S[v] = S[pCrawl->dest] + 1
pCrawl = pCrawl->next
Find max value in S and return


prerequisites(int **C, int n)
Init new Graph g of size n+1
For i = 0 to n do
Int j = 0
while (true)
If C[i][j] is 0 then
Break
g.addEdge(i+1, C[i][j])
J++
Int *inOrder = g.topologicalSort
Int longestChain g.findLongestChain(inOrder)
Return longestChain


The three parts of the heart of the solution:
the verbal description of the contents of the array/table
S[v] =  the maximum length chain of prerequisites from that vertex (class)
the mathematical formula that describes how to compute the entries in the table
S[v] = S[pCrawl->dest] + 1 if S[v] is less than the S[pCrawl->dest] + 1. This means the longest chain from v is the longest chain from one of its prerequisites if the longest chain from that prerequisite + 1 is greater than the current longest chain to get to v
the return value
The max value in S


Informal Description of the Algorithm
	This algorithm has 2 main parts: topological sort on a Graph represented as an Adjacency List, and then a Dynamic algorithm that keeps track of how many prerequisite classes each class has and finds the longest chain to each vertex. We start the algorithm by setting up a stack and also a Graph class that has an adjacency list, and also all the helper methods to add edges and control nodes. Next, we read in the input, stopping whenever we see a ‘0’. We set up the Graph adjacency list from the input we get. Next, we topological sort the graph, returning an array called inOrder that contains the class numbers in topologically sorted order. Next, we pass this array into the findLongestChain function and use a Dynamic loop to to figure out which prerequisite class had the most prerequisite classes of itself. We use Dynamic programming to keep the time complexity within the constraint. After we finish looping through every vertex and every edge in each vertex, we return the maximum value in the Dynamic array.


Reasoning of Correctness
We could use this algorithm without Dynamic programming, but that would be inefficient. The key to this algorithm is the Topological Sort. If you don’t TopSort the graph, you might be calculating longest chain to a class before calculating the longest chain to each of its prerequisites. By TopSorting the graph, we ensure that the order we calculate the longest chain is starting from all vertices that have no prerequisites and then going in order of parent classes, never calculating the longest chain of any class that we haven’t calculated the longest chain of all of its prerequisite classes. Now, let’s look at the Dynamic loop that actually calculates the length. We loop through every vertex and all of its edges to prerequisite classes. We take the maximum length chain of all of its prerequisite classes, and then add 1 to that max. We do this for every vertex, until the end we have the longest prerequisite chain for every class. We finish up by returning the max value in the Dynamic array.
Proof: Any topologically sorted graph will be in order of vertices where no vertex with a prerequisite vertex will appear before it. Therefore, calculating length of chains will always be calculated in order. We will never calculate a class prerequisite length before calculating all of its prerequisites.


Running Time Estimate (with reasoning) - Constraint: O(n+m)
This algorithm is O(n+m). Each of the two parts described above are each O(n+m). Let’s examine each
TopSort - The top sort function is O(n+m). We can prove this because TopSort is essentially a DFS with extra functionality. DFS is O(n+m). TopSort loops through every vertex and every edge of each vertex. This might appear to be O(n*m), but m is in fact the size of all edges. Because we only check each edge once, we can confirm that TopSort is O(n+m).
LongestChain - This function uses a Dynamic loop to calculate lengths. This function loops through every vertex and every edge for each vertex. The key to keeping this O(n+m) time is using a Dynamic lookup table. This table ensures that we only look at every edge ONCE. Because of this, we again have a loop that only looks at each edge once, so m times. And the for loop only looks at each vertex once, so in n time. As a result, this part of the algorithm is O(n+m).
Total - Getting input is O(n+m) because we read in a single number for each vertex and each edge. Setting up the Graph’s aList is also O(n+m) because we call addEdge for every edge. TopSort is O(n+m) as described above. Finally, calculating the longestChain is O(n+m) as described above. In total, the algorithm is O(n+m).

*/
