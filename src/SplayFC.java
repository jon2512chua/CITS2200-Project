
import java.util.Iterator;

/**
 * An implementation of a splay tree of strings with fast cloning and subset extraction.
 * @version 1.1
 */
public class SplayFC implements ISplayFC {

    /**
     * the current root cell
     */
    public Cell top;
    /**
     * Strings that are below (STRING_MIN) and above (STRING_MAX) all the words
     * that will be in a SplayFC tree.  Splaying on these is sometimes useful.
     */
    final static String STRING_MIN = "";
    final static String STRING_MAX = Character.toString(Character.MAX_VALUE);
    //Private helper count, used to count number of items in the tree
    private int count = 0;
    private Cell snapit = top; // iterator with root at the top

    /**
     * Constructs a SplayFC with a null root.
     */
    public SplayFC() {
        top = null;
    }

    /**
     * Constructs a SplayFC with the specified cell as root.
     * @param c the cell to be made root
     */
    public SplayFC(Cell c) {
        top = c;
    }

    /**
     * Get the top cell of the SplayFC tree.
     *
     * @return The top cell
     */
    public Cell getTop() {
        return top;
    }

    /**
     * Set the top cell of the SplayFC tree.
     *
     * @param c The new top cell.
     */
    public void setTop(Cell c) {
        top = c;
    }

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
    public String toString() {
        if (top == null) {
            return "[]\n";
        }
        return top.toString("", "   ", "   ", " -");
    }

    /**
     * A small abbreviation for the Cell constructor, which is handy when you need to
     * construct cells in many different places in your code.
     * @param k The key for the new cell.
     * @param l The left child of the new cell.
     * @param r the right child of the new cell.
     * @return The newly constructed cell.
     */
    private static Cell cell(String k, Cell l, Cell r) {
        return new Cell(k, l, r);
    }

