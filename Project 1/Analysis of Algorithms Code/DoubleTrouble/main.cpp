/*
Names:
Nick Greenquist
Bernie Cecchini
*/

#include <iostream>

using namespace std;

int stuckSteps = 1000000;
int minSteps = stuckSteps;

struct Pos
{
    int a;
    int b;
};

struct State
{
    Pos pos1;
    Pos pos2;
    int steps;
};

struct node
{
    State s;
    struct node *next;
};

class Queue
{
    private:
        node *rear;
        node *front;

    public:
        Queue();
        void enqueue(State s);
        void dequeue();
        bool isEmpty();
        State top();
};

Queue::Queue()
{
    rear = NULL;
    front = NULL;
}

State Queue::top()
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

void Queue::enqueue(State s)
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

void BFS(char ** C, int ****visited, State s, int A, int B)
{
    // Create a queue for BFS
    Queue queue;
    queue.enqueue(s);

    while(!queue.isEmpty())
    {
        s = queue.top();
        queue.dequeue();

        int moveA = 1;
        int moveB = 0;
        for(int i = 0; i < 4; i++)
        {
            int nextA1 = s.pos1.a;
            int nextB1 = s.pos1.b;
            int nextA2 = s.pos2.a;
            int nextB2 = s.pos2.b;
            bool add = true;

            nextA1 += moveA;
            nextA2 += moveA;
            nextB1 += moveB;
            nextB2 += moveB;

            //Check for escapes
            bool escapeThing1 = (nextA1 < 0 || nextA1 >= A || nextB1 < 0 || nextB1 >= B);
            bool escapeThing2 = (nextA2 < 0 || nextA2 >= A || nextB2 < 0 || nextB2 >= B);
            if(escapeThing1 && escapeThing2) {
                //both have escapee - check if this escape path is shortest path
                minSteps = s.steps + 1;
                return;
            }
            else if(escapeThing1) {
                add = false;
                nextA1 = s.pos1.a;
                nextB1 = s.pos1.b;
            }
            else if(escapeThing2) {
                add = false;
                nextA2 = s.pos2.a;
                nextB2 = s.pos2.b;
            }

            //Check for walls
            bool hitThing1 = (C[nextA1][nextB1] == 'x');
            bool hitThing2 = (C[nextA2][nextB2] == 'x');

            if(hitThing1 && hitThing2) {
                //both hit a wall, don't add newState, not valid move
                add = false;
            }
            else if(hitThing1) {
                nextA1 = s.pos1.a;
                nextB1 = s.pos1.b;
            }
            else if(hitThing2) {
                nextA2 = s.pos2.a;
                nextB2 = s.pos2.b;
            }

            //check for collision
            bool collided = (nextA1 == nextA2 && nextB1 == nextB2);
            if(collided) {
                add=false;
            }

            if(visited[nextA1][nextB1][nextA2][nextB2] > 0) {
                add=false;
            }

            if(add)
            {
                visited[nextA1][nextB1][nextA2][nextB2] = 1;
                State nextState;
                nextState.pos1.a = nextA1;
                nextState.pos1.b = nextB1;
                nextState.pos2.a = nextA2;
                nextState.pos2.b = nextB2;
                nextState.steps = s.steps + 1;
                queue.enqueue(nextState);
            }

            //update movement coordinates
            if(moveA == 1 && moveB == 0) {
                //cout << "trying down" << endl;
                moveA = -1;
                //cout << "trying up" << endl;
            }
            else if(moveA == -1 && moveB == 0) {
                //cout << "trying right" << endl;
                moveA = 0;
                moveB = 1;
            }
            else if(moveA == 0 && moveB == 1) {
                //cout << "trying left" << endl;
                moveB = -1;
            }
        }
    }
}


void doubleTrouble(char **C, int a, int b, Pos thing1, Pos thing2)
{
    State initState;
    initState.pos1.a = thing1.a;
    initState.pos1.b = thing1.b;
    initState.pos2.a = thing2.a;
    initState.pos2.b = thing2.b;
    initState.steps = 0;

    //set up visited state multi-dimensional array
    int ****v;
    v = new int ***[a];
    for(int i = 0; i < a; i++)
    {
        v[i] = new int **[b];
        for(int j = 0; j < b; j++)
        {
            v[i][j] = new int *[a];
            for(int k = 0; k < a; k++)
            {
                v[i][j][k] = new int[b];
                for(int m = 0; m < b; m++)
                {
                    v[i][j][k][m] = -1;
                }
            }
        }
    }
    v[thing1.a][thing1.b][thing2.a][thing2.b] = 0;

    BFS(C,v, initState, a, b);
}

int main()
{
    //get the maze from input
    int a;
    cin >> a;
    int b;
    cin >> b;

    Pos thing1;
    Pos thing2;
    char **C;
    C = new char *[a];
    for(int i = 0; i < a; i++)
    {
        C[i] = new char [b];
        char line[b+1];
        cin >> line;

        for(int j = 0; j < b; j++)
        {
            if(line[j] == '1')
            {
                thing1.a = i;
                thing1.b = j;
            }
            if(line[j] == '2')
            {
                thing2.a = i;
                thing2.b = j;
            }
            C[i][j] = line[j];
        }
    }

    doubleTrouble(C, a, b, thing1, thing2);

    if(minSteps == stuckSteps) {
        cout << "STUCK" << endl;
    }
    else {
        cout << minSteps << endl;
    }

    return 0;
}

