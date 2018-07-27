package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class CastDetailsActivity extends Activity {

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);

        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);


    }
}
