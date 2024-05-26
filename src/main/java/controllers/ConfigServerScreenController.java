package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import service.MovieTimeService;
import views.ConfigServerScreen;
import views.SeatConfigScreen;

public class ConfigServerScreenController implements ActionListener{
    private ConfigServerScreen view;
    private MovieTimeService movieTheaterSer;
    public ConfigServerScreenController(ConfigServerScreen view)
    {
    	this.view = view;
    	movieTheaterSer = new MovieTimeService();
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == view.stageConfigButton)
		{
			new SeatConfigScreen(this.view);
		}
		else if(e.getSource() == view.createTimeBtn)
		{
			String fromTime = view.timeStartText.getText();
			String toTime = view.timeEndText.getText();
			
			try {
				boolean check = movieTheaterSer.addMovieTime(fromTime, toTime);
				if(check)
				{
					JOptionPane.showMessageDialog(view, "Thêm suất chiếu thành công");
					this.view.updateMovieTimeCombobox();
				}
				else
				{
					JOptionPane.showMessageDialog(view, "Thêm suất chiếu thất bại");
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(view, e1.getMessage());
			}
		}	
	}
    
    
}
