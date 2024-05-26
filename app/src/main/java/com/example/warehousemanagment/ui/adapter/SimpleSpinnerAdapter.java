package com.example.warehousemanagment.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.model.models.login.CatalogModel;

import java.util.ArrayList;
import java.util.List;

public class SimpleSpinnerAdapter extends ArrayAdapter<CatalogModel>
{
    public SimpleSpinnerAdapter(List<CatalogModel> objects, @NonNull Context context) {
        super(context, R.layout.pattern_simple_spinner, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return rowView(convertView, position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return rowView(convertView, position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        //Views
        TextView tv;
        View v;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv=itemView.findViewById(R.id.spinnerText);
            v=itemView.findViewById(R.id.view);
        }
    }

    private View rowView(View convertView, int position)
    {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pattern_simple_spinner,null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);

        viewHolder.tv.setText(getItem(position).getTitle());


        return convertView;
    }
}
