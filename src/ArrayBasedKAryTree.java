import java.util.Iterator;

public class ArrayBasedKAryTree<E>{

    private class Node<E>{ //node class
        private E value;
        public int numberOfChildren;

        public Node(E value) {
            this.value = value;
            this.numberOfChildren=0;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }
    }

    private int k;
    private int size=0, level=0, capacity=0;
    private Node<E>[] tree;

    public ArrayBasedKAryTree(int k) {
        this.k = k;
    }

    public void addRoot(E root){
        tree = new Node[1];
        tree[0] = new Node<>(root);
        size++;
        capacity=capacity+1;
    }

    public boolean isRoot(int index){
        return index==0;
    }

    private void resize(){
        level++;
        capacity=(int)(capacity+Math.pow(k,level));
        Node<E>[] swap = new Node[capacity];
        for (int i = 0; i < tree.length; i++)
            swap[i] = tree[i];
        tree = swap;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public int numChildren(int index){
        return tree[index].numberOfChildren;
    }

    public void addChild(int parentIndex, E value){
        if (numChildren(parentIndex)==k) throw new IndexOutOfBoundsException("Impossible to add new child: maximum amount");
        if (capacity<=parentIndex+Math.pow(k,level)) resize();
        tree[k*parentIndex+1+numChildren(parentIndex)]= new Node(value);  //k*i+1+c formula, from Wiki
        tree[parentIndex].numberOfChildren++;
        size++;
    }

    public void printAllTree(){
        for (int i = 0; i < tree.length; i++) {
            if (tree[i]!=null)System.out.println(tree[i].value);
        }
    }

    public int parentIndex(int childIndex){
        return (childIndex-1)/k;
    }

    public void recountSize(){ // recounting size. For magic purposes.
        size=0;
        for (int i = 0; i < tree.length; i++) {
            if (tree[i]!=null) size++;
        }
    }
    public void removeNode(int index){
        if (isExternal(index)){
            tree[index]=null;
            size--;
        } else
        if (numChildren(index)==1){
            tree[index]=tree[k*index+1+1];
            tree[k*index+1+1]=null;
            size--;
        } else throw new IndexOutOfBoundsException("Can't remove nodes with more than 1 children - I'm not a murderer.");
    }

    public boolean isExternal (int index){
        return(numChildren(index)==0);
    }
    public boolean isInternal (int index){
        return(!isExternal(index));
    }

    private class ChildrenIterator implements Iterator<Integer>{
        int parentIndex;
        int currentChildIndex;
        public ChildrenIterator(int index){
            parentIndex =index;
            setCurrentChildIndex(parentIndex);
        }

        public void setCurrentChildIndex(int index) {
            this.currentChildIndex = k*index+1;
        }

        @Override
        public boolean hasNext() {
            return (currentChildIndex++<k* parentIndex +1+k);
        }

        @Override
        public Integer next() {
            return currentChildIndex++;
        }
    }

    private class ChildrenIterable implements Iterable<Integer>{
        int parentIndex;
        public ChildrenIterable(int index){
            parentIndex=index;
        }
        public Iterator<Integer> iterator(){
            return new ChildrenIterator(parentIndex);
        }
    }

    public Iterable<Integer> children(int index){
        return new ChildrenIterable(index);
    }
}

