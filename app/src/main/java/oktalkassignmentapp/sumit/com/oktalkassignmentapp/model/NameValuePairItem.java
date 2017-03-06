package oktalkassignmentapp.sumit.com.oktalkassignmentapp.model;

/**
 * Created by sumit on 3/5/17.
 */

public class NameValuePairItem {
    private String mName;
    private int mValue;

    public NameValuePairItem(String name, int value) {
        mName = name;
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }
}
