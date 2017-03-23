import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class that searches a graph using backtracking
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class Backtracking extends SearchMethod
{
	private List<String> path = new LinkedList<String>();

	/**
	 * Constructs an instance of the search method
	 * 
	 * @param graph
	 *            graph being searched
	 */
	public Backtracking(Map<String, List<EndVertex>> graph)
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
	public int search(String startVertex, String endVertex)
	{
		Map<String, Boolean> visited = new HashMap<String, Boolean>();
		Map<String, Integer> distance = new HashMap<String, Integer>();
		Map<String, String> predecessor = new HashMap<String, String>();
		for (String vertex : getGraph().keySet())
		{
			visited.put(vertex, false);
			distance.put(vertex, Integer.MAX_VALUE);
		}
		distance.put(startVertex, 0);
		if (!dfs(getGraph(), visited, distance, predecessor, startVertex,
				endVertex))
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
	 * Runs a depth-first search through the graph to calculate the max
	 * prerequisite chain
	 * 
	 * @param graph
	 *            graph of courses
	 * @param vertex
	 *            vertex being checked
	 */
	public boolean dfs(Map<String, List<EndVertex>> graph,
			Map<String, Boolean> visited, Map<String, Integer> distance,
			Map<String, String> predecessor, String currentVertex,
			String endVertex)
	{
		boolean found = false;
		visited.put(currentVertex, true);
		if (currentVertex.equals(endVertex))
		{
			return true;
		}
		for (EndVertex neighbor : graph.get(currentVertex))
		{
			if (!visited.get(neighbor.name))
			{
				distance.put(neighbor.name,
						distance.get(currentVertex) + neighbor.cost);
				predecessor.put(neighbor.name, currentVertex);
				if (dfs(graph, visited, distance, predecessor, neighbor.name,
						endVertex))
				{
					found = true;
					break;
				}
			}
		}
		return found;
	}

	/**
	 * Returns the path from the start to end vertex
	 * 
	 * @return order of vertices in the path
	 */
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
	public String getName()
	{
		return "Backtracking";
	}
}