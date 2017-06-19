package com.fido.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fido.domain.ListAndCount;
import com.fido.domain.Station;

public class ShowPathUtils {
	
	 //�Ը�����վ��·�ߣ�д��������·
        public static String showPath(ArrayList<Station> list){
        	StringBuffer buffer=new StringBuffer();
        	String change=null;
        	 for(int k=0;k<list.size();k++){
      			  if(k!=0&&k!=list.size()-1){
      				  Station map1= list.get(k-1);
      				  Station map2=list.get(k);
      				  Station map3=list.get(k+1);
      				  if(!MapUtils.ifsame(map1, map2,map3)){
      					    change=MapUtils.getSameSubwayName(map1,map2, map3);
      				  }  
      			  }
      			  if(change!=null){
      				buffer.append("��"+list.get(k).getSname()+"����"+change+"��");
      			  }
      			  else{
      				  if(k!=list.size()-1)
      				  buffer.append(list.get(k).getSname()+"��"); 
      				  else{
      				  buffer.append(list.get(k).getSname()+"\n\r");   
      				  buffer.append("*******************"+"\n\r");
      				  }
      			  }
      			  change=null;
      		  }
        	 return buffer.toString();

        }
        //��ʾ��������
        public static String showLessChange(List<ListAndCount> path){
          	HashMap<Integer,Integer> findLess=new HashMap<Integer,Integer>();//�ҳ����˴������ٵ�
          	int end=path.size()<=10?path.size():10;
	       	for(int i=0;i<end;i++){
	       		   findLess.put(i, 0);
	       	}
	       	String change=null;
	        for(int i=0;i<end;i++){
	        	 ArrayList<Station> temp=path.get(i).getList();//��ø���·
	        	 for(int k=0;k<temp.size();k++){
	       			  if(k!=0&&k!=temp.size()-1){
	       				  Station map1= temp.get(k-1);
	       				  Station map2=temp.get(k);
	       				  Station map3=temp.get(k+1);
	       				  if(!MapUtils.ifsame(map1, map2,map3)){
	       					    change=MapUtils.getSameSubwayName(map1,map2, map3);
	       				  }

	       			  }
	       			  if(change!=null){
	       				int value=findLess.get(i);
	       				 findLess.put(i, ++value);

	       			  }
	       			  change=null;
	       		  }
	        }
	        for(Map.Entry<Integer, Integer> entry:findLess.entrySet()){
	        	 System.out.println(entry.getKey()+"**"+entry.getValue());
	        }

	        //�������ҳ��������ٵ�
	     	List<Integer> index=new ArrayList<Integer>();
		     for(Map.Entry<Integer, Integer> entry:findLess.entrySet()){
		    	  int value=entry.getValue();
		    	  index.add(value);
		     }
		     int min=index.get(0);
		     int dex=0;
		     for(int i=1;i<index.size();i++){
		    	   if(min>index.get(i)){
		    		   min=index.get(i);
		    		   dex=i;
		    	   }
		     }
		     for(int i=0;i<path.get(dex).getList().size();i++){
		    	  System.out.println(path.get(dex).getList().get(i));
		     }
          return ShowPathUtils.showPath(path.get(dex).getList());
	}
        }

