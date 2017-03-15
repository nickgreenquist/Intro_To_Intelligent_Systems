import java.util.List;
import java.util.Map;

/**
 * Class that searches a graph using breadth-first search
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class BreadthFirstSearch extends SearchMethod
{
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
	public int search(String startVertex, String endVertex)
	{
	}

	/**
	 * Returns the path from the start to end vertex
	 * 
	 * @return order of vertices in the path
	 */
	public String getPath()
	{
	}

	/**
	 * Returns the name of the searching method
	 * 
	 * @return display name of method
	 */
	public String getName()
	{
	}
}