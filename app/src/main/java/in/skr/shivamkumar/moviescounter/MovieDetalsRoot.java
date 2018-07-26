
package in.skr.shivamkumar.moviescounter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieDetalsRoot {

    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("belongs_to_collection")
    private MovieDetailsRootBelongsToCollection mMovieDetailsRootBelongsToCollection;
    @SerializedName("budget")
    private Long mBudget;
    @SerializedName("genres")
    private List<MovieDetailsRootGenre> mMovieDetailsRootGenres;
    @SerializedName("homepage")
    private String mHomepage;
    @SerializedName("id")
    private Long mId;
    @SerializedName("imdb_id")
    private String mImdbId;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("production_companies")
    private List<MovieDetailsRootProductionCompany> mProductionCompanies;
    @SerializedName("production_countries")
    private List<MovieDetailsRootProductionCountry> mProductionCountries;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("revenue")
    private Long mRevenue;
    @SerializedName("runtime")
    private Long mRuntime;
    @SerializedName("spoken_languages")
    private List<MovieDetailsRootSpokenLanguage> mMovieDetailsRootSpokenLanguages;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("tagline")
    private String mTagline;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("video")
    private Boolean mVideo;
    @SerializedName("vote_average")
    private Double mVoteAverage;
    @SerializedName("vote_count")
    private Long mVoteCount;

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public MovieDetailsRootBelongsToCollection getBelongsToCollection() {
        return mMovieDetailsRootBelongsToCollection;
    }

    public void setBelongsToCollection(MovieDetailsRootBelongsToCollection movieDetailsRootBelongsToCollection) {
        mMovieDetailsRootBelongsToCollection = movieDetailsRootBelongsToCollection;
    }

    public Long getBudget() {
        return mBudget;
    }

    public void setBudget(Long budget) {
        mBudget = budget;
    }

    public List<MovieDetailsRootGenre> getGenres() {
        return mMovieDetailsRootGenres;
    }

    public void setGenres(List<MovieDetailsRootGenre> movieDetailsRootGenres) {
        mMovieDetailsRootGenres = movieDetailsRootGenres;
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

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public List<MovieDetailsRootProductionCompany> getProductionCompanies() {
        return mProductionCompanies;
    }

    public void setProductionCompanies(List<MovieDetailsRootProductionCompany> productionCompanies) {
        mProductionCompanies = productionCompanies;
    }

    public List<MovieDetailsRootProductionCountry> getProductionCountries() {
        return mProductionCountries;
    }

    public void setProductionCountries(List<MovieDetailsRootProductionCountry> productionCountries) {
        mProductionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public Long getRevenue() {
        return mRevenue;
    }

    public void setRevenue(Long revenue) {
        mRevenue = revenue;
    }

    public Long getRuntime() {
        return mRuntime;
    }

    public void setRuntime(Long runtime) {
        mRuntime = runtime;
    }

    public List<MovieDetailsRootSpokenLanguage> getSpokenLanguages() {
        return mMovieDetailsRootSpokenLanguages;
    }

    public void setSpokenLanguages(List<MovieDetailsRootSpokenLanguage> movieDetailsRootSpokenLanguages) {
        mMovieDetailsRootSpokenLanguages = movieDetailsRootSpokenLanguages;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTagline() {
        return mTagline;
    }

    public void setTagline(String tagline) {
        mTagline = tagline;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Boolean getVideo() {
        return mVideo;
    }

    public void setVideo(Boolean video) {
        mVideo = video;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Long voteCount) {
        mVoteCount = voteCount;
    }

}