    // Private helper method
<<<<<<< HEAD
    // Find the number of valid nodes in the tree, ie number of contents in the tree
    private int num() {
        return count;
=======
    // Fills the array with the contents of the tree in order from min to max
    private String snap() {
    	while()
>>>>>>> origin/master
    }

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
    public Cell splay(Cell c, String k) {
        if (c == null) {
            throw new NullPointerException("splay requires a non-null Cell");
        }

        int compareToCKey = k.compareTo(c.key());

        //The following section compares the current cell c.
        if (compareToCKey < 0 && c.lt != null) { // Search left
            int compareToLtKey = k.compareTo(c.lt.key());

            if (compareToLtKey < 0 && c.lt.lt != null) { // left zig-zig step
                Cell ll = splay(c.lt.lt, k); // Search recursively
                Cell newRR = cell(c.key(), c.lt.rt, c.rt); // Rearrange
                return cell(ll.key(), ll.lt, cell(c.lt.key(), ll.rt, newRR));

            } else if (compareToLtKey > 0 && c.lt.rt != null) { // left zig-zag step

                // Replace this with code similar to the zig-zig step above, but
                // following the zig-zag case in the specification.
                throw new RuntimeException("splay: implementation incomplete");

            } else // left zig step
            {
                return cell(c.lt.key(), c.lt.lt, cell(c.key(), c.lt.rt, c.rt));
            }
<<<<<<< HEAD
        } else if (compareToCKey > 0 && c.rt != null) { // Search right
            int compareToRtKey = k.compareTo(c.rt.key());

            if (compareToRtKey > 0 && c.rt.rt != null) { // right zig-zig step
                Cell rr = splay(c.rt.rt, k); // Search recursively
                Cell newRR = cell(c.key(), c.lt, c.rt.lt); // Rearrange
                return cell(rr.key(), cell(c.rt.key(), newRR, rr.lt), rr.rt);
=======
        } else if (compareToCKey < 0 && c.rt != null) {                   // Search right
            int compareToRtKey = k.compareTo(c.rt.key());

            if (compareToRtKey < 0 && c.rt.rt != null) {             // right zig-zig step
                Cell rr = splay(c.rt.rt, k);                     // Search recursively
                Cell newLL = cell(c.key(), c.rt.lt, c.lt);                // Rearrange
                return cell(rr.key(), rr.rt, cell(c.rt.key(), rr.lt, newLL));
>>>>>>> origin/master

            } else if (compareToRtKey > 0 && c.rt.lt != null) { // right zig-zag step

                // Replace this with code similar to the zig-zig step above, but
                // following the zig-zag case in the specification.
                throw new RuntimeException("splay: implementation incomplete");

            } else // right zig step
            {
                return cell(c.rt.key(), c.rt.rt, cell(c.key(), c.rt.lt, c.lt));
            }
        } else {
            return c;  // Special cases 1 and 2 in the specification
        }
    }

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
    public boolean add(String k) {
        if (top == null) {
            top = cell(k, null, null);
            count++;
            return true;
        }
        Cell s = splay(top, k);
        if (s.key().equals(k)) {
            return false;
        } else {
<<<<<<< HEAD
            if (s.lt == null && k.compareTo(s.key()) < 0) {
                Cell l = cell(k, null, null);
                Cell newS = cell(s.key(), l, s.rt);
                setTop(newS);
                setTop(splay(top, k));
                count++;
                return true;
            }
            if (s.rt == null && k.compareTo(s.key()) > 0) {
                Cell r = cell(k, null, null);
                Cell newS = cell(s.key(), s.lt, r);
                setTop(newS);
                setTop(splay(top, k));
                count++;
                return true;
            }

            if (k.compareTo(s.lt.key()) > 0 && k.compareTo(s.key()) < 0) {
                // for when k is between left tree of s and s
                Cell newS = cell(s.key(), null, s.rt);
                Cell newTop = cell(k, s.lt, newS);
                setTop(newTop);
                setTop(splay(top, k));
                count++;
                return true;
            } else {
                // for when k is between right tree of s and s
                Cell newS = cell(s.key(), s.lt, null);
                Cell newTop = cell(k, newS, s.rt);
                setTop(newTop);
                setTop(splay(top, k));
                count++;
                return true;
            }

=======
	        if(k.compareTo(s.lt.key()) < 0 && k.compareTo(s.key()) > 0 ) {
	        	Cell sk = cell(k, s.lt, s); // creates a new cell node, where its between left tree of s and s
	        	count ++;
	        	return true;
	        } else {
	        	Cell sk = cell(k, s, s.rt); // creates a new cell node, where its between s and right tree of s
	        	count ++;
	        	return true;
	        }
>>>>>>> origin/master
        }
    }

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
    public boolean remove(String k) {
        throw new RuntimeException("remove: implementation incomplete");
    }

    /**
     * Check whether a string k appears as a key in the splay tree.  This should
     * be done via splaying, with the tree being updated to the result of splaying
     * regardless of whether the key is found.
     *
     * @param k  The key string to look for.
     * @return true if k is included in the splay tree.
     */
    // TODO
    public boolean contains(String k) {
        // Need iterator at the start of the tree/ root

        throw new RuntimeException("contains: implementation incomplete");
    }

    /**
     * Extract a splay tree that contains all keys in the current tree that are
     * strictly less than k.  This should be done via splaying on k, updating
     * this SplayFC tree, as well as returning an extracted tree with as much
     * sharing of cells as possible.
     *
     * @param k The minimum string key to include.
     * @return The exracted splay tree.
     */
    // TODO
    public SplayFC headSet(String k) {
        throw new RuntimeException("headSet: implementation incomplete");
    }

    /**
     * Extract a splay tree that contains all keys in the current tree that are
     * greater than or equal to k.  This should be done via splaying on k, updating
     * this SplayFC tree, as well as returning an extracted tree with as much
     * sharing of cells as possible.
     *
     * @param k  The string below which keys should be included.
     * @return The extacted splay tree.
     */
    public SplayFC tailSet(String k) {
        throw new RuntimeException("tailSet: implementation incomplete");
    }

    /**
     * Extract a splay tree that contains all keys in the current tree that are
     * greater than or equal to k1 and strictly less than k2.
     *
     * @param k1  The minimum string key to include.
     * @param k2  The string below which keys should be included.
     * @return The extracted splay tree.
     */
    public SplayFC subSet(String k1, String k2) {
        throw new RuntimeException("subSet: implementation incomplete");
    }

    /**
     * Create a new SplayFC object that contains the same strings as this object.
     * This method must be fast. (SplayFC = Splay Tree with Fast Cloning) In
     * particular, cells should be shared with the original SplayFC object rather
     * than being copied.
     *
     * @return The created SplayFC object.
     */
    public SplayFC clone() {
        throw new RuntimeException("clone: implementation incomplete");
    }

<<<<<<< HEAD
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
    public Iterator<String> snapShotIterator() {
        return new SnapShotIterator(getTop());
    }
=======
    public Iterator<String> snapShotIterator() { 
    	return new SnapShotIterator(getTop());
    	}
>>>>>>> origin/master

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
    public Iterator<String> updatingIterator() {
        throw new RuntimeException("iterator: implementation incomplete");
    }

    //TODO: iterator must start at root
    // Implement the two iterator methods above by implementing the two iterator classes below.
    // Iterator that vists the min to max strings in order
    // Note should not rebalance the tree as ur traversing
    public class SnapShotIterator implements Iterator<String> {

        private Cell ssi;
        int i = 0;
        Cell arr[] = new Cell[count];

        /*
         * Iterator now starts at the minimum value of the tree
         */
        public SnapShotIterator(Cell root) {
<<<<<<< HEAD
            ssi = root;

            if (ssi.key() != null && hasNext()) {
                while (hasNext()) {
                    arr[i] = ssi; // store the cell into the array
                    ssi = ssi.lt;
                    i++;

                }
            }
=======
        	ssi = root;
        	
        	if(ssi.key() != null && hasNext()) {
            	while(hasNext()) {
            		arr[i] = ssi; // store the cell into the array 
            		ssi = ssi.lt;
            		i++;
            	}
        	}
>>>>>>> origin/master
            //throw new RuntimeException("iterator: implementation incomplete");
        }

        // You need to implement the following two methods here.
        public boolean hasNext() {
            return (ssi.lt.key() != null || ssi.rt.key() != null);
            // throw new RuntimeException("hasNext: implementation incomplete");
        }

        // Next goes from min to max in that order
        public String next() {
<<<<<<< HEAD
            // Traverse up to parent and then down to right tree of parent

            // TODO: use count to find length of all nodes added ie count
            //  also use array to store the pointers of visited nodes ie to move back up to parent(move up ancestry)
            // Now at the bottom of the tree
            Cell temp = ssi;
            if (!hasNext()) { // at the end of the sub tree with no sub trees to go down to
                ssi = arr[i];

            }


            throw new RuntimeException("Reached end of tree, no child to go to");
=======
        	// Traverse up to parent and then down to right tree of parent
        	
        	// TODO: use count to find length of all nodes added ie count
        	//  also use array to store the pointers of visited nodes ie to move back up to parent(move up ancestry) 
        	// Now at the bottom of the tree
        	
        	if(!hasNext()) { // at the end of the sub tree with no sub trees to go down to 
        		Cell temp = ssi;
        		ssi = arr[i-1];
        		return temp.key(); // return the parent
        	}
        	// Up at parent 
        	
        	
        	
        	throw new RuntimeException("Reached end of tree, no child to go to");
>>>>>>> origin/master
        }

        // You should leave the remove method as unsupported.
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public class UpdatingIterator implements Iterator<String> {

        // You need to implement the following two methods here.
        public boolean hasNext() {
            throw new RuntimeException("hasNext: implementation incomplete");
        }

        public String next() {
            throw new RuntimeException("next: implementation incomplete");
        }

        public void remove() {
            throw new RuntimeException("remove: implementation incomplete");
        }
    }
}
