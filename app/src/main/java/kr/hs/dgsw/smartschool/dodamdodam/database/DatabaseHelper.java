package kr.hs.dgsw.smartschool.dodamdodam.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import retrofit2.http.HEAD;

import com.annimon.stream.Stream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dodam_db.db";

    @Nullable
    private static DatabaseHelper databaseHelper = null;
    private final DatabaseManager dbManager = new DatabaseManager();

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
        db.execSQL(dbManager.getCreateTableTime());
        db.execSQL(dbManager.getCreateTablePlace());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_TOKEN);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_TIME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_PLACE);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insert(String tableName, Object insertValue) {
        final SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues;
        contentValues = getContentValuesByInsertValue(insertValue);

        db.insert(tableName, null, contentValues);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ContentValues getContentValuesByInsertValue(Object insertValue) {
        final ContentValues contentValues = new ContentValues();
        ArrayList<Map> maps = new ArrayList<>();

        if (insertValue instanceof ArrayList){
            for (Object obj : (ArrayList) insertValue){
                maps.add(convertObjectToMap(obj));
            }
        }else maps.add(convertObjectToMap(insertValue));

        for (Map map : maps){
            map.forEach((k,v) ->{
                if (v instanceof Integer){
                    contentValues.put((String) k, (int) v);
                } else if (v instanceof String){
                    contentValues.put((String) k, (String) v);
                } else if (v instanceof Boolean){
                    contentValues.put((String) k, ((Boolean) v) ? 1 : 0);
                } else if (v instanceof Double){
                    contentValues.put((String) k, (Double) v);
                }
            });
        }

        return contentValues;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public <T> T getData(String tableName, Object getData) {
        final SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        T result = (T) new ArrayList<>();
        boolean isList = getData instanceof ArrayList;

        if (isList){
            for (Object obj : (ArrayList) getData){
                maps.add(convertObjectToMap(obj));
            }
        }else maps.add(convertObjectToMap(getData));

        @SuppressLint("Recycle") final Cursor res = db.rawQuery("SELECT * FROM "+ tableName, null);

        while (res.moveToNext()){
            for (Map map :maps) {
                map.forEach((k, v) -> {
                    String key = (String) k;
                    if (v instanceof Integer) {
                        map.put(key, res.getInt(res.getColumnIndex(key)));
                    } else if (v instanceof String) {
                        map.put(key, res.getString(res.getColumnIndex(key)));
                    } else if (v instanceof Boolean) {
                        map.put(key, (res.getInt(res.getColumnIndex(key))) == 1);
                    } else if (v instanceof Double) {
                        map.put(key, res.getDouble(res.getColumnIndex(key)));
                    }
                });
                Object object = convertMapToObject(map, getData);
                if (isList) ((ArrayList) getData).add(object);
                else return (T) object;
            }
        }
        return result;
    }


    public Map convertObjectToMap(Object obj) {
        Map map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = fields.length - 1; i >= 0; i--) {
            fields[i].setAccessible(true);
            try{
                if (fields[i].getType() == Integer.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : 0);
                } else if (fields[i].getType() ==  String.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : "");
                } else if (fields[i].getType() ==  Boolean.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : true);
                } else if (fields[i].getType() ==  Double.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : 0);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return map;
    }


    public Object convertMapToObject(Map<String, Object> map, Object obj) {
        String keyAttribute;
        String setMethodString = "set";
        String methodString;
        Iterator itr = map.keySet().iterator();

        while (itr.hasNext()) {
            keyAttribute = (String) itr.next();
            methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodString.equals(methods[i].getName())) {
                    try {
                        methods[i].invoke(obj, map.get(keyAttribute));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }
}
