
import java.util.Iterator;

/**
 * An implementation of a splay tree of strings with fast cloning and subset extraction.
 * @version 1.1
 */
public class SplayFC implements ISplayFC {

    public Cell top;  // Stores the current root cell
    
    /**
     * Strings that are below (STRING_MIN) and above (STRING_MAX) all the words
     * that will be in a SplayFC tree.  Splaying on these is sometimes useful.
     */
    final static String STRING_MIN = "";
    final static String STRING_MAX = Character.toString(Character.MAX_VALUE);
    
    //Private helper count, used to count number of items in the tree
    private int count = 0;

    public SplayFC() {
        top = null;
    }

    public SplayFC(Cell c) {
        top = c;
    }

    public Cell getTop() {
        return top;
    }

    public void setTop(Cell c) {
        top = c;
    }

    /*
     * See ISplayFC.  You must not modify this method.
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
    // Find the number of valid nodes in the tree, ie number of contents in the tree
    private int num() {
    	return count;
    }

    public Cell splay(Cell c, String k) {
        if (c == null) {
            throw new NullPointerException("splay requires a non-null Cell");
        }

        // To help get you started, the code below shows how to implement
        // the cases for a left "zig-zig" step and a left "zig" step.  You need to
        // add the case for a left "zig-zag" step, and the cases for all three
        // kinds of right step (which mirror the left cases).

        int compareToCKey = k.compareTo(c.key());

        //The following section compares the current cell c.
        if (compareToCKey < 0 && c.lt != null) {                         // Search left
            int compareToLtKey = k.compareTo(c.lt.key());

            if (compareToLtKey < 0 && c.lt.lt != null) {             // left zig-zig step
                Cell ll = splay(c.lt.lt, k);                     // Search recursively
                Cell newRR = cell(c.key(), c.lt.rt, c.rt);                // Rearrange
                return cell(ll.key(), ll.lt, cell(c.lt.key(), ll.rt, newRR));

            } else if (compareToLtKey > 0 && c.lt.rt != null) {    // left zig-zag step

                // Replace this with code similar to the zig-zig step above, but
                // following the zig-zag case in the specification.
                throw new RuntimeException("splay: implementation incomplete");

            } else // left zig step
            {
                return cell(c.lt.key(), c.lt.lt, cell(c.key(), c.lt.rt, c.rt));
            }
        } else if (compareToCKey < 0 && c.rt != null) {                   // Search right
            int compareToRtKey = k.compareTo(c.rt.key());

            if (compareToRtKey < 0 && c.rt.rt != null) {             // right zig-zig step
                Cell rr = splay(c.rt.rt, k);                     // Search recursively
                Cell newLL = cell(c.key(), c.rt.lt, c.lt);                // Rearrange
                return cell(rr.key(), rr.rt, cell(c.rt.key(), rr.lt, newLL));

            } else if (compareToRtKey > 0 && c.rt.lt != null) {    // right zig-zag step

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

    public boolean add(String k) {
        if (top == null) {
            top = cell(k, null, null);
            count ++; 
            return true;
        }
        Cell s = splay(top, k); // now k is the root, and the tree has been rebalanced/sorted, or closest to k, 
        						// ie the node with lt that is less than k and rt that is greater to k 
        if(s.key() == k) {
        	return true; // there is a key s that already contains string k
        } else {
	        if(k.compareTo(s.lt.key()) < 0 && k.compareTo(s.key()) > 0 ) {
	        	Cell sk = cell(k, s.lt, s); // creates a new cell node, where its between left tree of s and s
	        	count ++;
	        	return true;
	        } else {
	        	Cell sk = cell(k, s, s.rt); // creates a new cell node, where its between s and right tree of s
	        	count ++;
	        	return true;
	        }
        }

        /* TODO: You need to fill the rest of this in with code uses s to modify top and
         * then returns appropriately.*/

      //  Iterator snapShot = SnapShotIterator(top); // snapShot at root of tree.
        /* Assume that the snapShot iterator is a cell for now.
         * The following line doesn't work as the iterator doesn't have any methods
         * to find the key.
         */
        // Searching if the string k is in the tree
//        while (snapShot.hasNext() && ((Cell) snapShot).key().compareTo(k) != 0) {
//        	snapShot.next();
//        }
//        return false;
        //throw new RuntimeException("add: implementation incomplete");
    }

    // Implement the rest of the methods of SplayFC below, along with any private methods you use.
    public boolean remove(String k) {
        throw new RuntimeException("remove: implementation incomplete");
    }

    // TODO
    public boolean contains(String k) {
    	// Need iterator at the start of the tree/ root
    	
        throw new RuntimeException("contains: implementation incomplete");
    }
    
    // TODO
    public SplayFC headSet(String k) {
        throw new RuntimeException("headSet: implementation incomplete");
    }

    public SplayFC tailSet(String k) {
        throw new RuntimeException("tailSet: implementation incomplete");
    }

    public SplayFC subSet(String k1, String k2) {
        throw new RuntimeException("subSet: implementation incomplete");
    }

    public SplayFC clone() {
        throw new RuntimeException("clone: implementation incomplete");
    }

//    public Iterator<String> snapShotIterator(Cell root) { 
//    	ssi = root;
//    	return ssi;
//    	//throw new RuntimeException("iterator: implementation incomplete"); 
//    	}

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
    	Cell arr[]  = new Cell[count];
    	
        /*
         * Iterator now starts at the minimum value of the tree
         */
        public SnapShotIterator(Cell root) {
        	ssi = root;
        	
        	if(ssi.key() != null && hasNext()) {
            	while(hasNext()) {
            		arr[i] = ssi; // store the cell into the array 
            		ssi = ssi.lt;
            		i++;
            		
            	}
        	}
            //throw new RuntimeException("iterator: implementation incomplete");
        }

        // You need to implement the following two methods here.
        public boolean hasNext() {
        	return (ssi.lt.key() != null || ssi.rt.key() != null) ;
           // throw new RuntimeException("hasNext: implementation incomplete");
        }

        // Next goes from min to max in that order
        public String next() {
        	// Traverse up to parent and then down to right tree of parent
        	
        	// TODO: use count to find length of all nodes added ie count
        	//  also use array to store the pointers of visited nodes ie to move back up to parent(move up ancestry) 
        	// Now at the bottom of the tree
        	Cell temp = ssi;
        	if(!hasNext()) { // at the end of the sub tree with no sub trees to go down to 
        		ssi = arr[i];
        		
        	}
        	
        	
        	throw new RuntimeException("Reached end of tree, no child to go to");
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
