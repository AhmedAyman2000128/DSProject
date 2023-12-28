package com.example.project.Level1.Error;

import java.util.Scanner;
import java.util.Stack;

public class ErrorDetect {
    private String Xml;
    private int errorline;
    private String ErrorMsg;

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public ErrorDetect(String Xml) {
        this.Xml = Xml;
        this.errorline = 0;
        ErrorMsg = "";
    }

    public void checkerror()  {
        Stack<String> stack = new Stack<String>();

        String txt = new String();
        Scanner scan = new Scanner(this.Xml);

        while (scan.hasNextLine()) {
            errorline++;
            txt = scan.nextLine();
            for (int i = 0; i < txt.length(); i++) {

                if (txt.charAt(i) != '<') {
                    continue;
                } else {
                    i++;
                    StringBuilder tag = new StringBuilder();
                    if (txt.charAt(i) == '/') {             //closing tag
                        // filling string tag in a buffer
                        while (txt.charAt(i) != '>') {
                            tag.append(txt.charAt(i));
                            i++;
                        }
                        stack.push(tag.toString());
                        if (!checkOpentag(stack,tag.toString())){
                            ErrorMsg += ("Element has no open tag for " + tag.toString().replace("/","") + " in line " + getErrorline() +"\n");
                        }

                    } else {                              // openning tag
                        // filling string tag in a buffer
                        while (txt.charAt(i) != '>') {
                            tag.append(txt.charAt(i));
                            i++;
                        }
                        //pushing tag in stack
                        stack.push(tag.toString());
                        // System.out.println(tag.toString());
                        if (!checkClosedtag(errorline,tag.toString())){
                            ErrorMsg +=("Element has no close tag for " + tag.toString() + " in line " + getErrorline() +'\n');
                        }

                    }

                }

            }

        }

    }
    private boolean checkClosedtag (int line , String txt ){
        Scanner scan = new Scanner(this.Xml);
        String x ,open,closed,check;
        int startline =0;
        open = "<" + txt + ">";
        closed = "</"+ txt + ">";

        while (scan.hasNextLine()){
            x = scan.nextLine();
            startline++;
            //System.out.println(x);
            if (startline < line){continue;}
            if (x.contains(open) && x.contains(closed) && line == startline){
                return true;
            } else if (x.contains(open) && startline > line) {
                return false;
            } else if (x.contains(closed)) {
                return true;
            }

        }
        return false;
    }
    private boolean checkOpentag (Stack<String> stack , String txt ){
        String x ;
        Stack<String> temp = new Stack<String>();
        temp.addAll(stack);
        temp.pop();
        while (!temp.isEmpty()){
            x = temp.pop();
            if ((txt.replace("/","")).equals(x.replace("/","")) && x.charAt(0) != '/'){
                return true;
            }else if ((txt.replace("/","")).equals(x.replace("/","")) && x.charAt(0) == '/'){
                return false;}
        }

        return false;
    }

    private int getErrorline(){return this.errorline;}


}
