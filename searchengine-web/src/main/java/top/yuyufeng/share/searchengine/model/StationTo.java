package top.yuyufeng.share.searchengine.model;

/**
 * @author yuyufeng
 * @date 2018/9/14.
 */
public class StationTo {
    private String id;
    private String startAreaId;
    private String startAreaName;
    private String startAreaNameAbbr;
    private String startAreaNamePinYin;
    private String zoneId;
    private String zoneName;
    private String zoneNameAbbr;
    private String zoneNamePinYin;
    private String keywordsGroup;

    public StationTo() {
    }

    public StationTo(String id, String startAreaId, String startAreaName, String startAreaNameAbbr, String startAreaNamePinYin, String zoneId, String zoneName, String zoneNameAbbr, String zoneNamePinYin) {
        this.id = id;
        this.startAreaId = startAreaId;
        this.startAreaName = startAreaName;
        this.startAreaNameAbbr = startAreaNameAbbr;
        this.startAreaNamePinYin = startAreaNamePinYin;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.zoneNameAbbr = zoneNameAbbr;
        this.zoneNamePinYin = zoneNamePinYin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartAreaId() {
        return startAreaId;
    }

    public void setStartAreaId(String startAreaId) {
        this.startAreaId = startAreaId;
    }

    public String getStartAreaName() {
        return startAreaName;
    }

    public void setStartAreaName(String startAreaName) {
        this.startAreaName = startAreaName;
    }

    public String getStartAreaNameAbbr() {
        return startAreaNameAbbr;
    }

    public void setStartAreaNameAbbr(String startAreaNameAbbr) {
        this.startAreaNameAbbr = startAreaNameAbbr;
    }

    public String getStartAreaNamePinYin() {
        return startAreaNamePinYin;
    }

    public void setStartAreaNamePinYin(String startAreaNamePinYin) {
        this.startAreaNamePinYin = startAreaNamePinYin;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneNameAbbr() {
        return zoneNameAbbr;
    }

    public void setZoneNameAbbr(String zoneNameAbbr) {
        this.zoneNameAbbr = zoneNameAbbr;
    }

    public String getZoneNamePinYin() {
        return zoneNamePinYin;
    }

    public void setZoneNamePinYin(String zoneNamePinYin) {
        this.zoneNamePinYin = zoneNamePinYin;
    }

    public String getKeywordsGroup() {
        return startAreaName+" "+startAreaNameAbbr+" "+startAreaNamePinYin+" "+zoneName+" "+zoneNameAbbr+" "+zoneNamePinYin;
    }

    public void setKeywordsGroup(String keywordsGroup) {
        this.keywordsGroup = keywordsGroup;
    }

    @Override
    public String toString() {
        return "StationTo{" +
                "id='" + id + '\'' +
                ", startAreaId='" + startAreaId + '\'' +
                ", startAreaName='" + startAreaName + '\'' +
                ", startAreaNameAbbr='" + startAreaNameAbbr + '\'' +
                ", startAreaNamePinYin='" + startAreaNamePinYin + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", zoneName='" + zoneName + '\'' +
                ", zoneNameAbbr='" + zoneNameAbbr + '\'' +
                ", zoneNamePinYin='" + zoneNamePinYin + '\'' +
                '}';
    }
}
