import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * A basic testing program for the 2011 CITS2200 project.
 * @version 1.1
 */
public class Test {

	/**
	 * Print the specified SplayFC tree.
	 * @param t The tree of interest.
	 * @param name The string representing the variable name of t.
	 */
	public static void print(SplayFC t, String name) {
		System.out.println(name + " =");
		System.out.println(t);
	}

	/**
	 * Determine the string representation of a specified string.
	 * @param s The string of interest.
	 * @return The string representation of s.
	 */
	private static String show(String s) {
		return '\"' + s + '\"';
	}

	/**
	 * Test the add method of a specified SplayFC tree.
	 * @param t The tree of interest.
	 * @param name The string representing the variable name of t.
	 * @param s The string to add.
	 */
	public static void test_add(SplayFC t, String name, String s) {
		System.out.println(name + ".add(" + show(s) + ")");
		t.add(s);
		print(t,name);  // Consider commenting this out while debugging other parts
	}
	
	/**
	 * Test the contains method of a specified SplayFC tree.
	 * @param t The tree of interest.
	 * @param name The string representing the variable name of t.
	 * @param s The string to test membership for.
	 */
	public static void test_contains(SplayFC t, String name, String s) {
		System.out.println(name + ".contains(" + show(s) + ") = " + t.contains(s));
		print(t,name);  // Consider commenting this out while debugging other parts
	}

	/**
	 * Test both iterators of a specified SplayFC.
	 * @param t The SplayFC of interest.
	 * @param name The string representing the variable name of t.
	 */
	public static void test_iteration(SplayFC t, String name) {
		System.out.println("snapShotIteration over " + name + " ...");
		Iterator<String> it1 = t.snapShotIterator();
		
		System.out.println(it1.toString());
		int i = 0;
		while (it1.hasNext()) {
			if(++i == 11) t.remove("some");
			String ans = it1.next();
			System.out.println(i + " Next: " + show(ans));
			System.out.println(it1.toString());
			if(i == 3) t.remove("helped");
		}
		t.add("some");
		t.add("helped");
		print(t, name);
		
		System.out.println("updatingIterator over " + name + " ...");
		
		Iterator<String> it3 = t.updatingIterator();
		System.out.println(it3.toString());
		i = 0;
		while (it3.hasNext()) {
			try {
				if(++i == 11) t.remove("some");
				String ans = it3.next();
				System.out.println(i + " Next: " + show(ans));
				if(i == 3) t.remove("helped");
			} catch (ConcurrentModificationException e) { System.out.println("thrown " + e); }
		}

		t.add("some");
		t.add("helped");
		print(t, name);

	}
	
	/**
	 * Test the subSet method of a specified SplayFC.
	 * @param t The SplayFC of interest.
	 * @param name The string representing the variable name of t.
	 * @param s1 The minimum bound.
	 * @param s2 The maximum bound.
	 */
	public static void test_subSet(SplayFC t, String name, String s1, String s2) {
		System.out.println(name + ".subSet(" + show(s1) + ", " + show(s2) + ") = \n" + t.subSet(s1,s2));
	}

	/**
	 * Test the headSet method of a specified SplayFC.
	 * @param t The SplayFC of interest.
	 * @param name The string representing the variable name of t.
	 * @param s The string to test.
	 */
	public static void test_headSet(SplayFC t, String name, String s) {
		SplayFC t2 = t.clone();
		System.out.println(name + ".headSet(" +show(s)+ ") = \n" + t2.headSet(s));
	}

	/**
	 * Test the tailSet method of a specified SplayFC.
	 * @param t The SplayFC of interest.
	 * @param name The string representing the variable name of t.
	 * @param s The string to test.
	 */
	public static void test_tailSet(SplayFC t, String name, String s) {
		SplayFC t2 = t.clone();
		System.out.println(name + ".tailSet(" + show(s) + ") = \n" + t2.tailSet(s));
	}
	
