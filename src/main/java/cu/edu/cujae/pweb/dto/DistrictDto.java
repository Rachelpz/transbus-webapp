package cu.edu.cujae.pweb.dto;

public class DistrictDto {
    private String district_id;
    private String district_name;
    private boolean newRecord;

    public DistrictDto() {
        super();
    }

    public DistrictDto(String district_id, String district_name, boolean newRecord) {
        this.district_id = district_id;
        this.district_name = district_name;
        this.newRecord = newRecord;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }
}
