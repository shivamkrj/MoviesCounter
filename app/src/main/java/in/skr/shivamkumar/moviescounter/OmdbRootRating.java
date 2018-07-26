
package in.skr.shivamkumar.moviescounter;

import com.google.gson.annotations.SerializedName;

public class OmdbRootRating {

    @SerializedName("Source")
    private String mSource;
    @SerializedName("Value")
    private String mValue;

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

}
