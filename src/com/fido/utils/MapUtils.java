package com.fido.utils;

import java.util.HashMap;
import java.util.Map;

import com.fido.domain.Station;
import com.fido.domain.Subway;

public class MapUtils {
	
	//判断第一第三点是否存在相同线路，若不同，则第二个点肯定是换乘点
	//当第一第三点存在相同线路时，还要判断它们三者是否同时存在相同线路，若不存在，则第二个点也是换乘点
      public static boolean ifsame(Station first,Station second,Station third){
    	      HashMap<Integer,Subway> map1= first.getSubwayMap();
    	      HashMap<Integer,Subway> map2= second.getSubwayMap();
			  HashMap<Integer,Subway> map3=third.getSubwayMap();
    	   for(Integer snumber:map1.keySet()){
    		     if(map3.containsKey(snumber)){
    		    	 if(map2.containsKey(snumber)){
    		    	     return true;
    		    	 }
    		    	 return false;
    		     }
    	   }
    	   return false;
      }
      //返回换乘点和她下一个点的公共线路，就是要显示的换乘线 
      public static String getSameSubwayName(Station first,Station second,Station third){
    	  HashMap<Integer,Subway> map1=first.getSubwayMap();
		  HashMap<Integer,Subway> map2=second.getSubwayMap();
		  HashMap<Integer,Subway> map3=third.getSubwayMap();
    	  if((!ifsame(first,second,third))){
    	  for(Integer snumber:map2.keySet()){
 		     if(map3.containsKey(snumber)){
 		    	 return map3.get(snumber).getBname();
 		     }
// 		     //也是3号线的两条分支换乘的特殊处理~
 		     if(map3.containsKey(302)&&map2.containsKey(3)&&!third.getSname().equals("体育西路")){
 		    	 
 		    	     return map3.get(302).getBname();
 		     }
 		     if(map3.containsKey(3)&&map2.containsKey(302)&&!third.getSname().equals("体育西路")){
 		    	return map3.get(3).getBname();
 		     }
 	   }
    	  }
 	         return null;
      }
      
      //遍历站点的所属线路的map，测试用
      public static void show(Station station){
    	  HashMap<Integer,Subway> map =station.getSubwayMap();
    	  for(Map.Entry<Integer, Subway> entry:map.entrySet()){
    		  System.out.print(entry.getKey()+"**"+entry.getValue().getBname()+"、");
    	  }
      }
}
