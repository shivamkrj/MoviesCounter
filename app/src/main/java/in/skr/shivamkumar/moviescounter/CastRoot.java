
package in.skr.shivamkumar.moviescounter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CastRoot {

    @SerializedName("cast")
    private List<CastRootCast> mCastRootCast;
    @SerializedName("crew")
    private List<Object> mCrew;
    @SerializedName("id")
    private Long mId;

    public List<CastRootCast> getCast() {
        return mCastRootCast;
    }

    public void setCast(List<CastRootCast> castRootCast) {
        mCastRootCast = castRootCast;
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
