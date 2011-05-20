
import java.util.Iterator;

/**
 * An implementation of a splay tree of strings with fast cloning and subset extraction.
 * @version 1.1
 */
public class SplayFC implements ISplayFC {

    public Cell top;  // Stores the current root cell
    /**
     * Strings that are below (STRING_MIN) and above (STRING_MAX) are all the words
     * that will be in a SplayFC tree.  Splaying on these is sometimes useful.
     */
    final static String STRING_MIN = "";
    final static String STRING_MAX = Character.toString(Character.MAX_VALUE);
    //Private helper count, used to count number of items in the tree
    private int count = 0;

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

                Cell lr = splay(c.lt.rt, k);
                // rearranged parent
                Cell newRRP = cell(c.lt.key(), c.lt.lt, c.lt.rt.lt);
                // rearranged grandparent
                Cell newRRG = cell(c.key(), c.lt.rt.rt, c.rt);
                return cell(lr.key(), newRRP, newRRG);
            } else // left zig step
            {
                return cell(c.lt.key(), c.lt.lt, cell(c.key(), c.lt.rt, c.rt));
            }
        } else if (compareToCKey > 0 && c.rt != null) { // Search right
            int compareToRtKey = k.compareTo(c.rt.key());

            if (compareToRtKey > 0 && c.rt.rt != null) { // right zig-zig step
                Cell rr = splay(c.rt.rt, k); // Search recursively
                Cell newRR = cell(c.key(), c.lt, c.rt.lt); // Rearrange
                return cell(rr.key(), cell(c.rt.key(), newRR, rr.lt), rr.rt);

            } else if (compareToRtKey > 0 && c.rt.lt != null) { // right zig-zag step

                Cell rl = splay(c.rt.lt, k);
                // rearranged parent
                Cell newRRP = cell(c.rt.key(), c.rt.lt.rt, c.rt.rt);
                // rearranged grandparent
                Cell newRRG = cell(c.key(), c.lt, c.rt.lt.lt);
                return cell(rl.key(), newRRG, newRRP);

            } else // right zig step
            {
                return cell(c.rt.key(), cell(c.key(), c.lt, c.rt.lt), c.rt.rt);
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

        }
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

    public Iterator<String> snapShotIterator() {
        return new SnapShotIterator(getTop());
    }

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
            ssi = root;

            if (ssi.key() != null && hasNext()) {
                while (hasNext()) {
                    arr[i] = ssi; // store the cell into the array
                    ssi = ssi.lt;
                    i++;

                }
            }
            //throw new RuntimeException("iterator: implementation incomplete");
        }

        // You need to implement the following two methods here.
        public boolean hasNext() {
            return (ssi.lt.key() != null || ssi.rt.key() != null);
            // throw new RuntimeException("hasNext: implementation incomplete");
        }

        // Next goes from min to max in that order
        public String next() {
            // Traverse up to parent and then down to right tree of parent

            // TODO: use count to find length of all nodes added ie count
            //  also use array to store the pointers of visited nodes ie to move back up to parent(move up ancestry)
            // Now at the bottom of the tree
            Cell temp = ssi;
            if (!hasNext()) { // at the end of the sub tree with no sub trees to go down to
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
