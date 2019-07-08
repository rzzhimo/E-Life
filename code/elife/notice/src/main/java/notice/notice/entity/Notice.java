package notice.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * postComments class
 *
 * @author 符永锐
 * @date 2019/07/05*/
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @Column(name = "id")
    private int noticeId;
    @Column(name = "time")
    private String noticeTime;
    @Column(name = "content")
    private String noticeContent;
    @Column(name = "managername")
    private String managerName;
    @Column(name = "communityId")
    private int communityId;
    Notice(){
        super();
    }
    public Notice(String noticeTime,String noticeContent,String managerName,int communityId){

        this.noticeTime=noticeTime;
        this.noticeContent=noticeContent;
        this.managerName=managerName;
        this.communityId=communityId;

    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public void setManagerame(String managername) {
        this.managerName = managerName;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public int getCommunityId() {
        return communityId;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public String getNoticeTime() {
        return noticeTime;
    }
}
