package bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountTableCsv {
    @CsvBindByName(column = "工号-箱头-分箱")
    private String boxId;
    @CsvBindByName(column = "梯种")
    private String tGroup;
    @CsvBindByName(column = "物料编码")
    private String code;
    @CsvBindByName(column = "物料描述")
    private String desc;
    @CsvBindByName(column ="数量")
    private String num;
    @CsvBindByName(column = "箱头作业指令产出日期")
    private String produceDate;
    @CsvBindByName(column = "物料要求送货日期")
    private String deliDate;

    private String daoXueCode;
    private String daoXueDesc;
    private String tiLaGanCode;
    private String tiLaGanDesc;
    private Integer sortId;
    private Integer serialId;

    private String zhiJiaAssemble;
    private String zhiJiaAssembleDesc;
    private String imageCode;



}
