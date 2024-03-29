package estateforum.estateforum.controller;

import estateforum.estateforum.entity.PostComments;
import estateforum.estateforum.service.PostCommentsService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * postComments class
 *
 * @author 符永锐
 * @date 2019/07/04*/

@RequestMapping(path = "/api/postComments")
@Controller
public class PostCommentsController {

    @Autowired
    PostCommentsService postCommentsService;

    @RequestMapping(path = "/findComments")
    @ResponseBody
    public JSONArray findComment(HttpServletRequest request, @RequestParam String pid, @RequestParam int page, @RequestParam int size){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (StringUtils.isEmpty(name) || "2".equals(role)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", 0);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        return postCommentsService.findAllByPidPage(pid,page,size);
    }
    @RequestMapping(path = "/addComments")
    @ResponseBody
    public JSONObject addComments(HttpServletRequest request, @RequestParam String pid, @RequestParam  String commenterName, @RequestParam String postComment){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (StringUtils.isEmpty(name) || "2".equals(role)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", 0);
            return jsonObject;
        } else {
            net.minidev.json.JSONObject object = new net.minidev.json.JSONObject();
            if (postComment.equals("") || pid.equals("") || commenterName.equals("")) {
                object.put("addComments", "0");
                object.put("message", "评论内容不能为空");
                return object;
            }
            List<PostComments> l1 = postCommentsService.findAllByPid(pid);
            int location = 2;
            for (int i = 0; i < l1.size(); i++) {
                int tmp = l1.get(i).getLocation();
                if (tmp >= location) {
                    location = tmp + 1;
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String commentsTime = df.format(new Date());
            PostComments postComments = new PostComments(pid, commenterName, commentsTime, postComment, location);
            postCommentsService.saveComments(postComments);
            object.put("addComments", "1");
            object.put("message", "发表评论成功");
            return object;
        }
    }
    @RequestMapping(path = "/deleteComments")
    @ResponseBody
    public JSONObject deleteComments(HttpServletRequest request, @RequestParam String pid,@RequestParam int location){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (StringUtils.isEmpty(name) || !"1".equals(role)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", 0);
            return jsonObject;
        } else {
            postCommentsService.deleteComments(pid, location);
            net.minidev.json.JSONObject object = new net.minidev.json.JSONObject();
            object.put("deleteComments", "1");
            object.put("message", "删除评论成功");
            return object;
        }
    }
}
