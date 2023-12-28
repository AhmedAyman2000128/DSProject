package com.example.project.Level1.compress_decompress;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.PriorityQueue;

public class HuffmanEncoder {
    private static final int MAX_SIZE = 256;

    public EncodedData compress(String data){
        int[] freq = frequencyArray(data); //get freq array
        Node root = huffmanTree(freq); //build tree of character frequencies
        HMap<Character,String> lookupTable = buildLookupTable(root); //table of huffman
        return new EncodedData(root,generateEncodedData(data,lookupTable));
    }
    
    public void writeCompressed(EncodedData encData, File file) throws IOException {

        BitSet bitSet = new BitSet(encData.encodedData.length());
        int bitcounter = 0;
        for(Character c : encData.encodedData.toCharArray()) {
            if(c.equals('1')) {
                bitSet.set(bitcounter);
            }
            bitcounter++;
        }
        byte[] bytes = bitSet.toByteArray();
//        BitSet bsbs = BitSet.valueOf(bitSet.toByteArray());
//        String binaryString = "";
//        for(int i = 0; i <= bsbs.length(); i++) {
//            if(bsbs.get(i)) {
//                binaryString += "1";
//            } else {
//                binaryString += "0";
//            }
//        }
//        System.out.println(binaryString);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }

    public String decompress(EncodedData data){
        StringBuilder b = new StringBuilder();
        Node n = data.getRoot(); //start from root of tree
        int i=0;
        while(i<data.getEncodedData().length()){
            while(!n.isLeaf()){
                char number = data.getEncodedData().charAt(i);
                if(number == '1'){
                    n = n.right;
                }else if(number == '0'){
                    n = n.left;
                }else{
                    throw new IllegalArgumentException("invalid bit in message " + number);
                }
                i++;
            }
            b.append(n.c);
            n = data.getRoot(); // go back to root in next iteration
        }
        return b.toString();
    }

    private static String generateEncodedData(String data, HMap<Character,String> lookupTable){ //done
        StringBuilder builder = new StringBuilder();
        for(char c : data.toCharArray()){
            builder.append(lookupTable.get(c));
        }
        return builder.toString();
    }

    private static HMap<Character,String> buildLookupTable(Node root){ //done
        HMap<Character,String> lookupTable = new HMap<>();
        buildLookupTableRec(root,"",lookupTable);
        return lookupTable;
    }

    private static void buildLookupTableRec(Node node, String s, HMap<Character, String> lookupTable) { //done
        if(!node.isLeaf()){
            buildLookupTableRec(node.left,s+'0',lookupTable);
            buildLookupTableRec(node.right,s+'1',lookupTable);
        }else{
            lookupTable.put(node.c,s);
        }
    }
    private Node huffmanTree(int[] freq){ //done
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for(char i = 0; i< MAX_SIZE; i++){
            if(freq[i]>0) pq.add(new Node(i,freq[i],null,null));  // generating leaf nodes
        }
        while (pq.size()>1){
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0',left.freq +right.freq,left,right);
            pq.add(parent);
        }
        return pq.poll();
    }

    private static int[] frequencyArray(String s){  //done
        int[] freq = new int[MAX_SIZE];
        for(int i =0 ;i<s.length();i++){
            char c = s.charAt(i);
            freq[c]++;
        }
        return freq;
    }

    class Node implements Comparable<Node>{  //done
        private char c;
        private int freq;
        private Node left;
        private Node right;

        public Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        boolean isLeaf(){
            return this.left ==null && this.right ==null;
        }

        //compareTo is required for priority queue sorting
        @Override
        public int compareTo(Node o) {
            int comparison;
            if(this.freq>o.freq) {
                comparison = 1;
            }
            else if(this.freq<o.freq){
                comparison = -1;
            }else{
                comparison = Integer.compare(this.c,o.c);
            }
            return comparison;
        }

    }
    public static class EncodedData { //correct
        Node root;
        String encodedData;

        public EncodedData(Node root, String encodedData) {
            this.root = root;
            this.encodedData = encodedData;
        }

        public Node getRoot() {
            return root;
        }

        public String getEncodedData() {
            return encodedData;
        }
    }
}


