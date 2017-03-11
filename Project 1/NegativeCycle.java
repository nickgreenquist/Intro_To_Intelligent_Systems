import java.util.Scanner;
import java.util.function.ToIntFunction;

/**
 * Determines whether a directed weight graph with exactly one negative edge has
 * a negative weight cycle in O(n^2) time.
 * 
 * @author Alex Hedges
 */
public class NegativeCycle
{
	/**
	 * Read edges into adjacency list and determines whether a directed weight
	 * graph with exactly one negative edge has a negative weight cycle
	 * 
	 * @param args
	 *            none used
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in); // Reads in inputs
		int n = in.nextInt();
		int m = in.nextInt();

		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] graph = (LinkedList<Integer>[]) new LinkedList[n
				+ 1];
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] weights = (LinkedList<Integer>[]) new LinkedList[n
				+ 1];
		for (int i = 1; i <= n; i++)
		{
			graph[i] = new LinkedList<Integer>();
			weights[i] = new LinkedList<Integer>();
		}
		for (int i = 1; i <= m; i++)
		{
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();

			graph[u].appendValue(v);
			weights[u].appendValue(w);
		}

		in.close();

		boolean negWeightCycleExists = negativeWeightCycleExists(graph, weights,
				n);
		if (negWeightCycleExists)
		{
			System.out.println("YES");
		} else
		{
			System.out.println("NO");
		}
	}

	/**
	 * Determines whether a directed weight graph with exactly one negative edge
	 * has a negative weight cycle
	 * 
	 * @param graph
	 *            adjacency list of connections
	 * @param weights
	 *            mirrors adjacency list with weights
	 * @param n
	 *            number of vertices
	 * @return true if the graph has a negative weight cycle
	 */
	public static boolean negativeWeightCycleExists(LinkedList<Integer>[] graph,
			LinkedList<Integer>[] weights, int n)
	{
		// Tests each edge and stores the one with a negative weight
		int negativeWeight = 0;
		int source = 0;
		int destination = 0;
		for (int i = 1; i <= n; i++)
		{
			graph[i].goToFront();
			weights[i].goToFront();
			while (graph[i].hasNext())
			{
				if (weights[i].curValue() < 0)
				{
					negativeWeight = weights[i].curValue();
					source = graph[i].curValue();
					destination = i;
				}
				graph[i].next();
				weights[i].next();
			}
		}

		// Compute all shortest paths from the end vertex of the negative weight
		// edge
		int[] distances = dijkstra(graph, weights, source, n);

		// Find the shortest path to the start vertex of the negative weight
		// edge
		int minPathCost = distances[destination];

		// Return true if the resulting cycle is negative
		return minPathCost + negativeWeight < 0;
	}

	/**
	 * Implementation of Dijkstra's algorithm
	 * 
	 * Runs in O(m log n).
	 * 
	 * @param graph
	 *            adjacency list of connections
	 * @param weights
	 *            mirrors adjacency list with weights
	 * @param source
	 *            source node for
	 * @param n
	 *            number of vertices
	 * @return array of distances from source to each vertex
	 */
	public static int[] dijkstra(LinkedList<Integer>[] graph,
			LinkedList<Integer>[] weights, int source, int n)
	{
		// Let H=∅
		UnsortedArray<Vertex> queue = new UnsortedArray<Vertex>(n,
				v -> v.index);
		int[] distances = new int[n + 1];
		// For every vertex v do
		for (int v = 1; v <= n; v++)
		{
			// dist[v]=∞
			distances[v] = Integer.MAX_VALUE;
		}
		// dist[s]=0
		distances[source] = 0;
		// Update (s)
		dijkstraUpdate(graph, weights, distances, queue, source, n);
		// For i=1 to n-1 do
		for (int i = 1; i <= n - 1; i++)
		{
			// u=extract vertex from H of smallest cost
			int u = queue.extractMin().index;
			// Update(u)
			dijkstraUpdate(graph, weights, distances, queue, u, n);
		}
		// Return dist[]
		return distances;
	}

