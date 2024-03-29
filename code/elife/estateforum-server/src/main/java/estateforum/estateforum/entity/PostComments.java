package estateforum.estateforum.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
/**
 * postComments class
 *
 * @author 符永锐
 * @date 2019/07/04*/
@Document(collection = "postComments")
public class PostComments {

    @Id
    private String commenterName;
    private String pid;


    private String commentsTime;
    private String postComment;

    /**楼数*/
    private int location;

    public PostComments() {super();}

    public PostComments(String pid,String commenterName,String commentsTime,String postComment,int location){
        this.pid=pid;
        this.commenterName=commenterName;
        this.commentsTime=commentsTime;
        this.postComment=postComment;
        this.location=location;

    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setCommenterName(String commenter) {
        this.commenterName = commenter;
    }

    public void setCommentsTime(String commentsTime) {
        this.commentsTime = commentsTime;
    }

    public void setpostComment(String postComment) {
        this.postComment = postComment;
    }



    public String getPid() {
        return pid;
    }

    public  String getCommenterName() {
        return commenterName;
    }

    public String getCommentsTime() {
        return commentsTime;
    }

    public String getpostComment() {
        return postComment;
    }
    @Override
    public int hashCode(){
        int result = 0;
        result = 31 * result + (commenterName != null ? commenterName.hashCode() : 0);
        result = 31 * result + (commentsTime != null ? commentsTime.hashCode() : 0);
        return result;
    }
}
