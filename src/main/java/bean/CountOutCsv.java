package bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    @CsvBindByName(column = "支架装配")
    private String zhiJiaAssemble;
    @CsvBindByName(column = "支架装配-物料描述")
    private String zhiJiaAssembleDesc;
    @CsvBindByName(column = "立柱图号")
    private String imageCode;

    public String gettGroup() {
        return tGroup;
    }
}
