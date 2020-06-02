package com.mohallekedukandar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Products> {
    private Context context;
    private List<Products> items;
    private List<CartObj> ctems;

    public CartAdapter(Context context,List<Products> list,List<CartObj> list1)
    {
        super(context,R.layout.row_layout,list);
        this.items=list;
        this.context=context;
        this.ctems=list1;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.row_layout, parent, false);

        ImageView ivProdP = convertView.findViewById(R.id.ivProdP);
        TextView tvNameP = convertView.findViewById(R.id.tvNameP);
        TextView tvPriceP = convertView.findViewById(R.id.tvPriceP);
        TextView tvQuanP = convertView.findViewById(R.id.tvQuanP);

        int totpr =  items.get(position).getPrice();
        totpr = totpr*ctems.get(position).getQunt();

        int resId = Integer.parseInt(items.get(position).getProduct_Id());
        int idres= ApplicationClass.imgids[resId-100];
        tvNameP.setText(items.get(position).getName());
        tvQuanP.setText(" "+ctems.get(position).getQunt());
        tvPriceP.setText("Rs "+totpr);
        ivProdP.setImageResource(idres);
        return convertView;
    }
}
