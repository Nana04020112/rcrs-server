
import java.util.*;
public class CollectionPractice{
    public static void main(String[] args) {
        List<String> cityList=new ArrayList<>();
        cityList.add("北京");
        cityList.add("上海");
        cityList.add("杭州");
        cityList.add("合肥");
        cityList.add("南京");
        System.out.println("-----List 城市列表 -----");
        for(String city:cityList){
            System.out.println(city);
        }
        Set<Integer> numSet=new HashSet<>();
        int[] arr={2,5,6,8,9,7,5,8,9,6};
        for(int num:arr){
            numSet.add(num);
        }
        System.out.println("-----Set数字集合-----");
        System.out.println("添加了10个数字，去重后元素："+numSet);
        Map<String,Integer> userMap=new HashMap<>();
        userMap.put("小明",18);
        userMap.put("小黄",19);
        userMap.put("小昊",24);
        userMap.put("小控",26);
        System.out.println("-----Map姓名年龄-----");
        for(Map.Entry<String,Integer> entry:userMap.entrySet()){
            System.out.println("姓名："+entry.getKey()+"年龄："+entry.getValue);
        }
    }
}
