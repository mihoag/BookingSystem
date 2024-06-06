package views;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.SeatConfigScreenController;
import models.Zone;
import service.MovieService;
import utils.TimeZoneUtitls;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class SeatConfigScreen extends JFrame {

	private JPanel contentPane;
	public JTable zoneInfoTable;
	public DefaultTableModel zoneInfoTableModel;
	public JTextField zoneNameTextField;
	public JTextField rowNumTextField;
	private JLabel rowNumLabel;
	public JTextField seatNumPerRowText;
	private JLabel priceLabel;
	public JTextField priceTextField;
	public JButton addBtn;
	public JButton deleteBtn;
	private JLabel stageConfigLabel;
	private JLabel zoneNameLabel;
	private JLabel seatNumPerRowLabel;
    private ConfigServerScreen configScreen;
  
	
    public void initData()
    {
      updateZoneTable();
    }
    
    public void updateZoneTable()
    {
    	 zoneInfoTableModel.setRowCount(0);
    	 
    	 String movieName = (String) configScreen.movieNameCombobox.getSelectedItem();
    	 String movieTime = (String) configScreen.movieTimeCombobox.getSelectedItem();
    	 if(movieName != null && movieTime != null)
    	 {
    		 String[] components = TimeZoneUtitls.splitTimeZone(movieTime);
        	 
        	 List<Zone> listZone =  MovieService.getInstance().getListZoneFromMovieTime(movieName, components[0], components[1]);
        	 
             for(Zone zone : listZone)
             {
          	   zoneInfoTableModel.addRow(new Object[] {zone.getName(), zone.getRowNum(), zone.getSeatsPerRow(), zone.getPrice()});  
             }
    	 }
    }
    
    public void resetTextField()
    {
    	this.zoneNameTextField.setText("");
    	this.priceTextField.setText("");
    	this.rowNumTextField.setText("");
    	this.seatNumPerRowText.setText("");
    }
    
    public void setDataForTextField(String zoneName, Integer numRow, Integer seatPerRow, Double price)
    {
    	this.zoneNameTextField.setText(zoneName);
    	this.rowNumTextField.setText(numRow + "");
    	this.seatNumPerRowText.setText(seatPerRow + "");
    	this.priceTextField.setText(price + "");
    }
    public void addMouseEventforJTable()
	{
            zoneInfoTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int cnt = zoneInfoTable.getSelectedRow();
				//
				String zoneName = (String) zoneInfoTable.getValueAt(cnt, 0);
				Integer rowNum = (Integer)zoneInfoTable.getValueAt(cnt, 1);
				Integer seatPerRow = (Integer)zoneInfoTable.getValueAt(cnt, 2);
				Double price = (Double)zoneInfoTable.getValueAt(cnt, 3);
				
				setDataForTextField(zoneName, rowNum, seatPerRow, price);
			}
		});      
	}
    
    
	public SeatConfigScreen(ConfigServerScreen configScreen) {
		this.configScreen = configScreen;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SeatConfigScreen.class.getResource("/assets/serverIcon.png")));
		setBounds(100, 100, 816, 525);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(236, 200, 123));
	
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		stageConfigLabel = new JLabel("CẤU HÌNH SÂN KHẤU");
		stageConfigLabel.setBounds(231, 10, 340, 37);
		stageConfigLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(stageConfigLabel);
		
		zoneInfoTableModel = new DefaultTableModel();
	    zoneInfoTableModel.addColumn("Tên khu");
	    zoneInfoTableModel.addColumn("Số hàng");
	    zoneInfoTableModel.addColumn("Số cột");
	    zoneInfoTableModel.addColumn("Giá");
	    
		zoneInfoTable = new JTable(zoneInfoTableModel);
		
		zoneInfoTable.setBounds(27, 166, 745, 312);
		
	    JScrollPane sc = new JScrollPane(zoneInfoTable);
	    sc.setLocation(58, 194);
		sc.setSize(690, 258);
	    sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(sc);
		
		zoneNameLabel = new JLabel("Tên khu");
		zoneNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		zoneNameLabel.setBounds(27, 77, 136, 37);
		contentPane.add(zoneNameLabel);
		
		zoneNameTextField = new JTextField();
		zoneNameTextField.setBounds(106, 77, 96, 32);
		contentPane.add(zoneNameTextField);
		zoneNameTextField.setColumns(10);
		
		seatNumPerRowLabel = new JLabel("Số ghế/hàng");
		seatNumPerRowLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		seatNumPerRowLabel.setBounds(411, 77, 136, 37);
		contentPane.add(seatNumPerRowLabel);
		
		rowNumTextField = new JTextField();
		rowNumTextField.setColumns(10);
		rowNumTextField.setBounds(298, 77, 96, 32);
		contentPane.add(rowNumTextField);
		
		rowNumLabel = new JLabel("Số hàng");
		rowNumLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rowNumLabel.setBounds(220, 77, 136, 37);
		contentPane.add(rowNumLabel);
		
		seatNumPerRowText = new JTextField();
		seatNumPerRowText.setColumns(10);
		seatNumPerRowText.setBounds(530, 77, 96, 32);
		contentPane.add(seatNumPerRowText);
		
		priceLabel = new JLabel("Gía");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		priceLabel.setBounds(649, 77, 38, 37);
		contentPane.add(priceLabel);
		
		priceTextField = new JTextField();
		priceTextField.setColumns(10);
		priceTextField.setBounds(681, 77, 96, 32);
		contentPane.add(priceTextField);
		
		SeatConfigScreenController ac = new SeatConfigScreenController(this,configScreen);
		
		addBtn = new JButton("Thêm");
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addBtn.setBounds(308, 124, 85, 39);
		addBtn.addActionListener(ac);
		contentPane.add(addBtn);
		
		deleteBtn = new JButton("Xóa");
		deleteBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		deleteBtn.setBounds(421, 124, 85, 39);
		deleteBtn.addActionListener(ac);
		contentPane.add(deleteBtn);
	
		setVisible(true);
		
		addMouseEventforJTable();
		initData();
	}
}