	/**
	 * Test the remove method of a specified SplayFC.
	 * @param t The SplayFC of interest.
	 * @param name The string representing the variable name of t.
	 * @param s The string to remove.
	 */
	public static void test_remove(SplayFC t, String name, String s) {
		System.out.println(name + ".remove(" + show(s) + ") ...");
		t.remove(s);
		print(t, name);
	}
	
	private static int colWidth = 80;
	private static String pad = "----------------------------------------------";
	public static void printDivider(String centreText) {	
		String padding = pad.substring(0, (colWidth - centreText.length())/2);
		System.out.println(padding + centreText + padding);
	}

	/**
	 * Run a series of tests on a SplayFC implementation.
	 * @param args the command line arguments (ignored)
	 */
	public static void main(String[] args) {
		//SplayFC t1 = new SplayFC();
		SplayFC t1 = new SplayFCMODEL();
		String name = "t1";

		printDivider("------ Starting test for SplayFC add ------");
		
		print(t1, name);

		test_add(t1, name, "seam");
		test_add(t1, name, "heal");
		test_add(t1, name, "he");
		test_add(t1, name, "ham");
		test_add(t1, name, "some");
		test_add(t1, name, "hear");
		test_add(t1, name, "help");
		test_add(t1, name, "helped");
		test_add(t1, name, "heart");
		test_add(t1, name, "so");
		test_add(t1, name, "head");
		test_add(t1, name, "hello");
		
		printDivider("------ Starting test for SplayFC contains ------");

		test_contains(t1, name, "hear");
		test_contains(t1, name, "help");
		test_contains(t1, name, "head");
 		test_contains(t1, name, "h");
		test_contains(t1, name, "x");
		test_contains(t1, name, "hearing");

		printDivider("------ Starting test for SplayFC contains:in-order ------");

		test_contains(t1, name, "ham");		
		test_contains(t1, name, "he");
		test_contains(t1, name, "head");
		test_contains(t1, name, "heal");
		test_contains(t1, name, "hear");
		test_contains(t1, name, "heart");
		test_contains(t1, name, "hello");
		test_contains(t1, name, "help");
		test_contains(t1, name, "helped");
		test_contains(t1, name, "seam");
		test_contains(t1, name, "so");
		test_contains(t1, name, "some");
		
		printDivider("------ Starting test for SplayFC contains:rebalancing ------");

 		test_contains(t1, name, "h");
		test_contains(t1, name, "hand");
		test_contains(t1,name,"hello");		
		
		System.out.println();
		printDivider("------ Starting test for SplayFC iterator ------");

		test_iteration(t1, name);

		printDivider("------ Starting test for SplayFC headset ------");

		test_headSet(t1, name, "");
		test_headSet(t1, name, "he");
		test_headSet(t1, name, "hea");
		test_headSet(t1, name, "hear");
		test_headSet(t1, name, "heart");
		test_headSet(t1, name, "hearts");
		test_headSet(t1, name, "x");

		printDivider("------ Starting test for SplayFC tailset ------");

		System.out.println();
		test_tailSet(t1, name, "");
		test_tailSet(t1, name, "he");
		test_tailSet(t1, name, "heal");
		test_tailSet(t1, name, "seal");
		test_tailSet(t1, name, "sead");
		test_tailSet(t1, name, "seem");

		printDivider("------ Starting test for SplayFC subSet ------");

		System.out.println();
		test_subSet(t1, name, "", "x");
		test_subSet(t1, name, "he", "heart");
		test_subSet(t1, name, "h","s");
		test_subSet(t1, name, "s", "h");
		
		System.out.println();
				
		printDivider("------ Starting test for SplayFC clone ------");
		SplayFC t2 = t1.clone();
		
		test_add(t2,"t2","clone");
		test_add(t2,"t2","here");
		print(t1,name);

		printDivider("------ Starting test for SplayFC remove ------");

		print(t1, name);
		test_remove(t1, name, "ham");
		test_remove(t1, name, "he");
		test_remove(t1, name, "seam");
		test_remove(t1, name, "hear");
		test_remove(t1, name, "heal");
		test_remove(t1, name, "hello");
		test_remove(t1, name, "helped");
		
		System.out.println();
		printDivider("------------------- Finished -------------------");
	}
}
