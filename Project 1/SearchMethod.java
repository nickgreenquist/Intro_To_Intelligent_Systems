import java.util.List;
import java.util.Map;

/**
 * An abstract class for different search methods
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public abstract class SearchMethod
{
	/**
	 * Graph being searched
	 */
	private Map<String, List<EndVertex>> graph;

	/**
	 * Constructs an instance of the search method
	 * 
	 * @param graph
	 *            graph being searched
	 */
	public SearchMethod(Map<String, List<EndVertex>> graph)
	{
		this.graph = graph;
	}

	/**
	 * Getter for stored graph
	 * 
	 * @return graph
	 */
	public Map<String, List<EndVertex>> getGraph()
	{
		return graph;
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
	public abstract int search(String startVertex, String endVertex);

	/**
	 * Returns the path from the start to end vertex
	 * 
	 * @return order of vertices in the path
	 */
	public abstract String getPath();

	/**
	 * Returns the name of the searching method
	 * 
	 * @return display name of method
	 */
	public abstract String getName();
}