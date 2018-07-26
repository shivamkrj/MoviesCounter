
package in.skr.shivamkumar.moviescounter;

import com.google.gson.annotations.SerializedName;

public class TvDetailsRootCreatedBy {

    @SerializedName("credit_id")
    private String mCreditId;
    @SerializedName("gender")
    private Long mGender;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_path")
    private Object mProfilePath;

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
    }

    public Long getGender() {
        return mGender;
    }

    public void setGender(Long gender) {
        mGender = gender;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(Object profilePath) {
        mProfilePath = profilePath;
    }

}
