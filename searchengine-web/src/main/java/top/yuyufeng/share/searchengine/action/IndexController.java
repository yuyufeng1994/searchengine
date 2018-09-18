package top.yuyufeng.share.searchengine.action;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yuyufeng.share.searchengine.model.ServiceBusDetailDTO;
import top.yuyufeng.share.searchengine.model.StationTo;
import top.yuyufeng.share.searchengine.service.RemotingBusService;
import top.yuyufeng.share.searchengine.utils.ChineseSpellTool;
import top.yuyufeng.share.searchengine.utils.LuceneUtils;

import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "/searchBykeyWords")
    @ResponseBody
    PageInfo<StationTo> searchBykeyWords(String keywords, Integer page) {
        if (page == null) {
            page = 1;
        }
        keywords = ChineseSpellTool.trimSpellWithChinese(keywords);
        PageInfo<StationTo> pageInfo = LuceneUtils.doSearch(keywords, 1);
        return pageInfo;
    }

    @RequestMapping(value = "search")
    String search(Model model) {
        return "search";
    }

    @RequestMapping(value = "doQuery")
    @ResponseBody
    List<ServiceBusDetailDTO>  doQuery(String beginStation,String endStation,String leaveDate) {
        List<ServiceBusDetailDTO> serviceBusDetailDTOList = null;
        try {
            serviceBusDetailDTOList = RemotingBusService.queryByBababus(beginStation, endStation, leaveDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceBusDetailDTOList;
    }


}
