package in.skr.shivamkumar.moviescounter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCasts extends RecyclerView.Adapter<ViewHolderCast> {

    Context context;
    ViewItemClickListener listener;
    ArrayList<CastRootCast> itemsCast;

    public AdapterCasts(Context context, ViewItemClickListener listener, ArrayList<CastRootCast> itemsCast) {
        this.context = context;
        this.listener = listener;
        this.itemsCast = itemsCast;
    }

    @NonNull
    @Override
    public ViewHolderCast onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_layout_casts,viewGroup,false);
        return new ViewHolderCast(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCast viewHolderCast, int position) {
        CastRootCast item = itemsCast.get(position);
        viewHolderCast.castNameTextView.setText(item.getName());
        viewHolderCast.castRoleTextView.setText(item.getCharacter());

        ImageView imageView = viewHolderCast.castImageView;
        String url = "https://image.tmdb.org/t/p/w185/"+ item.getProfilePath();
        Log.d("play",url+"");
        Picasso.get().load(url).fit()
                .placeholder(R.drawable.images_loading)
                .into(viewHolderCast.castImageView);

    }

    @Override
    public int getItemCount() {
        return itemsCast.size();
    }
}
