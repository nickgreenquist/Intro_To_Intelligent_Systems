import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Finds the shortest path between points on campus using the specified
 * searching method
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class PathFinder
{
	private static final String USAGE = "Usage: PathFinder method_name filename.txt";

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
			System.err.println(USAGE);
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
			// Read until delimiter
			while (!(line = br.readLine()).equals("%%%"))
			{
				String[] tokens = line.split(" ");
				String startVertex = tokens[0];
				String endVertex = tokens[1];
				int cost = Integer.parseInt(tokens[2]);

				if (!graph.containsKey(startVertex)) // Forward edge
				{ // If no list exists, make a new list
					graph.put(startVertex, new LinkedList<EndVertex>());
				}
				graph.get(startVertex).add(new EndVertex(endVertex, cost));

				if (!graph.containsKey(endVertex)) // Backward edge
				{ // If no list exists, make a new list
					graph.put(endVertex, new LinkedList<EndVertex>());
				}
				graph.get(endVertex).add(new EndVertex(startVertex, cost));
			}
			// Read to the end of the file
			while ((line = br.readLine()) != null)
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

		// System.out.println(graph.toString());
		// System.out.println(heuristics.toString());

		SearchMethod searchMethod = null;
		if (method.equals("astar"))
		{
			searchMethod = new AStar(graph, heuristics);
		} else if (method.equals("backtracking"))
		{
			searchMethod = new Backtracking(graph);
		} else if (method.equals("bfs"))
		{
			searchMethod = new BreadthFirstSearch(graph);
		} else
		{
			System.err.println(USAGE);
			return;
		}

		System.out.println("Search method: " + searchMethod.getName());
		Scanner in = new Scanner(System.in);
		System.out.print("Start node: ");
		String startVertex = in.next();
		System.out.print("End node: ");
		String endVertex = in.next();
		in.close();

		int cost = searchMethod.search(startVertex, endVertex);
		if (cost == -1)
		{
			System.out.println(
					"No path was found from the start node to the end node.");
		} else
		{
			System.out.println("Cost: " + cost);
			System.out.println("Path: " + searchMethod.getPath());
		}
	}
}