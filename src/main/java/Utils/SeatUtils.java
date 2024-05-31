package Utils;

import java.util.ArrayList;
import java.util.List;

public class SeatUtils {
   public  static List<Object> splitSeatId(String seatId)
   {
	   List<Object> objects = new ArrayList<>();
	   String[] components = seatId.split("-");
	   objects.add(components[0]);
	   objects.add(Integer.parseInt(components[1]));
	   objects.add(Integer.parseInt(components[2]));
	   return objects;
   }
}
