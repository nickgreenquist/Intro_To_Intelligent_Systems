import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Finds the shortest path between points on campus using the specified
 * searching method
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class PathFinder
{
	/**
	 * Read edges into adjacency list and heuristics into an array, then finds
	 * the shortest path from the source to destination
	 * 
	 * @param args
	 *            The first parameter, method_name, is the name of the searching
	 *            algorithm used. It can be either BFS, backtracking, or A* The
	 *            second parameter, filename.txt, is the name of the text file
	 *            containing graph information.
	 */
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.err.println("Usage: PathFinder method_name filename.txt");
			return;
		}
		String method = args[0];
		String fileName = args[1];

		// Create graph
		Map<String, List<EndVertex>> graph = new HashMap<String, List<EndVertex>>();
		Map<String, Integer> heuristics = new HashMap<String, Integer>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			String line;
			while (!(line = br.readLine()).equals("%%%")) // Read until
															// delimiter
			{
				String[] tokens = line.split(" ");
				String startVertex = tokens[0];
				String endVertex = tokens[1];
				int cost = Integer.parseInt(tokens[2]);
				if (!graph.containsKey(startVertex)) // If no list exists, make
														// a new list
				{
					graph.put(startVertex, new LinkedList<EndVertex>());
				}
				graph.get(startVertex).add(new EndVertex(endVertex, cost));
			}
			while ((line = br.readLine()) != null) // Read to the end of the
													// file
			{
				String[] tokens = line.split(" ");
				String vertex = tokens[0];
				int cost = Integer.parseInt(tokens[1]);
				heuristics.put(vertex, cost);
			}
		} catch (FileNotFoundException e)
		{
			System.err.println("The given file does not exist");
			e.printStackTrace();
			return;
		} catch (IOException e)
		{
			System.err.println("There are problems reading the file");
			e.printStackTrace();
			return;
		}

		System.out.println(graph.toString());
		System.out.println(heuristics.toString());
	}

	/**
	 * Class that stores components of a vertex together for easier processing
	 */
	public static class EndVertex implements Comparable<EndVertex>
	{
		/**
		 * Name of the vertex
		 */
		public String name;

		/**
		 * Distance from the start vertex of an edge to this vertex
		 */
		public int cost;

		/**
		 * Constructs an instance of EndVertex
		 * 
		 * Runs in O(1).
		 * 
		 * @param name
		 *            name of the vertex
		 * @param cost
		 *            distance from the start vertex of an edge to this vertex
		 */
		public EndVertex(String name, int cost)
		{
			this.name = name;
			this.cost = cost;
		}

		/**
		 * Compares two instances of the EndVertex class.
		 * 
		 * Runs in O(1).
		 * 
		 * @param other
		 *            EndVertex to compare this one to
		 * @return -1 if this EndVertex comes before the other, 1 if this
		 *         EndVertex comes after the other, and 0 if they come at the
		 *         same rank. This order is by increasing cost, and then
		 *         increasing name.
		 */
		public int compareTo(EndVertex other)
		{
			if (this.cost < other.cost)
			{
				return -1;
			} else if (this.cost > other.cost)
			{
				return 1;
			} else
			{
				return this.name.compareTo(other.name);
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
			if (!(other instanceof EndVertex))
			{
				return false;
			}

			// Cast for further operations
			EndVertex o = (EndVertex) other;
			if (!o.name.equals(this.name) || o.cost != this.cost)
			{
				return false;
			} else
			{
				return true;
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Runs in O(1). This was simply written for completion's sake and
		 * possibly testing.
		 */
		@Override
		public int hashCode()
		{
			int result = 17;
			result = 31 * result + name.hashCode();
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
			String output = "{name: " + name + ", cost: " + cost + "}";
			return output;
		}
	}
}