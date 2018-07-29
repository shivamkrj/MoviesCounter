package in.skr.shivamkumar.moviescounter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderRectangular extends RecyclerView.ViewHolder {

    View itemView;
    ImageView imageView;
    TextView movieNameTextView;
    TextView ratingTextView;
    TextView genreTextView;
    ImageView checkBox;

    public ViewHolderRectangular(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView=itemView.findViewById(R.id.imageView);
        movieNameTextView=itemView.findViewById(R.id.movieNameTextView);
        ratingTextView=itemView.findViewById(R.id.ratingTextView);
        genreTextView=itemView.findViewById(R.id.genreTextView);
        checkBox=itemView.findViewById(R.id.checkbox);
    }
}
