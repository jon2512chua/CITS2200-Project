
/**
 * An implementation of a binary tree cell with a string key.  No modifications
 * are allowed after creation.
 * 
 * @version 1.1
 */
public class Cell {
	
	// The following instance variables are final to ensure that a cell
	// is never modified after it is constructed.
	
	private final String key;
	public final Cell lt;
	public final Cell rt;
	
	public Cell(String k, Cell left, Cell right) {
		key = k;
		lt = left;
		rt = right;
	}

	public String key() { return key; }
	
	public String toString(String prefix,String lString, String rString, String upDown) {
		String s = prefix + upDown + key+"\n";
		String s1 = lt==null ? "" : lt.toString(prefix+lString, "    ", "|   ", " /-");
		String s2 = rt==null ? "" : rt.toString(prefix+rString, "|   ", "    ", " \\-");
		return s1 + s + s2;
	}
  
}
