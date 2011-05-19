
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
            return true;
        }
        Cell s = splay(top, k);

        /* TODO: You need to fill the rest of this in with code uses s to modify top and
         * then returns appropriately.*/

        Iterator snapShot = snapShotIterator();
        /* Assume that the snapShot iterator is a cell for now.
         * The following line doesn't work as the iterator doesn't have any methods
         * to find the key.
         */
        //while (snapShot.hasNext() && snapShot.key().compareTo(k) != 0) {
        //}
        //return false;
        throw new RuntimeException("add: implementation incomplete");
    }

    // Implement the rest of the methods of SplayFC below, along with any private methods you use.
    public boolean remove(String k) {
        throw new RuntimeException("remove: implementation incomplete");
    }

    public boolean contains(String k) {
        throw new RuntimeException("contains: implementation incomplete");
    }

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
    /*
     * TODO: iterator must start at root
     */

    public Iterator<String> snapShotIterator() {
        throw new RuntimeException("iterator: implementation incomplete");
    }

    public Iterator<String> updatingIterator() {
        throw new RuntimeException("iterator: implementation incomplete");
    }

    // Implement the two iterator methods above by implementing the two iterator classes below.
    public class SnapShotIterator implements Iterator<String> {

        // You need to implement the following two methods here.
        public boolean hasNext() {
            throw new RuntimeException("hasNext: implementation incomplete");
        }

        public String next() {
            throw new RuntimeException("next: implementation incomplete");
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
