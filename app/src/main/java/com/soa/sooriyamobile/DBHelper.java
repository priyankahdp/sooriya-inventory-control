package com.soa.sooriyamobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MyDBName.db";
   public static final String INVENTORY_TABLE_NAME = "inventory";
   public static final String INVENTORY_COLUMN_ID = "id";
   public static final String INVENTORY_COLUMN_ITEM_NAME = "itemName";
   public static final String INVENTORY_COLUMN_QTY = "qty";
   public static final String INVENTORY_COLUMN_UNIT_PRICE = "unitPrice";

   private HashMap hp;

   public DBHelper(Context context) {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table "+INVENTORY_TABLE_NAME+" (id INTEGER primary key, "+INVENTORY_COLUMN_ITEM_NAME+" TEXT,"+INVENTORY_COLUMN_QTY+" INTEGER, "+INVENTORY_COLUMN_UNIT_PRICE+" REAL)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Rose incense sticks\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Jasmine incense sticks\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Salmal incense sticks\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Mantra incense sticks\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Big candles\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Small candles\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Ice disinfectant 100ml\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Lemon disinfectant 500ml\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"toothpick\",0,0.00)");
      db.execSQL("INSERT INTO " + INVENTORY_TABLE_NAME+ " ("+INVENTORY_COLUMN_ITEM_NAME+", "+INVENTORY_COLUMN_QTY+", "+INVENTORY_COLUMN_UNIT_PRICE+" ) " +
              " VALUES (\"Lemon hand wash 260ml\",0,0.00)");

   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS "+INVENTORY_TABLE_NAME);
      onCreate(db);
   }

   public boolean insertItem (String itemName, int qty, double unitPrice) {
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
      Cursor res =  db.rawQuery( "select * from "+INVENTORY_TABLE_NAME+" where "+INVENTORY_COLUMN_ID+" ="+id+"", null );
      return res;
   }
   
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, INVENTORY_TABLE_NAME);
      return numRows;
   }
   
   public boolean updateItem (Integer id, String itemName, int qty, double unitPrice) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(INVENTORY_COLUMN_ITEM_NAME, itemName);
      contentValues.put(INVENTORY_COLUMN_QTY, qty);
      contentValues.put(INVENTORY_COLUMN_UNIT_PRICE, unitPrice);
      db.update(INVENTORY_TABLE_NAME, contentValues, INVENTORY_COLUMN_ID+" = ? ", new String[] { Integer.toString(id) } );
      return true;
   }

   public Integer deleteItem (Integer id) {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete(INVENTORY_TABLE_NAME,INVENTORY_COLUMN_ID+" = ? ", new String[] { Integer.toString(id) });
   }
   
   public ArrayList<String> getAllItems() {
      ArrayList<String> array_list = new ArrayList<String>();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from "+ INVENTORY_TABLE_NAME, null );
      res.moveToFirst();
      while(res.isAfterLast() == false){
         array_list.add(res.getString(res.getColumnIndex(INVENTORY_COLUMN_ITEM_NAME)));
         res.moveToNext();
      }
      return array_list;
   }
}