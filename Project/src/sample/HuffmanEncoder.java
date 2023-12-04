package sample;


import java.util.PriorityQueue;

public class HuffmanEncoder {
    private static final int ALPHAPET_SIZE = 256;

    public HuffmanEncodedResult compress(String data){
        int[] freq = buildFrequencyTable(data);
        Node root = buildHuffmanTree(freq);
        MyHashMap<Character,String> lookupTable = buildLookupTable(root);
        return new HuffmanEncodedResult(root,generateEncodedData(data,lookupTable));
    }

    private static String generateEncodedData(String data,MyHashMap<Character,String> lookupTable){
        StringBuilder builder = new StringBuilder();
        for(char c : data.toCharArray()){
            builder.append(lookupTable.get(c));
        }
        return builder.toString();
    }

    private static MyHashMap<Character,String> buildLookupTable(Node root){
        MyHashMap<Character,String> lookupTable = new MyHashMap<>();
        buildLookupTableImpl(root,"",lookupTable);
        return lookupTable;
    }

    private static void buildLookupTableImpl(Node node, String s, MyHashMap<Character, String> lookupTable) {
        if(!node.isLeaf()){
            buildLookupTableImpl(node.leftChild,s+'0',lookupTable);
            buildLookupTableImpl(node.rightChild,s+'1',lookupTable);
        }else{
            lookupTable.put(node.character,s);
        }
    }
    private Node buildHuffmanTree(int[] freq){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for(char i=0;i<ALPHAPET_SIZE;i++){
            if(freq[i]>0) priorityQueue.add(new Node(i,freq[i],null,null));
        }
        if(priorityQueue.size()==1) priorityQueue.add(new Node('\0',1,null,null));
        while (priorityQueue.size()>1){
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0',left.frequency+right.frequency,left,right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    private static int[] buildFrequencyTable(String data){
        int[] freq = new int[ALPHAPET_SIZE];
        for(char c : data.toCharArray()){
            freq[c]++;
        }
        return freq;
    }

    class Node implements Comparable<Node>{
        private char character;
        private int frequency;
        private Node leftChild;
        private Node rightChild;

        public Node(char character, int frequency, Node leftChild, Node rightChild) {
            this.character = character;
            this.frequency = frequency;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
        boolean isLeaf(){
            return this.leftChild==null && this.rightChild==null;
        }

        @Override
        public int compareTo(Node o) {
            int freqComparison = Integer.compare(this.frequency,o.frequency);
            if (freqComparison!=0){
                return freqComparison;
            }
            return Integer.compare(this.character,o.character);
        }




    }
    class HuffmanEncodedResult{
        Node root;
        String encodedData;

        public HuffmanEncodedResult(Node root, String encodedData) {
            this.root = root;
            this.encodedData = encodedData;
        }
    }
}
