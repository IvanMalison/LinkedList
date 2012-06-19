import java.util.Comparator;

class Node<Type> {
    Node<Type> next;
    Type val;

    public Node(Type v) {
	val = v;
	next = null;
    }
}

public class LinkedList<Type> {
    protected Node<Type> head;
    protected Comparator<Type> comparator;
    protected boolean ordered = true;

    public LinkedList() {
	head = null;
    }
    
    public LinkedList(Type v) {
	head = new Node<Type>(v);
    }

    public void insertInOrder(Type v) {
	try {
	    if(comparator == null) {
		throw new Exception("No Comparator Object.");
	    }
	    if(!ordered) {
		throw new Exception("The list is not currently ordered.");
	    }
	    Node<Type> node = new Node<Type>(v);
	    if(head == null || comparator.compare(head.val, node.val) >= 0) {
		node.next = head;
		head = node;
		return;
	    }
	    Node<Type> runner = head;
	    while(runner.next != null && comparator.compare(v, runner.next.val) > 0) {
		runner = runner.next;
	    }
	    node.next = runner.next;
	    runner.next = node;
	} catch(Exception e) {
	    System.err.println(e.getMessage());
	}
    }

    public void insertAtHead(Type v) {
	Node<Type> node = new Node<Type>(v);
	node.next = head;
	head = node;
	
	if(node.next != null && comparator.compare(node.val, node.next.val) > 0) {
	    ordered = false;
	}
    }

    public void insertAt(int i, Type v) {
	try {
	    if(i == 0) {
		insertAtHead(v);
		return;
	    } else if (head == null) {
		throw new Exception(String.format("Not Possible to insert at index %d.", i));
	    }
	    int count = 0;
	    Node<Type> runner = head;
	    while(count < i-1) {
		runner = runner.next;
		count++;
		if(runner == null) {
		    throw new Exception(String.format("Not Possible to insert at index %d.", i));
		}
	    }
	    Node<Type> next = runner.next;
	    runner.next = new Node<Type>(v);
	    runner.next.next = next;
	    if(runner.next.next != null && comparator.compare(runner.next.val, runner.next.next.val) > 0) {
		ordered = false;
	    }
	} catch (Exception e) {
	    System.err.println(e.getMessage());
	}
    }

    private Node<Type> getNode(Node<Type> start, int i) {
	Node<Type> runner = start;
	for(int count = 0; count < i; count++) {
	    if(runner == null) {
		return null;
	    }
	    runner = runner.next;
	}

	if(runner == null) {
	    return null;
	} else {
	    return runner;
	}
    }

    public Type get(int i) {
	Node<Type> node = getNode(head, i); 
	return node == null ? null : node.val;
    }

    public boolean removeHead() {
	if(head == null) return false;
	head = head.next;
	return true;
    }

    public boolean remove(int i) {
	if(i == 0) {	    
	    return removeHead();
	}

	Node<Type> runner = head;
	for(int count = 0; count < i-1; count++) {
	    if(runner == null) {
		return false;
	    }
	    runner = runner.next;
	}

	if(runner == null || runner.next == null) {
	    return false;
	} else {
	    runner.next = runner.next.next;
	    return true;
	}
    }

    public void printValues() {
	if(head == null) return;
	for(Node<Type> runner = head; runner != null; runner = runner.next) {
	    if(runner instanceof Object) {
		System.out.print((Object) runner.val);
	    } else {
		System.out.print(runner.val);
	    }
	    if(runner.next != null) {
		System.out.print(", ");
	    }		
	}
	System.out.println(".");
    }

    public void reverse() {
	if (head == null) return;	
	Node<Type> node = head;
	Node<Type> next = head.next;
	head.next = null;
	while(next != null) {
	    Node<Type> temp;
	    temp = next.next;
	    next.next = node;
	    node = next;
	    next = temp;
	}
	head = node;
    }

    public void sort(Comparator<Type> comp) {
	comparator = comp;
	sort();
    }

    public void sort() {
	if(comparator != null) {
	    head = mergeSort(head, length());
	}
    }

    private Node<Type> mergeSort(Node<Type> node, int size) {
	if(size == 2) {
	    if(comparator.compare(node.val, node.next.val) > 0) {
		node.next.next = node;
		node = node.next;
	    }
	    node.next.next = null;
	    return node;
	} else if(size == 1) {
	    node.next = null;
	    return node;
	}
	int a = size/2;
	Node<Type> second = getNode(node, a);
	return merge(mergeSort(node, a), a, mergeSort(second, size - a), size - a);
    }

    private Node<Type> merge(Node<Type> a, int aSize, Node<Type> b, int bSize) {
	Node<Type> first, current;
	if(comparator.compare(a.val, b.val) < 0) {
	    first = a;
	    aSize--;
	    a = a.next;
	} else {
	    first = b;
	    bSize--;
	    b = b.next;
	}
	
	current = first;
	while(aSize > 0 && bSize > 0) {
	    if(comparator.compare(a.val, b.val) < 0) {
		aSize--;
		current.next = a;
		a = a.next;
	    } else {
		bSize--;
		current.next = b;
		b = b.next;
	    }
	    current = current.next;
	}
	if(aSize > 0) {
	    current.next = a;
	} else {
	    current.next = b;
	}
	return first;
    }

    public int length() {
	Node<Type> runner = head;
	int count = 0;
	while(runner != null) {
	    count++;
	    runner = runner.next;
	}
	return count;
    }

    public boolean isOrdered() {
	return ordered;
    }
}

class StringLinkedList extends LinkedList<String> {
    public StringLinkedList() {
	comparator = String.CASE_INSENSITIVE_ORDER;
    }
}

class IntLinkedList extends LinkedList<Integer> implements Comparator<Integer> {
    public IntLinkedList() {
	comparator = this;
    }

    public void insertInOrder(int v) {
	insertInOrder(new Integer(v));
    }

    public void insertAtHead(int v) {
	insertAtHead(new Integer(v));
    }

    public void insertAt(int i, int v) {
	insertAt(i, new Integer(v));	
    }

    public int compare(Integer a, Integer b) {
	return a.intValue() < b.intValue() ? -1 : (a.intValue() == b.intValue() ? 0 : 1);
    }
    
    public boolean equals(Object o) {
	return o == this;
    }
}