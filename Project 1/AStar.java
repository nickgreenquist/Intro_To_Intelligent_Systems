import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.*;

/**
 * Class that searches a graph using A*
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class AStar extends SearchMethod
{
	private Map<String, Integer> heuristic;
	// data structures used

	private List<String> path;

	/**
	 * Constructs an instance of the search method
	 * 
	 * @param graph
	 *            graph being searched
	 * @param heuristic
	 *            heuristic function for search
	 */
	public AStar(Map<String, List<EndVertex>> graph,
			Map<String, Integer> heuristic)
	{
		super(graph);
		this.heuristic = heuristic;
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
		// set up priority queue that sorts based on final cost
		PriorityQueue<Node> open = new PriorityQueue<>(
				(Object o1, Object o2) -> {
					Node n1 = (Node) o1;
					Node n2 = (Node) o2;

					return n1.finalCost < n2.finalCost ? -1
							: n1.finalCost > n2.finalCost ? 1 : 0;
				});
		Map<String, Integer> closed = new HashMap<String, Integer>();
		Map<String, Node> openNodes = new HashMap<String, Node>();
		path = new LinkedList<String>();

		// set up start vertex as a node
		Node startNode = new Node(startVertex, 0, 0, 0, null);

		// add start node to both open lists
		open.add(startNode);
		openNodes.put(startNode.abbr, startNode);

		Node current;
		while (true)
		{
			// pop the least cost final node in the open list
			current = open.poll();
			if (current == null)
			{
				break;
			}

			//System.out.println("Expanding " + current.abbr);

			closed.put(current.abbr, 1);
			openNodes.remove(current.abbr);

			if (current.abbr.equals(endVertex))
			{
				int finalCost = 0;

				// set up path
				while (true)
				{
					if (current == null)
					{
						return finalCost;
					}
					path.add(current.abbr);
					finalCost += current.baseCost;

					current = current.parent;
				}
			}

			// loop through each neighbor of current node
			for (int i = 0; i < getGraph().get(current.abbr).size(); i++)
			{
				String n = getGraph().get(current.abbr).get(i).name;

				// if neighbor is in closed skip to next neighbor
				if (!closed.containsKey(n))
				{
					// if new path to neighbor is shorter or neighbor not in
					// open
					int tempNeighborFinalCost = getGraph().get(current.abbr)
							.get(i).cost + heuristic.get(n) + current.cost;
					if (!open.contains(n))
					{
						// set final cost of n
						Node t = new Node(n, 
								getGraph().get(current.abbr).get(i).cost, 										//base cost
								current.cost + getGraph().get(current.abbr).get(i).cost,						//cost (g)
								current.cost + getGraph().get(current.abbr).get(i).cost + heuristic.get(n),		//finalCost (f)
								current);

						open.add(t);
						openNodes.put(n, t);
						
						//let's see the neighbors we got from this new node
						//System.out.println(n + ": " + tempNeighborFinalCost);
					}
					if (open.contains(n))
					{ // neighbor is in open
						if (tempNeighborFinalCost < openNodes.get(n).finalCost)
						{
							// update final cost
							Node t = new Node(n, 
									getGraph().get(current.abbr).get(i).cost, 									//base cost
									current.cost + getGraph().get(current.abbr).get(i).cost,					//cost (g)
									current.cost + getGraph().get(current.abbr).get(i).cost + heuristic.get(n), //finalCost (f)
									current);
							openNodes.remove(n);
							openNodes.put(n, t);
							open.remove(n);
							open.add(t);
							
							//let's see the neighbors we got from this new node
							//System.out.println(n + ": " + tempNeighborFinalCost);
						}
					}
				}
			}
		}

		return -1;
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
		for (int i = path.size() - 1; i >= 0; i--)
		{
			p += path.get(i);
			if (i != 0)
			{
				p += " -> ";
			}
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
		return "A*";
	}

	/**
	 * Node used for A* algorithm
	 */
	private static class Node
	{
		public String abbr;
		public int baseCost;
		public int cost;
		public int finalCost;
		public Node parent; // the node we came from

		/**
		 * Create a new Node instance
		 * 
		 * @param abbr
		 *            name of the node
		 * @param cost
		 *            cost of the node
		 * @param finalCost
		 *            find cost of the node
		 * @param parent
		 *            predecessor of the node
		 */
		public Node(String abbr, int bc, int cost, int finalCost, Node parent)
		{
			this.abbr = abbr;
			this.baseCost = bc;
			this.cost = cost;
			this.finalCost = finalCost;
			this.parent = parent;
		}

		/**
		 * {@inheritDoc}
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
			Node o = (Node) other;
			if (!o.abbr.equals(this.abbr) || o.cost != this.cost
					|| o.finalCost != this.finalCost
					|| !o.parent.equals(this.parent))
			{
				return false;
			} else
			{
				return true;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode()
		{
			int result = 17;
			result = 31 * result + abbr.hashCode();
			result = 31 * result + cost;
			result = 31 * result + finalCost;
			result = 31 * result + parent.hashCode();
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString()
		{
			String output = "{abbr: " + abbr + ", cost: " + cost
					+ ", finalCost: " + finalCost + ", parent: " + parent.abbr
					+ "}";
			return output;
		}
	}
}