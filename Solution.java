package pck;


import java.util.*;

import org.junit.*;
import org.junit.runner.*;



//  
//  The Store
//  
//  We have items for sale, but most decrease in quality as they near their expiration!
//  One of our cousins helped us make this, but we were hoping you could clean it up.
//  
//  Some Information:
//  
//  * All items have a "days_left" value which is the number of days until expiration.
//  * All items have a "quality" value which denotes how valuable the item is.
//  * At the end of each day our system recalculates the values for every item.
//  
//  Pretty simple, right? Well this is where it gets interesting:
//  
//  * Usually, most items decrease in "quality" by 1 every day.
//  * Once the sell by date reaches 0, "quality" decreases by 2 every day.
//  * The "quality" of an item is never negative.
//  * "Cheese" increases in "quality" the older it gets.
//    * "quality" increases by 2 when there are 10 days or less
//    * "quality" increases by 3 when there are 5 days or less
//  * "Ticket" works the same as "Cheese" EXCEPT:
//    * "quality" becomes 0 once expired ("days_left" is 0)
//  * The "quality" of an item is never more than 50.
//  * "Emerald" is a unique item, its "quality" is always 80 and it never changes.
//  
//  Feel free to make any changes to the "Store.update_quality" method and add any
//  new code as long as everything still works correctly. 
//  However, do not alter the "Item" class.
//  


public class Solution {
	    
    // #############################################
    //  Store   
    public static class Store {
    	
    	interface Validation{
    	   	 
        	Item update(Item item);
        }
        
        public static class CheeseValidation implements Validation
        {
        	
    		public Item update(Item item) {    			
    			if ((item.quality < 50)){    				
	    			if ((item.days_left < 6)) {
	                    item.quality = item.quality + 2;
	                }
	                if ((item.days_left > 5) && (item.days_left < 11)) {
	                    item.quality = item.quality +1;
	                }    			
    			}
    			else if((item.quality >= 50))
                	item.quality = 50;
    			
    			item.days_left = (item.days_left - 1);
    			return item;
    		}    	  	
        }    
        
        public static class TicketValidation implements Validation
        {
        	public Item update (Item item){
        		if ((item.quality < 50)){
        		
	        		if(item.days_left == 0){
	        			item.quality = 0;
	        			item.days_left = (item.days_left - 1);
	        			return item;
	        		}	        		

	        		if ((item.days_left < 6)) {
	                    item.quality = item.quality + 2;
	                }
	                if ((item.days_left > 5) && (item.days_left < 11)) {
	                    item.quality = item.quality +1;
	                }	        		
	                
	                item.days_left = (item.days_left - 1);
        		}
          		
        		
    			return item;   		
    			}    	
        }
        
        public static class EmraldValidation implements Validation
        {
       
    		public Item update(Item item) {    			
    				item.quality = 80;    			
    			return item;
    		}
        	
        }
        
        public static class GeneralValidation implements Validation
        {
        	public Item update (Item item){  
        		
        		if((item.days_left == 0)|| (item.quality > 0))
        			item.quality = item.quality - 2;
        		
        		if ((item.quality >= 50)) {
                    item.quality = 50;
        		}
        		
        		if(item.quality <= 0)
      		        item.quality = 0;       		
        		       		     			
        		
        		item.days_left = (item.days_left - 1);
        		if ((item.days_left < 1) && (item.quality > 0)){
        			item.quality = (item.quality - 1);
        		}
        		      		
    			return item;        		
        	}   	
        	
        }
    
        public static Solution.Item[] update_quality(Solution.Item[] items) {
            for (int i = 0; (i < items.length); i++) {
                   items[i].quality = (items[i].quality + 1);
                    switch(items[i].name){
	                    case "Cheese":
	                    	CheeseValidation cheeseValidationObj = new CheeseValidation();
	                    	items[i] = cheeseValidationObj.update(items[i]);                 	
	                        break;                  	
	                    case "Ticket":
	                    	TicketValidation ticketValidationObj = new TicketValidation();
	                    	items[i] = ticketValidationObj.update(items[i]);                 	
	                        break;
	                        
	                    case "Emerald":
	                    	EmraldValidation emraldValidationObj = new EmraldValidation();
	                    	items[i] = emraldValidationObj.update(items[i]);
	                    	break;
	                    	
	                    default :
	                    	GeneralValidation generalValidationObj = new GeneralValidation();
	                    	items[i] = generalValidationObj.update(items[i]);
	                    	break;                  	   	
                    }
                }

            return items;
        }
    }
    
