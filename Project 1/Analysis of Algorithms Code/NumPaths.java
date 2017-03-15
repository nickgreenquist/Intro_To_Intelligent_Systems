import java.util.Scanner;

/**
 * Computes the number of shortest paths between two points in an undirected
 * graph in O(n + m) time.
 * 
 * @author Alex Hedges
 */
public class NumPaths
{

	/**
	 * Read edges into adjacency list and determines the number of shortest
	 * paths between two points in an undirected graph
	 * 
	 * @param args
	 *            none used
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in); // Reads in inputs
		int n = in.nextInt();
		int m = in.nextInt();

		int s = in.nextInt();
		int t = in.nextInt();

		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] graph = (LinkedList<Integer>[]) new LinkedList[n
				+ 1]; // Create array of linked lists
		for (int i = 1; i <= n; i++)
		{
			graph[i] = new LinkedList<Integer>();
		}
		for (int i = 1; i <= m; i++)
		{
			int vertex1 = in.nextInt();
			int vertex2 = in.nextInt();
			graph[vertex1].appendValue(vertex2);
			graph[vertex2].appendValue(vertex1);
		}

		in.close();

		// Calculate number of shortest paths to each vertex
		int[] seen = new int[n + 1];
		int[] distance = new int[n + 1];
		modifiedBfs(graph, seen, distance, s, n);

		// Output number of shortest paths to target vertex
		System.out.println(seen[t]);
	}

	/**
	 * Runs a breadth-first search on the given graph from the specified vertex.
	 * It stores both the distance and number of shortest paths to each vertex.
	 * 
	 * Runs in O(n + m).
	 * 
	 * @param graph
	 *            graph adjacency list form
	 * @param seen
	 *            array of number of times each vertex is seen on a shortest
	 *            path search
	 * @param distance
	 *            array of shortest distance to each vertex
	 * @param s
	 *            start vertex
	 * @param n
	 *            number of vertices
	 */
	public static void modifiedBfs(LinkedList<Integer>[] graph, int[] seen,
			int[] distance, int s, int n)
	{
		int[] level = new int[n + 1];
		// Initialize each vertex
		for (int v = 1; v <= n; v++)
		{
			seen[v] = 0;
			distance[v] = 1;
			level[v] = -1;
		}
		// Set up queue
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.appendValue(s);
		// Give special values to source vertex
		seen[s] = 1;
		distance[s] = 0;
		level[s] = 0;
		int nextLevel = 1;
		while (queue.size > 0) // While vertices remain in the queue
		{
			int head = queue.removeFromFront(); // Dequeue
			// If new level is reached, advance to next level
			if (level[head] == nextLevel)
			{
				nextLevel++;
			}
			// For every vertex of the dequeued vertex
			graph[head].goToFront();
			while (graph[head].hasNext())
			{
				int u = graph[head].curValue();
				// If the vertex has not been seen before, enqueue it and set
				// its distance and level to 1 more than those of the dequeued
				// vertex
				if (seen[u] == 0)
				{
					queue.appendValue(u);
					distance[u] = distance[head] + 1;
					level[u] = level[head] + 1;
				}
				// If the vertex is in the level after the dequeued vertex, add
				// the number of shortest paths to the dequeued node
				if (level[u] == level[head] + 1)
				{
					seen[u] += seen[head];
				}
				graph[head].next();
			}
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
