public class Main {
    public static void main(String[] args){
        //some demonstration of all this magic
        ArrayBasedKAryTree<Integer> k;
        k = new ArrayBasedKAryTree<>(3);
        k.addRoot(0);
        k.addChild(0,1);
        k.addChild(0,2);
        k.addChild(0,3);
        k.addChild(3,4);
        k.removeNode(3);
        k.printAllTree();

    }
}

