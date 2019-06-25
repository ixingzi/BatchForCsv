import bean.*;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class CsvFactory {
    public static List<DaoXueCsv> daoXueCsvs;
    public static List<CountTableCsv> countTableCsvs;
    public static List<LiZhuCsv> liZhuCsvs;
    public static List<TiLaganCsv> tiLaganCsvs;
    public static List<ZhijiaAssemble> zhijiaAssembleCsvs;
    public static List<ImageCode> imageCodeCsvs;
    private static CsvFactory csvFactory=new CsvFactory();
    private CsvFactory(){}
    public static CsvFactory getInstance(){
        return csvFactory;
    }
    public void importDaoXue(String filename) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            daoXueCsvs = new CsvToBeanBuilder(new InputStreamReader(in,"gbk"))
                    .withType(DaoXueCsv.class).build().parse();
        } catch (FileNotFoundException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void importCountTable(String filename) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            countTableCsvs = new CsvToBeanBuilder(new InputStreamReader(in,"gbk"))
                    .withType(CountTableCsv.class).build().parse();
        } catch (FileNotFoundException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void importLiZhu(String filename){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            liZhuCsvs = new CsvToBeanBuilder(new InputStreamReader(in,"gbk"))
                    .withType(LiZhuCsv.class).build().parse();
        } catch (FileNotFoundException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void importTiLagan(String filename){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            tiLaganCsvs = new CsvToBeanBuilder(new InputStreamReader(in,"gbk"))
                    .withType(TiLaganCsv.class).build().parse();
        } catch (FileNotFoundException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void importZhiJiaAssemble(String filename){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            zhijiaAssembleCsvs = new CsvToBeanBuilder(new InputStreamReader(in,"gbk"))
                    .withType(ZhijiaAssemble.class).build().parse();
        } catch (FileNotFoundException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void importImageCode(String filename){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            imageCodeCsvs = new CsvToBeanBuilder(new InputStreamReader(in,"gbk"))
                    .withType(ImageCode.class).build().parse();
        } catch (FileNotFoundException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
