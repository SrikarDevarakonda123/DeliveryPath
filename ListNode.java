/**
 * ListNode.java
 * 
 * This class provides the blueprint for the elements (objects) in the
 * LinkedList.
 * 
 * @author Srikar Devarakonda
 * @version 1.0
 * @since 3/27/23
*/ 

public class ListNode<E>
{
	/**	This is the data value the ListNode holds **/
	private E value;
	
	/** This is the reference to the next ListNode it points to **/
	private ListNode<E> next;
	
	public ListNode(E v)
	{
		value = v;
		next = null;
	}
	
	public ListNode(E v, ListNode<E> n)
	{
		value = v;
		next = n;
	}
	
	/**
	 * returns value of ListNode
	 * @return	E	value
	 */
	public E getValue()
	{
		return value;
	}
	
	/**
	 * returns the next ListNode this ListNode points too
	 * @return	ListNode<E>	 next ListNode
	 */
	public ListNode<E> getNext()
	{
		return next;
	}
	
	/**
	 * sets the value of this ListNode
	 * @param	E	value
	 */
	public void setValue(E v)
	{
		value = v;
	}
	
	/**
	 * sets the reference to the next ListNode
	 * @param	ListNode<E>	 next ListNode
	 */
	public void setNext(ListNode<E> n)
	{
		next = n;
	}
}