/*
Corresponding Pseudo Code
Int stuckSteps = 1000000;
Int minSteps = stuckSteps


Struct Pos
Int a, int b


Struct State
Pos pos1, Pos pos2
Int steps


Queue implementation
Enqueue, dequeue, isEmpty, and top functions


BFS(char **C, int ****visited, State s, int A, int B)
Create queue and push s
While queue is not empty do
S = queue.top
Queue.dequeue
Int moveA = 1, int moveB = 0
For i = 0 to 4 do
Set next positions to s.positions coordinates
Bool add = true
Increment next positions by moveA and moveB
Bool escapeThing1 is true if nextA2 or nextB2 is out of bounds of A*B matrix
Bool escapeThing2 is true if nextA2 or nextB2 is out of bounds of A*B matrix
If both things have escaped, set minSteps to s.steps + 1 and return
If only one thing has escaped, reset that thing’s positions and set add to false
Bool hitThing1 is true if its position is on top of an ‘x’ in the matrix maze
Bool hitThing2 is true if its position is on top of an ‘x’ in the matrix maze
If both things have hit an ‘x’, set add to false
If only one thing has hit an x, reset the next position to s.position
If the things have collided, set add to false
If visited[nextA1][nextB1][nextA2][nextB2] is greater than 0 then set add to false
If add is true then
visited[nextA1][nextB1][nextA2][nextB2] = 1
Set up a newState and set its pos to next positions
Set newState.steps to s.steps + 1
queue.enqueue(newState)
Change the move values so we check left, right, down, and up
doubleTrouble(char **C, int a, int b, Pos thing1, Pos thing2)
Set up initState and set positions to thing1 and thing2 and set steps to 0
Set up 4D visited array named v
Loop from 0 to a, 0 to b, 0 to a, 0 to b, all nested and do
v[i][j][k][m] = -1
Set vof index using thing1 and thing2’s positions to 0
BFS(C, v, initState, a, b)


main()
Read in matrix dimensions and set to int a and int b
Read in input to set up maze and store in 2D array C
Capture the positions of thing1 and thing2
doubleTrouble(C, a, b, thing1, thing2)
If minSteps is unchanged and still equal to stuckSteps then
Print ‘STUCK’
Else print minSteps


Informal Description of the Algorithm
This algorithm uses BFS to search for an exit to the maze. Because both things move together, you can use one BFS call to move both. But, since we use one BFS call, we have to check multiple constraints (as given by the homework description). We start the algorithm by reading in the input and storing the maze and also capturing the positions of thing1 and thing2 (the initial State). Next, we pass this info to our doubleTrouble function. This function sets up a 4D array of size a*b*a*b. We set the visited of the initial state to visited. Now, we call BFS. In this function, we keep track of the states we have visited using a queue. For every valid state we are in, we try to add 4 children. Each of these children is a future state that would be to the left, right, up, or down of the current state. We don’t add this child state if any of the constraints have been validated (see pseudo code). If the next move is valid, we add the future state to the queue. We keep up this cycle until the queue is empty. If both things escape together at anytime during the BFS, we return out of the function. We print the global minSteps variable. If this variable is unchanged, we pring ‘STUCK.’ If it was changed when both things escaped, then we print minSteps (if both things escape together, minSteps is set to that state’s number of steps).


Reasoning of Correctness
This algorithm is correct because it uses BFS to search all possible valid states ‘exploding out’ from the initial state. What this means is we search every valid neighbor from every valid vertex stemming from the init state, keeping track of how many steps it took to get to each valid state. If both things escape the maze boundaries at the same time, we simply return out the function. We do not need to continue searching for more optimal solutions like if we were using DFS. Since we use BFS and visit every valid state adjacent to previous states, only the shortest path will be set as minSteps and printed out.
Proof - We keep track of all visited states using a 4D matrix. This ensures that for every possible position of thing1, thing2 can be in every other valid position and vice versa. This array could contain EVERY possible position of both things. Using BFS, we update this array, ensuring we never visit the same state twice. If we escape at the same time, the steps leading up to every state are ensured to be minimal.


Running Time Estimate (with reasoning) - Constraint: O((ab)^2)
The trick with this problem was understanding that a*b is the total number of vertices in the maze. Worst case, every possible position in the maze is valid. This would lead to ab possible vertices. Because we have TWO things, the total possible worst case valid states is (ab)^2. To set up the visited array, we take (ab)^2 time. Furthermore, using BFS with two things, the worst case scenario would be close to (ab)^2 possible checks. Because we keep track of visited states, we could never explore more than (ab)^2 states. As a result, the entire algorithm is O((ab)^2) time.
Reading Input - O(a*b)
doubleTrouble -  Initializing the visited 4D matrix takes a*b*a*b time, or O((ab)^2) time.
BFS - Checks all valid states until all valid states exhausted or double escape. Can never visit more than (ab)^2 states, so the time is O((ab)^2).
Total - Since setting up the 4D visited array is the most time complex part of the algorithm, the total complexity of the algorithm is O((ab)^2).

*/
