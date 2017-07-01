package com.example.s521573.sqlitepractice;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s521573.sqlitepractice.Database.CarContract;
import com.example.s521573.sqlitepractice.Database.CarDbHelper;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private CarListAdapter mAdapter;
    private SQLiteDatabase mDatabase;

    private EditText mManufactureEdit;
    private EditText mModelEdit;
    private EditText mPriceEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManufactureEdit = (EditText)findViewById(R.id.car_manufacture_edit_text);
        mModelEdit = (EditText)findViewById(R.id.car_model_edit_text);
        mPriceEdit = (EditText)findViewById(R.id.car_price_edit_text);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.carRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CarDbHelper dbHelper = new CarDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = getAllCars();

        mAdapter = new CarListAdapter(this,cursor);
        mRecyclerView.setAdapter(mAdapter);

        // To delete a selected Item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long)viewHolder.itemView.getTag();
                removeCar(id);
                mAdapter.swapCursor(getAllCars());
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    public void addToWaitlist(View view) {

        if (mManufactureEdit.getText().length() == 0 || mModelEdit.getText().length() == 0 ||
                mPriceEdit.getText().length() == 0) return;

        int carPrice = 1;
        try{
            carPrice = Integer.parseInt(mPriceEdit.getText().toString());
        }catch (NumberFormatException e){
            Log.e(LOG_TAG,"Failed to parse car price text to number " +e.getMessage() );
        }

        addCar(mManufactureEdit.getText().toString(),mModelEdit.getText().toString(),carPrice);

        // Update the cursor in the adapter to trigger UI to display the new list
        mAdapter.swapCursor(getAllCars());

        //Clear text fields
        mPriceEdit.clearFocus();
        mManufactureEdit.getText().clear();
        mModelEdit.getText().clear();
        mPriceEdit.getText().clear();

        hideSoftKeyboard();
    }

    private Cursor getAllCars() {
        return mDatabase.query(
                CarContract.Carlist.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CarContract.Carlist.COLUMN_TIMESTAMP);
    }

    private long addCar(String manufacture, String model, int carPrice) {

        ContentValues values = new ContentValues();
        values.put(CarContract.Carlist.COLUMN_MANUFACTURE,manufacture);
        values.put(CarContract.Carlist.COLUMN_MODEL,model);
        values.put(CarContract.Carlist.COLUMN_PRICE,carPrice);
        Toast.makeText(this, "Value is just added", Toast.LENGTH_SHORT).show();
        return mDatabase.insert(CarContract.Carlist.TABLE_NAME,null,values);
    }

    private boolean removeCar(long id){
        return mDatabase.delete(CarContract.Carlist.TABLE_NAME, CarContract.Carlist._ID + "=" + id,null) > 0;
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
