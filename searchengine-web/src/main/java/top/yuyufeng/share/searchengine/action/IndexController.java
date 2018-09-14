package top.yuyufeng.share.searchengine.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author yuyufeng
 * @date 2018/9/14.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/")
    String index(Model model) {
        model.addAttribute("userName", "俞育峰~");
        return "index";
    }

    @RequestMapping(value = "/date")
    @ResponseBody
    Date date(Model model) {
        return new Date();
    }
}