	/**
	 * Update method for Dijkstra's algorithm
	 * 
	 * @param graph
	 *            adjacency list of connections
	 * @param weights
	 *            mirrors adjacency list with weights
	 * @param distances
	 *            shortest distances from source to each vertex
	 * @param queue
	 *            queue of vertices to check
	 * @param u
	 *            vertex being updated
	 * @param n
	 *            number of vertices
	 */
	public static void dijkstraUpdate(LinkedList<Integer>[] graph,
			LinkedList<Integer>[] weights, int[] distances,
			UnsortedArray<Vertex> queue, int u, int n)
	{
		// For every neighbor v of u
		graph[u].goToFront();
		weights[u].goToFront();
		while (graph[u].hasNext())
		{
			int v = graph[u].curValue();
			int weight = weights[u].curValue();
			// If dist[v]>dist[u]+w(u,v) then
			if (distances[v] > distances[u] + weight)
			{
				// dist[v]=dist[u]+w(u,v)
				distances[v] = distances[u] + weight;
				Vertex neighbor = new Vertex(v, distances[v]);
				// If v not in H then
				if (queue.contains(neighbor))
				{
					queue.decreasePriority(neighbor);
				} else
				{
					// Add v to H
					queue.add(neighbor);
				}
			}
			graph[u].next();
			weights[u].next();
		}
	}

	/**
	 * Class that stores components of a vertex together for easier processing
	 */
	public static class Vertex implements Comparable<Vertex>
	{
		/**
		 * Index of the vertex, for reference purposes
		 */
		public int index;

		/**
		 * Length of shortest path to vertex from source
		 */
		public int cost;

		/**
		 * Constructs an instance of Vertex
		 * 
		 * Runs in O(1).
		 * 
		 * @param index
		 *            index of the vertex
		 * @param cost
		 *            length of shortest path to vertex from source
		 */
		public Vertex(int index, int cost)
		{
			this.index = index;
			this.cost = cost;
		}

