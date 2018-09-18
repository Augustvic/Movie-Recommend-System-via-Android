package com.example.movierec.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movierec.R;
import com.example.movierec.ListItem.RecItem;

import java.util.List;

/**
 * Created by August on 2018/5/4.
 */

public class RecItemAdapter extends ArrayAdapter<RecItem>{

    private int resourceId;

    public RecItemAdapter(Context context, int textViewResourceId, List<RecItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView (int position, View covertView, ViewGroup parent) {
        RecItem movie = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView recitem_image = (ImageView) view.findViewById(R.id.recitem_image);
        TextView recitem_title = (TextView) view.findViewById(R.id.recitem_title);
        ImageView recitem_rank = (ImageView) view.findViewById(R.id.recitem_rank);
        recitem_image.setImageResource(movie.getImageID());
        recitem_title.setText(movie.getTitle());
        recitem_rank.setImageResource(movie.getRankID());
        return view;
    }
}
