package com.fido.utils;

import java.util.HashMap;

import com.fido.domain.Subway;

public class MapUtils {
      public static boolean ifsame(HashMap<Integer,Subway> map1,HashMap<Integer,Subway> map2){
    	   for(Integer snumber:map1.keySet()){
    		     if(map2.containsKey(snumber))
    		    	 return true;
    		     //3号线北延段和3号线被视为同一条线，其实这里不直接写在程序中比较利于维护
//    		     if((map2.containsKey(302)&&map1.containsKey(3))||(map2.containsKey(3)&&map1.containsKey(302)))
//    		    	 return true;
    	   }
    	   return false;
      }
      public static String getSameSubwayName(HashMap<Integer,Subway> map1,HashMap<Integer,Subway> map2,HashMap<Integer,Subway> map3){
    	  if((!ifsame(map1,map3))){
    	  for(Integer snumber:map2.keySet()){
 		     if(map3.containsKey(snumber))
 		    	 return map3.get(snumber).getBname();
 	   }
    	  }
 	         return null;
      }
}
