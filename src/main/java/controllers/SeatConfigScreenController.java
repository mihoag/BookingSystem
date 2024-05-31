package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import models.MovieTime;
import service.MovieTimeService;
import views.ConfigServerScreen;
import views.SeatConfigScreen;

public class SeatConfigScreenController implements ActionListener{

	private SeatConfigScreen view;
	private MovieTimeService service;
	private ConfigServerScreen serverView;
    
	public SeatConfigScreenController(SeatConfigScreen view, ConfigServerScreen serverScreen)
	{
		this.view = view;
		this.serverView = serverScreen;
		service = new MovieTimeService();
	}
	
	  
    public void configBroadcast(List<MovieTime> movieTimes)
    {
    	this.serverView.serverThread.broadcast(movieTimes);
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	     if(e.getSource() == view.addBtn)
	     {
	    	 try {
	    		 
	    		// Check if exist movie time
	    		List<MovieTime> lsMovieTime = service.getMovieTimes();
	    		if(lsMovieTime.size() == 0)
	    		{
	    			JOptionPane.showMessageDialog(view, "Danh sách suất chiếu đang rỗng. Cần thêm 1 suất chiếu trước!");
	    			return;
	    		}
	    		 
	    		String name = view.zoneNameTextField.getText();
	 	        Integer rowNum = Integer.parseInt(view.rowNumTextField.getText());  
	 	        Integer seatNumPerRow = Integer.parseInt(view.seatNumPerRowText.getText());
	 	        Double price = Double.parseDouble(view.priceTextField.getText());
	 	        
	 	        /// 
	 	        boolean check = service.addZone(name, rowNum, seatNumPerRow, price);
	 	        if(check)
	 	        {
	 	           JOptionPane.showMessageDialog(view, "Thêm khu thành công");
	 	           // updata table
	 	           view.zoneInfoTableModel.addRow(new Object[] {name, rowNum, seatNumPerRow, price});
	 	           view.resetTextField();
	 	           // update seat map
	 	           serverView.updateSeatMap();
	 	           // update client
	 	           List<MovieTime> movieTimes = service.getMovieTimes();
	 	           configBroadcast(movieTimes);
	 	        }
	 	        else
	 	        {
	 	           JOptionPane.showMessageDialog(view, "Thêm khu thất bại");	
	 	        }
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(view, e2.getMessage());
			}
	     } 
	     else if(e.getSource() == view.deleteBtn)
	     {
	        try {
	        	int cnt =  view.zoneInfoTable.getSelectedRow();
				String zoneName = (String) view.zoneInfoTable.getValueAt(cnt, 0);
				Boolean check = service.deleteZoneByName(zoneName);
				
				if(check)
				{
				   view.updateZoneTable();
				   serverView.updateSeatMap();
				   // update client
	 	           List<MovieTime> movieTimes = service.getMovieTimes();
	 	           configBroadcast(movieTimes);  
				   JOptionPane.showMessageDialog(view, "Xóa khu thành công");
			    }
				else
				{
					JOptionPane.showMessageDialog(view, "Xóa khu thất bại");
				}
				
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(view, e2.getMessage());
			}
	     }
	}

}
