package com.example.movierec.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movierec.ListItem.NormalItem;
import com.example.movierec.R;

import java.util.List;

/**
 * Created by August on 2018/5/8.
 */

public class NormalItemAdapter extends ArrayAdapter<NormalItem> {

    private int resourceId;

    public NormalItemAdapter(Context context, int textViewResourceId, List<NormalItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View covertView, ViewGroup parent) {
        NormalItem normalItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView normalItem_title = (TextView) view.findViewById(R.id.normalitem_title);
        ImageView normalItem_image = (ImageView) view.findViewById(R.id.normalitem_image);
        ImageView normalItem_arrow = (ImageView) view.findViewById(R.id.normalitem_arrow);
        normalItem_title.setText(normalItem.getTitle());
        normalItem_image.setImageResource(normalItem.getImageID());
        normalItem_arrow.setImageResource(normalItem.getArrowID());
        return view;
    }
}
