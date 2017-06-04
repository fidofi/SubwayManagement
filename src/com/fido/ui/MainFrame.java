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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		MainFrame window = new MainFrame();
		window.frame.setVisible(true);
		window.frame.setResizable(false);

	}
	public MainFrame() {
		service =new MakeGraphServ();
		File file=new File("D://graph.txt");
		if(!file.exists()){
			List<Station> commonList=service.getDao().getCommonList();
			for(int i=0;i<commonList.size();i++){
				 service.getCommon().addCommonStation(commonList.get(i));
				 System.out.println(commonList.get(i));
			}	
	        for(int k=0;k<8;k++){
	      	  service.create(k+1);
	        }
	        service.create(302);
	        Station one=service.getDao().getStationByName("林和西");
	        Station two=service.getDao().getStationByName("体育西路");
	        service.insertEdge(one, two);           
	        service.getDao().write2txt(service.getGraph());		
		}
		graph=service.getDao().read();
		serv=new MakePathServ(graph);
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
				//  textArea.setText(null);
				  String startName=start.getText();
				  String endName=end.getText();
				   if(startName!=null&&endName!=null&&startName.trim()!=""&&endName.trim()!=""){
					List<ArrayList<Station>> path=serv.findAllPath(startName, endName);
			       	String change=null;	
			       	int j=0;
			       	for(int i=path.size()>6?path.size()-3:0;i<path.size();i++){ //取最后三个记录			       	 
			       		textArea.append("方案"+(++j)+":");
			       		  ArrayList<Station> temp=path.get(i);
			       		  for(int k=0;k<temp.size();k++){
			       			  if(k!=0&&k!=temp.size()-1){
			       				  HashMap<Integer,Subway> map1= temp.get(k-1).getSubwayMap();
			       				  HashMap<Integer,Subway> map2=temp.get(k).getSubwayMap();
			       				  HashMap<Integer,Subway> map3=temp.get(k+1).getSubwayMap();
			       				  if(!MapUtils.ifsame(map1, map3)){
			       					    change=MapUtils.getSameSubwayName(map1,map2, map3);
			       				  }
			       			  }
			       			  if(change!=null){
			       				  textArea.append("在"+temp.get(k).getSname()+"换乘"+change+"→");

			       			  }
			       			  else{
			       				  if(k!=temp.size()-1)
			       				  textArea.append(temp.get(k).getSname()+"→"); 
			       				  else{
			       					textArea.append(temp.get(k).getSname()+"\n\r");   
			       				  textArea.append("*******************"+"\n\r");
			       				  }
			       			  }
			       			  change=null;
			       		  }
			       	}
			}
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
				       textArea.setText(null);
				 		List<Station> list=service.getDao().getStationListByBname((String)comboBox.getSelectedItem());	
				 		for(int i=0;i<list.size();i++){
				 			if(i!=list.size()-1)
				 				textArea.append(list.get(i).getSname()+"——");
				 			else
				 				textArea.append(list.get(i).getSname());	
				 		}
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
