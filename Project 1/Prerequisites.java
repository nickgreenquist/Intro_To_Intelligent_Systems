import java.util.Scanner;

/**
 * Determines the size of the longest prerequisite chain
 * 
 * @author Alex Hedges
 */
public class Prerequisites
{
	public static final int ARRAY_START_SIZE = 100;

	/**
	 * Read graph into adjacency list and determines the size of the longest
	 * prerequisite chain
	 * 
	 * @param args
	 *            none used
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in); // Reads in inputs
		int n = in.nextInt();

		int[][] graph = new int[n + 1][]; // Create jagged 2D array
		for (int i = 1; i <= n; i++)
		{
			int[] neighbors = new int[ARRAY_START_SIZE]; // Initialize neighbors
			int j = 1;
			int input;
			do
			{
				input = in.nextInt();
				if (input != 0) // If a neighbor remains, store the neighbor
				{
					neighbors[j] = input;
					j++;
				}
				// If too many neighbors to fit in array, double array length
				// and copy over to new array
				if (j > neighbors.length - 5)
				{
					int[] temp = new int[2 * neighbors.length];
					for (int k = 1; k < neighbors.length; k++)
					{
						temp[k] = neighbors[k];
					}
					neighbors = temp;
				}
			} while (input != 0);
			graph[i] = neighbors;
		}

		in.close();

		System.out.println(maxPrereqChain(graph, n));
	}

	/**
	 * Determines the size of the longest prerequisite chain
	 * 
	 * @param graph
	 *            graph of courses
	 * @param n
	 *            number of vertices
	 * @return size of the longest prerequisite chain
	 */
	public static int maxPrereqChain(int[][] graph, int n)
	{
		int[] maxPrereqs = new int[n + 1];
		// Set size of max prereq chain for each vertex to undefined
		for (int i = 1; i <= n; i++)
		{
			maxPrereqs[i] = -1;
		}
		// For each vertex, if not done already, calculate the size of max
		// prereq chain
		for (int i = 1; i <= n; i++)
		{
			if (maxPrereqs[i] == -1)
			{
				dfs(graph, maxPrereqs, i);
			}
		}
		// Check each vertex's max prereq chain to find largest
		int max = Integer.MIN_VALUE;
		for (int i = 1; i <= n; i++)
		{
			max = Math.max(max, maxPrereqs[i]);
		}
		return max;
	}

	/**
	 * Runs a depth-first search through the graph to calculate the max
	 * prerequisite chain
	 * 
	 * @param graph
	 *            graph of courses
	 * @param maxPrereqs
	 *            stored calculations of max prerequisites
	 * @param vertex
	 *            vertex being checked
	 */
	public static void dfs(int[][] graph, int[] maxPrereqs, int vertex)
	{
		int prereq = graph[vertex][1];
		if (prereq == 0) // Base case: chain of size 1 if no prereqs
		{
			maxPrereqs[vertex] = 1;
			return;
		}
		int neighborIndex = 1;
		int max = Integer.MIN_VALUE;
		while (prereq != 0) // For each neighbor
		{
			// Calculate max prereq chain if uncalculated
			if (maxPrereqs[prereq] == -1)
			{
				dfs(graph, maxPrereqs, prereq);
			}
			max = Math.max(max, maxPrereqs[prereq]); // Find maximum
			neighborIndex++;
			prereq = graph[vertex][neighborIndex];
		}
		maxPrereqs[vertex] = max + 1; // Set maximum for vertex
	}
}