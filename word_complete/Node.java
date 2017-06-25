/**
 * @author Lior
 * node.java
 */
package word_complete;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * a class that represent a node in a trie. 
 * trie is a tree that has many branches (ex. all the possible alphabet letters)
 */
public class Node {
	Node[] nodes;
	boolean isWord ;
	boolean isDeletable;
	
	/**
	 * CTOR  - creates a new empty node. 
	 */
	public Node( ) { 
		super();
		this.nodes  = new Node[26];
		this.isWord = false;
		this.isDeletable = false;
	}
	/**
	 * PURPOSE - converts a char to an index. ( ex. 'a' is 0, 'b' is 1 etc. )
	 * @param ch - char to convert
	 * @return - an int representation
	 */
	public int index(char ch){
		return (Character.toLowerCase(ch) - 'a');
	}
	
	public int index(String str){
		return (index(str.charAt(0)));
	}
	/**
	 * PURPOSE - add a word to the dictionary
	 * @param str - the word to add
	 */
	public void add(String str) {
		if (str.length() == 0) { // stop condition :  if  the string has been consumed - mark as word and start to coil back the recursion
			isWord = true;
			return;
		}
		int curr = index(str.charAt(0));
//		System.out.print(str.charAt(0) + " = " + curr +" , ");
		if (nodes[curr] == null){
			nodes[curr] = new Node();
		}
		nodes[curr].add(str.substring(1));
	}
	
	/**
	 * PURPOSE - check if a word exists at the dictionary. based on _size_ method.
	 * @param str - the word to check
	 * @return
	 */
	public boolean exists(String str){
		return (size(str)>0);
	}
	
	/**
	 * PURPOSE - check the size of the word
	 * @param str - the word to check
	 * @return the size or negative if doesn't exist
	 */
	public int size(String str){
		if (str.length() == 0){ // stop condition : if all the of string has been consumed - check that it is a word
			if (isWord){
				return 0;
			}
			
			return -1;
		}
		
		int curr = index (str.charAt(0));
		if (nodes[curr]== null){ // didn't consume all the string - it isn't in the dictionary
			return -1;
		} 
		
		int ret = nodes[curr].size(str.substring(1));
		if (ret >= 0){
			return ret +1;
		} 
		return -1;
	}
	/**
	 * PURPOSE - checks if all the nodes aren't in use. 
	 * @return - false if atlist one node is in use.
	 */
	public boolean checkEmpty(){
		boolean ret = true;
		
		for (int i =0; i< nodes.length; ++i){
			ret &= (nodes[i] == null);
		}
		
		return ret && !isWord;
	}
	/**
	 * PURPOSE - to remove a word from the dictionary. if nodes aren't used - it removes them.
	 * @param str
	 */
	public void delete(String str){
		if (str.length() == 0){ // stop condition :  if the string has been consumed
			isWord = false; // clear the word flag 
			isDeletable = checkEmpty(); // check if node isn't used for other words
			return;
		}
		int curr = index(str);
		if (nodes[curr] == null) {
			return;
		}
		nodes[curr].delete(str.substring(1)); // the recursion step - consume the string
		
		if (nodes[curr].isDeletable){ // if the sub-node is deletable - delete it.
			nodes[curr] = null;
		}
		
		isDeletable = checkEmpty(); // if no sub-nodes exist - this node is deletable also
	}
	/**
	 * PURPOSE - print TRIE as Depth First Search
	 * @param root
	 * @return
	 */
	public static String printDFS(Node root){ // recursive solution
		if (root == null){
			return "";
		}
		StringBuilder ret = new StringBuilder();

//		System.out.println("********* Print DFS *********");
		for (int i=0; i < root.nodes.length; ++i){
			if (root.nodes[i] != null) {
				ret.append( " "+(char)('a' + i));
				if (root.nodes[i] .isWord){
					ret.append('.');
				}
				ret.append(printDFS(root.nodes[i]));
			}
		}
		ret.append("-");
		return ret.toString();
	}
}