		/**
		 * Compares two instances of the Vertex class.
		 * 
		 * Runs in O(1).
		 * 
		 * @param other
		 *            Vertex to compare this one to
		 * @return -1 if this Vertex comes before the other, 1 if this Vertex
		 *         comes after the other, and 0 if they come at the same rank.
		 *         This order is by increasing cost, and then increasing index.
		 */
		public int compareTo(Vertex other)
		{
			if (this.cost < other.cost)
			{
				return -1;
			} else if (this.cost > other.cost)
			{
				return 1;
			} else
			{
				if (this.index < other.index)
				{
					return -1;
				} else if (this.index > other.index)
				{
					return 1;
				} else
				{

					return 0;

				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Runs in O(1). This was simply written for completion's sake and
		 * possibly testing.
		 */
		@Override
		public boolean equals(Object other)
		{
			// Shortcut to ensure reflexive property
			if (other == this)
			{
				return true;
			}

			// Prevents null or incorrect classes from being compared
			if (!(other instanceof Vertex))
			{
				return false;
			}

			// Cast for further operations
			Vertex o = (Vertex) other;
			if (o.index != this.index || o.cost != this.cost)
			{
				return false;
			} else
			{
				return true;
			}
		}

		/**
		 * To ensure hashCodes are equal when two instances of Vertex are equal,
		 * the hash is calculated from the hashes of all data stored in the
		 * queue.
		 * 
		 * Runs in O(1). This was simply written for completion's sake and
		 * possibly testing.
		 */
		@Override
		public int hashCode()
		{
			int result = 17;
			result = 31 * result + index;
			result = 31 * result + cost;
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Runs in O(1). This was simply written for completion's sake and
		 * possibly testing.
		 */
		@Override
		public String toString()
		{
			String output = "{index: " + index + ", cost: " + cost + "}";
			return output;
		}
	}

	/**
	 * A generic unsorted array priority queue
	 *
	 * @param <T>
	 *            type of element to store that is comparable
	 */
	public static class UnsortedArray<T extends Comparable<T>>
	{
		/**
		 * Array of elements
		 */
		private T[] array;

		/**
		 * Maximum array size
		 */
		private int maxSize;

		/**
		 * Function that maps each element to a unique integer from 1 to maxSize
		 */
		private ToIntFunction<T> toIntFunction;

		/**
		 * Constructs an instance of UnsortedArray
		 * 
		 * Runs in O(1).
		 * 
		 * @param maxSize
		 *            maximum array size
		 * @param toIntFunction
		 *            function that maps each element to a unique integer from 1
		 *            to maxSize
		 */

		@SuppressWarnings("unchecked")
		public UnsortedArray(int maxSize, ToIntFunction<T> toIntFunction)
		{
			this.maxSize = maxSize;
			this.toIntFunction = toIntFunction;
			array = (T[]) new Comparable[maxSize + 1];
		}

		/**
		 * Extracts the minimum element from the priority queue
		 * 
		 * Runs in O(log n).
		 * 
		 * @return minimum element
		 */
		public T extractMin()
		{
			T min = null;
			for (int i = 1; i <= maxSize; i++)
			{
				if ((min == null) || (min != null && array[i] != null
						&& min.compareTo(array[i]) < 0))
				{
					min = array[i];
				}
			}
			array[toInt(min)] = null;
			return min;
		}

		/**
		 * Determines whether element is in the priority queue
		 * 
		 * Runs in O(1).
		 * 
		 * @param e
		 *            element to search for
		 * @return true if element is in the priority queue, false otherwise
		 */
		public boolean contains(T e)
		{
			return array[toInt(e)] != null;
		}

		/**
		 * Decreases the priority of the given element in the priority queue
		 * 
		 * Runs in O(1).
		 * 
		 * @param e
		 *            element to decrease priority of
		 */
		public void decreasePriority(T e)
		{
			add(e);
		}

		/**
		 * Adds element to priority queue
		 * 
		 * Runs in O(1).
		 * 
		 * @param e
		 *            element to add to heap
		 */
		public void add(T e)
		{
			array[toInt(e)] = e;
		}

		/**
		 * Determines the integer corresponding to the given element
		 * 
		 * Runs in time of toIntFunction.
		 * 
		 * @param e
		 *            element to convert to map to integer
		 * @return integer corresponding to element
		 */
		private int toInt(T e)
		{
			return toIntFunction.applyAsInt(e);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Not guaranteed to run in O(1), so do not run. This was simply written
		 * for completion's sake and possibly testing.
		 */
		@Override
		public boolean equals(Object other)
		{
			// Shortcut to ensure reflexive property
			if (other == this)
			{
				return true;
			}

			// Prevents null or incorrect classes from being compared
			if (!(other instanceof LinkedList))
			{
				return false;
			}

			// Cast for further operations
			@SuppressWarnings("unchecked")
			UnsortedArray<T> o = (UnsortedArray<T>) other;
			if (o.maxSize != this.maxSize)
			{
				return false;
			}

			// Checks each nodes' value for equality
			for (int i = 1; i <= maxSize; i++)
			{
				if (!o.array[i].equals(this.array[i]))
				{
					return false;
				}
			}
			return true;
		}

		/**
		 * To ensure hashCodes are equal when two instances of Vertex are equal,
		 * the hash is calculated from the hashes of all data stored in the
		 * priority queue, as well as the maximum size of the priority queue.
		 * 
		 * Not guaranteed to run in O(1), so do not run. This was simply written
		 * for completion's sake and possibly testing.
		 */
		@Override
		public int hashCode()
		{
			int result = 17;
			for (int i = 1; i <= maxSize; i++)
			{
				result = 31 * result + array[i].hashCode();
			}
			result = 31 * result + maxSize;
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Not guaranteed to run in O(1), so do not run. This was simply written
		 * for completion's sake and possibly testing.
		 */
		@Override
		public String toString()
		{
			String output = "{maxSize: " + maxSize + ", nodes: [";
			for (int i = 1; i <= maxSize; i++)
			{
				output += array[i];

				if (i != maxSize)
				{
					output += ", ";
				}
			}
			output += "]}";
			return output;
		}
	}

	/**
	 * A generic linked list with easy access to the front and back
	 *
	 * @param <T>
	 *            type of element to store
	 */
	public static class LinkedList<T>
	{
		/**
		 * Start node
		 */
		private Node<T> startNode;

		/**
		 * End node
		 */
		private Node<T> endNode;

		/**
		 * Current node, when iterating
		 */
		private Node<T> cursor;

		/**
		 * Size of the linked list
		 */
		public int size;

		/**
		 * Constructs an instance of LinkedList
		 * 
		 * Runs in O(1).
		 */
		public LinkedList()
		{
			this.startNode = null;
			this.endNode = null;
			this.cursor = null;
			this.size = 0;
		}

		/**
		 * Adds value to front of linked list
		 * 
		 * Runs in O(1).
		 * 
		 * @param value
		 *            value to prepend
		 */
		public void prependValue(T value)
		{
			Node<T> node = new Node<T>(value);
			if (endNode == null)
			{
				endNode = node;
			}
			node.nextNode = startNode;
			startNode = node;
			size++;
		}

		/**
		 * Adds value to end of linked list
		 * 
		 * Runs in O(1).
		 * 
		 * @param value
		 *            value to append
		 */
		public void appendValue(T value)
		{
			Node<T> node = new Node<T>(value);
			if (startNode == null)
			{
				startNode = node;
			} else
			{
				endNode.nextNode = node;
			}
			endNode = node;
			size++;
		}

		/**
		 * Remove value from front of linked list
		 * 
		 * Runs in O(1).
		 * 
		 * @return element stored at front, or null if none exists
		 */
		public T removeFromFront()
		{
			if (startNode == null)
			{
				return null;
			}
			Node<T> node = startNode;
			startNode = node.nextNode;
			size--;
			if (startNode == null)
			{
				endNode = null;
			}
			return node.value;
		}

		/**
		 * Remove value from back of linked list
		 * 
		 * Runs in O(n).
		 * 
		 * @return element stored at back, or null if none exists
		 */
		public T removeFromBack()
		{
			if (endNode == null)
			{
				return null;
			}
			size--;
			Node<T> node;
			if (startNode == endNode)
			{
				node = startNode;
				startNode = null;
				endNode = null;
			} else
			{
				Node<T> curr = startNode;
				while (curr.nextNode != endNode)
				{
					curr = curr.nextNode;
				}
				curr.nextNode = null;
				node = curr;
				endNode = curr;
			}
			return node.value;
		}

		/**
		 * Append given linked list to current linked list. Please note that
		 * this does not merge lists. Referencing the other list might have
		 * inaccurate results and will likely result in an incorrect size.
		 * 
		 * Runs in O(1).
		 * 
		 * @param list
		 *            linked list to append
		 */
		public void appendList(LinkedList<T> list)
		{
			if (size == 0)
			{
				startNode = list.startNode;
			} else
			{
				endNode.nextNode = list.startNode;
			}
			endNode = list.endNode;
			size += list.size;
		}

		/**
		 * Determines whether the cursor is at a valid node
		 * 
		 * Runs in O(1).
		 * 
		 * @return true if current node exists, false otherwise
		 */
		public boolean hasNext()
		{
			return cursor != null;
		}

		/**
		 * Advances the cursor to the next node, if possible
		 * 
		 * Runs in O(1).
		 */
		public void next()
		{
			if (cursor == null)
			{
				return;
			}
			cursor = cursor.nextNode;
		}

		/**
		 * Determines the value of the node the cursor is at
		 * 
		 * Runs in O(1).
		 * 
		 * @return value of the node
		 */
		public T curValue()
		{
			return cursor.value;
		}

		/**
		 * Resets the cursor to the front of the linked list
		 * 
		 * Runs in O(1).
		 */
		public void goToFront()
		{
			cursor = startNode;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Not guaranteed to run in O(1), so do not run. This was simply written
		 * for completion's sake and possibly testing.
		 */
		@Override
		public boolean equals(Object other)
		{
			// Shortcut to ensure reflexive property
			if (other == this)
			{
				return true;
			}

			// Prevents null or incorrect classes from being compared
			if (!(other instanceof LinkedList))
			{
				return false;
			}

			// Cast for further operations
			@SuppressWarnings("unchecked")
			LinkedList<T> o = (LinkedList<T>) other;
			if (o.size != this.size)
			{
				return false;
			}

			// Checks each nodes' value for equality
			o.goToFront();
			this.goToFront();
			while (hasNext())
			{
				if (!o.curValue().equals(this.curValue()))
				{
					return false;
				}
				o.next();
				this.next();
			}
			return true;
		}

		/**
		 * To ensure hashCodes are equal when two instances of Edge are equal,
		 * the hash is calculated from the hashes of all data stored in the
		 * queue.
		 * 
		 * Not guaranteed to run in O(1), so do not run. This was simply written
		 * for completion's sake and possibly testing.
		 */
		@Override
		public int hashCode()
		{
			int result = 17;
			goToFront();
			while (hasNext())
			{
				result = 31 * result + cursor.hashCode();
				next();
			}
			result = 31 * result + size;
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Not guaranteed to run in O(1), so do not run. This was simply written
		 * for completion's sake and possibly testing.
		 */
		@Override
		public String toString()
		{
			String output = "{size: " + size + ", nodes: [";
			goToFront();
			while (hasNext())
			{
				output += curValue();
				next();
				if (hasNext())
				{
					output += ", ";
				}
			}
			output += "]}";
			return output;
		}

		/**
		 * Node for LinkedList
		 *
		 * @param <T>
		 *            type of element to store
		 */
		private static class Node<T>
		{
			/**
			 * Value stored in the node
			 */
			public T value;

			/**
			 * Next node in the linked list
			 */
			public Node<T> nextNode;

			/**
			 * Index of the node, for reference purposes
			 */
			private int index;

			/**
			 * Next index to be assigned, used to ensure indices are unique
			 */
			private static int nextIndex = 1;

			/**
			 * Constructs an instance of Node
			 * 
			 * Runs in O(1).
			 * 
			 * @param value
			 *            value to be stored in the node
			 */
			public Node(T value)
			{
				this.value = value;
				this.nextNode = null;
				this.index = nextIndex;
				nextIndex++;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * Not guaranteed to run in O(1), so do not run. This was simply
			 * written for completion's sake and possibly testing.
			 */
			@Override
			public boolean equals(Object other)
			{
				// Shortcut to ensure reflexive property
				if (other == this)
				{
					return true;
				}

				// Prevents null or incorrect classes from being compared
				if (!(other instanceof Node))
				{
					return false;
				}

				// Cast for further operations
				@SuppressWarnings("unchecked")
				Node<T> o = (Node<T>) other;
				if (o.index != this.index)
				{
					return false;
				} else
				{
					return true;
				}
			}

			/**
			 * To ensure hashCodes are equal when two instances of Node are
			 * equal, the hash is calculated from the hashes of all data stored
			 * in the queue.
			 * 
			 * Not guaranteed to run in O(1), so do not run. This was simply
			 * written for completion's sake and possibly testing.
			 */
			@Override
			public int hashCode()
			{
				int result = 17;
				result = 31 * result + value.hashCode();
				result = 31 * result + index;
				return result;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * Not guaranteed to run in O(1), so do not run. This was simply
			 * written for completion's sake and possibly testing.
			 */
			@Override
			public String toString()
			{
				String output = "{value: " + value + ", nextNode: "
						+ nextNode.index + "}";
				return output;
			}
		}
	}
}