package com.example.project.Level1.Error;

import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class ErrorCheckAndCorrect {
    String beforeModString;
    String afterModString;
    String errorMessage;
    //Constructor
    public ErrorCheckAndCorrect(String s){
        this.beforeModString = s;
        errorMessage = error_detect_and_solve();
    }
    //return error message
    private String error_detect_and_solve(){
        Vector<String> added_opening_tags=new Vector<String>();
        Vector<String>added_closed_tags=new Vector<String>();
        StringBuffer newString = new StringBuffer(beforeModString);
        Stack<String> stackName = new Stack<String>();
        Stack<Integer>openingTagIndex = new Stack<Integer>();
        int first_opening_flag=0;
        int start_index_of_opening_tag=0;
        String openingTagName_f = "";
        String closingTagName_f = "";
        for(int i=0;i<newString.length();i++)
        {
            if (newString.charAt(i) == '<' && newString.charAt(i + 1) != '/' && newString.charAt(i + 1) != '?' && first_opening_flag == 0)//opening tag
            {
                i++;
                start_index_of_opening_tag = i;
                while (newString.charAt(i) != '>') {
                    openingTagName_f = openingTagName_f + newString.charAt(i);
                    i++;
                }
                first_opening_flag = 1;

            } else if (newString.charAt(i) == '<' && newString.charAt(i + 1) == '/')//closing tag
            {
                closingTagName_f = "";

                i += 2;
                while (newString.charAt(i) != '>') {
                    closingTagName_f = closingTagName_f + newString.charAt(i);
                    i++;
                }
            }
        }
        String openingTagName_f2="";
        String closingTagName_f2="";
        int no_of_same_opening_tag=0;
        int no_of_same_closing_tag=0;
        for(int i=0;i<newString.length();i++)
        {
            if (newString.charAt(i) == '<' && newString.charAt(i + 1) != '/' && newString.charAt(i + 1) != '?' )//opening tag
            {
                openingTagName_f2="";
                i++;

                while (newString.charAt(i) != '>') {
                    openingTagName_f2 = openingTagName_f2 + newString.charAt(i);
                    i++;
                }

                if(openingTagName_f.equals(openingTagName_f2))
                {
                    no_of_same_opening_tag++;
                }
                if(closingTagName_f.equals(openingTagName_f2))
                {
                    no_of_same_closing_tag++;
                }
            }
            else if (newString.charAt(i) == '<' && newString.charAt(i + 1) == '/')//closing tag
            {
                closingTagName_f2 = "";
                //start of opening tag name
                i += 2;

                //get name of closing tag
                while (newString.charAt(i) != '>') {
                    closingTagName_f2 = closingTagName_f2 + newString.charAt(i);
                    i++;

                }
                if(openingTagName_f.equals(closingTagName_f2))
                {
                    no_of_same_opening_tag++;
                }
                if(closingTagName_f.equals(closingTagName_f2))
                {
                    no_of_same_closing_tag++;
                }
            }
        }
        if(no_of_same_opening_tag==1) //closing tag of outer node is missing
        {
            newString.insert(newString.length(),"</"+openingTagName_f+">");
            added_closed_tags.add(openingTagName_f);
        }
        else if(no_of_same_closing_tag==1)  //opening tag of outer node is missing
        {

            newString.insert(start_index_of_opening_tag-1,"<"+closingTagName_f+">\n");

            added_opening_tags.add(closingTagName_f);
        }
        String firstTagName="";
        int flagFirstTagName=0;
        for(int i=0;i<newString.length()-2;i++){
            String st =newString.substring(i,i+2);
            if(st.charAt(0)=='<' && st.charAt(1)!='/'&&st.charAt(1)!='?')//opening tag
            {
                String openingTagName ="";
                int j=i+1;//start of openning tag name
                while(newString.charAt(j)!='>'){
                    openingTagName=openingTagName+newString.charAt(j);
                    j++;
                }
                stackName.push(openingTagName);//push onto the stack
                openingTagIndex.push(i+openingTagName.length()+"<>".length());
                if(flagFirstTagName==0){
                    flagFirstTagName=1;
                    firstTagName=firstTagName+openingTagName;
                }
            }
            else if(st.charAt(0)=='<' && st.charAt(1)=='/')//closing tag
            {
                String closingTagName ="";
                //start of openning tag name
                int j=i+2;
                //get name of closing tag
                while(newString.charAt(j)!='>'){
                    closingTagName=closingTagName+newString.charAt(j);
                    j++;
                }
                //if topOfStack == closing then pop
                if(!stackName.empty()&&stackName.peek().equals(closingTagName)){//same name pop
                    stackName.pop();
                    openingTagIndex.pop();
                }
                else{
                    //to allow only one node in the xml doc
                    if(closingTagName.equals(firstTagName)){
                        //ignore
                    }
                    else {
                        //adding openning tag for any closing tag that has no openning one
                        String addedOpenningTag = '\n' + "<" + closingTagName + ">";
                        int e = i - 1;//go left by one
                        while (newString.charAt(e) != '>') {
                            e--;
                        }
                        e++;
                        newString.insert(e, addedOpenningTag);
                        added_opening_tags.add(closingTagName);
                        i = i + addedOpenningTag.length();
                    }
                }
            }
        }
        String tag="";
        while(!stackName.empty()){
            int start=openingTagIndex.peek();
            while(newString.charAt(start)!='<' && start < newString.length()){
                openingTagIndex.pop();
                openingTagIndex.push(start);
                start++;
            }
            if(firstTagName.equals(stackName.peek()) && stackName.size()==1){
                //ignore
                tag=stackName.peek();
            }
            else {
                added_closed_tags.add(stackName.peek());
                newString.insert(openingTagIndex.peek(), /*added*/'\n'+"</" + stackName.peek() + '>' + '\n'/*removes \n*/);
            }
            stackName.pop();
            openingTagIndex.pop();
        }
        //to make only one node that contain many nodes in xml
        if(!tag.equals("") && (newString.lastIndexOf("</"+tag+'>')<0 || newString.lastIndexOf("</"+tag+'>')>newString.length())){
            added_closed_tags.add(tag);
            newString.append('\n'+"</"+tag+'>');
        }
        //removing spaces at beginning
        Scanner newScanner = new Scanner(newString.toString());
        String lastString="";
        while(newScanner.hasNextLine()){
            String line=newScanner.nextLine();
            line.replaceAll("\n","");
            for(int i=0;i<line.length();i++){
                if(line.charAt(i)==' ' || line.charAt(i)=='\t'){
                    line=line.substring(i+1,line.length());
                    i--;
                }
                else if(line.charAt(i)=='\n'){
                    System.out.println("found");
                    line=line.substring(i+1,line.length());
                    i--;
                    break;
                }
                else{
                    line.replaceAll("\n","");
                    break;
                }
            }
            lastString+=line;
        }
        StringBuffer k =new StringBuffer(lastString);
        for(int i=0;i<k.length()-3;i++){
            if(k.charAt(i)=='>'){
                k.insert(i+1,'\n');
                i++;
            }
            if(k.charAt(i)!='\n' && k.charAt(i+1)=='<' && k.charAt(i+2)=='/'){
                k.insert(i+1,'\n');
                i=i+1;
            }
        }
        lastString=k.toString();
        String modSpecs="";
        if(added_opening_tags.size()!=0){
            modSpecs = modSpecs + "Added opening tags :\n";
            for(int i=0;i<added_opening_tags.size();i++)
            {
                modSpecs=modSpecs+added_opening_tags.get(i)+"  ";
            }
        }
        if(added_closed_tags.size()!=0){
            modSpecs=modSpecs+"\n"+"Added closing tags :\n";
            for(int i=0;i<added_closed_tags.size();i++) {
                modSpecs=modSpecs+added_closed_tags.get(i) + "  ";
            }
        }
        afterModString=lastString;
        return modSpecs;
    }
    public String getXmlAfterCorrection(){
        return afterModString;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
