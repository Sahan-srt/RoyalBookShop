package shopapp.royal.royalbookshop;

/**
 * Created by hansaanuradhawickramanayake on 2/12/18.
 */

public class Messages {

    String message;
    long time;
    String currentUser;
    String userName;

    public Messages() {
    }

    public Messages(String message, long time, String currentUser, String userName) {
        this.message = message;
        this.time = time;
        this.currentUser = currentUser;
        this.userName = userName;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
