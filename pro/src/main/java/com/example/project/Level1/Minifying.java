package com.example.project.Level1;

public class Minifying {
    String beforeManify;
    String afterManify;
    public Minifying(String s){
        this.beforeManify = s;
        this.afterManify = minify();
    }
    private String minify() {
        int ss=0;
        String s = beforeManify;
        while (s.charAt(ss)!='<')
        {
            ss++;
        }
        s=s.substring(ss,s.length());

        ss=s.length()-1;
        while(s.charAt(ss)!='>'){
            ss--;
        }
        s=s.substring(0,ss+1);

        StringBuffer newString = new StringBuffer(s);

        String lastString = "";

        int end_of_each_block=0;
        int start_of_each_block=0;
        int there_is_next_block=0;
        int is_there_words_between_tags=0;
        int first_tag=0;

        for(int i=first_tag;i<newString.length();i++)
        {


            int flag=0;
            if(newString.charAt(i)=='<')
            {
                first_tag=1;
                there_is_next_block=0;
                start_of_each_block=i;
                is_there_words_between_tags=0;
                flag=1;
                int zzz=0;
                while(newString.charAt(i)!='>'&&zzz==0)
                {

                    i++;
                }

                i++;

                if(i<newString.length())
                {

                    while((newString.charAt(i)!='<')&&(newString.length()>i))
                    {
                        if((newString.charAt(i)!=' ')&&(newString.charAt(i)!='\t')&&(newString.charAt(i)!='\n'))
                        {
                            is_there_words_between_tags=1;
                        }

                        i++;
                        if(newString.charAt(i)=='<')
                        {
                            there_is_next_block=1;
                        }

                    }
                }
                i--;
                end_of_each_block=i;
            }
            i=start_of_each_block;
            if(flag==1&&there_is_next_block==1)
            {
                if(is_there_words_between_tags==0)
                {

                    while(i<=end_of_each_block&&i<newString.length())
                    {
                        if((newString.charAt(i)!=' ')&&(newString.charAt(i)!='\n')&&(newString.charAt(i)!='\t')&&(i<newString.length()))
                        {
                            lastString+=newString.charAt(i);
                        }
                        i++;

                    }

                }
                else
                {
                    while(i<=end_of_each_block&&i<newString.length())
                    {

                        lastString+=newString.charAt(i);

                        i++;
                    }
                }
                flag=0;
            }

            else if (there_is_next_block==0)
            {


                i=start_of_each_block;
                while(i<newString.length())
                {
                    lastString+=newString.charAt(i);
                    i++;
                }
                break;
            }
            i=end_of_each_block;

        }
        return lastString;
    }
    public String getManifiedString(){
        return afterManify;
    }
}
