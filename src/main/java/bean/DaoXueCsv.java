package bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class DaoXueCsv {
    @CsvBindByName(column = "工号")
    public String id;
    @CsvBindByName(column = "物料编码")
    public String code;
    @CsvBindByName(column = "物料描述")
    public String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
