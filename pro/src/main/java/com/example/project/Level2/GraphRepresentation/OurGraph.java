package com.example.project.Level2.GraphRepresentation;

import java.util.Vector;

public class OurGraph {
    public Vector<User> users;
    public OurGraph()
    {
        users=new Vector<User>();
    }
    public OurGraph(String xml){
        getUsers(xml);
    }
    Vector<User> getVectorOfUsers(){
        return users;
    }
    public OurGraph(Vector<User> users)
    {
        this.users=users;
    }
    public void getUsers(String s){
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
                    user.getFollowers().clear();
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
                        user.getFollowers().add(Integer.valueOf(simplified));
                    }
                    else if(openingTagName.equals("id") && followersflag ==0){
                        user.setId(Integer.valueOf(simplified));
                    }
                    else if(openingTagName.equals("name")){
                        user.setName(simplified);
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
                    user.setPosts(userPosts);
                }
                else if(closingTagName.equals("post")){
                    postflag=0;
                    if(mypost!=null)
                        userPosts.add(mypost);
                }
            }
        }
        ////////////////////////////////////////////////////////
        for (int i=0;i<users.size();i++)
        {
            for(int j = 0; j< users.get(i).getFollowers().size(); j++)
            {
                int user_exist=1;
                for(int k=0;k<users.size();k++)
                {
                    if(users.get(k).getId()==users.get(i).getFollowers().get(j))
                    {
                        user_exist=0;
                    }
                }
                if(user_exist==1)
                {
                    User adding_user=new User();
                    adding_user.setId(users.get(i).getFollowers().get(j));
                    users.add(adding_user);
                }
            }
        }
        /////////////////////////////////////////////////////////
        this.users = users;
    }
    public int get_number_of_users()
    {
        return users.size();
    }
    // Most influncer
    public String get_most_influencer_user()
    {
        String output = "";
        int number_of_followers=0;
        User most_influencer_user=null;
        for(int i=0;i<users.size();i++)
        {
            if(number_of_followers< users.get(i).getFollowers().size())
            {
                number_of_followers= users.get(i).getFollowers().size();
                most_influencer_user=users.get(i);
            }
        }
        output = "The most influencer user is "+most_influencer_user.getName();
        return output;
    }

    public User getUserFromId(Integer id){
        User user = null;
        for (User u: users) {
                if(id.equals(u.getId())){
                    user = u;
                    break;
                }
        }
        return user;
    }
    // Mutual followers
    public String getMutualFollowers(User user1, User user2) {
        Vector<User> mutual = new Vector<>();
        User mutualuser = null;
        String output = "";
        for (Integer id : user1.getFollowers()) {
            for (Integer id2: user2.getFollowers()) {
                if (id.equals(id2)){
                    mutualuser = getUserFromId(id);
                    mutual.add(mutualuser);
                }
            }
        }
        output += "Mutual followers between "+ user1.getName() + " and " + user2.getName() + " :" + "\n";
        String k="";
        String total="";
        for (User u : mutual) {
            k += u.getName() +" , ID : " + u.getId() + "\n";
        }
        if(k.length()==0){
            total = "No mutual users";
        }
        else {
            total = output+k;
        }
        return total;
    }
    // SuggestionList
    private Vector<User> SuggestionList(User user){
        Vector<User> List = new Vector<User>();
        User suggest = null;
        List.add(user);
        for (User u :users) {
            if(u.getId() == user.getId()) {continue;}
            boolean IsFollower = false;
            for (Integer id:user.getFollowers()) {
                if (id.equals(u.getId())  ){
                   IsFollower = true;
                }

            }
            if(!IsFollower){
                suggest = u;
                List.add(suggest);
            }
        }

        return List;
    }
    public String SuggestionListForEach(){
        Vector<Vector<User>> list = new Vector<>();
        String output = "";
        for (User u :users) {
            list.add(SuggestionList(u));
        }

        for (Vector<User> v : list) {
            int i = 0;
            if(v.size() == 1){
                continue;
            }
            else {
                for (User u : v) {
                    if (i == 0) {
                        output += "Suggestion list for " + u.getName() + " : ";
                    } else {
                        output += u.getName() + " ";
                    }
                    i++;

                }
            }
            output += "\n";
        }

        return output;
    }
    // Most active
    private int TotalConnection(User user){
        int counter = 0;
        counter += user.getFollowers().size();
        for (User u :users) {
            boolean isConnected = false;
            if(u.getId() == user.getId()){continue;}
            for (Integer id2 : user.getFollowers()){
                if (id2.equals(u.getId())){
                    isConnected= true;
                }
            }
            if(isConnected){continue;}
            for (Integer id :u.getFollowers()) {

                if (id.equals(user.getId())){
                    counter++;
                }
            }
        }
        return counter;
    }

    public String Mostactive(){
        int index = 0;
        Integer max = Integer.MIN_VALUE;
        Vector<Integer> totalconnectlist = new Vector<>();
        User user = null;
        for (User u:users) {
            totalconnectlist.add(Integer.valueOf(TotalConnection(u)));
        }
        for (Integer i : totalconnectlist) {
            if (i > max){
                max = i;
            }
        }
        index = totalconnectlist.indexOf(max);
        user = users.get(index);
        String output ="Most active user Name : "+ user.getName() + " , ID : "+ user.getId();
        return  output;
    }
    //Post search
    public String searchPostsAndTopics(String wordOrTopic){
        String finalString="";
        for(int u=0;u<users.size();u++){
            User user = users.get(u);
            for(int p=0;p<user.getPosts().size();p++){
                Post post = user.getPosts().get(p);
                int flagToGetOneTopic=0;
                for(int t=0;t<post.getTopics().size();t++){
                    String topic = post.getTopics().get(t);
                    if(topic.equals(wordOrTopic) && flagToGetOneTopic==0){
                        finalString+= "user id : " + user.getId() + "\n" + "User name : " + user.getName() + "\n" + "post Number : "+ (p+1) + "\n" + post.getBody() +"\n"+"\n";
                        flagToGetOneTopic=1;
                    }
                }
                if(post.getBody().contains(wordOrTopic)){
                    finalString+= "user id : " + user.getId() + "\n" + "User name : " + user.getName() + "\n" + "post Number : "+ (p+1) + "\n" + "Post : " + post.getBody() +"\n"+"\n";
                }
            }
        }
        return finalString;
    }
    public Vector<Integer> GetIDs() {
        Vector<Integer> V = new Vector<>();
        for (User u : users) {
            V.add(u.getId());
        }
        return V;
    }
    //String searchedpost = searchPostsAndTopics(string)
    //String  =

}