
package in.skr.shivamkumar.moviescounter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvRoot {

    @Expose
    private Long page;
    @SerializedName("results")
    private List<TvResult> tvResults;
    @SerializedName("total_pages")
    private Long totalPages;
    @SerializedName("total_results")
    private Long totalResults;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<TvResult> getTvResults() {
        return tvResults;
    }

    public void setTvResults(List<TvResult> tvResults) {
        this.tvResults = tvResults;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}
