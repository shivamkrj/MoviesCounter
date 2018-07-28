
package in.skr.shivamkumar.moviescounter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TvCreditsRoot {

    @SerializedName("cast")
    private List<TvCreditsRootCast> mTvCreditsRootCast;
    @SerializedName("crew")
    private List<Object> mCrew;
    @SerializedName("id")
    private Long mId;

    public List<TvCreditsRootCast> getCast() {
        return mTvCreditsRootCast;
    }

    public void setCast(List<TvCreditsRootCast> tvCreditsRootCast) {
        mTvCreditsRootCast = tvCreditsRootCast;
    }

    public List<Object> getCrew() {
        return mCrew;
    }

    public void setCrew(List<Object> crew) {
        mCrew = crew;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

}
