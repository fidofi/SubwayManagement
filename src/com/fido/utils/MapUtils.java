package com.fido.utils;

import java.util.HashMap;
import java.util.Map;

import com.fido.domain.Station;
import com.fido.domain.Subway;

public class MapUtils {
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
      public static String getSameSubwayName(Station first,Station second,Station third){
    	  HashMap<Integer,Subway> map1=first.getSubwayMap();
		  HashMap<Integer,Subway> map2=second.getSubwayMap();
		  HashMap<Integer,Subway> map3=third.getSubwayMap();
    	  if((!ifsame(first,second,third))){
    	  for(Integer snumber:map2.keySet()){
 		     if(map3.containsKey(snumber)){
 		    	 return map3.get(snumber).getBname();
 		     }
 		     //也是3号线的两条分支换乘的特殊处理~
 		     if(map3.containsKey(302)&&map2.containsKey(3)){
 		    	     return map3.get(302).getBname();
 		     }
 		     if(map3.containsKey(3)&&map2.containsKey(302)){
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
