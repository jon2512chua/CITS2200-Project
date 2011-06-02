
import java.util.Iterator;

/**
 * Test for SnapShotIterator
 * @author Jonathan Chua
 */
public class SSITest {
    public static void main(String[] args) {
        SplayFC sfc = new SplayFC();
        sfc.add("hello");
        sfc.add("world");
        sfc.add("how");
        sfc.add("are");
        sfc.add("you");
        System.out.println("Original tree.");
        System.out.println(sfc.toString());

        /* SnapShotIterator */
        System.out.println("SnapShotIterator test.");
        Iterator<String> it = sfc.snapShotIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("");

        /* headSet() */
        System.out.println("headSet() test.");
        SplayFC head = sfc.headSet("hello");
        System.out.println(head.toString());
        System.out.println("");

        /* tailSet() */
        System.out.println("tailSet() test.");
        SplayFC tail = sfc.tailSet("how");
        System.out.println(tail.toString());
        System.out.println("");

        /* subSet() */
        System.out.println("subSet() test.");
        SplayFC sub = sfc.subSet("are", "world");
        System.out.println(sub.toString());
        System.out.println("");
    }
}
