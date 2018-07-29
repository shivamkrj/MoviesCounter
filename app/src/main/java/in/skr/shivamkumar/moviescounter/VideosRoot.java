
package in.skr.shivamkumar.moviescounter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VideosRoot {

    @SerializedName("id")
    private Long mId;
    @SerializedName("results")
    private List<VideosRootResult> mVideosRootResults;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<VideosRootResult> getResults() {
        return mVideosRootResults;
    }

    public void setResults(List<VideosRootResult> videosRootResults) {
        mVideosRootResults = videosRootResults;
    }

}
