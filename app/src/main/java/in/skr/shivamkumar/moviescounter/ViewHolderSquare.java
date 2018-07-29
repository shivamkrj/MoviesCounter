package in.skr.shivamkumar.moviescounter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderSquare extends RecyclerView.ViewHolder {

    View itemView;
    ImageView imageView;
    TextView movieNameTextView;
    ImageView checkBox;

    public ViewHolderSquare(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView=itemView.findViewById(R.id.imageView);
        movieNameTextView=itemView.findViewById(R.id.movieNameTextView);
        checkBox=itemView.findViewById(R.id.checkbox);
    }
}
