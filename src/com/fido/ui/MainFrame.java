package com.fido.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.fido.domain.Graph;
import com.fido.domain.Station;
import com.fido.domain.Subway;
import com.fido.service.MakeGraphServ;
import com.fido.service.MakePathServ;
import com.fido.utils.MapUtils;

public class MainFrame {

	private JFrame frame;
	private JTextField start;
	private JTextField end;
	private JTextArea textArea;
	private JComboBox comboBox;
	private JPanel panel;//放背景图的
	private MakeGraphServ service;
    private MakePathServ serv;
    private Graph graph;//图结构
    
    private JLabel timeLabel;//显示程序用时
    
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		MainFrame window = new MainFrame();
		window.frame.setVisible(true);
		window.frame.setResizable(false);

	}

	public MainFrame() {
		service = new MakeGraphServ();
		// 界面一开始就判断是否存在图结构的磁盘文件，若没有要新建，有的话直接搜索，不用新建
		File file = new File("D://graph.txt");
		if (!file.exists()) {
			List<Station> commonList = service.getDao().getCommonList();
			for (int i = 0; i < commonList.size(); i++) {
				service.getCommon().addCommonStation(commonList.get(i));
				System.out.println(commonList.get(i));
			}
			for (int k = 0; k < 8; k++) {
				service.create(k + 1);
			}
			service.create(302);
			Station one = service.getDao().getStationByName("林和西");
			Station two = service.getDao().getStationByName("体育西路");
			service.insertEdge(one, two);
			service.getDao().write2txt(service.getGraph());
		}
		graph = service.getDao().read();
		serv = new MakePathServ(graph);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 506, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel=new JPanel(){
			protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon("src/3.jpg");  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, frame.getWidth(),  
                        frame.getHeight(), icon.getImageObserver());  
  
            }  
  
        };
        panel.setLayout(null);
		frame.setContentPane(panel);
	    timeLabel = new JLabel("查询用时：");
		timeLabel.setFont(new Font("方正卡通简体", Font.PLAIN, 16));
		timeLabel.setBounds(10, 300, 200, 15);
		panel.add(timeLabel);

		JLabel titleLabel = new JLabel("广州地铁");
		titleLabel.setBounds(197, 10, 114, 40);
		titleLabel.setFont(new Font("华康海报体W12(P)", Font.PLAIN, 27));
		panel.add(titleLabel);

		JLabel smallTitle = new JLabel("GuangZhou Metro");
		smallTitle.setBounds(199, 48, 114, 15);
		smallTitle.setFont(new Font("华康海报体W12", Font.PLAIN, 14));
		panel.add(smallTitle);

		//线路查询选项卡面板
		JPanel stationPanel = new JPanel(){
			protected void paintComponent(Graphics g) {  
  
  
            }  
		};
		stationPanel.setBounds(70, 101, 388, 168);
		stationPanel.setLayout(null);

		JButton buttonByStation = new JButton("查询");
		buttonByStation.setBounds(185, 127, 80, 31);
		stationPanel.add(buttonByStation);
		buttonByStation.setFont(new Font("方正卡通简体", Font.PLAIN, 18));
		buttonByStation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				long startTime=System.currentTimeMillis();   //获取开始时间
				  textArea.setText(null);
				  String startName=start.getText();
				  String endName=end.getText();
				   if(startName!=null&&endName!=null&&startName.trim()!=""&&endName.trim()!=""){
					   if(startName.equals(endName)){
						   JOptionPane.showMessageDialog(null, "起点与终点相同！");
						   return;
					   }
					 System.out.println(startName+"***"+endName);
					List<ArrayList<Station>> path=serv.findAllPath(startName, endName);
					if(path==null){
						JOptionPane.showMessageDialog(null, "未找到该路径！");
						   return;
					}
					System.out.println("原本有的长度为"+path.size());
			       	HashMap<Integer,Integer> findLess=new HashMap<Integer,Integer>();//找出换乘次数最少的
			       	for(int i=0;i<path.size();i++){
			       		System.out.print("放进去的I"+i+"、");
			       		 findLess.put(i, 0);
			       	}
			       	String change=null;	
//			       	int writeIndex=(path.size()>6?path.size()-3:0);
			       	int writeIndex=path.size()-1;
			       	int j=0;
			       	for(int i=path.size()-1;i>=0;i--){ //取最后三个记录,最短放第一个	
			       		int current=0;
                         if(i>=writeIndex){
			       		textArea.append("途径站点最少："+"\n\r");
                         }
			       		  ArrayList<Station> temp=path.get(i);
			       		  for(int k=0;k<temp.size();k++){
			       			  if(k!=0&&k!=temp.size()-1){
			       				  if(current!=0){
			       				  HashMap<Integer,Subway> map1= temp.get(k-1).getSubwayMap();
			       				  HashMap<Integer,Subway> map2=temp.get(k).getSubwayMap();
			       				  HashMap<Integer,Subway> map3=temp.get(k+1).getSubwayMap();
			       				  if(!MapUtils.ifsame(map1, map3)){
			       					    change=MapUtils.getSameSubwayName(map1,map2, map3);
			       				  }
			       				  }
			       				  else{
				       				  HashMap<Integer,Subway> map1= temp.get(0).getSubwayMap();
				       				  HashMap<Integer,Subway> map2=temp.get(k).getSubwayMap();
				       				  HashMap<Integer,Subway> map3=temp.get(k+1).getSubwayMap();
				       				  if(!MapUtils.ifsame(map1, map3)){
				       					    change=MapUtils.getSameSubwayName(map1,map2, map3);
				       				  }
			       				  }
			       					  
			       			  }
			       			  if(change!=null){
			       				if(i>=writeIndex)
			       				  textArea.append("在"+temp.get(k).getSname()+"换乘"+change+"→");
			       				int value=findLess.get(i);
			       				  findLess.put(i, ++value);
			       				   current=findLess.get(i);//获得当前的换乘次数

			       			  }
			       			  else{
			       				if(i>=writeIndex){
			       				  if(k!=temp.size()-1)
			       				  textArea.append(temp.get(k).getSname()+"→"); 
			       				  else{
			       					textArea.append(temp.get(k).getSname()+"\n\r");   
			       				  textArea.append("*******************"+"\n\r");
			       				  }
			       				}
			       			  }
			       			  change=null;
			       		  }
			       	}
			    	//主要是为了找出换乘最小的线路，如何知道map的value，求所有value最小时对应的Key 即path集合里的下标
			       	List<Integer> index=new ArrayList<Integer>();
			     for(Map.Entry<Integer, Integer> entry:findLess.entrySet()){
			    	  int key=entry.getKey();
			    	  int value=entry.getValue();
			    	  index.add(value);
			     }
			     System.out.println("长度"+index.size());
			     int min=index.get(0);
			     System.out.println("初始最小值为"+min);
			     int dex=0;
			     for(int i=1;i<index.size();i++){
			    	 System.out.println("最小值："+min);
			    	   if(min>=index.get(i)){
			    		   min=index.get(i);
			    		   dex=i;
			    	   }
			     }
			     System.out.println("下标："+dex);
			     //接下来输出换乘次数最少的路线，无非是确定好path中对应的下标，其实可以在上面抽取相同方法出来写的，因为接下来的都是重复代码。。之前没注意
			  //   int changeto =(path.size()>6?(path.size()-dex-1):dex);
			     int changeto=dex;
			     System.out.println("确定好的下标："+changeto);
			     System.out.println(path.size());
			     ArrayList<Station> lessChange=path.get(changeto);//换乘次数最少的,这里有个坑，注意两次下标的起点是不一样的
			     textArea.append("换乘次数最少的路线为："+"\n\r");
			     String change2=null;
			     int current2=0;
			     for(int k=0;k<lessChange.size();k++){    
      	       			  if(k!=0&&k!=lessChange.size()-1){
                                  if(current2!=0){
            	       				  HashMap<Integer,Subway> map1= lessChange.get(k-1).getSubwayMap();
            	       				  HashMap<Integer,Subway> map2=lessChange.get(k).getSubwayMap();
            	       				  HashMap<Integer,Subway> map3=lessChange.get(k+1).getSubwayMap();
            	       				  if(!MapUtils.ifsame(map1, map3)){
            	       					    change2=MapUtils.getSameSubwayName(map1,map2, map3);
            	       				  }
                                  }
                                  else{
                                	  HashMap<Integer,Subway> map1= lessChange.get(0).getSubwayMap();
            	       				  HashMap<Integer,Subway> map2=lessChange.get(k).getSubwayMap();
            	       				  HashMap<Integer,Subway> map3=lessChange.get(k+1).getSubwayMap();
            	       				  if(!MapUtils.ifsame(map1, map3)){
            	       					    change2=MapUtils.getSameSubwayName(map1,map2, map3);
            	       				  }
                                  }                               	  
    	       			  }
	       			  if(change2!=null){
	       				  textArea.append("在"+lessChange.get(k).getSname()+"换乘"+change2+"→");
	       				  current2++;
	       			  }
	       			  else{
	       				  if(k!=lessChange.size()-1)
	       				  textArea.append(lessChange.get(k).getSname()+"→"); 
	       				  else{
	       					textArea.append(lessChange.get(k).getSname()+"\n\r");   
	       				  }
	       			  }
	       			  change2=null;
			     }
			}
				   long endTime=System.currentTimeMillis(); //获取结束时间		   
				   System.out.println("程序运行时间： "+(double)(endTime-startTime)/1000+"s");
				   String timeByStation=(double)(endTime-startTime)/1000+"秒";
				   timeLabel.setText("查询用时:"+timeByStation);
			}
		});

		JLabel startLabel = new JLabel("起点：");
		startLabel.setFont(new Font("方正卡通简体", Font.PLAIN, 20));
		startLabel.setBounds(87, 23, 69, 29);
		stationPanel.add(startLabel);

		JLabel endLabel = new JLabel("终点：");
		endLabel.setFont(new Font("方正卡通简体", Font.PLAIN, 20));
		endLabel.setBounds(87, 75, 69, 31);
		stationPanel.add(endLabel);

		start = new JTextField();
		start.setBounds(166, 24, 99, 28);
		stationPanel.add(start);
		start.setColumns(10);

		end = new JTextField();
		end.setText("");
		end.setBounds(166, 79, 99, 28);
		stationPanel.add(end);
		end.setColumns(10);
		
		panel.add(stationPanel);

	//地铁线号查询选项卡面板
		JPanel subwayPanel = new JPanel(){
			protected void paintComponent(Graphics g) {  

  
            }  
		};
		subwayPanel.setBounds(70, 101, 388, 168);
		subwayPanel.setLayout(null);

		JLabel label_one = new JLabel("请选择线路");
		label_one.setBounds(150, 24, 95, 20);
		label_one.setFont(new Font("方正卡通简体", Font.PLAIN, 19));
		subwayPanel.add(label_one);

		 comboBox = new JComboBox(); //下拉框
		comboBox.setBounds(150, 68, 100, 21);
		List<String> list=service.getDao().getAllSubwayName();
		for(int i=0;i<list.size();i++){
			 comboBox.addItem(list.get(i));//将所有线路的名字加入到下拉框列表中
		}
		subwayPanel.add(comboBox);

		JButton buttonBySubway = new JButton("查询");
		buttonBySubway.setFont(new Font("方正卡通简体", Font.PLAIN, 18));
		buttonBySubway.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long startTime=System.currentTimeMillis();   //获取开始时间
				       textArea.setText(null);
				 		List<Station> list=service.getDao().getStationListByBname((String)comboBox.getSelectedItem());	
				 		for(int i=0;i<list.size();i++){
				 			if(i!=list.size()-1)
				 				textArea.append(list.get(i).getSname()+"——");
				 			else
				 				textArea.append(list.get(i).getSname());	
				 		}
				 		long endTime=System.currentTimeMillis(); //获取结束时间

				 	      System.out.println("程序运行时间： "+(double)(endTime-startTime)/1000+"s");
				 	     String timeBySubway=(double)(endTime-startTime)/1000+"秒";
						   timeLabel.setText("查询用时:"+timeBySubway);
			}
		});
		buttonBySubway.setBounds(150, 122, 93, 23);
		subwayPanel.add(buttonBySubway);
		buttonBySubway.setFont(new Font("方正卡通简体", Font.PLAIN, 18));
		panel.add(subwayPanel);
		
		//选项卡
		JTabbedPane jtp = new JTabbedPane();
		jtp.setBounds(43, 96, 411, 217);
		jtp.add("线路查询", stationPanel);
		panel.add(jtp); 
		jtp.add("地铁查询", subwayPanel);
		
	    textArea = new JTextArea();
	    textArea.setRows(6);
	    JPanel panelOutput= new JPanel();
	    panelOutput.setBounds(43, 323, 411, 138);
	    panelOutput.setLayout(null);
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setBounds(0, 5, 411, 123);
	    panelOutput.add(scrollPane);
		textArea.setBackground(Color.WHITE);
		textArea.setLineWrap(true);
		panel.add(panelOutput);
		

	}
}
