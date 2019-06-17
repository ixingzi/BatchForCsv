import bean.CountTableCsv;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class CommonUtil {
    public static void putOneOrTwo(Map<String,List<CountTableCsv>> listMap){
        for(List<CountTableCsv> list:listMap.values()) {
            int size = list.size();
            for (int i = 0; i < list.size() / 2; i++) {
                list.get(i).setSortId(1);
            }
            for (int i = list.size() / 2; i < (list.size() / 2) * 2; i++) {
                list.get(i).setSortId(2);
            }
        }
    }
    public static String nullToStr(String str){
        if(str==null){
            return "";
        }
        return str;
    }
    public static int compare(String str1,String str2){
        if (StringUtils.isBlank(str1)){
            return 1;
        }
        if (StringUtils.isBlank(str2)){
            return -1;
        }
        return StringUtils.compare(str1,str2);
    }
}
