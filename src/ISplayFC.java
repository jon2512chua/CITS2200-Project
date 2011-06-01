import java.util.Iterator;

/**
 * An interface to a set of strings. The SplayFC class must implement this. Do
 * not modify this file or your code may fail all tests during marking.
 * 
 * @version 1.1
 */
public interface ISplayFC {

	/**
	 * Get the top cell of the SplayFC tree.
	 * 
	 * @return The top cell
	 */
	public Cell getTop();

	/**
	 * Set the top cell of the SplayFC tree.
	 * 
	 * @param c The new top cell.
	 */
	public void setTop(Cell c);

	/**
	 * Splay the tree with root c to build a new tree that has the same keys but
	 * has a node that is "nearest" to k at the root. Here "nearest" means the new
	 * root node's key is equal to k if k is in the tree, otherwise there must be
	 * no other key in the tree in between k and the root node's key. (In the
	 * latter case there may be two "nearest" nodes - one less than k and one
	 * greater than k).
	 * 
	 * Splaying is done by returning a new tree rather than modifying the input
	 * tree. The returned tree should generally share some of the existing cells
	 * from the input tree, and should create new cells for the remainder.
	 * 
	 * The exact scheme for splaying that must be implemented is described in the
	 * project specification.
	 * 
	 * @param c The cell at root of the tree to splay.
	 * @param k The key for which a "nearest" key from the tree is moved to the
	 *          root.
	 * @return The root cell of the new tree.
	 */
	public Cell splay(Cell c, String k);

	/**
	 * Insert a specified string key into the splay tree. If the tree is empty,
	 * create a new cell. Otherwise, splay on k and if k is not found replace the
	 * resulting root cell, with key h, by two new cells such that k is the new
	 * root and h is appropriately in either the left or right child.
	 * <p>
	 * 
	 * So, e.g., if h < k the top of the tree is modified as follows (after
	 * splaying on k):
	 * 
	 * <pre>
	 * {@code .
	 *                            /-LTREE 
	 *    /-LTREE              /-h
	 *   h         =>        -k 
	 *    \-RTREE              \-RTREE
	 * }
	 * </pre>
	 * 
	 * Note that this method needs to call the "splay" method, so the splay
	 * method should be written first.
	 * 
	 * @param k
	 *          The string key to insert.
	 * @return true if k was not already in the splay tree.
	 */
	public boolean add(String k);

	/**
	 * Remove a specified string from the splay tree.
	 * This should be done by splaying on k and then splaying on the resulting
	 * left subtree to bring its maximum element to its root.  This is then
	 * rearranged along with the right subtree.
	 * 
	 * There are special cases for when the key is not found and when particular
	 * trees are null - you'll need to figure these out.
	 *  
	 * @param k The string to remove.
	 * @return true if k is was the splay tree.
	 */
	public boolean remove(String k);

	/**
	 * Check whether a string k appears as a key in the splay tree.  This should
	 * be done via splaying, with the tree being updated to the result of splaying
	 * regardless of whether the key is found.
	 * 
	 * @param k  The key string to look for.
	 * @return true if k is included in the splay tree.
	 */
	public boolean contains(String k);

	/**
	 * Extract a splay tree that contains all keys in the current tree that are
	 * strictly less than k.  This should be done via splaying on k, updating
	 * this SplayFC tree, as well as returning an extracted tree with as much 
	 * sharing of cells as possible.
	 * 
	 * @param k  The string below which keys should be included.
	 * @return The exracted splay tree.
	 */
	public SplayFC headSet(String k);

	/**
	 * Extract a splay tree that contains all keys in the current tree that are
	 * greater than or equal to k.  This should be done via splaying on k, updating
	 * this SplayFC tree, as well as returning an extracted tree with as much 
	 * sharing of cells as possible.
	 * 
	 * @param k The minimum string key to include.
	 * @return The extacted splay tree.
	 */
	public SplayFC tailSet(String k);

	/**
	 * Extract a splay tree that contains all keys in the current tree that are
	 * greater than or equal to k1 and strictly less than k2.
	 * 
	 * @param k1  The minimum string key to include.
	 * @param k2  The string below which keys should be included.
	 * @return The extracted splay tree.
	 */
	public SplayFC subSet(String k1, String k2);

	/**
	 * Create a new SplayFC object that contains the same strings as this object.
	 * This method must be fast. (SplayFC = Splay Tree with Fast Cloning) In
	 * particular, cells should be shared with the original SplayFC object rather
	 * than being copied.
	 * 
	 * @return The created SplayFC object.
	 */
	public SplayFC clone();

	/**
	 * Create an iterator that visits the strings in this SplayFC tree in order.
	 * If the tree is updated while the iterator is active, the iterator should
	 * still visit the strings that were in the tree when the iterator was
	 * created.
	 * 
	 * The iterator's remove method should always throw
	 * UnsupportedOperationException
	 * 
	 * The use of this iterator should not result in rebalancing of the original
	 * SplayFC tree.
	 * 
	 * @return The iterator object.
	 * 
	 */
	public Iterator<String> snapShotIterator();

	/**
	 * Create an iterator that visits the strings in this SplayFC tree in order.
	 * If the tree is updated while the iterator is active, the iterator should be
	 * affected by the update. Specifically, each call to next should visit the
	 * least element currently in the tree that is greater than the previously
	 * returned elements, and the hasNext method should be consistent with this.
	 * 
	 * The iterator's remove operation should be implemented. The iterator's next
	 * method should throw java.util.ConcurrentModificationException if remove
	 * has been called on the SplayFC tree since the most recent call to hasNext.
	 * 
	 * The use of this iterator should not result in rebalancing of the original
	 * SplayFC tree.
	 * 
	 * @return The iterator object.
	 * 
	 */
	public Iterator<String> updatingIterator();

	/**
	 * Generates a string representation of a SplayFC tree, formatted to show the
	 * tree structure starting from the root node on the left, going to the leaves
	 * on the right.
	 * <p>
	 * 
	 * You should not modify this method, since it will be used during testing.
	 * 
	 * @return The string representation of the tree.
	 */
	public String toString();
}
