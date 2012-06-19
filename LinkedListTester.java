import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LinkedListTester {
    static BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
    public static void main(String[] args) {
	IntLinkedList ll = new IntLinkedList();
	while(true) {	    
	    printDivider();
	    ll.printValues();
	    printMessage();
	    printDivider();
	    try{
		int val = Integer.valueOf(br.readLine());
		printDivider();
		switch(val) {
		case 0:
		    ll.printValues();
		    break;
		case 1:
		    ll.insertAtHead(getValue());
		    break;
		case 2:
		    ll.insertInOrder(getValue());
		    break;
		case 3:
		    ll.insertAt(getIndex(), getValue());
		    break;
		case 4:
		    System.out.println(ll.get(getIndex()));
		    break;
		case 5:
		    ll.remove(getIndex());
		    break;
		case 6:
		    ll.sort();
		    break;
		case 7:
		    ll.reverse();
		    break;
		default:
		    System.out.println("Input number was not an option");
		    break;
		}
	    } catch(IOException e) {
		System.err.print("Input was not a number... ");
		System.err.println(e.getMessage());
	    }
	}
    }

    public static int getIndex() {
	try {
	    System.out.println("Enter the index.");
	    int val = Integer.valueOf(br.readLine());
	    printDivider();
	    return val;
	} catch(Exception e) {
	    System.err.print("Input was not a number... using 0 instead");
	    return 0;
	}
    }

    public static int getValue() {
	try {
	    System.out.println("Enter the value to insert.");
	    int val = Integer.valueOf(br.readLine());
	    printDivider();
	    return val;
	} catch(Exception e) {
	    System.err.print("Input was not a number... using 0 instead");
	    return 0;
	}
    }

    public static void printMessage() {
	System.out.println("\t0. Print\n\t1. InsertAtHead\n\t2. InsertInOrder\n\t3. InsertAt\n\t4. Get\n\t" +
			   "5. Remove\n\t6. Sort\n\t7. Reverse");
    }
    
    public static void printDivider() {
	System.out.println("--------------------------------------------------------------------------------");
    }
}