import java.util.*;

import sun.security.jca.GetInstance.Instance;

/**
 * Class that searches a graph using A*
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class AStar extends SearchMethod
{
	private Map<String, Integer> heuristic;
	
	/**
	 * Constructs an instance of the search method
	 * 
	 * @param graph
	 *            graph being searched
	 */
	public AStar(Map<String, List<EndVertex>> graph, Map<String, Integer> heuristic)
	{
		super(graph);
		this.heuristic = heuristic;
	}
	
	//Node used for AStar algorithm
	static class Node {
		
	    Node parent; //the node we came from
	    
	    int cost;
	    int finalCost;
	    String abbr;
	}
	
	//data structures used
	static PriorityQueue<Node> open;
	static Map<String, Node> openNodes;
	static Map<String, Integer> closed;
	private List<String> path;

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
		//set up priorityqueue that sorts based on final cost
		open = new PriorityQueue<>((Object o1, Object o2) -> {
			Node n1 = (Node)o1;
			Node n2 = (Node)o2;
			
			return n1.finalCost<n2.finalCost?-1:
				n1.finalCost>n2.finalCost?1:0;
		});
		closed = new HashMap<String, Integer>();
		openNodes = new HashMap<String, Node>();
		path = new LinkedList<String>();
		
		//set up start vertex as a node
		Node startNode = new Node();
		startNode.finalCost = 0;
		startNode.cost = 0;
		startNode.abbr = startVertex;
		
		//add start node to both open lists
		open.add(startNode);
		openNodes.put(startNode.abbr, startNode);
		
		Node current;		
		while(true)
		{
			//pop the least cost final node in the open list
			current = open.poll();
			if(current==null) {
				break;
			}
			
			System.out.println("Expanding " + current.abbr);
			
			closed.put(current.abbr, 1);
			openNodes.remove(current.abbr);
			
			if(current.abbr.equals(endVertex)) {
				int finalCost = 0;
				System.out.println("Found path");
				//set up path
				while(true) {
					if(current==null) {
						return finalCost;
					}
					path.add(current.abbr);
					finalCost += current.cost;
					
					current = current.parent;
				}
			}
			
			//loop through each neighbor of current node
			for(int i = 0; i < getGraph().get(current.abbr).size(); i++)
			{
				String n = getGraph().get(current.abbr).get(i).name;
				
				//if neighbor is in closed skip to next neighbor			
				if(!closed.containsKey(n)) {
					System.out.println(n);
					
					//if new path to neighbor is shorter or neighbor not in open
					int tempNeighborFinalCost = getGraph().get(current.abbr).get(i).cost
							+ heuristic.get(n) + current.finalCost;
					if(!open.contains(n)) {
						//System.out.println(n + " not in open, adding");
						
						//set final cost of n
						Node t = new Node();
						t.abbr = n;
						t.cost = getGraph().get(current.abbr).get(i).cost;
						t.finalCost = tempNeighborFinalCost;
						t.parent = current;
						
						open.add(t);
						openNodes.put(n, t);
					}
					if(open.contains(n)) { //neighbor is in open
						if(tempNeighborFinalCost < openNodes.get(n).finalCost) {
							//update final cost
							Node t = new Node();
							t.abbr = n;
							t.cost =  getGraph().get(current.abbr).get(i).cost;
							t.finalCost = tempNeighborFinalCost;
							t.parent = current;
							openNodes.remove(n);
							openNodes.put(n, t);
							open.remove(n);
							open.add(t);
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
	public String getPath()
	{
		String p = "";
		for(int i =  path.size() - 1; i >= 0; i--) {
			p += path.get(i);
			if(i != 0) {
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
	public String getName()
	{
		return "";
	}
}