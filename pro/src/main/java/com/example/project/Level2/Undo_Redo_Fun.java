package com.example.project.Level2;

import java.util.Stack;

public class Undo_Redo_Fun {
    private Stack<String> undo_stack;
    private Stack<String> redo_stack;
    public Undo_Redo_Fun(){
        undo_stack = new Stack<String>();
        redo_stack = new Stack<String>();
    }
    public void addString(String s){
        undo_stack.push(s);
    }
    public String undo(){
        if(undo_stack.size()>0){
            redo_stack.push(undo_stack.peek());
            undo_stack.pop();
        }
        if(undo_stack.size()==0){
            return "";
        }
        else {
            return undo_stack.peek();
        }
    }
    public String redo(){
        if(redo_stack.size()==0){
            return "";
        }
        else{
            String k = redo_stack.peek();
            undo_stack.push(k);
            redo_stack.pop();
            return k;
        }
    }

}
