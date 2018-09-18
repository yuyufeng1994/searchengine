package top.yuyufeng.share.searchengine.model;

/**
 * 服务中的车次
 *
 * @author yuyufeng
 * @date 2017/10/17
 */
public class ServiceBusDetailDTO {
    private String departure;
    private String destination;
    private String busDate;
    private String busTime;
    private String ticketPrice;
    private String departureStation;
    private String ticketLeft;
    private String busType;
    private String dataFrom;
    private String busNo;

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBusDate() {
        return busDate;
    }

    public void setBusDate(String busDate) {
        this.busDate = busDate;
    }

    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getTicketLeft() {
        return ticketLeft;
    }

    public void setTicketLeft(String ticketLeft) {
        this.ticketLeft = ticketLeft;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    @Override
    public String toString() {
        return "ServiceBusDetailDTO{" +
                "departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", busDate='" + busDate + '\'' +
                ", busTime='" + busTime + '\'' +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", ticketLeft='" + ticketLeft + '\'' +
                ", busType='" + busType + '\'' +
                ", dataFrom='" + dataFrom + '\'' +
                ", busNo='" + busNo + '\'' +
                '}';
    }
}
