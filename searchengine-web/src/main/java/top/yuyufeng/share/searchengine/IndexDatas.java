package top.yuyufeng.share.searchengine;

import org.springframework.util.StringUtils;
import top.yuyufeng.share.searchengine.model.StationTo;
import top.yuyufeng.share.searchengine.utils.LuceneUtils;
import top.yuyufeng.share.searchengine.utils.PinYinUtil;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yuyufeng
 * @date 2018/9/14.
 */
public class IndexDatas {
    public static Set<String> map = new HashSet<>();

    public static void main(String[] args) throws IOException {
        String thisLine = null;
        try {
            FileReader file = new FileReader(IndexDatas.class.getClassLoader().getResource("data/tb_tic_station_to.txt").getFile());
            BufferedReader br = new BufferedReader(file);
            while ((thisLine = br.readLine()) != null) {
                String[] thisLines = thisLine.split(" ");
                String startAreaId;
                String startAreaName;
                String zoneId;
                String zoneName;
                if (thisLines.length == 3) {
                    startAreaId = thisLines[0];
                    startAreaName = thisLines[1];
                    zoneId = "001";
                    zoneName = thisLines[2];
                } else if (thisLines.length == 4) {
                    startAreaId = thisLines[0];
                    startAreaName = thisLines[1];
                    zoneId = thisLines[2];
                    zoneName = thisLines[3];
                } else {
                    continue;
                }
                if(StringUtils.isEmpty(startAreaName)){
                    continue;
                }
                if(StringUtils.isEmpty(zoneName)){
                    continue;
                }
                String startAreaNameAbbr = PinYinUtil.cn2FirstSpell(startAreaName);
                String startAreaNamePinyin = PinYinUtil.cn2Spell(startAreaName);
                String zoneNameAbbr = PinYinUtil.cn2FirstSpell(zoneName);
                String zoneNamePinyin = PinYinUtil.cn2Spell(zoneName);
                StationTo stationTo = new StationTo();
                stationTo.setId(null);
                stationTo.setStartAreaId(startAreaId);
                stationTo.setStartAreaName(startAreaName);
                stationTo.setStartAreaNameAbbr(startAreaNameAbbr);
                stationTo.setStartAreaNamePinYin(startAreaNamePinyin);
                stationTo.setZoneId(zoneId);
                stationTo.setZoneName(zoneName);
                stationTo.setZoneNameAbbr(zoneNameAbbr);
                stationTo.setZoneNamePinYin(zoneNamePinyin);
                System.out.println(stationTo);
                StationTo stationIndex = LuceneUtils.doIndex(stationTo);
                System.out.println(stationIndex);
//                System.out.println(startAreaNamePinyin);
//                System.out.println(zoneNamePinyin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* for (String s : map) {
            System.out.println(s);
        }*/

    }
}
