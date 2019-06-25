package bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZhijiaAssemble {
    @CsvBindByName(column = "工号")
    public String id;
    @CsvBindByName(column = "物料编码")
    public String code;
    @CsvBindByName(column = "物料描述")
    public String desc;
}
