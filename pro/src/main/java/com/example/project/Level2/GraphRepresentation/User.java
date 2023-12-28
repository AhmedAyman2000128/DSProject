package com.example.project.Level2.GraphRepresentation;

import com.example.project.Level2.GraphRepresentation.Post;

import java.util.Vector;

public class User {
    //data members
    private String name;
    private int id;
    private Vector<Post>posts;
    private Vector<Integer>followers;
    //Constructor
    User(){
        name ="";
        id = 0;
        posts = new Vector<Post>();
        followers = new Vector<Integer>();
    }
    User(String name, int id, Vector<Post>posts, Vector<Integer>followers){
        this.name =name;
        this.id = id;
        this.posts = posts;
        this.followers = followers;
    }
    //Getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Vector<Post> getPosts() {
        return posts;
    }

    public Vector<Integer> getFollowers() {
        return followers;
    }

    //setters
    // set name of user
    public void setName(String name) {
        this.name = name;
    }
    // set id of user
    public void setId(int id) {
        this.id = id;
    }
    // set posts of user
    public void setPosts(Vector<Post>posts) {
        this.posts = posts;
    }
    // set followers of user
    public void setFollowers(Vector<Integer> followers) {
        this.followers = followers;
    }

    //parsing xml
    /*static Vector<User> getUsersInfo(String s){
        Vector<User>users = new Vector<User>();
        Vector<Post>userPosts = new Vector<Post>();
        Vector<String>postTopics;
        Post mypost =new Post();
        User user = null;
        int followersflag=0;
        int postflag = 0;
        for(int i=0;i<s.length()-2;i++){
            String st =s.substring(i,i+2);
            if(st.charAt(0)=='<' && st.charAt(1)!='/'&&st.charAt(1)!='?')//opening tag
            {
                String openingTagName ="";
                int j=i+1;//start of openning tag name
                while(s.charAt(j)!='>'){
                    openingTagName=openingTagName+s.charAt(j);
                    j++;
                }
                j++;
                if(openingTagName.equals("user")){
                    user = new User();
                    userPosts = new Vector<Post>();
                    userPosts.clear();
                }
                else if(openingTagName.equals("followers")){
                    followersflag=1;
                    user.followers.clear();
                }
                else if(openingTagName.equals("posts")){
                    userPosts = new Vector<Post>();
                    userPosts.clear();
                }
                else if(openingTagName.equals("post")){
                    postflag =1;
                    mypost = new Post();
                    postTopics = new Vector<String>();
                    postTopics.clear();
                }
                else if(user!=null && (openingTagName.equals("name") || openingTagName.equals("id") || openingTagName.equals("body") || openingTagName.equals("topic"))){
                    String betweenTag ="";
                    while(s.charAt(j)!='<'){
                        betweenTag=betweenTag+s.charAt(j);
                        j++;
                    }
                    String simplified ="";
                    int start=0;
                    int end=betweenTag.length()-1;
                    //triming
                    while(betweenTag.charAt(start)==' ' || betweenTag.charAt(start)=='\t' || betweenTag.charAt(start)=='\n'){
                        start++;
                    }
                    while(betweenTag.charAt(end)==' ' || betweenTag.charAt(end)=='\t' || betweenTag.charAt(end)=='\n'){
                        end--;
                    }
                    simplified = betweenTag.substring(start,end+1);
                    if(openingTagName.equals("id") && followersflag ==1){
                        user.followers.add(Integer.valueOf(simplified));
                    }
                    else if(openingTagName.equals("id") && followersflag ==0){
                        user.id = Integer.valueOf(simplified);
                    }
                    else if(openingTagName.equals("name")){
                        user.name = simplified;
                    }
                    else if(openingTagName.equals("body")){
                        mypost.setBody(simplified);
                    }
                    else if(openingTagName.equals("topic")){
                        mypost.addTopic(simplified);
                    }

                }
            }
            else if(st.charAt(0)=='<' && st.charAt(1)=='/')//closing tag
            {
                String closingTagName ="";
                //start of openning tag name
                int j=i+2;
                //get name of closing tag
                while(s.charAt(j)!='>'){
                    closingTagName=closingTagName+s.charAt(j);
                    j++;
                }
                if(closingTagName.equals("user")){
                    users.add(user);
                    user = null;
                }
                else if(closingTagName.equals("followers")){
                    followersflag=0;
                    //end of followers for this user
                }
                else if(closingTagName.equals("posts")){
                    user.posts = userPosts;
                }
                else if(closingTagName.equals("post")){
                    postflag=0;
                    if(mypost!=null)
                        userPosts.add(mypost);
                }
            }
        }
        return users;
    }*/
}