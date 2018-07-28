
package in.skr.shivamkumar.moviescounter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieCreditsRoot {

    @SerializedName("cast")
    private List<MovieCreditsRootCast> mMovieCreditsRootCast;
    @SerializedName("crew")
    private List<Object> mCrew;
    @SerializedName("id")
    private Long mId;

    public List<MovieCreditsRootCast> getCast() {
        return mMovieCreditsRootCast;
    }

    public void setCast(List<MovieCreditsRootCast> movieCreditsRootCast) {
        mMovieCreditsRootCast = movieCreditsRootCast;
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
