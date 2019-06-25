import bean.*;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class FastRun {
    public static List<CountTableCsv> lizhuList, otherList;
    private static int NotMatch = 999;

    public static void main(String[] args) throws IOException {

        File directory = new File("");//设定为当前文件夹
        try {
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        } catch (Exception e) {
        }

        HashMap<String, String> map = getProperties();
        String dir = map.get("dir") == null ? "" : map.get("dir");
        CsvFactory.getInstance().importCountTable(dir + map.get("count"));
        CsvFactory.getInstance().importDaoXue(dir + map.get("daoxue"));
        CsvFactory.getInstance().importLiZhu(dir + map.get("lizhu"));
        CsvFactory.getInstance().importTiLagan(dir + map.get("tilangan"));
        System.out.println(map);
        CsvFactory.getInstance().importZhiJiaAssemble(dir + map.get("zhijia"));
        CsvFactory.getInstance().importImageCode(dir + map.get("imagecode"));

        //提取立柱
        lizhuList = CsvFactory.countTableCsvs.stream().filter((obj) -> "立柱组合".equals(obj.getDesc()) || StringUtils.startsWith(obj.getDesc(), "立柱装配")).collect(Collectors.toList());

        //匹配 立柱 NotMatch 的不参与后续匹配，不包括立柱装备
        Set<String> lizhuExist = CsvFactory.liZhuCsvs.stream().map(LiZhuCsv::getCode).collect(Collectors.toSet());
        lizhuList = lizhuList.stream().map((obj) -> {
            if (!lizhuExist.contains(obj.getCode()) && !StringUtils.startsWith(obj.getDesc(), "立柱装配")) {
                obj.setSortId(NotMatch);
            }
            return obj;
        }).collect(Collectors.toList());

        //匹配导靴
        Map<String, DaoXueCsv> daoXueExist = CsvFactory.daoXueCsvs.stream().collect(Collectors.toMap(DaoXueCsv::getId, obj -> obj, (k1, k2) -> k1));
        lizhuList = lizhuList.stream().map((obj) -> {
            if (daoXueExist.containsKey(obj.getCode())) {
                obj.setDaoXueCode(daoXueExist.get(obj.getCode()).getCode());
                obj.setDaoXueDesc(daoXueExist.get(obj.getCode()).getDesc());
            }
            return obj;
        }).collect(Collectors.toList());
        //匹配提拉杆
        Map<String, TiLaganCsv> tiLaGanExist = CsvFactory.tiLaganCsvs.stream().collect(Collectors.toMap(TiLaganCsv::getId, obj -> obj, (k1, k2) -> k1));
        lizhuList = lizhuList.stream().map((obj) -> {
            if (tiLaGanExist.containsKey(obj.getCode())) {
                obj.setTiLaGanCode(tiLaGanExist.get(obj.getCode()).getCode());
                obj.setTiLaGanDesc(tiLaGanExist.get(obj.getCode()).getDesc());
            }
            return obj;
        }).collect(Collectors.toList());
        //匹配支架装配
        Map<String, ZhijiaAssemble> zhiJiaExist = CsvFactory.zhijiaAssembleCsvs.stream().collect(Collectors.toMap(ZhijiaAssemble::getId, obj -> obj, (k1, k2) -> k1));
        lizhuList = lizhuList.stream().map((obj) -> {
            if (zhiJiaExist.containsKey(obj.getCode())) {
                obj.setZhiJiaAssemble(zhiJiaExist.get(obj.getCode()).getCode());
                obj.setZhiJiaAssembleDesc(zhiJiaExist.get(obj.getCode()).getDesc());
            }
            return obj;
        }).collect(Collectors.toList());
        //匹配立柱图号
        Map<String, ImageCode> imageCodeExist = CsvFactory.imageCodeCsvs.stream().collect(Collectors.toMap(ImageCode::getId, obj -> obj, (k1, k2) -> k1));
        lizhuList = lizhuList.stream().map((obj) -> {
            if (imageCodeExist.containsKey(obj.getCode())) {
                obj.setImageCode(imageCodeExist.get(obj.getCode()).getPicNum());
            }
            return obj;
        }).collect(Collectors.toList());

        Map<String, List<CountTableCsv>> listMap;
        /*给相同的物料编码赋值*/
        listMap = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null && StringUtils.isNotEmpty(obj.getCode()))
                .collect(Collectors.groupingBy(CountTableCsv::getCode));
        CommonUtil.putOneOrTwo(listMap);

        /*给立柱装配赋值*/
        listMap = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null && StringUtils.startsWith(obj.getDesc(), "立柱装配"))
                .collect(Collectors.groupingBy(obj -> "other"));
        CommonUtil.putOneOrTwo(listMap);

        /*导靴提拉杆都为空，按支架装配 分*/
        listMap = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null && StringUtils.isAllBlank(obj.getDaoXueCode(), obj.getZhiJiaAssemble()))
                .collect(Collectors.groupingBy(obj -> {
                    String tGroup = obj.getTGroup();
                    if (tGroup != null && tGroup.split("-").length > 0) {
                        return CommonUtil.nullToStr(tGroup.split("-")[0]);
                    }
                    return CommonUtil.nullToStr(obj.getTGroup());
                }));
        CommonUtil.putOneOrTwo(listMap);



        /*给相同的体拉杆和导靴赋值*/
        listMap = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null)
                .collect(Collectors.groupingBy(obj -> {
                    return CommonUtil.nullToStr(obj.getDaoXueCode()) + "-" + CommonUtil.nullToStr(obj.getTiLaGanCode());
                }));
        CommonUtil.putOneOrTwo(listMap);



        /*给相同的导靴赋值*/
        listMap = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null)
                .collect(Collectors.groupingBy(obj -> CommonUtil.nullToStr(obj.getDaoXueCode())));
        CommonUtil.putOneOrTwo(listMap);

        /*给相同的提拉杆赋值*/
        listMap = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null)
                .collect(Collectors.groupingBy(obj -> CommonUtil.nullToStr(obj.getTiLaGanCode())));
        CommonUtil.putOneOrTwo(listMap);

        /*都不同赋值*/
        listMap = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null)
                .collect(Collectors.groupingBy(obj -> "other"));
        CommonUtil.putOneOrTwo(listMap);
        Optional<CountTableCsv> only = lizhuList.stream()
                .filter(obj -> obj.getSortId() == null).findAny();
        //为奇数项最后随机赋值
        only.orElse(new CountTableCsv()).setSortId((int) (Math.random() * 2) + 1);

        //序号赋值
        listMap = lizhuList.stream().filter(obj -> obj.getSortId() != NotMatch).sorted((c1, c2) -> {
            //按立柱装配排序
            boolean c1Assemble = StringUtils.startsWith(c1.getDesc(), "立柱装配");
            boolean c2Asswmble = StringUtils.startsWith(c2.getDesc(), "立柱装配");
            if (c1Assemble && !c2Asswmble) {
                return 1;
            } else if (!c1Assemble && c2Asswmble) {
                return -1;
            }
            //按sortid排序
            if (!c1.getSortId().equals(c2.getSortId())) {
                return Integer.compare(c1.getSortId(), c2.getSortId());
            }

            //两个都有的排在前面
            int emptyCountC1 = (StringUtils.isNotBlank(c1.getTiLaGanCode()) ? 0 : 4) + (StringUtils.isNotBlank(c1.getDaoXueCode()) ? 0 : 2)+(StringUtils.isNotBlank(c1.getZhiJiaAssemble())?0:1);
            int emptyCountC2 = (StringUtils.isNotBlank(c2.getTiLaGanCode()) ? 0 : 4) + (StringUtils.isNotBlank(c2.getDaoXueCode()) ? 0 : 2)+(StringUtils.isNotBlank(c2.getZhiJiaAssemble())?0:1);
            if (c1.getSortId() == 1) {
                if (emptyCountC1 == 6) {
                    emptyCountC1 =-1;
                }
                if (emptyCountC2 == 6) {
                    emptyCountC2 =-1;
                }
            }
            if (emptyCountC1 != emptyCountC2) {
                return Integer.compare(emptyCountC1, emptyCountC2);
            }
            if (!CommonUtil.nullToStr(c1.getZhiJiaAssemble()).equals(CommonUtil.nullToStr(c2.getZhiJiaAssemble()))) {
                return CommonUtil.compare(c1.getZhiJiaAssemble(), c2.getZhiJiaAssemble());
            }
            if (!CommonUtil.nullToStr(c1.getDaoXueCode()).equals(CommonUtil.nullToStr(c2.getDaoXueCode()))) {
                return CommonUtil.compare(c1.getDaoXueCode(), c2.getDaoXueCode());
            }
            if (!CommonUtil.nullToStr(c1.getTiLaGanCode()).equals(CommonUtil.nullToStr(c2.getTiLaGanCode()))) {

                return CommonUtil.compare(c1.getTiLaGanCode(), c2.getTiLaGanCode());
            }
            if (!CommonUtil.nullToStr(c1.getImageCode()).equals(CommonUtil.nullToStr(c2.getImageCode()))) {

                return CommonUtil.compare(c1.getImageCode(), c2.getImageCode());
            }
            return CommonUtil.compare(c1.getCode(), c2.getCode());

        }).collect(Collectors.groupingBy(obj -> CommonUtil.nullToStr(String.valueOf(obj.getSortId()))));
        for (List<CountTableCsv> list : listMap.values()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSerialId(i + 1);
            }
        }

        //将立柱和非立柱配对
        otherList = CsvFactory.countTableCsvs.stream().filter((obj) -> !("立柱组合".equals(obj.getDesc())) && !StringUtils.startsWith(obj.getDesc(), "立柱装配")).collect(Collectors.toList());

        Map<String, CountTableCsv> otherMap = lizhuList.stream().collect(Collectors.toMap(obj -> {
            if (StringUtils.isBlank(obj.getBoxId())) {
                return "";
            }
            String[] strs = obj.getBoxId().split("-");
            if (strs.length > 0) {
                return strs[0];
            }
            return "";
        }, obj -> obj, (k1, k2) -> k1));

        for (CountTableCsv countTableCsv : otherList) {
            if (StringUtils.isNoneBlank(countTableCsv.getBoxId())) {
                String[] strs = countTableCsv.getBoxId().split("-");
                if (strs.length > 0 && otherMap.containsKey(strs[0])) {
                    countTableCsv.setSortId(otherMap.get(strs[0]).getSortId());
                    countTableCsv.setSerialId(otherMap.get(strs[0]).getSerialId());
                }
            }
        }
        //复制到导出列表
        List<CountOutCsv> list = new ArrayList<>();
        for (CountTableCsv countTableCsv : lizhuList) {
            CountOutCsv countOutCsv = new CountOutCsv();
            try {
                BeanUtils.copyProperties(countOutCsv, countTableCsv);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(countOutCsv);
        }
        for (CountTableCsv countTableCsv : otherList) {
            CountOutCsv countOutCsv = new CountOutCsv();
            try {
                BeanUtils.copyProperties(countOutCsv, countTableCsv);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(countOutCsv);
        }
        HashMap<String, Integer> tableOrderMap = new HashMap<>();
        tableOrderMap.put("工号-箱头-分箱", 2);
        tableOrderMap.put("梯种", 3);
        tableOrderMap.put("物料编码", 4);
        tableOrderMap.put("物料描述", 5);
        tableOrderMap.put("数量", 6);
        tableOrderMap.put("导靴", 11);
        tableOrderMap.put("导靴物料描述", 12);
        tableOrderMap.put("提拉杆", 13);
        tableOrderMap.put("提拉杆物料描述", 14);
        tableOrderMap.put("箱头作业指令产出日期", 7);
        tableOrderMap.put("物料要求送货日期", 8);
        tableOrderMap.put("生产线", 9);
        tableOrderMap.put("序号", 10);
        tableOrderMap.put("支架装配", 15);
        tableOrderMap.put("支架装配-物料描述", 16);
        tableOrderMap.put("立柱图号", 17);


        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("name.csv"), "gbk");
            HeaderColumnNameMappingStrategy<CountOutCsv> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(CountOutCsv.class);
            strategy.setColumnOrderOnWrite((c1, c2) ->
                    Integer.compare(tableOrderMap.get(c1), tableOrderMap.get(c2))
            );
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(out).
                    withMappingStrategy(strategy).build();
            for (CountOutCsv countOutCsv : list) {
                beanToCsv.write(countOutCsv);
                out.flush();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Success Output");
        System.in.read();

    }

    public static HashMap<String, String> getProperties() {
        HashMap<String, String> map = new HashMap<>(128);
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("config.properties"), "gbk"));
            properties.load(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取key对应的value值
        map.put("count", properties.getProperty("count"));
        map.put("tilangan", properties.getProperty("tilangan"));
        map.put("lizhu", properties.getProperty("lizhu"));
        map.put("daoxue", properties.getProperty("daoxue"));
        map.put("zhijia", properties.getProperty("zhijia"));
        map.put("imagecode", properties.getProperty("imagecode"));

        return map;
    }
}
