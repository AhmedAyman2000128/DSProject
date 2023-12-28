package com.example.project.Level1;

import java.util.Scanner;

public class Indentation {
    private String s;

    public Indentation(String s){
        this.s = s;
        addIdententation();
    }

    private void addIdententation(){
        Scanner w=new Scanner(s);
        while(w.hasNextLine()){
            String r = w.nextLine();
        }
        String k ="";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' ' && i == 0){
                while(s.charAt(i)==' ') {
                    i++;
                }
            }
            if(s.charAt(i)=='\n') {
                k = k + s.charAt(i);
                i++;
                while (i<s.length() && s.charAt(i) == ' ') {
                    i++;
                }
            }
            if(i<s.length())
                k=k+s.charAt(i);
        }
        Scanner scanner =new Scanner(k);
        String u ="";
        String n="";
        while(scanner.hasNext()){
            String q = scanner.nextLine();
            if(q.length()>2 && q.charAt(0)=='<' && q.charAt(1)!='/'){
                n=n+u+q+'\n';
                u=u+"      ";
                int i=1;
                String word="";
                while(q.charAt(i)!='>'){
                    word=word+q.charAt(i);
                    i++;
                }
                String word2="";
                for(;i<q.length();i++){
                    int flag=0;
                    if(q.charAt(i)=='<'){
                        i=i+2;
                        while(q.charAt(i)!='>') {
                            word2 = word2 + q.charAt(i);
                            i++;
                        }
                        flag=1;
                    }
                    if(flag==1)break;
                }
                if(word.equals(word2)){
                    u=u.substring(0,u.length()-6);
                }
            }
            else if(q.length()>2 && q.charAt(0)=='<' && q.charAt(1)=='/'){
                if(u.length()!=0)
                    u=u.substring(0,u.length()-6);
                n=n+u+q+'\n';
            }
            else{
                n=n+u+q+'\n';
            }
        }
        this.s=n;
    }

    public String getIntendedString(){
        return s;
    }
}
