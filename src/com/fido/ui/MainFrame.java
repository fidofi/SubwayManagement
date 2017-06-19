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
	private JPanel panel;//�ű���ͼ��
	private MakeGraphServ service;
    private MakePathServ serv;
    private Graph graph;//ͼ�ṹ
    
    private JLabel timeLabel;//��ʾ������ʱ
    
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		MainFrame window = new MainFrame();
		window.frame.setVisible(true);
		window.frame.setResizable(false);

	}

	public MainFrame() {
		service = new MakeGraphServ();
		// ����һ��ʼ���ж��Ƿ����ͼ�ṹ�Ĵ����ļ�����û��Ҫ�½����еĻ�ֱ�������������½�
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
			Station one = service.getDao().getStationByName("�ֺ���");
			Station two = service.getDao().getStationByName("������·");
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
                ImageIcon icon = new ImageIcon(getClass().getResource("/3.jpg"));  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, frame.getWidth(),  
                        frame.getHeight(), icon.getImageObserver());  
  
            }  
  
        };
        panel.setLayout(null);
		frame.setContentPane(panel);
	    timeLabel = new JLabel("��ѯ��ʱ��");
		timeLabel.setFont(new Font("������ͨ����", Font.PLAIN, 16));
		timeLabel.setBounds(10, 300, 200, 15);
		panel.add(timeLabel);

		JLabel titleLabel = new JLabel("���ݵ���");
		titleLabel.setBounds(197, 10, 114, 40);
		titleLabel.setFont(new Font("����������W12(P)", Font.PLAIN, 27));
		panel.add(titleLabel);

		JLabel smallTitle = new JLabel("GuangZhou Metro");
		smallTitle.setBounds(199, 48, 114, 15);
		smallTitle.setFont(new Font("����������W12", Font.PLAIN, 14));
		panel.add(smallTitle);

		//��·��ѯѡ����
		JPanel stationPanel = new JPanel(){
			protected void paintComponent(Graphics g) {  
  
  
            }  
		};
		stationPanel.setBounds(70, 101, 388, 168);
		stationPanel.setLayout(null);

		JButton buttonByStation = new JButton("��ѯ");
		buttonByStation.setBounds(185, 127, 80, 31);
		stationPanel.add(buttonByStation);
		buttonByStation.setFont(new Font("������ͨ����", Font.PLAIN, 18));
		buttonByStation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				long startTime=System.currentTimeMillis();   //��ȡ��ʼʱ��
				  textArea.setText(null);
				  String startName=start.getText();
				  String endName=end.getText();
				   if(startName!=null&&endName!=null&&startName.trim()!=""&&endName.trim()!=""){
					   if(startName.equals(endName)){
						   JOptionPane.showMessageDialog(null, "������յ���ͬ��");
						   return;
					   }
					List<ArrayList<Station>> path=serv.findAllPath(startName, endName);
					if(path==null){
						JOptionPane.showMessageDialog(null, "δ�ҵ���·����");
						   return;
					}
			       	HashMap<Integer,Integer> findLess=new HashMap<Integer,Integer>();//�ҳ����˴������ٵ�
			       	for(int i=0;i<path.size();i++){
			       		 findLess.put(i, 0);
			       	}
			       	String change=null;	
			       	//int writeIndex=(path.size()>6?path.size()-3:0);
			       	int writeIndex=path.size()-1;
			       	int j=0;
			       	for(int i=path.size()-1;i>=0;i--){ //ȡ���������¼,��̷ŵ�һ��	
//			       		int current=0;
                         if(i>=writeIndex){
			       		textArea.append(";��վ�����٣�"+"\n\r");
                         }
			       		  ArrayList<Station> temp=path.get(i);
			       		  System.out.println(temp.size());
			       		  for(int k=0;k<temp.size();k++){
			       			  if(k!=0&&k!=temp.size()-1){
//			       				  if(current!=0){
			       				  Station map1= temp.get(k-1);
			       				  Station map2=temp.get(k);
			       				  Station map3=temp.get(k+1);
			       				  MapUtils.show(map1);
			       				MapUtils.show(map2);
			       				MapUtils.show(map3);
			       				  if(!MapUtils.ifsame(map1, map3)){
			       					    change=MapUtils.getSameSubwayName(map1,map2, map3);
			       				  }
//			       				  }
			       				  else{
			       					  Station map11= temp.get(0);
				       				  Station map12=temp.get(k);
				       				  Station map13=temp.get(k+1);
				       				  if(!MapUtils.ifsame(map11, map13)){
				       					    change=MapUtils.getSameSubwayName(map11,map12, map13);
				       				  }
			       				  }	  
			       			  }
			       			  if(change!=null){
			       				if(i>=writeIndex)
			       				 textArea.append("��"+temp.get(k).getSname()+"����"+change+"��");
			       				int value=findLess.get(i);
			       				 findLess.put(i, ++value);
//			       				   current=findLess.get(i);//��õ�ǰ�Ļ��˴���

			       			  }
			       			  else{
			       				if(i>=writeIndex){
			       				  if(k!=temp.size()-1)
			       				  textArea.append(temp.get(k).getSname()+"��"); 
			       				  else{
			       					textArea.append(temp.get(k).getSname()+"\n\r");   
			       				  textArea.append("*******************"+"\n\r");
			       				  }
			       				}
			       			  }
			       			  change=null;
			       		  }
			       	}
			    	//��Ҫ��Ϊ���ҳ�������С����·�����֪��map��value��������value��Сʱ��Ӧ��Key ��path��������±�
			       	List<Integer> index=new ArrayList<Integer>();
			     for(Map.Entry<Integer, Integer> entry:findLess.entrySet()){
			    	  int key=entry.getKey();
			    	  int value=entry.getValue();
			    	  index.add(value);
			     }
			     int min=index.get(0);
			     int dex=0;
			     for(int i=1;i<index.size();i++){
			    	   if(min>=index.get(i)){
			    		   min=index.get(i);
			    		   dex=i;
			    	   }
			     }
			     //������������˴������ٵ�·�ߣ��޷���ȷ����path�ж�Ӧ���±꣬��ʵ�����������ȡ��ͬ��������д�ģ���Ϊ�������Ķ����ظ����롣��֮ǰûע��
			  //   int changeto =(path.size()>6?(path.size()-dex-1):dex);
			     int changeto=dex;
			     ArrayList<Station> lessChange=path.get(changeto);//���˴������ٵ�,�����и��ӣ�ע�������±������ǲ�һ����
			     textArea.append("���˴������ٵ�·��Ϊ��"+"\n\r");
			     String change2=null;
			     int current2=0;
			     for(int k=0;k<lessChange.size();k++){    
      	       			  if(k!=0&&k!=lessChange.size()-1){
                                  if(current2!=0){
                                	  Station map1= lessChange.get(k-1);
    			       				  Station map2=lessChange.get(k);
    			       				  Station map3=lessChange.get(k+1);
            	       				  if(!MapUtils.ifsame(map1, map3)){
            	       					    change2=MapUtils.getSameSubwayName(map1,map2,map3);
            	       				  }
                                  }
                                  else{
                                	  Station map1= lessChange.get(0);
				       				  Station map2=lessChange.get(k);
				       				  Station map3=lessChange.get(k+1);
            	       				  if(!MapUtils.ifsame(map1, map3)){
            	       					    change2=MapUtils.getSameSubwayName(map1,map2, map3);
            	       				  }
                                  }                               	  
    	       			  }
	       			  if(change2!=null){
	       				  textArea.append("��"+lessChange.get(k).getSname()+"����"+change2+"��");
	       				  current2++;
	       			  }
	       			  else{
	       				  if(k!=lessChange.size()-1)
	       				  textArea.append(lessChange.get(k).getSname()+"��"); 
	       				  else{
	       					textArea.append(lessChange.get(k).getSname()+"\n\r");   
	       				  }
	       			  }
	       			  change2=null;
			     }
			}
				   long endTime=System.currentTimeMillis(); //��ȡ����ʱ��		   
				   System.out.println("��������ʱ�䣺 "+(double)(endTime-startTime)/1000+"s");
				   String timeByStation=(double)(endTime-startTime)/1000+"��";
				   timeLabel.setText("��ѯ��ʱ:"+timeByStation);
			}
		});

		JLabel startLabel = new JLabel("��㣺");
		startLabel.setFont(new Font("������ͨ����", Font.PLAIN, 20));
		startLabel.setBounds(87, 23, 69, 29);
		stationPanel.add(startLabel);

		JLabel endLabel = new JLabel("�յ㣺");
		endLabel.setFont(new Font("������ͨ����", Font.PLAIN, 20));
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

	//�����ߺŲ�ѯѡ����
		JPanel subwayPanel = new JPanel(){
			protected void paintComponent(Graphics g) {  

  
            }  
		};
		subwayPanel.setBounds(70, 101, 388, 168);
		subwayPanel.setLayout(null);

		JLabel label_one = new JLabel("��ѡ����·");
		label_one.setBounds(150, 24, 95, 20);
		label_one.setFont(new Font("������ͨ����", Font.PLAIN, 19));
		subwayPanel.add(label_one);

		 comboBox = new JComboBox(); //������
		comboBox.setBounds(150, 68, 100, 21);
		List<String> list=service.getDao().getAllSubwayName();
		for(int i=0;i<list.size();i++){
			 comboBox.addItem(list.get(i));//��������·�����ּ��뵽�������б���
		}
		subwayPanel.add(comboBox);

		JButton buttonBySubway = new JButton("��ѯ");
		buttonBySubway.setFont(new Font("������ͨ����", Font.PLAIN, 18));
		buttonBySubway.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long startTime=System.currentTimeMillis();   //��ȡ��ʼʱ��
				       textArea.setText(null);
				 		List<Station> list=service.getDao().getStationListByBname((String)comboBox.getSelectedItem());	
				 		for(int i=0;i<list.size();i++){
				 			if(i!=list.size()-1)
				 				textArea.append(list.get(i).getSname()+"����");
				 			else
				 				textArea.append(list.get(i).getSname());	
				 		}
				 		long endTime=System.currentTimeMillis(); //��ȡ����ʱ��

				 	      System.out.println("��������ʱ�䣺 "+(double)(endTime-startTime)/1000+"s");
				 	     String timeBySubway=(double)(endTime-startTime)/1000+"��";
						   timeLabel.setText("��ѯ��ʱ:"+timeBySubway);
			}
		});
		buttonBySubway.setBounds(150, 122, 93, 23);
		subwayPanel.add(buttonBySubway);
		buttonBySubway.setFont(new Font("������ͨ����", Font.PLAIN, 18));
		panel.add(subwayPanel);
		
		//ѡ�
		JTabbedPane jtp = new JTabbedPane();
		jtp.setBounds(43, 96, 411, 217);
		jtp.add("��·��ѯ", stationPanel);
		panel.add(jtp); 
		jtp.add("������ѯ", subwayPanel);
		
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
