package bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCode {
    @CsvBindByName(column = "工号")
    public String id;
    @CsvBindByName(column = "物料编码")
    public String code;
    @CsvBindByName(column = "立柱图号")
    public String picNum;
}
