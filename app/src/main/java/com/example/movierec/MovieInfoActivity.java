package com.example.movierec;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movierec.Helper.DataBaseHelper;

import org.w3c.dom.Text;

public class MovieInfoActivity extends AppCompatActivity {

    private String userID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        Intent movieInfoI = getIntent();
        Bundle movieInfoB = movieInfoI.getExtras();

        TextView movieinfo_text_title = (TextView) findViewById(R.id.movieinfo_text_title);
        TextView movieinfo_text_genres = (TextView) findViewById(R.id.movieinfo_text_genresc);
        TextView movieinfo_text_release = (TextView) findViewById(R.id.movieinfo_text_releasec);
        final TextView movieinfo_text_rate = (TextView) findViewById(R.id.movieinfo_text_rate);
        final TextView movieinfo_text_ratec = (TextView) findViewById(R.id.movieinfo_text_ratec);
        final RatingBar movieinfo_bar_rate = (RatingBar) findViewById(R.id.movieinfo_bar_rate);

        final String titles = movieInfoB.getString("title");
        userID = movieInfoB.getString("userID");

        ImageView movieinfo_image_pic = (ImageView) findViewById(R.id.movieinfo_image_pic);
        switch(titles) {
            case "L.A. Confidential (1997)":
                movieinfo_image_pic.setImageResource(R.drawable.laconfidential);
                break;
            case "Hitman (2007)":
                movieinfo_image_pic.setImageResource(R.drawable.hitman);
                break;
            case "Blade Runner (1982)":
                movieinfo_image_pic.setImageResource(R.drawable.bladerunner);
                break;
            case "Back to the Future Part II (1989)":
                movieinfo_image_pic.setImageResource(R.drawable.backtothefuturepart2);
                break;
            case "Shrek 2 (2004)":
                movieinfo_image_pic.setImageResource(R.drawable.shrek2);
                break;
            case "Sky Captain and the World of Tomorrow (2004)":
                movieinfo_image_pic.setImageResource(R.drawable.skycaptainandtheworldoftomorrow);
                break;
            case "Die Hard 2 (1990)":
                movieinfo_image_pic.setImageResource(R.drawable.diehard2);
                break;
            case "Kung Fu Panda (2008)":
                movieinfo_image_pic.setImageResource(R.drawable.kungfupanda);
                break;
            case "Godfather, The (1972)":
                movieinfo_image_pic.setImageResource(R.drawable.thegodfather);
                break;
            case "Schindler's List (1993)":
                movieinfo_image_pic.setImageResource(R.drawable.schindlerslist);
                break;
            case "Godfather: Part II, The (1974)":
                movieinfo_image_pic.setImageResource(R.drawable.thegodfatherpart2);
                break;
            case "Star Wars: Episode IV - A New Hope (1977)":
                movieinfo_image_pic.setImageResource(R.drawable.starwarsanewhope);
                break;
            case "Fight Club (1999)":
                movieinfo_image_pic.setImageResource(R.drawable.fightclub);
                break;
            case "Braveheart (1995)":
                movieinfo_image_pic.setImageResource(R.drawable.braveheart);
                break;
            case "Star Wars: Episode VI - Return of the Jedi (1983)":
                movieinfo_image_pic.setImageResource(R.drawable.starwarsreturnofthejedi);
                break;
            case "Alien (1979)":
                movieinfo_image_pic.setImageResource(R.drawable.alien);
                break;
            case "Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb (1964)":
                movieinfo_image_pic.setImageResource(R.drawable.drstrangelove);
                break;
            case "Taxi Driver (1976)":
                movieinfo_image_pic.setImageResource(R.drawable.taxidriver);
                break;
            case "Toy Story (1995)":
                movieinfo_image_pic.setImageResource(R.drawable.toystory);
                break;
            case "Beauty and the Beast (1991)":
                movieinfo_image_pic.setImageResource(R.drawable.beautyandthebeast);
                break;
            case "Cinderella (1950)":
                movieinfo_image_pic.setImageResource(R.drawable.cinderella);
                break;
            case "Jurassic Park (1993)":
                movieinfo_image_pic.setImageResource(R.drawable.jurassicpark);
                break;
            case "Titanic (1997)":
                movieinfo_image_pic.setImageResource(R.drawable.titanic);
                break;
            case "Witness for the Prosecution (1957)":
                movieinfo_image_pic.setImageResource(R.drawable.witness);
                break;
            case "12 Angry Men (1957)":
                movieinfo_image_pic.setImageResource(R.drawable.angrymen);
                break;
            case "Tom and Huck (1995)":
                movieinfo_image_pic.setImageResource(R.drawable.tomandhuck);
                break;
            default:
                movieinfo_image_pic.setImageResource(R.drawable.zoo);
        }

