/**
 * BestPath.java
 *
 * This program solves the Travelling Salesman Problem using different heuristics.
 * The TSP is a best-path algorithm which connects a series of different points
 * with the shortest path, returning back to the element it starts at.
 *
 * @author Srikar Devarakonda
 * @version 1.0
 * @since 3/29/2023
 */

import java.awt.Color;

public class BestPath
{
	private ListNode<Point> first;

	public BestPath()
	{
		first = null;
	}
	
	/**
	 * This method counts how many total elements are in the LinkedList
	 * @return	int		number of elements
	 */
	public int size()
	{
		ListNode<Point> temp = first;
		int count = 0;
		while (temp != null)
		{
			count++;
			temp = temp.getNext();
		}
		return count;
	}
	
	/**
	 * This method goes through the linked list and adds the distances
	 * between all the elements. This includes from the last Point to the
	 * first Point (because it is a circular route).
	 * @return	double	total length
	 */
	public double length ( )
	{
		ListNode<Point> temp = first;
		double total = 0;
		while (temp.getNext() != null)
		{
			total += temp.getValue().getDist(temp.getNext().getValue());
			temp = temp.getNext();
		}
		total += temp.getValue().getDist(first.getValue());
		return total;
	}
	
	public void insertPointAtNearestNeighbor(Point p)
	{
		if (first == null)
		{
			first = new ListNode<Point>(p);
			return;
		}
		double min = -1.0;
		int index = 0;
		int maxIndex = 0;
		ListNode<Point> temp = first;
		while (temp != null)
		{
			if (temp.getValue().getDist(p) < min || min == -1.0)
			{
				maxIndex = index+1;
				min = temp.getValue().getDist(p);
			}
			index++;
			temp = temp.getNext();
		}
		insert(p, maxIndex);
		/**
		temp = first;
		while (temp.getNext() != null)
		{
			temp = temp.getNext();
		}
		temp.setNext(first);
		**/
		
	}
	
	/**
	 * This method inserts a given element at a given index in the LinkedList. 
	 * It uses indexes similar to an array.
	 * @param	value	to be inserted
	 * @param	index	location to be inserted
	 */
	private void insert(Point value, int index)
	{
		int i = 0;
		ListNode<Point> temp = first;
		while (i != index-1 && temp.getNext() != null)
		{
			temp = temp.getNext();
			i++;
		}
		if (temp.getNext() == null)
		{
			temp.setNext(new ListNode<Point>(value));
			temp = temp.getNext();
			return;
		}
		ListNode<Point> temp2 = new ListNode<Point>(value, temp.getNext());
		temp.setNext(temp2);
	}
	
	//  You'll need other methods
	
	public void draw ( )
	{
		//int count = 0;
		ListNode<Point> node = first;
		while(node.getNext() != null)
		{
			
			//  Draw the points and connect them
			
			StdDraw.setPenColor(new Color(0,0,255));
			StdDraw.line(node.getValue().getX(),node.getValue().getY(),node.getNext().getValue().getX(), node.getNext().getValue().getY());
			StdDraw.setPenColor(new Color(255,0,0));
			StdDraw.filledCircle(node.getValue().getX(),node.getValue().getY(),4);
			node = node.getNext();
		}
		
		StdDraw.setPenColor(new Color(0,0,255));
		StdDraw.line(node.getValue().getX(),node.getValue().getY(),first.getValue().getX(), first.getValue().getY());
		StdDraw.setPenColor(new Color(255,0,0));
		StdDraw.filledCircle(node.getValue().getX(),node.getValue().getY(),4);
	}
	
    public String toString()
    {
		int count = 0;
		ListNode<Point> node = first;
		String result = new String("");
		while(node != null)
		{
			result += String.format("%4d: %s%n",count,(Point)node.getValue());
			node = node.getNext();
			count++;
		}
		return result;
    }
    
    public void insertPointAtSmallestIncrease(Point p)
    {
		if (first == null)
		{
			first = new ListNode<Point>(p);
			return;
		}
		double len = length();
		ListNode<Point> temp = first;
		int index = 1;
		int minIndex = 1;
		double minDist = -1.0;
		while (temp.getNext() != null)
		{
			if (temp.getNext() == null) break;
			if (p.getDist(temp.getNext().getValue()) + temp.getValue().getDist(p) + len - temp.getValue().getDist(temp.getNext().getValue()) < minDist || minDist == -1.0)
			{
				minDist = p.getDist(temp.getNext().getValue()) + temp.getValue().getDist(p) + len - temp.getValue().getDist(temp.getNext().getValue());
				minIndex = index;
			}
			index++;
			temp = temp.getNext();
		}
		if (temp.getValue().getDist(p) + p.getDist(first.getValue()) +len - temp.getValue().getDist(first.getValue()) < minDist &&
			first.getNext() != null && minDist != -1.0)
			minIndex=index+1;
		
		
		insert(p,minIndex);
	}
}
