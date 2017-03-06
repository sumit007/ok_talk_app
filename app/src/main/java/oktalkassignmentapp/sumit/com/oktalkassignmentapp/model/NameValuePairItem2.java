package oktalkassignmentapp.sumit.com.oktalkassignmentapp.model;

/**
 * Created by sumit on 3/6/17.
 */

public class NameValuePairItem2 {

    private String mName;
    private String mValue;

    public NameValuePairItem2(String name, String value) {
        mName = name;
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

}
