
package in.skr.shivamkumar.moviescounter;

import com.google.gson.annotations.SerializedName;

public class PersonDetailsRoot {

    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("biography")
    private String mBiography;
    @SerializedName("birthday")
    private String mBirthday;
    @SerializedName("deathday")
    private Object mDeathday;
    @SerializedName("gender")
    private Long mGender;
    @SerializedName("homepage")
    private String mHomepage;
    @SerializedName("id")
    private Long mId;
    @SerializedName("imdb_id")
    private String mImdbId;
    @SerializedName("known_for_department")
    private String mKnownForDepartment;
    @SerializedName("name")
    private String mName;
    @SerializedName("place_of_birth")
    private String mPlaceOfBirth;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("profile_path")
    private String mProfilePath;

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public Object getDeathday() {
        return mDeathday;
    }

    public void setDeathday(Object deathday) {
        mDeathday = deathday;
    }

    public Long getGender() {
        return mGender;
    }

    public void setGender(Long gender) {
        mGender = gender;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public void setHomepage(String homepage) {
        mHomepage = homepage;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public void setImdbId(String imdbId) {
        mImdbId = imdbId;
    }

    public String getKnownForDepartment() {
        return mKnownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        mKnownForDepartment = knownForDepartment;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

}
