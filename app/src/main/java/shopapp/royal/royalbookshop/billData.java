package shopapp.royal.royalbookshop;
/**
 * Created by Sahan Ridma on 2/20/2018.
 */

public class billData {

    String total;
    String cash;
    String balance;

    public billData(String total, String cash, String balance, String date) {
        this.total = total;
        this.cash = cash;
        this.balance = balance;
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;
}
