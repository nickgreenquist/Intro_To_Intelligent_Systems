import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Class that searches a graph using breadth-first search
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class BreadthFirstSearch extends SearchMethod
{
	private List<String> path = new LinkedList<String>();

	/**
	 * Constructs an instance of the search method
	 * 
	 * @param graph
	 *            graph being searched
	 */
	public BreadthFirstSearch(Map<String, List<EndVertex>> graph)
	{
		super(graph);
	}

	/**
	 * Searches the graph for the path between two vertices
	 * 
	 * @param startVertex
	 *            start vertex for search
	 * @param endVertex
	 *            end vertex for search
	 * @return cost of the resulting path
	 */
	@Override
	public int search(String startVertex, String endVertex)
	{
		Map<String, Integer> distance = new HashMap<String, Integer>();
		Map<String, String> predecessor = bfs(getGraph(), distance, startVertex,
				endVertex);
		if (predecessor == null) // If no path connects the specified vertices
		{
			return -1;
		}
		path.add(endVertex);
		String currentVertex = endVertex;
		while (predecessor.get(currentVertex) != null)
		{
			String previous = predecessor.get(currentVertex);
			path.add(0, previous);
			currentVertex = previous;
		}
		return distance.get(endVertex);
	}

	/**
	 * Runs a breadth-first search on the given graph from the specified vertex.
	 * It stores the distance and predecessor to each vertex.
	 * 
	 * @param graph
	 *            graph in adjacency list form
	 * @param distance
	 *            distance from the start vertex to the given vertex
	 * @param startVertex
	 *            start vertex
	 * @param endVertex
	 *            end vertex
	 * @return distances from the start vertex to every other calculated vertex
	 */
	public Map<String, String> bfs(Map<String, List<EndVertex>> graph,
			Map<String, Integer> distance, String startVertex, String endVertex)
	{
		Map<String, Boolean> visited = new HashMap<String, Boolean>();
		Map<String, String> predecessor = new HashMap<String, String>();
		// Initialize each vertex
		for (String vertex : graph.keySet())
		{
			visited.put(vertex, false);
			distance.put(vertex, Integer.MAX_VALUE);
		}
		// Give special values to source vertex
		visited.put(startVertex, true);
		distance.put(startVertex, 0);
		// Set up queue
		Queue<String> queue = new LinkedList<String>();
		queue.add(startVertex);
		while (!queue.isEmpty()) // While vertices remain in the queue
		{
			String head = queue.remove(); // Dequeue
			for (EndVertex neighbor : graph.get(head))
			{
				if (!visited.get(neighbor.name))
				{
					visited.put(neighbor.name, true);
					distance.put(neighbor.name,
							distance.get(head) + neighbor.cost);
					predecessor.put(neighbor.name, head);
					queue.add(neighbor.name);
				}
				if (neighbor.name.equals(endVertex))
				{
					return predecessor;
				}
			}
		}
		// If no path to the end vertex is found, don't return predecessors
		return null;
	}

	/**
	 * Returns the path from the start to end vertex
	 * 
	 * @return order of vertices in the path
	 */
	@Override
	public String getPath()
	{
		String p = "";
		String delimiter = "";
		for (String vertex : path)
		{
			p += delimiter + vertex;
			delimiter = " -> ";
		}
		return p;
	}

	/**
	 * Returns the name of the searching method
	 * 
	 * @return display name of method
	 */
	@Override
	public String getName()
	{
		return "Breadth-first search";
	}
}