
package in.skr.shivamkumar.moviescounter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PersonImagesRoot {

    @SerializedName("id")
    private Long mId;
    @SerializedName("profiles")
    private List<PersonImagesRootProfile> mPersonImagesRootProfiles;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<PersonImagesRootProfile> getProfiles() {
        return mPersonImagesRootProfiles;
    }

    public void setProfiles(List<PersonImagesRootProfile> personImagesRootProfiles) {
        mPersonImagesRootProfiles = personImagesRootProfiles;
    }

}
