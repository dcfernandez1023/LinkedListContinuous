/*
    This LinkedList (LL) data structure is singly linked and contains the data members head (first node), tail (last node),
    and size (number of nodes in the list).  Instantiations of this LL class contain instance methods that can add,
    remove, get, and reverse the nodes of the LinkedList.  The nodes of this LL class are privately defined and instantiated
    within this LL class.
*/
public class LLContinuous<E> {
    public Node<E> head; //first node in the LL
    public Node<E> tail; //last node in the LL
    public int size; //number of nodes in the LL
    /*
        PARAMS: none
        DESCRIPTION: Constructor; initially points head and tail to null since no nodes in this LinkedList exist
        RETURN: none
    */
    public LLContinuous() {
        this.head = this.tail = null; //set head and tail to null b/c LL is initially empty
        this.size = 0; //set size to 0 b/c LL is initially empty
    }
    /*
        PARAMS: none
        DESCRIPTION: Overriden toString() method; creates string representation of the LL
        RETURN: none
    */
    @Override
    public String toString() {
        String s = "";
        //loop through LL
        for(Node<E> currentNode = this.head; currentNode != null; currentNode = currentNode.next) {
            s = s + currentNode.value;
            if(currentNode.next != null) {
                //append to list in loop
                s = s + "--> ";
            }
        }
        //return string representation of LL
        return s;
    }
    /*
        PARAMS:
            * E item - data of type E that will be added to the LL as a Node object
        DESCRIPTION: adds a new node at the end of the LL
        RETURN: none
    */
    public void add(E item) {
        Node<E> node = new Node<>(item);
        //if head is null, then list is empty, so add at the head
        if(this.head == null) {
            this.head = node;
            this.tail = node;
        }
        //if head is not null, then list is empty, so add at tail
        else {
            this.tail.next = node; //set current tail's next to the new node
            this.tail = node; //set current tail to the new node
        }
        size++; //increment size
    }
    /*
    PARAMS:
        * E item - data of type E that will be added to the LL as a Node object
        * int position - the location where the new node is to be inserted in the LL
    DESCRIPTION: adds a new node at the end of the LL; throws exception if position is invalid
    RETURN: none
    */
    public void add(E item, int position) throws Exception {
        //if position is invalid, throw exception
        if(position < 0 || position >= this.size) {
            throw new Exception("Position " + position + " out of bounds for size " + this.size);
        }
        Node<E> newNode = new Node<>(item);
        //if position is 0, then add at the head
        if(position == 0) {
            Node<E> temp = this.head; //create temp node to preserve current head node
            this.head = newNode; //set head to the new node
            this.head.next = temp; //set head's next to the previous head
        }
        else {
            Node<E> currentNode = this.head;
            //loop through LL until the node before the specified position
            for (int i = 0; i < position - 1; i++) {
                currentNode = currentNode.next;
            }
            //current node becomes the node right before the node at the specified position
            //this section of the code places the new node in front of the current node, and then places the previous current node's next in front of the new node
            newNode.next = currentNode.next; //set the new node's next to the current node's next
            currentNode.next = newNode; //set current node's next to the new node
        }
        size++; //increment size
    }
    /*
        PARAMS:
            * int position - the location of the node to be returned
        DESCRIPTION: returns the value of the node at the specified position; throws exception if position is invalid or LL is empty
        RETURN: returns the value of the node at the specified position
    */
    public E get(int position) throws Exception {
        //throw exception if LL is empty
        if(this.head == null) {
            throw new Exception("Cannot get nodes from an empty list");
        }
        //throw exception if position is invalid
        if(position < 0 || position >= this.size) {
            return null;
        }
        //loop through LL until the node at the specified position is reached
        Node<E> currentNode = this.head;
        for(int i = 0; i < position; i++) {
            currentNode = currentNode.next;
        }
        //return value of node at the specified position
        return currentNode.value;
    }
    /*
        PARAMS:
            * int position - location of the node to be removed in the LL
        DESCRIPTION: removes a node at the specified position while maintaining the order of the LL; throws exception if LL is empty or position is invalid
        RETURN: returns the value of the node that was removed
    */
    public E remove(int position) throws Exception {
        //throw exception if LL is empty
        if(this.head == null) {
            throw new Exception("Cannot remove nodes from an empty list");
        }
        //throw exception is position is invalid
        if(position < 0 || position >= this.size) {
            throw new Exception("Position " + position + " out of bounds for size " + this.size);
        }
        //if position is 0, remove at the head
        if(position == 0) {
            Node<E> temp = this.head; //create temp node to preserve current head
            this.head = temp.next; //set current head to temp's next (the previous head will be garbage collected)
            size--; //decrement size
            return temp.value;
        }
        //loop through LL until the node before the node at the specified position
        Node<E> currentNode = this.head;
        for(int i = 0; i < position - 1; i++) {
            currentNode = currentNode.next;
        }
        //currentNode becomes the node right before the node at the specified position
        //the code below makes currentNode's next point to currentNode.next.next, thus making the node at the specified position get garbage collected/de-referenced
        Node<E> temp = currentNode.next; //preserve current node's next in a temp variable
        currentNode.next = temp.next; //set currentNode's next to temp's next (temp gets garbage collected)
        size--; //decrement size
        return temp.value;
    }
    /*
        PARAMS:
            * E head - value of head
        DESCRIPTION: reverses the LL from tail to head; throws exception if LL is empty
        RETURN: value at new head
    */
    public E reverse(E head) throws Exception {
        //throw exception if LL is empty
        if(this.head == null) {
            throw new Exception("Cannot reverse nodes of an empty list.");
        }
        //initialize necessary nodes in order to walk through the LL while maintaining, advancing, and referencing the proper nodes
        Node<E> currentNode = this.head;
        Node<E> prevNode =  null, nextNode = null;
        while(currentNode != null) {
            nextNode = currentNode.next; //advance nextNode
            currentNode.next = prevNode; //point currentNode.next to prevNode (establishes links for when the list is reversed)
            prevNode = currentNode; //advance prevNode
            currentNode = nextNode; //advance nextNode
        }
        this.head = currentNode = prevNode; //set head
        return this.head.value; //return head's value
    }

