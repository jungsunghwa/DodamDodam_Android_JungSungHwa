package kr.hs.dgsw.smartschool.dodamdodam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tchat_database.db";
    @Nullable
    private static DatabaseHelper databaseHelper = null;
    private final DBManager dbManager = new DBManager();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Nullable
    public static DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    @NonNull
    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
            return databaseHelper;
        } else {
            return databaseHelper;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL(dbManager.getCreateTableToken());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "token");
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insert(String tableName, Object insertValue){
        final SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = getContentValuesByInsertValue(insertValue);
        db.insert(tableName, null, contentValues);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ContentValues getContentValuesByInsertValue(Object insertValue) {
        final ContentValues contentValues = new ContentValues();

        Map map = convertObjectToMap(insertValue);

        map.forEach((k,v) ->{
            if (v instanceof Integer){
                contentValues.put((String) k, (int) v);
            } else if (v instanceof String){
                contentValues.put((String) k, (String) v);
            } else if (v instanceof Boolean){
                contentValues.put((String) k, ((Boolean) v) ? 1 : 0);
            } else if (v instanceof Double){
                contentValues.put((String) k, (Double) v);
            } else if (v instanceof Byte[]){
                contentValues.put((String) k, (byte[]) v);
            }
        });

        return contentValues;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public <T> T getData(String tableName, Object getData){
        final SQLiteDatabase db = this.getWritableDatabase();
        Map<String, Object> map;
        map = convertObjectToMap(getData);

        final Cursor res = db.rawQuery("SELECT * FROM "+tableName, null);

        while (res.moveToNext()){
            map.forEach((k,v) ->{
                String key = (String) k;
                if (v instanceof Integer){
                    map.put(key, res.getInt(res.getColumnIndex(key)));
                } else if (v instanceof String){
                    map.put(key, res.getString(res.getColumnIndex(key)));
                } else if (v instanceof Boolean){
                    map.put(key, (res.getInt(res.getColumnIndex(key))) == 1);
                } else if (v instanceof Double){
                    map.put(key, res.getDouble(res.getColumnIndex(key)));
                } else if (v instanceof Byte[]){
                    map.put(key, res.getBlob(res.getColumnIndex(key)));
                }
            });
        }

        Object object = convertMapToObject(map, getData);

        return (T) object;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map convertObjectToMap(Object obj){
        Map map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i= fields.length - 1; i >= 0; i--){
            fields[i].setAccessible(true);
            try{
                map.put(fields[i].getName(), fields[i].get(obj));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return map;
    }


    public Object convertMapToObject(Map<String,Object> map,Object obj){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();

        while(itr.hasNext()){
            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for(int i=0;i<methods.length;i++){
                if(methodString.equals(methods[i].getName())){
                    try{
                        methods[i].invoke(obj, map.get(keyAttribute));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }
}
