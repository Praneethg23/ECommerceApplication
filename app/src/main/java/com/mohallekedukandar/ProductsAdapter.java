package com.mohallekedukandar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProductsAdapter  extends ArrayAdapter<Products> {

    private Context context;
    private List<Products> items;

    public ProductsAdapter(Context context,List<Products> list)
    {
        super(context,R.layout.row_layout,list);
        this.items=list;
        this.context=context;
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

        String name="p"+items.get(position).getProduct_Id();

        int resId = Integer.parseInt(items.get(position).getProduct_Id());
        int idres= ApplicationClass.imgids[resId-100];
        tvNameP.setText(items.get(position).getName());
        tvQuanP.setText(items.get(position).getPackage_Quantity());
        tvPriceP.setText("Rs "+(Integer.toString(items.get(position).getPrice())));
        ivProdP.setImageResource(idres);
        return convertView;
    }

}
