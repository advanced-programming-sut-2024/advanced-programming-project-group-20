package Model;

public class Request {
    String friendName;
    private String result ;

    public Request(String friendName) {
        this.friendName = friendName;
        this.result = "waiting";
    }

    public void setResult(String result) {
        if (result.equals("waiting"))
            this.result = result;
    }



    public String getResult() {
        return result;
    }

    public String getFriendName() {
        return friendName;
    }
}
