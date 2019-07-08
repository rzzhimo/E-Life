package notice.notice.controller;

import notice.notice.entity.Notice;
import notice.notice.entity.NoticeUser;
import notice.notice.repository.NoticeUserRepository;
import notice.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * postComments class
 *
 * @author 符永锐
 * @date 2019/07/05*/
@RequestMapping(path = "/api")
@Controller

public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeUserRepository noticeUserRepository;
    @RequestMapping(path = "/addNotice")
    @ResponseBody
    public String addNotice(@RequestParam String content,@RequestParam String managerName,@RequestParam int communityId, @RequestParam String username,@RequestParam int isMass){
        if("".equals(content)||"".equals(managerName)||communityId==0){
            return "信息不能为空";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String noticeTime = df.format(new Date());
        Notice notice=new Notice(noticeTime,content,managerName,communityId);
         int noticeId = noticeService.save(notice);
         if(isMass==0){
             NoticeUser noticeUser=new NoticeUser(noticeId,username);
             noticeUserRepository.save(noticeUser);

         }else {

         }
        return "发布物业通知成功";
    }
    @RequestMapping(path = "/findMyNotice")
    @ResponseBody
    public List<Notice> findMyNotice(@RequestParam String username){
        return noticeService.findByUsername(username);
    }
    @RequestMapping(path = "/deleteOneNotice")
    @ResponseBody
    public String deleteOneNotice(@RequestParam int noticeId){
        noticeService.deleteByNotcieId(noticeId);
        return "删除该条通知成功";
    }
    @RequestMapping(path = "/deleteMyNotice")
    @ResponseBody
    public String deleteMyNotice(@RequestParam String username){
        noticeService.deleteAllByUsername(username);
        return "删除所有通知成功";
    }
}
