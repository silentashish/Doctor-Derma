package np.com.gashish.www.skin;

public class FeedDisplay {
    private String mnameDetail;
    private String memailDetail;
    private String mResponse;
    private String mId;

    public FeedDisplay(String vnameDetail,String vemailDetail ,String vResponse, String vId)
    {
        mnameDetail=vnameDetail;
        memailDetail=vemailDetail;
        mResponse=vResponse;
        mId=vId;
    }

    public String getUserName() {return mnameDetail ;}

    public String getUserEmail() {return memailDetail;}

    public String getUserResponse() {return mResponse;}

    public String getUserId() {return mId;}

}
