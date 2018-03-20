package shopapp.royal.royalbookshop;

/**
 * Created by Akila Devinda on 1/31/2018.
 */

public class ExmapleItem {
    private String itemName ;
    private String itemPrice;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }



    private String itemID;

    public ExmapleItem(String itemName, String itemPrice,String itemID) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
