package top.yuyufeng.share.searchengine;

import com.github.pagehelper.PageInfo;
import top.yuyufeng.share.searchengine.model.StationTo;
import top.yuyufeng.share.searchengine.utils.LuceneUtils;

/**
 * @author yuyufeng
 * @date 2018/9/14.
 */
public class SearchDatas {
    public static void main(String[] args) {
        PageInfo<StationTo> pageInfo =LuceneUtils.doSearch("hz ph", 1);
        if (pageInfo.getList() != null) {
            for (StationTo stationTo : pageInfo.getList()) {
                System.out.println(stationTo);
            }
        }
    }
}
