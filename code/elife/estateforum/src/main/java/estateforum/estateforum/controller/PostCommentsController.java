package estateforum.estateforum.controller;

import estateforum.estateforum.entity.PostComments;
import estateforum.estateforum.service.PostCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * postComments class
 *
 * @author 符永锐
 * @date 2019/07/04*/

@RequestMapping(path = "/api")
@Controller
public class PostCommentsController {

    @Autowired
    PostCommentsService postCommentsService;

    @GetMapping(path = "/findComments")
    @ResponseBody
    public List<PostComments> findComment(@RequestParam String pid){
        return postCommentsService.findAllByPid(pid);
    }
    @PostMapping(path = "/addComments")
    @ResponseBody
    public String addComments(@RequestParam String pid,@RequestParam  String commenterName,@RequestParam String postComment){
        if(postComment.equals("")||pid.equals("")||commenterName.equals("")){
            return "评论内容不能为空";
        }
        List<PostComments> p1=postCommentsService.findAllByPid(pid);
        int location = p1.size()+2;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String commentsTime = df.format(new Date());
        PostComments postComments = new PostComments(pid,commenterName,commentsTime,postComment,location);
        postCommentsService.saveComments(postComments);
        return "发表评论成功";
    }
    @GetMapping(path = "/deleteComments")
    @ResponseBody
    public String deleteComments(@RequestParam String pid,@RequestParam int location){
        postCommentsService.deleteComments(pid,location);
        return "删除评论成功";
    }
}