package top.yuyufeng.share.searchengine.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import top.yuyufeng.share.searchengine.model.ServiceBusDetailDTO;
import top.yuyufeng.share.searchengine.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuyufeng
 * @date 2018/9/17.
 */
public class RemotingBusService {
    public static List<ServiceBusDetailDTO> queryByBababus(String begin, String end, String date) throws Exception {
        int page = 1;
        List<ServiceBusDetailDTO> ServiceBusDetailDTOList = new ArrayList<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("type", "ticket");
        querys.put("from", begin);
        querys.put("to", end);
        querys.put("date", date);
        String host = "http://bus.bababus.com";
        String path = "/ticket/ticketList.htm";
        Map<String, String> headers = new HashMap<>();
        boolean hasNextPage = true;
        while (hasNextPage) {

            hasNextPage = false;
            HttpResponse response = HttpUtils.doGet(host, path, "GET", headers, querys);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(result);
            try {
                //查出所有页数
                JSONArray jsonArray = jsonObject.getJSONObject("content").getJSONArray("busNumberList");
                if (jsonArray != null && jsonArray.size() >= 10) {
                    hasNextPage = true;
                    querys.put("page", ++page + "");
                }
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject innerObject = jsonArray.getJSONObject(i);
                    ServiceBusDetailDTO ServiceBusDetailDTO = new ServiceBusDetailDTO();
                    ServiceBusDetailDTO.setBusDate(innerObject.getString("leaveDate"));
                    ServiceBusDetailDTO.setBusTime(innerObject.getString("leaveTime"));
                    ServiceBusDetailDTO.setBusType(innerObject.getString("vehicleMode"));
                    ServiceBusDetailDTO.setDeparture(begin);
                    ServiceBusDetailDTO.setDepartureStation(innerObject.getString("beginStationName"));
                    ServiceBusDetailDTO.setDestination(innerObject.getString("endStationName"));
                    ServiceBusDetailDTO.setTicketPrice(innerObject.getString("fullPrice"));
                    ServiceBusDetailDTO.setTicketLeft(innerObject.getString("remainSeat"));
                    ServiceBusDetailDTO.setBusNo(innerObject.getString("busId"));
                    ServiceBusDetailDTO.setDataFrom("http://bus.bababus.com");
                    ServiceBusDetailDTOList.add(ServiceBusDetailDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return ServiceBusDetailDTOList;
    }

    public static void main(String[] args) throws Exception {
        List<ServiceBusDetailDTO> busDetailDTOList = queryByBababus("杭州", "上海", "2018-09-17");
        for (ServiceBusDetailDTO serviceBusDetailDTO : busDetailDTOList) {
            System.out.println(serviceBusDetailDTO);
        }
    }
}
