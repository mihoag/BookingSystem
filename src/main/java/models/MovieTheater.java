package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MovieTheater implements Serializable {
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
  
   private List<Zone> listZone;
   
   public MovieTheater() {
	  listZone = new ArrayList<>();
   }
   
   public List<Zone> getListZone() {
	return listZone;
   }

   public void setListZone(List<Zone> listZone) {
	 this.listZone = listZone;
   }

   public boolean addZone(Zone zone)
   {
	   if(this.listZone.contains(zone))
	   {
		   return false;
	   }
	   this.listZone.add(zone);
	   return true;
   }
   
   public void removeZone(Zone zone)
   {
	   this.listZone.remove(zone);
   }
   
   public void sortZoneDescByPrice()
   {
	  Collections.sort(this.listZone, new Comparator<Zone>() {
      @Override
      public int compare(Zone o1, Zone o2) {
			// TODO Auto-generated method stub
		    Double delta =  o2.getPrice() - o1.getPrice();
		    if(delta > 0)
		    {
		    	return 1;
		    }
		    else if(delta < 0)
		    {
		    	return -1;
		    }
            return 0;
		}
	});
   }
}
