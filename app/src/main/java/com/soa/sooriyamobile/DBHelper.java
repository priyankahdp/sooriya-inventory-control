package com.soa.sooriyamobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String INVENTORY_TABLE_NAME = "inventory";
    public static final String INVENTORY_COLUMN_ID = "id";
    public static final String INVENTORY_COLUMN_ITEM_NAME = "itemName";
    public static final String INVENTORY_COLUMN_QTY = "qty";
    public static final String INVENTORY_COLUMN_UNIT_PRICE = "unitPrice";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + INVENTORY_TABLE_NAME + " (id INTEGER primary key, " + INVENTORY_COLUMN_ITEM_NAME + " TEXT," + INVENTORY_COLUMN_QTY + " INTEGER, " + INVENTORY_COLUMN_UNIT_PRICE + " REAL)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Rose incense sticks\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Jasmine incense sticks\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Salmal incense sticks\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Mantra incense sticks\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Big candles\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Small candles\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Ice disinfectant 100ml\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Lemon disinfectant 500ml\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"toothpick\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Lemon hand wash 260ml\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Soorya Bundle\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Soorya Gross\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )  VALUES (\"Soorya Dozen\",1,1.00)");
        db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME + " (" + INVENTORY_COLUMN_ITEM_NAME + ", " + INVENTORY_COLUMN_QTY + ", " + INVENTORY_COLUMN_UNIT_PRICE + " )   VALUES (\"Soorya Units\",1,1.00)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(String itemName, int qty, double unitPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVENTORY_COLUMN_ITEM_NAME, itemName);
        contentValues.put(INVENTORY_COLUMN_QTY, qty);
        contentValues.put(INVENTORY_COLUMN_UNIT_PRICE, unitPrice);
        db.insert(INVENTORY_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + INVENTORY_TABLE_NAME + " where " + INVENTORY_COLUMN_ID + " =" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, INVENTORY_TABLE_NAME);
        return numRows;
    }

    public boolean updateItem(Integer id, String itemName, int qty, double unitPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVENTORY_COLUMN_ITEM_NAME, itemName);
        contentValues.put(INVENTORY_COLUMN_QTY, qty);
        contentValues.put(INVENTORY_COLUMN_UNIT_PRICE, unitPrice);
        db.update(INVENTORY_TABLE_NAME, contentValues, INVENTORY_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INVENTORY_TABLE_NAME, INVENTORY_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public void deductInventory(int requestedQtyInt, int recordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + INVENTORY_TABLE_NAME + " SET " + INVENTORY_COLUMN_QTY + " ='" + requestedQtyInt + "' WHERE " + INVENTORY_COLUMN_ID + " ='" + recordId + "'");
    }


    public ArrayList<SaleObj> getAllItemsForSales() {
        ArrayList<SaleObj> array_list = new ArrayList<SaleObj>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + INVENTORY_TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            SaleObj saleObj = new SaleObj();
            saleObj.setId(res.getInt(res.getColumnIndex(INVENTORY_COLUMN_ID)));
            saleObj.setItemName(res.getString(res.getColumnIndex(INVENTORY_COLUMN_ITEM_NAME)));
            saleObj.setQty(res.getInt(res.getColumnIndex(INVENTORY_COLUMN_QTY)));
            saleObj.setUnitPrice(res.getInt(res.getColumnIndex(INVENTORY_COLUMN_UNIT_PRICE)));
            array_list.add(saleObj);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllItems() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + INVENTORY_TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(INVENTORY_COLUMN_ITEM_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public void performTakeout(Integer bundleCount, Integer grossCount, Integer dozenCount, Integer unitCount) {

        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor resBundle = dbRead.rawQuery("select "+INVENTORY_COLUMN_QTY+" from " + INVENTORY_TABLE_NAME + " where " + INVENTORY_COLUMN_ITEM_NAME + " ='Soorya Bundle' ", null);
        //=============================================================
        String bQty=null;
        if (resBundle.moveToFirst()) {
            bQty = resBundle.getString(resBundle.getColumnIndex(INVENTORY_COLUMN_QTY));
            resBundle.close();
        }
        resBundle.close();
        Integer bundleQty= Integer.parseInt(bQty)-bundleCount;
        //=============================================================
        String gQty=null;
        Cursor resGross = dbRead.rawQuery("select "+INVENTORY_COLUMN_QTY+" from " + INVENTORY_TABLE_NAME + " where " + INVENTORY_COLUMN_ITEM_NAME + " ='Soorya Gross' ", null);
        if (resGross.moveToFirst()) {
            gQty = resGross.getString(resGross.getColumnIndex(INVENTORY_COLUMN_QTY));
            resGross.close();
        }
        resGross.close();
        Integer grossQty= Integer.parseInt(gQty)+(5-grossCount);
        //=============================================================
        String dQty=null;
        Cursor resDozen = dbRead.rawQuery("select "+INVENTORY_COLUMN_QTY+" from " + INVENTORY_TABLE_NAME + " where " + INVENTORY_COLUMN_ITEM_NAME + " ='Soorya Dozen' ", null);
        if (resDozen.moveToFirst()) {
            dQty = resDozen.getString(resDozen.getColumnIndex(INVENTORY_COLUMN_QTY));
            resDozen.close();
        }
        resDozen.close();
        Integer dozenQty= Integer.parseInt(dQty)+((grossCount*12)-dozenCount);
        //=============================================================
        String uQty=null;
        Cursor resUnits = dbRead.rawQuery("select "+INVENTORY_COLUMN_QTY+" from " + INVENTORY_TABLE_NAME + " where " + INVENTORY_COLUMN_ITEM_NAME + " ='Soorya Units' ", null);
        if (resUnits.moveToFirst()) {
            uQty = resUnits.getString(resUnits.getColumnIndex(INVENTORY_COLUMN_QTY));
            resUnits.close();
        }
        resUnits.close();
        Integer unitsQty= Integer.parseInt(uQty)+unitCount;
        //=============================================================
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        dbWrite.execSQL("UPDATE "+INVENTORY_TABLE_NAME+" SET "+INVENTORY_COLUMN_QTY +"="+ bundleQty +" WHERE "+INVENTORY_COLUMN_ITEM_NAME+" ='Soorya Bundle' ");
        dbWrite.execSQL("UPDATE "+INVENTORY_TABLE_NAME+" SET "+INVENTORY_COLUMN_QTY +"="+ grossQty +"  WHERE "+INVENTORY_COLUMN_ITEM_NAME+" ='Soorya Gross' ");
        dbWrite.execSQL("UPDATE "+INVENTORY_TABLE_NAME+" SET "+INVENTORY_COLUMN_QTY +"="+ dozenQty +"  WHERE "+INVENTORY_COLUMN_ITEM_NAME+" ='Soorya Dozen' ");
        dbWrite.execSQL("UPDATE "+INVENTORY_TABLE_NAME+" SET "+INVENTORY_COLUMN_QTY +"="+ unitsQty +"  WHERE "+INVENTORY_COLUMN_ITEM_NAME+" ='Soorya Units' ");
    }
}