    //#######################################################
    
    
    
    // #############################################
    // Item
  
    public class Item {
  
    
        public Item(String name, int days_left, int quality) {
            this.name = name;
            this.days_left = days_left;
            this.quality = quality;
        }

        public String name;

        public int days_left;

        public int quality;
      
        public int hashCode() {          
            int result = 0;
            result = ((result * 5) ^ this.name.hashCode());
            result = ((result * 7) ^ this.days_left);
            result = ((result * 11) ^ this.quality);
            return result;
        }

        

        public boolean equals(Object obj) {

            
          if (obj == null) {
            return false;
          }
          
          if (!(obj instanceof Item)) {
            return false;
          }
          Item other = (Item) obj;
          return 
             this.name.equals(other.name) 
              && this.days_left == other.days_left 
              && this.quality == other.quality;
        }
        
        public String toString() {
            return String.format("<Item: %s, %s, %s>", this.name, this.days_left, this.quality);
        }


    }
    
    // #############################################
    //  Tests

    public Item[] items;
    public Item[] result;

    @Before
    public void setup() {
        this.items = new Item[1];
        this.result = new Item[1];
    }


	@Test
    public final void test_example() {
        Assert.assertEquals(true, true);
    }
            
    @Test
    public final void test_regular_items_decrease_by_one() {
        this.items[0] = new Item("Bread", 10, 20);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Bread", 9, 19));
    }

    @Test
    public final void test_regular_item_quality_does_not_go_below_zero() {
        this.items[0] = new Item("Bread", 2, 0);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Bread", 1, 0));
    }

    @Test
    public final void test_quality_goes_up_for_cheese() {
        this.items[0] = new Item("Cheese", 20, 30);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Cheese", 19, 31));
    }

    @Test
    public final void test_quality_goes_up_for_ticket() {
        this.items[0] = new Item("Ticket", 19, 31);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Ticket", 18, 32));
    }

    @Test
    public final void test_quality_goes_up_by_2_for_cheese_with_10_days_or_less_left() {
        this.items[0] = new Item("Cheese", 10, 30);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Cheese", 9, 32));
    }

    @Test()
    public final void test_quality_goes_up_by_2_for_ticket_with_10_days_or_less_left() {
        this.items[0] = new Item("Ticket", 9, 31);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Ticket", 8, 33));
    }

    @Test
    public final void test_quality_goes_up_by_3_for_cheese_with_5_days_or_less_left() {
        this.items[0] = new Item("Cheese", 4, 11);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Cheese", 3, 14));
    }

    @Test
    public final void test_quality_goes_up_by_3_for_ticket_with_5_days_or_less_left() {
        this.items[0] = new Item("Ticket", 4, 11);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Ticket", 3, 14));
    }

    @Test
    public final void test_going_from_unexpired_to_expired() {
        this.items[0] = new Item("Bread", 1, 11);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Bread", 0, 9));
    }

    @Test
    public final void test_quality_decreases_twice_as_fast_after_expired() {
        this.items[0] = new Item("Bread", 0, 11);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Bread", -1, 9));
    }

    @Test
    public final void test_cheese_quality_increases_by_3_after_expired() {
        this.items[0] = new Item("Cheese", 0, 20);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Cheese", -1, 23));
    }

    @Test
    public final void test_ticket_goes_to_quality_0_after_expired() {
        this.items[0] = new Item("Ticket", 0, 20);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Ticket", -1, 0));
    }

    @Test
    public final void test_emerald() {
        this.items[0] = new Item("Emerald", 0, 80);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Emerald", 0, 80));
    }

    @Test
    public final void test_quality_does_not_increase_past_50() {
        this.items[0] = new Item("Cheese", 4, 49);
        this.result = Store.update_quality(this.items);
        Assert.assertEquals(this.result[0], new Item("Cheese", 3, 50));
    }

    
    public static void main(String[] args) {
        JUnitCore.main("Solution");  
    }
}

