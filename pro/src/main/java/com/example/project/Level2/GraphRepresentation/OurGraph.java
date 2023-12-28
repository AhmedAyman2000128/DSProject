package com.example.project.Level2.GraphRepresentation;

import java.util.Vector;

public class OurGraph {
    public Vector<User> users;
    public OurGraph()
    {
        users=new Vector<User>();
    }

    Vector<User> getVectorOfUsers(){
        return users;
    }
    public OurGraph(Vector<User> users)
    {
        this.users=users;
    }

    public int get_number_of_users()
    {
        return users.size();
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