    private static class Node<E> {
        Node<E> next; //points to this node's next node
        E value; //value of the node (type E -- can be any data type except primitives)
        /*
            PARAMS:
                * E value - value to be stored within the node
            DESCRIPTION: constructor; constructs a new node object; sets next to null and value to the value parameter
            RETURN: none
       */
        public Node(E value) {
            this.next = null;
            this.value = value;
        }
        //getters and setters -- the rubric said to have them, but I never really had the need to use it since its data members are public
        public void setValue(E value) {
            this.value = value;
        }
        public E getValue() {
            return this.value;
        }
        public void setNext(E value) {
            this.next = new Node<E>(value);
        }
        public Node<E> getNext() {
            return this.next;
        }
    }

    /*
        PARAMS: none
        DESCRIPTION: determines whether or not the LinkedList is continuous by determining if the tail's next points
                     to a node that came before the tail in the LinkedList
        RETURN: int index; the index of the tail's next which makes the LinkedList continuous
    */
    public int findContinuousIndex() {
        //initialize counter
        int i = 0;
        //initialize last Node to keep track of the node that makes the LinkedList continouous
        Node<E> last = this.tail.next;
        //initialize current Node to compare to the last Node as the program loops through the LinkedList
        Node<E> current = this.head;
        //if the last Node (which is the tail's next) is not null, then this LinkedList is potentially continuous
        if(last != null) {
            //loop through LinkedList; loop control based on size of LinkedList
            while(i < this.size) {
                /*
                if the addresses of last Node and current Node are the same, then they are pointing to the same Node object in memory;
                this means that tail's next points to a node that comes before the tail, thus making the LinkedList continuous
                */
                if(last.toString().equals(current.toString())) {
                    //return the index of the continuous node
                    return i;
                }
                i++; //increment counter
                current = current.next; //increment pointer
            }
        }
        return -1; //return -1 if tail's next is null (meaning that LinkedList is not continuous
    }

    public void printNodeAddress(Node<E> node1, Node<E> node2) {
        System.out.println(node1);
        System.out.println(node2);
    }

    public static void main(String[] args) throws Exception {
        LLContinuous<String> L1 = new LLContinuous<>();
        L1.add("hello");
        L1.add("hi");
        L1.add("yo");
        L1.tail.next = L1.head.next.next;
        //L1.printNodeAddress(L1.tail.next, L1.head.next);
        System.out.println(L1.findContinuousIndex());
    }
}

