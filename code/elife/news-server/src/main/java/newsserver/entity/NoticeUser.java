package newsserver.entity;

import javax.persistence.*;

/**
 * postComments class
 *
 * @author 符永锐
 * @date 2019/07/05*/
@Entity
@Table(name = "noticeuser")
@IdClass(noticeUserMap.class)
public class NoticeUser {
    @Id
    @Column(name = "id")
    private int id;
    @Id
    @Column(name = "username")
    private String username;


    public NoticeUser(){

    }
    public NoticeUser(int noticeId,String username){
        this.id=noticeId;
        this.username=username;
    }
    public void setUsername(String username) {
        this.username=username;
    }

    public void setNoticeId(int noticeId) {
        this.id=noticeId;
    }

    public String getUsername() {
        return this.username;
    }

    public int getNoticeId() {
        return this.id;
    }
}