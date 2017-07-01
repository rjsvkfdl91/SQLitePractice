package com.example.s521573.sqlitepractice;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s521573.sqlitepractice.Database.CarContract;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder>{

    private Context mContext;
    private Cursor mCursor;

    public CarListAdapter(Context mContext, Cursor cursor) {
        this.mContext = mContext;
        this.mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View  view = inflater.inflate(R.layout.car_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        while (!mCursor.moveToPosition(position)) return;

        final String manufacture = mCursor.getString(mCursor.getColumnIndex(CarContract.Carlist.COLUMN_MANUFACTURE));
        final String model = mCursor.getString(mCursor.getColumnIndex(CarContract.Carlist.COLUMN_MODEL));
        final int price = mCursor.getInt(mCursor.getColumnIndex(CarContract.Carlist.COLUMN_PRICE));
        long id = mCursor.getLong(mCursor.getColumnIndex(CarContract.Carlist._ID));

        holder.mManufacture.setText(manufacture);
        holder.mModel.setText(model);
        holder.mPrice.setText(String.valueOf(price));
        holder.itemView.setTag(id);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, model+ " of "+manufacture+" is: "+price, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null)
            mCursor.close();
        mCursor = newCursor;

        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mManufacture;
        private TextView mModel;
        private TextView mPrice;
        private View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            mManufacture = itemView.findViewById(R.id.manufacture_text_view);
            mModel = itemView.findViewById(R.id.model_text_view);
            mPrice = itemView.findViewById(R.id.price_text_view);
            mView = itemView;
        }
    }
}