        String tag = movieInfoB.getString("tag");
        if(tag.equals("noRate")) {


            String[] movieInfoString = movieInfobyTitle(titles);
            String title = movieInfoString[0];
            String genres = movieInfoString[1];
            String release = movieInfoString[2];
            int rate = 0;

            movieinfo_text_title.setText(title);
            movieinfo_text_genres.setText(genres);
            movieinfo_text_release.setText(release);
            movieinfo_text_rate.setText("You have not rated yet");
            movieinfo_text_ratec.setText("");
            movieinfo_bar_rate.setRating(rate);
        }
        else {
            String[] movieInfoString = movieInfo(userID, titles);
            String title = movieInfoString[0];
            String genres = movieInfoString[1];
            String release = movieInfoString[2];
            String rate = movieInfoString[3];

            movieinfo_text_title.setText(title);
            movieinfo_text_genres.setText(genres);
            movieinfo_text_release.setText(release);
            movieinfo_text_rate.setText("Your rate: ");
            movieinfo_text_ratec.setText(rate);
            movieinfo_bar_rate.setRating(Float.parseFloat(rate));
        }

        Button movieinfo_btn_rate = (Button) findViewById(R.id.movieinfo_btn_rate);
        movieinfo_btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemID = getItemID(titles);
                float newRate = movieinfo_bar_rate.getRating();
                refreshRate(userID, itemID, Float.toString(newRate));
                Toast.makeText(MovieInfoActivity.this, "Update rate successful", Toast.LENGTH_SHORT).show();

                String rateU = getRate(userID, itemID);
                movieinfo_text_rate.setText("Your rate: ");
                movieinfo_text_ratec.setText(rateU);
                movieinfo_bar_rate.setRating(Float.parseFloat(rateU));
            }
        });

        Button movieinfo_btn_back = (Button) findViewById(R.id.movieinfo_btn_back);
        movieinfo_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public String getItemID(String titles) {
        String itemID = "1";
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "iteminfo" ;
        String[] columns = new  String[] {"itemID"};
        String selection = "title=?" ;
        String[] selectionArgs = new String[]{titles};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            itemID = cursor.getString(cursor.getColumnIndex("itemID"));
        }
        return itemID;
    }

    public void refreshRate(String userID, String itemID, String newRate) {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "rated" ;
        String[] columns = new  String[] {"score"};
        String selection = "userID=? and itemID=?" ;
        String[] selectionArgs = new String[]{userID, itemID};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            myDbHelper.updateRate(userID, itemID, newRate);
        }
        else {
            myDbHelper.insertRate(userID, itemID, newRate);
        }
    }

    public String[] movieInfo(String userID, String titles) {

        String[] res = new String[4];
        String itemID = new String("1");

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "iteminfo" ;
        String[] columns = new  String[] {"itemID", "genres"};
        String selection = "title=?" ;
        String[] selectionArgs = new  String[]{titles};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            itemID = cursor.getString(cursor.getColumnIndex("itemID"));
            res[1] = cursor.getString(cursor.getColumnIndex("genres"));
            String[] ss = new String[20];
            ss = titles.split("\\(");
            res[0] = ss[0];
            String release = ss[1];
            res[2] = release.substring(0, release.length()-1);
        }
        res[3] = getRate(userID, itemID);
        return res;
    }

    public String getRate(String userID, String itemID) {

        String rate = new String("3");

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "rated" ;
        String[] columns = new  String[] {"score"};
        String selection = "userID=? and itemID = ?" ;
        String[] selectionArgs = new  String[]{userID, itemID};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            rate = cursor.getString(cursor.getColumnIndex("score"));
        }
        return rate;
    }

    public String[] movieInfobyTitle(String titles) {
        String[] res = new String[3];

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "iteminfo" ;
        String[] columns = new  String[] {"itemID", "genres"};
        String selection = "title=?" ;
        String[] selectionArgs = new  String[]{titles};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            res[1] = cursor.getString(cursor.getColumnIndex("genres"));
            String[] ss = new String[20];
            ss = titles.split("\\(");
            res[0] = ss[0];
            String release = ss[1];
            res[2] = release.substring(0, release.length()-1);
        }
        return res;
    }
}
