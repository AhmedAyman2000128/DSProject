/*
 * File: Json.java
 * -------------------
 * This file contains the implementation of the Json class.
 * Json is responsible for converting Xml format into json by turning Xml into Tree nodes {opentag,closedtag,leaf}
 * then connect these nodes as a tree then print this tree structure
 *
 *  Create an instance of it then use JsonConverter() method and pass it String of  Xml format
 *  then use getJsonstr() method  to get the String
 *
 *
 */

import java.util.ArrayList;
import java.util.Stack;

public class Json {

    private  String Jsonstr;

    Json(){ Jsonstr = "";}

    public String getJsonstr() {
        return Jsonstr;
    }

    public  void JsonConverter(String xml){
        // Xml --> Tree nodes --> Tree --> Json
        StringBuilder Json = new StringBuilder();
        toJson(nodesToTree(ToTreeNodes(xml)),0,Json);
        String JsonOut = "{\n" +  Json.toString() + "\n}";
        this.Jsonstr = JsonOut;
    }

// Converting each line to either open tag or closed tag or Leaf specifying it as tree node and putting it in array of tree nodes

    private static ArrayList<TreeNode> ToTreeNodes(String str){
        ArrayList<TreeNode> nodes = new ArrayList<>();

        for (int i = 0 ; i < str.length() ; i++){
            StringBuilder data = new StringBuilder();

            if (str.charAt(i) == ' ' || str.charAt(i) == '\n') {
                continue;
            }
            if (str.charAt(i) == '<' && str.charAt(i+1) =='/'){     //Closed tag
                i += 2;
                while(str.charAt(i) != '>'){
                    data.append(str.charAt(i));
                    i++;
                }
                TreeNode t = new TreeNode(Nodetype.CLOSED_TAG, data.toString().trim());
                nodes.add(t);

            } else if (str.charAt(i) == '<' && str.charAt(i+1) !='/') { //Open tag
                i += 1;
                while(str.charAt(i) != '>'){
                    data.append(str.charAt(i));
                    i++;
                }

                TreeNode t = new TreeNode(Nodetype.OPEN_TAG, data.toString().trim());
                nodes.add(t);

            } else{                               // Leaf
                while (str.charAt(i) != '<'){
                    data.append(str.charAt(i));
                    i++;
                }
                TreeNode t = new TreeNode(Nodetype.LEAF,data.toString().trim());
                nodes.add(t);
                i--;
            }

        }

        return nodes;
    }

    /*Converting Treenodes into Tree and returning the Head node of the tree (Root of the tree)
    and giving for each parent his child
    * */

    public static TreeNode nodesToTree(ArrayList<TreeNode> nodes) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current;

        for (int i = 0; i < nodes.size(); i++) {
            current = nodes.get(i);

            if (current.getType() != Nodetype.CLOSED_TAG ) {
                stack.push(current);
            } else {
                Stack<TreeNode> reverse = new Stack<>();
                TreeNode child = new TreeNode(Nodetype.CHILD, current.getData());
                TreeNode top = stack.pop();

                while (top.getType() != Nodetype.OPEN_TAG && !stack.isEmpty()) {
                    // pushing childs but reversed
                    reverse.push(top);
                    top = stack.pop();
                }

                // Process reversed closed tags
                while (!reverse.isEmpty()) {
                    child.addChild(reverse.pop());
                }

                if (!stack.isEmpty()) {
                    top = stack.peek();
                } else {
                    top = null;
                }

                if (!stack.isEmpty() && top.getData().equals(current.getData())) {
                    // Top data of stack is the same as the closed tag data
                    top.type= Nodetype.DUPLICATE_TAG;

                    if (child.getChildren().size() == 1) {
                        top.addChild(child.getChildren().get(0));
                    } else {
                        if (!top.isDuplicated()) {
                            // Handle duplicated tag
                            child.setData("");
                            TreeNode tempChild = new TreeNode(Nodetype.CHILD, "");
                            tempChild.setChildren(top.getChildren());
                            top.setChildren(new ArrayList<>());
                            top.addChild(tempChild);
                            top.addChild(child);
                            top.setDuplicated(true);

                        } else {
                            child.setData(""); // Set data to empty for non-first duplicated tags
                            top.addChild(child);
                        }
                    }
                } else if (child.getChildren().size() == 1 && child.getChildren().get(0).getType() == Nodetype.LEAF) {
                    child.type = Nodetype.PARENT;
                    stack.push(child);
                } else {
                    stack.push(child);
                }
            }
        }

        return stack.pop();
    }
    // Converting Tree to JSON
    public static void toJson(TreeNode head, int space, StringBuilder str) {

        space++;
        str.append(repeat("    ", space));

        if (head.getType() == Nodetype.LEAF) {
            str.append("\"").append(head.getData()).append("\"");
        } else if (head.getType() == Nodetype.PARENT) {
            str.append("\"").append(head.getData()).append("\": \"").append(head.getChildren().get(0).getData()).append("\"");
        } else {
            if (!head.getData().isEmpty()) {
                str.append("\"").append(head.getData()).append("\": ");
            }

            if (head.getType() == Nodetype.DUPLICATE_TAG) {
                str.append("[\n");
            } else {
                str.append("{\n");
            }

            for (int i = 0; i < head.getChildren().size(); i++) {
                if (head.getType() == Nodetype.DUPLICATE_TAG && head.getChildren().get(i).getType() == Nodetype.PARENT) {
                    str.append(repeat("    ", space + 1)).append("{\n");
                    toJson(head.getChildren().get(i), space, str);
                    str.append("\n").append(repeat("    ", space + 1)).append("}");
                } else {
                    toJson(head.getChildren().get(i), space, str);
                }

                if (i < head.getChildren().size() - 1) {
                    str.append(", \n");
                } else {
                    str.append('\n').append(repeat("    ", space));

                    if (head.getType() == Nodetype.DUPLICATE_TAG) {
                        str.append("]");
                    } else {
                        str.append("}");
                    }
                }
            }
        }
    }
    public static String repeat(String s , int r){
        StringBuilder rep = new StringBuilder();
        while(r > 0){
            rep.append( "    ");
            r--;
        }
        return rep.toString();
    }
   private static class TreeNode{
        private String data ;
        private Nodetype type ;
        private boolean duplicated = false;
        private ArrayList<TreeNode> children;
        public TreeNode(Nodetype type , String data){
            this.type = type;
            this.data = data;
            children = new ArrayList<>();
        }
        public void addChild(TreeNode child){ this.children.add(child);}
        public String getData(){return data;}
        public Nodetype getType(){return type;}
        public void setData(String s) { this.data = s;}
        public void setDuplicated(boolean b) {
            this.duplicated = b;
        }
        public ArrayList<TreeNode> getChildren() {
            return this.children;
        }
        public void setChildren(ArrayList<TreeNode> children) {
            this.children = children;
        }
        public boolean isDuplicated() {
            return duplicated;
        }
    }
    enum Nodetype{

        OPEN_TAG,CLOSED_TAG,CHILD,PARENT,LEAF,DUPLICATE_TAG
    }
}

