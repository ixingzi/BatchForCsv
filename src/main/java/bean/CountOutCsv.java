package bean;

import com.opencsv.bean.CsvBindByName;

public class CountOutCsv {
    @CsvBindByName(column = "工号-箱头-分箱")
    private String boxId;
    @CsvBindByName(column = "梯种")
    private String tGroup;
    @CsvBindByName(column = "物料编码")
    private String code;
    @CsvBindByName(column = "物料描述")
    private String desc;
    @CsvBindByName(column = "数量")
    private String num;
    @CsvBindByName(column = "导靴")
    private String daoXueCode;
    @CsvBindByName(column = "导靴物料描述")
    private String daoXueDesc;
    @CsvBindByName(column = "提拉杆")
    private String tiLaGanCode;
    @CsvBindByName(column = "提拉杆物料描述")
    private String tiLaGanDesc;
    @CsvBindByName(column = "箱头作业指令产出日期")
    private String produceDate;
    @CsvBindByName(column = "物料要求送货日期")
    private String deliDate;
    @CsvBindByName(column = "生产线")
    private Integer sortId;
    @CsvBindByName(column = "序号")
    private Integer serialId;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String gettGroup() {
        return tGroup;
    }

    public void settGroup(String tGroup) {
        this.tGroup = tGroup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDaoXueCode() {
        return daoXueCode;
    }

    public void setDaoXueCode(String daoXueCode) {
        this.daoXueCode = daoXueCode;
    }

    public String getDaoXueDesc() {
        return daoXueDesc;
    }

    public void setDaoXueDesc(String daoXueDesc) {
        this.daoXueDesc = daoXueDesc;
    }

    public String getTiLaGanCode() {
        return tiLaGanCode;
    }

    public void setTiLaGanCode(String tiLaGanCode) {
        this.tiLaGanCode = tiLaGanCode;
    }

    public String getTiLaGanDesc() {
        return tiLaGanDesc;
    }

    public void setTiLaGanDesc(String tiLaGanDesc) {
        this.tiLaGanDesc = tiLaGanDesc;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getDeliDate() {
        return deliDate;
    }

    public void setDeliDate(String deliDate) {
        this.deliDate = deliDate;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }
}
