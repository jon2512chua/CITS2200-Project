
import java.util.Iterator;
import java.util.NoSuchElementException;

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
     * Strings that are below (STRING_MIN) and above (STRING_MAX) are all the words
     * that will be in a SplayFC tree.  Splaying on these is sometimes useful.
     */
    final static String STRING_MIN = "";
    final static String STRING_MAX = Character.toString(Character.MAX_VALUE);

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
                return true;
            }
            if (s.rt == null && k.compareTo(s.key()) > 0) {
                Cell r = cell(k, null, null);
                Cell newS = cell(s.key(), s.lt, r);
                setTop(newS);
                setTop(splay(top, k));
                return true;
            }

            if (s.lt != null && k.compareTo(s.lt.key()) > 0 && k.compareTo(s.key()) < 0) {
                // for when k is between left tree of s and s
                Cell newS = cell(s.key(), null, s.rt);
                Cell newTop = cell(k, s.lt, newS);
                setTop(newTop);
                setTop(splay(top, k));
                return true;
            }
            if (s.rt != null && k.compareTo(s.rt.key()) < 0 && k.compareTo(s.key()) > 0) {
                // for when k is between right tree of s and s
                Cell newS = cell(s.key(), s.lt, null);
                Cell newTop = cell(k, newS, s.rt);
                setTop(newTop);
                setTop(splay(top, k));
                return true;
            }

        }
        // whatever else happens that's not expected
        return false;
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
        // base case of when tree is empty
        if (getTop() == null) {
            return false;
        } else { // When tree is not empty
            if (contains(k)) {
                setTop(splay(top, k));
                if (top.lt == null) {
                    Cell newTop = cell(top.rt.key(), top.rt.lt, top.rt.rt);
                    setTop(newTop);
                    return true;
                } else {
                    Cell l = splay(top.lt, STRING_MAX);
                    Cell newTop = cell(l.key(), l.lt, top.rt);
                    setTop(newTop);
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Check whether a string k appears as a key in the splay tree.  This should
     * be done via splaying, with the tree being updated to the result of splaying
     * regardless of whether the key is found.
     *
     * @param k  The key string to look for.
     * @return true if k is included in the splay tree.
     */
    public boolean contains(String k) {
        setTop(splay(top, k));
        return getTop().key().equals(k);
    }

    /**
     * Extract a splay tree that contains all keys in the current tree that are
     * strictly less than k.  This should be done via splaying on k, updating
     * this SplayFC tree, as well as returning an extracted tree with as much
     * sharing of cells as possible.
     *
     * @param k  The string below which keys should be included.
     * @return The extracted splay tree.
     */
    public SplayFC headSet(String k) {
        SplayFC treeClone;
        splay(this.getTop(), k);
        treeClone = this.clone();
        //treeClone.splay(treeClone.getTop(), k); // cloned tree is now ordered with k at root
        Iterator<String> upi = treeClone.updatingIterator(); // iterator of the clone
        while (upi.next() != null) { // while it does not reach the end of tree
            upi.next(); //must return the element first before deleting it
            upi.remove();
        }
        // Once removal of the right sub tree is done
        return treeClone;
    }

    /**
     * Extract a splay tree that contains all keys in the current tree that are
     * greater than or equal to k.  This should be done via splaying on k, updating
     * this SplayFC tree, as well as returning an extracted tree with as much
     * sharing of cells as possible.
     *
     * @param k The minimum string key to include.
     * @return The extracted splay tree.
     */
    public SplayFC tailSet(String k) {
        SplayFC treeClone;
        splay(this.getTop(), k);
        treeClone = this.clone();
        Iterator<String> ssi = treeClone.snapShotIterator();
        splay(treeClone.getTop(), ssi.next()); // ssi,next returns the lowest element in tree
        Iterator<String> upi = treeClone.updatingIterator(); // upi is now at the root(lowest element)
        while (!upi.next().equals(k)) {
            upi.next();
            upi.remove();
        }
        return treeClone;
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
        SplayFC treeClone;
        treeClone = this.clone();
        Iterator<String> ssi = treeClone.snapShotIterator();
        splay(treeClone.getTop(), ssi.next()); // Lowest element now at root
        Iterator<String> upi = treeClone.updatingIterator();
        while (!upi.next().equals(k1)) {
            upi.next();
            upi.remove();
        }
        // Removed all elements from lowest till to k1
        splay(treeClone.getTop(), k2);
        Iterator<String> upi2 = treeClone.updatingIterator();
        // upi.next(); // to move to the next element first....
        while (upi.next() != null) {
            upi2.next();
            upi2.remove();
        }
        return treeClone;
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
        SplayFC newSplayFC = this;
        return newSplayFC;
    }

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
        return new SnapShotIterator(this);
    }

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

    // Implement the two iterator methods above by implementing the two iterator classes below.
    // Iterator that visits the min to max strings in order
    // Note should not rebalance the tree as ur traversing
    public class SnapShotIterator implements Iterator<String> {

        private SplayFC ssi;
        private Cell c;

        public SnapShotIterator(SplayFC sfc) {
            ssi = sfc.clone();
            ssi.setTop(ssi.splay(ssi.getTop(), STRING_MIN));
            c = cell(null, null, ssi.getTop());
        }

        public boolean hasNext() {
            return (c.rt != null);
        }

        public String next() throws NoSuchElementException {
            if (hasNext()) {
                c = c.rt;
                String temp = c.key();
                ssi.remove(c.key());
                ssi.splay(ssi.getTop(), STRING_MIN);
                return temp;
            } else {
                throw new NoSuchElementException("Reached end of tree, no child to go to.");
            }
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
