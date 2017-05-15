package util;

public class Converter {
	
	public static String getBrand(String code) {
		String brand = "";
		switch (code) {
			case "x1" :
			case "x2" :
			case "x3" :
			case "x4" :
			case "x5" :
			case "x6" :
			case "x7" :
			case "x8" :
			case "x9" :
			case "x10" :
			case "x11" :
			case "x12" :
			case "x13" :
			case "x14" :
			case "x15" :
			case "x16" :
			case "x17" :
				brand = "Mc Donals";
				break;
			case "x18" :
			case "x19" :
			case "x20" :
			case "x21" :
			case "x22" :
			case "x23" :
			case "x24" :
			case "x25" :
			case "x26" :
			case "x27" :
			case "x28" :
			case "x29" :
			case "x30" :
			case "x31" :
			case "x32" :
				brand = "Burger King";
				break;
			case "x33" :
			case "x34" :
			case "x35" :
			case "x36" :
			case "x37" :
			case "x38" :
			case "x39" :
			case "x40" :
			case "x41" :
			case "x42" :
			case "x43" :
			case "x44" :
			case "x45" :
			case "x46" :
			case "x47" :
			case "x48" :
			case "x49" :
			case "x50" :
				brand = "Subway";
				break;
		}
		
		return brand;
	}
	
   public static String convert(String code) {
      String name = "";
      switch(code) {
         case "x1" :
            name = "Big Mac";
            break;
         case "x2" :
            name = "Cheeseburger";
            break;
         case "x3" :
            name = "Buttermilk Crispy Chicken";
            break;
         case "x4" :
            name = "Artisan Grilled Chicken";
            break;
         case "x5" :
            name = "Chicken McNuggets";
            break;
         case "x6" :
            name = "South Salad";
            break;
         case "x7" :
            name = "Bancon Ranch Salad";
            break;
         case "x8" :
            name = "Side Salad";
            break;
         case "x9" :
            name = "Sausage Burrito";
            break;
         case "x10" :
            name = "Sausage Muffin";
            break;
         case "x11" :
            name = "Sausage Biscuit";
            break;
         case "x12" :
            name = "Hash Browns";
            break;
         case "x13" :
            name = "Egg & Cheese Biscuit";
            break;
         case "x14" :
            name = "Shake";
            break;
         case "x15" :
            name = "Sundae";
            break;
         case "x16" :
            name = "Apple Pie";
            break;
         case "x17" :
            name = "Cone";
            break;
         case "x18" :
            name = "WHOPPER";
            break;
         case "x19" :
            name = "Bacon Cheese WHOPPER";
            break;
         case "x20" :
            name = "Cheeseburger";
            break;
         case "x21" :
            name = "Double Cheeseburger";
            break;
         case "x22" :
            name = "Bacon Cheeseburger";
            break;
         case "x23" :
            name = "Original Chicken Salad";
            break;
         case "x24" :
            name = "Garden Grilled Chicken Salad";
            break;
         case "x25" :
            name = "Bacon Cheddar Chicken Salad";
            break;
         case "x26" :
            name = "Side Salad";
            break;
         case "x27" :
            name = "Morning Star Veggie Burger";
            break;
         case "x28" :
            name = "Sundae (caramel or chocolat fudge)";
            break;
         case "x29" :
            name = "Chocolate Chip Cookies";
            break;
         case "x30" :
            name = "Soft Serve";
            break;
         case "x31" :
            name = "Cinnamon Roll";
            break;
         case "x32" :
            name = "Dutch Apple Pie";
            break;
         case "x33" :
            name = "Fully Loaded Croissan'wich";
            break;
         case "x34" :
            name = "Two Sausage Burrito";
            break;
         case "x35" :
            name = "Two Cinnamon ROlls";
            break;
         case "x36" :
            name = "Hash Browns";
            break;
         case "x37" :
            name = "Black Forest Ham";
            break;
         case "x38" :
            name = "Carved Turkey";
            break;
         case "x39" :
            name = "Oven Roasted Chicken";
            break;
         case "x40" :
            name = "Roast Beef";
            break;
         case "x41" :
            name = "Subway Club";
            break;
         case "x42" :
            name = "Subway Melt";
            break;
         case "x43" :
            name = "Apple Slices";
            break;
         case "x44" :
            name = "Chips";
            break;
         case "x45" :
            name = "Cookies";
            break;
         case "x46" :
            name = "Bacon, Egg & Cheese";
            break;
         case "x47" :
            name = "Black Forrest Ham, Egg & Cheese";
            break;
         case "x48" :
            name = "Egg & Cheese Omelet";
            break;
         case "x49" :
            name = "Steak, Egg & Cheese";
            break;
         case "x50" :
            name = "Hash Browns";
            break;      	
      }
   	
      return name;
   }

   public static double getPrice(int i) {
	   Matrix matrix = Input.initializeMatrix(0, 0, 0, 0, 0, 0);
	   return matrix.getUpperMatrix()[1][i];
   }
}
