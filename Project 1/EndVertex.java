/**
 * Class that stores components of a vertex together for easier processing
 * 
 * @author Nicholas Greenquist
 * @author Alex Hedges
 */
public class EndVertex implements Comparable<EndVertex>
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
	 * @return -1 if this EndVertex comes before the other, 1 if this EndVertex
	 *         comes after the other, and 0 if they come at the same rank. This
	 *         order is by increasing cost, and then increasing name.
	 */
	@Override
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
	 * Runs in O(1). This was simply written for completion's sake and possibly
	 * testing.
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
	 * Runs in O(1). This was simply written for completion's sake and possibly
	 * testing.
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
	 * Runs in O(1). This was simply written for completion's sake and possibly
	 * testing.
	 */
	@Override
	public String toString()
	{
		String output = "{name: " + name + ", cost: " + cost + "}";
		return output;
	}
}