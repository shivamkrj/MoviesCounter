package in.skr.shivamkumar.moviescounter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderCast extends RecyclerView.ViewHolder {

    View itemView;
    ImageView castImageView;
    TextView castNameTextView;
    TextView castRoleTextView;

    public ViewHolderCast(View itemView) {
        super(itemView);
        this.itemView = itemView;
        castImageView = itemView.findViewById(R.id.castImageView);
        castNameTextView = itemView.findViewById(R.id.castName);
        castRoleTextView = itemView.findViewById(R.id.castRole);
    }
}
