package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.MovieConfigScreenController;
import models.Movie;
import service.MovieService;


public class MovieConfigScreen extends JFrame {
	private JPanel contentPane;
	public JTable movieTable;
	public DefaultTableModel movieTableModel;
	public JTextField movieNameTextField;
	private JLabel dateLabel;
	public JButton addBtn;
	public JButton deleteBtn;
	private JLabel stageConfigLabel;
	private JLabel movieNameLabel;
    public com.toedter.calendar.JDateChooser movieDateInput;   
    private ConfigServerScreen configView;
    private static SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
	
  
    public void updateTable()
    {
    	 List<Movie> movies = MovieService.getInstance().getMovies();
         int count = 0;
         movieTableModel.setRowCount(0);
         for(Movie movie : movies)
         {
        	 movieTableModel.addRow(new Object[] {++count, movie.getMovieName(), movie.getDate().toString()});
         }
    }
    public void resetInputField()
    {
       movieNameTextField.setText("");
       movieDateInput.setDate(null); 
    }
    
    public void setDataForInput(String movieName, String date)
    {
    	movieNameTextField.setText(movieName);
    	try {
			movieDateInput.setDate(formatter.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    public void initData()
    {
        updateTable(); 
    }
    
    public void addEventForComponent()
    {
       movieTable.addMouseListener(new MouseListener() {
		
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
			int cnt = movieTable.getSelectedRow();
			//
			String movieName  = (String) movieTable.getValueAt(cnt, 1);
			String movieDate = (String) movieTable.getValueAt(cnt, 2);
		
			setDataForInput(movieName, movieDate);
		}
	});
       
       
    }
    
	public MovieConfigScreen(ConfigServerScreen configView) {
		MovieConfigScreenController ac = new MovieConfigScreenController(this, configView);
		
		this.configView = configView;
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
		
		movieTableModel = new DefaultTableModel();
		movieTableModel.addColumn("STT");
		movieTableModel.addColumn("Tên phim");
		movieTableModel.addColumn("Ngày chiếu");
	   
	    
		movieTable = new JTable(movieTableModel);
		
		movieTable.setBounds(27, 166, 745, 312);
		
	    JScrollPane sc = new JScrollPane(movieTable);
	    sc.setLocation(58, 194);
	    sc.setSize(690, 258);
	    sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(sc);
		
		movieNameLabel = new JLabel("Tên phim");
		movieNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieNameLabel.setBounds(27, 77, 136, 37);
		contentPane.add(movieNameLabel);
		
		movieNameTextField = new JTextField();
		movieNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		movieNameTextField.setBounds(118, 77, 321, 32);
		contentPane.add(movieNameTextField);
		movieNameTextField.setColumns(10);
		
		dateLabel = new JLabel("Ngày chiếu");
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		dateLabel.setBounds(475, 77, 136, 37);
		contentPane.add(dateLabel);
	
	
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
		
		movieDateInput = new com.toedter.calendar.JDateChooser();
		movieDateInput.getCalendarButton().setFont(new Font("Tempus Sans ITC", Font.PLAIN, 20));
		movieDateInput.setSize(187, 37);
		movieDateInput.setLocation(582, 77);
		contentPane.add(movieDateInput);		
	
		setVisible(true);
		
		// Add event for components
		addEventForComponent();
		
		// Init data
		initData();
	}

}
