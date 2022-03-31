/*
 * Entity object being persisted to the database
 */
package yeah;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Oscar
 */

@Entity
@Table(name = "test18036512")
public class Post implements Serializable, Comparable<Post>{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue //FIGURE THIS OUT
    @Column(name = "postid")
    private long postID;

    @Column (name = "timeposted")
    private String time;
    
    @Column (name = "parentpost")
    private long parentPost;
    
    @Column (name = "content")
    private String content;
    
    @Column (name = "userposted")
    private String user;
    
    public Post(){
    }
    
    public long getPostID() {
        return postID;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }

    public long getParentPost(){
        return parentPost;
    }
    
    public void setParentPost(long parentPost){
        this.parentPost = parentPost;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTime(){
        return time;
    }
    
    public void setTime(String time){
        this.time = time;
    }
    
    public String getUser(){
        return user;
    }
    
    public void setUser(String user){
        this.user = user;
    }

    //compareTo for which post is older, to sort posts in a thread by age.
    @Override
    public int compareTo(Post o) {
        return (int)(postID - o.getPostID());
    }
}
