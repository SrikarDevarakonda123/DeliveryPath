/**
 * AdjacencyMatrix.java
 * 
 * This program solves the Travelling Salesman problem
 * using an adjaceny matrix that holds the distances from
 * each point to another. It uses the insert point at smallest
 * increase method to do so.
 * 
 * @author  Srikar Devarakonda
 * @version 1.0
 * @since   4/6/23
 */

//import java.awt.List;
import java.util.ArrayList;

public class AdjacencyMatrix 
{
    /* Adjaceny matrix that represents the lengths from one point to each other.
     * It is read point[row index] to point[column index] is length. */
    // private double[][] matrix = {{0, 1, 3, 2},
    //                          {1, 0, 4, 9},
    //                          {3, 2, 0, 7},
    //                          {2, 9, 8, 0}};

	private double[][] matrix;
    
    /* Represents the first ListNode in the LinkedList. */
    private ListNode<Integer> first;

	/**
	 * This constructor is for if the adjacency matrix has already been constructed
	 * and the instance matrix needs to be matched.
	 * @param m	the matrix to be set
	 */
	public AdjacencyMatrix(double[][] m)
	{
		matrix = m;
	}

	/**
	 * This constructor is for if there is an ArrayList of x&y coordinates (double[2])
	 * that needs to be set into the AdjacencyMatrix
	 * @param m	the arraylist with the points
	 */
	public AdjacencyMatrix(ArrayList<double[]> m)
	{
		matrix = new double[m.size()][m.size()];
		for (int i =0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix.length; j++)
			{
				matrix[i][j] = calcDistance(m.get(i), m.get(j));
				//System.out.print((int)calcDistance(m.get(i), m.get(j)) + " ");
			}
			//System.out.println();
		}
	}

	/**
	 * This is a no args constructor
	 */
	public AdjacencyMatrix() {}

    public static void main(String[] args)
    {
        AdjacencyMatrix am = new AdjacencyMatrix();
        am.run();
    }

    /**
	 * This method counts how many total elements are in the LinkedList
	 * @return	int		number of elements
	 */
    public int size()
	{
		ListNode<Integer> temp = first;
		int count = 0;
		while (temp != null)
		{
            //System.out.println(temp.getValue());
			count++;
			temp = temp.getNext();
		}
		return count;
	}

    /**
     * This method calculates the total length of the path given
     * by the LinkedList first
     * @return  total length
     */
	public double length()
	{
		ListNode<Integer> temp = first;
		double total = 0;
		while (temp.getNext() != null)
		{
            total += matrix[temp.getValue()][temp.getNext().getValue()];
			//total += temp.getValue().getDist(temp.getNext().getValue());
			temp = temp.getNext();
		}
		total += matrix[temp.getValue()][first.getValue()];
		return total;
	}

    /**
     * This method runs the whole program.
     */
    public void run()
    {
        for (int i = 0; i < matrix.length; i++)
        {
            //System.out.println(i);
            insertPointAtSmallestIncrease(i);
        }
        //System.out.println(length());
        //int count = size();
	    printLinkedList();
		System.out.println("\n\n");

    }

    /**
     * This method does the actual insertion of the point into the Linked
     * List making the shortest path.
     * @param p point to insert
     */
    public void insertPointAtSmallestIncrease(int p)
    {
		if (first == null)
		{
			first = new ListNode<Integer>(p);
			return;
		}
		//double len = length();
		ListNode<Integer> temp = first;
		int index = 1;
		int minIndex = 1;
		double minDist = -1.0;
        double temp2;
		while (temp.getNext() != null)
		{
			//if (temp.getNext() == null) break;
            temp2 = matrix[temp.getValue()][p] + matrix[p][temp.getNext().getValue()] - matrix[temp.getValue()][temp.getNext().getValue()];
            if (temp2 < minDist || minDist == -1.0)
			{
				minDist = temp2;
				minIndex = index;
			}
			index++;
			temp = temp.getNext();
		}
        if (matrix[temp.getValue()][p] + matrix[p][first.getValue()] - matrix[temp.getValue()][first.getValue()] < minDist)// && first.getNext()!=null && minDist != -1.0)
			minIndex=index+1;
		
		insert(p,minIndex);
	}

    /**
	 * This method inserts a given element at a given index in the LinkedList. 
	 * It uses indexes similar to an array.
	 * @param	value	to be inserted
	 * @param	index	location to be inserted
	 */
	private void insert(int value, int index)
	{
		// int i = 0;
		// ListNode<Integer> temp = first;
		// while (i != index-1 && temp.getNext() != null)
		// {
		// 	temp = temp.getNext();
		// 	i++;
		// }
		// if (temp.getNext() == null)
		// {
		// 	temp.setNext(new ListNode<Integer>(value));
		// 	temp = temp.getNext();
		// 	return;
		// }
		// ListNode<Integer> temp2 = new ListNode<Integer>(value, temp.getNext());
		// temp.setNext(temp2);


		if (index < 0) 
		{
            System.out.println("Invalid index. Index cannot be negative.");
            return;
        }

		ListNode<Integer> temp = new ListNode<Integer>(value);

		if (index == 0) 
		{
            temp.setNext(first);
			first = temp;
        } 
		else 
		{
            ListNode<Integer> current = first;
            int currentIndex = 0;
            
            while (current != null && currentIndex < index - 1) 
			{
                current = current.getNext();
                currentIndex++;
            }
            
            if (current == null) 
			{
				ListNode<Integer> current1 = first;
				while (current1.getNext()!=null)
				{
					current1 = current1.getNext();
				}
				current1.setNext(temp);
                //System.out.println("Invalid index. Index exceeds the size of the linked list.");
            }
            else
			{
				temp.setNext(current.getNext());
            	current.setNext(temp);
			}
        }
	}

	/**
	 * This method prints out the Linked List which shows the path between all 
	 * the points.
	 */
	public void printLinkedList()
	{
		ListNode<Integer> temp = first;
		while (temp!=null)
		{
			System.out.println(temp.getValue());
			temp = temp.getNext();
		}
	}

	/**
	 * This method returns the linked list with the path
	 * @return	linked list
	 */
	public ListNode<Integer> getFirst()
	{
		return first;
	}

	/**
	 * This method calculates the distance between two points. It assumes
	 * that the only weight is the 2 dimensional distance and no other factors.
	 * @param x	the first coord
	 * @param y	the second coord
	 * @return	double	the distance
	 */
	private double calcDistance(double[] x, double[] y)
	{
		return Math.sqrt(Math.pow(x[0]-y[0],2) + Math.pow(x[1]-y[1],2));
	}
}