package group.controller;

import group.service.BargainService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ztHou
 */
@RequestMapping(path = "/api/bargain")
@RestController
public class BargainController {
    private final BargainService bargainService;

    public BargainController(BargainService bargainService) {
        this.bargainService = bargainService;
    }

    @RequestMapping(path = "/addBargain")
    @ResponseBody
    public JSONObject addBargain(HttpServletRequest request, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String title,
                                 @RequestParam Long merchantId, @RequestParam String goods, @RequestParam String content,
                                 @RequestParam Integer status){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (StringUtils.isEmpty(name) || !("2".equals(role))) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", 0);
            return jsonObject;
        }
        return bargainService.addBargain(startTime, endTime, title, merchantId, goods, content, status);
    }

    @RequestMapping(path = "/deleteBargain")
    @ResponseBody
    public JSONObject deleteBargain(HttpServletRequest request, @RequestParam Long id){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (StringUtils.isEmpty(name) || !("2".equals(role))) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", 0);
            return jsonObject;
        }
        return bargainService.deleteBargain(id);
    }

    @RequestMapping(path = "/getAllBargain")
    @ResponseBody
    public JSONArray getAllBargain(HttpServletRequest request){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        if (StringUtils.isEmpty(name)) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("login", 0);
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        return bargainService.getAllBargain();
    }

    @RequestMapping(path = "/getBargainByMerchantId")
    @ResponseBody
    public JSONArray getBargainByMerchantId(HttpServletRequest request, @RequestParam Long id){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        if (StringUtils.isEmpty(name)) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("login", 0);
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        return bargainService.getBargainByMerchantId(id);
    }
}
