package top.yuyufeng.share.searchengine;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

/**
 * @author yuyufeng
 * @date 2018/9/14.
 */
public class IndexDatas {
    public static void main(String[] args) throws IOException {
        String thisLine = null;
        try {
//            System.out.println(IndexDatas.class.getClassLoader().getResource(""));
//            FileReader file = new FileReader("G://searchshare/tb_tic_station_to.txt");
            FileReader file = new FileReader(IndexDatas.class.getClassLoader().getResource("data/tb_tic_station_to.txt").getFile());
            BufferedReader br = new BufferedReader(file);
            while ((thisLine = br.readLine()) != null) {
                String[] thisLines = thisLine.split(" ");
                System.out.println(thisLines[0] + "|" + thisLines[1] + "|" + thisLines[2] + "|" + thisLines[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
