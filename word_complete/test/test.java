/**
 * @author Lior
 * test.java
 */
package word_complete.test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import word_complete.Node;

/**
 *
 */
public class test {
	Node root;
	List<String> dictionary ;

	@BeforeClass
	public static void init(){
		List<String> dictionary = Arrays.asList("M", "May", "Maybe", "Joe", "Cafe" , "Decafe", "Cafeinated", "Monday", "Miss" );
		System.out.println(dictionary.toString());
	}
	
	@Before
	public void setup(){
		root = new Node();
		dictionary = Arrays.asList("M", "May", "Maybe", "Joe", "Cafe" , "Decafe", "Cafeinated", "Monday", "Miss" );
		
		dictionary.stream().forEach( x -> { // populate the trie w/ all the words from the list
			root.add(x);
		});
	}
	/**
	 * CASES
	 * I. adding elements 
	 */
	@Test
	public void testAdd(){
		dictionary.stream().forEach( x -> {
			assert(root.size(x) == x.length());
		});
		
		assert(!root.exists("false"));
		assert(!root.exists("ma"));
		assert(root.size("false")<0);
		assert(root.size("ma")<0);
		
	}
	/**
	 * PURPOSE
	 * cases:
	 * I. removing elements don't exist on trie (checked after each removal
	 * II. the trie is completely empty (no nodes at root)
	 * III. removing and adding works w/o collisions.
	 * IV. partial removal works (removing "may" doesn't delete "maybe"
	 * V. trying to remove a partial word doesn't damage existing word
	 * VI. trying to remove a non existent string (not even partial word) doesn't work
	 */
	@Test
	public void testDeleteAll(){
		dictionary.stream().forEach( x -> { // populate the trie w/ all the words from the list
			root.delete(x);
			assert(!root.exists(x)); // case I
		});
		
		assert(!root.exists("maybe"));
		assert(root.checkEmpty()); // case II. check root is empty
		// check if adding after deleting works ok 
		root.add(dictionary.get(0));
		assert(root.exists(dictionary.get(0)));
//		 * VI. trying to remove a non existent string (not even partial word) doesn't work
		root.delete("xxxx");
	}

	
	@Test
	public void testDeletePartial(){ //  IV. partial removal works (removing "may" doesn't delete "maybe"

		root = new Node();
		root.add("Maybe");
		root.add("May");
		root.delete("may");
		assert(!root.exists("may"));
		assert(root.exists("maybe"));
		root.delete("mayb");
		assert(root.exists("maybe"));
		assert(!root.exists("mayb"));
	}
	
	@Test
	public void testPrintDFS(){
//		dictionary.stream().forEach( x -> root.add(x) );
		
		String print = Node.printDFS(root);
		System.out.println("printDFS = " + print);
	}
	
	
	
	
	@After
	public void finalizer(){
		root = null;
	}
}
