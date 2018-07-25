package in.skr.shivamkumar.moviescounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSquareView extends RecyclerView.Adapter<ViewHolderSquare> {

    Context context;
    ViewItemClickListener listener;
    ArrayList<UpcomingResult> items;

    public AdapterSquareView(Context context, ArrayList<UpcomingResult> items, ViewItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderSquare onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_layout_square,viewGroup,false);
        return new ViewHolderSquare(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSquare viewHolder, int i) {
        UpcomingResult item = items.get(i);
        viewHolder.movieNameTextView.setText(item.getTitle());

        String imgUrl = "https://image.tmdb.org/t/p/w500" + item.getPosterPath();
        ImageView imageView = viewHolder.imageView;
        Picasso.get().load(imgUrl).fit().into(imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(viewHolder.itemView,viewHolder.getAdapterPosition());
            }
        });
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set Favorite
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}