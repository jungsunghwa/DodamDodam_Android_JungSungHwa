package kr.hs.dgsw.smartschool.dodamdodam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.annimon.stream.Stream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dodam_db";

    private static volatile DatabaseHelper INSTANCE;
    private final DatabaseManager dbManager = new DatabaseManager();

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @NonNull
    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if (INSTANCE == null)
                    INSTANCE = new DatabaseHelper(context);
            }
        }
        return INSTANCE;
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

    public void insert(String tableName, Object insertValue) {
        final SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues;
        contentValues = getContentValuesByInsertValue(insertValue);

        db.insert(tableName, null, contentValues);
    }

    @SuppressWarnings("unchecked")
    private ContentValues getContentValuesByInsertValue(Object insertValue) {
        final ContentValues contentValues = new ContentValues();

        ArrayList<Map<Object, Object>> maps = new ArrayList<>();

        if (insertValue instanceof ArrayList) {
            for (Object obj : (ArrayList) insertValue) {
                maps.add(convertObjectToMap(obj));
            }
        } else maps.add(convertObjectToMap(insertValue));

        for (Map<Object, Object> map : maps) {
            Stream.of(map).forEach(stringObjectEntry -> {
                Object k = stringObjectEntry.getKey();
                Object v = stringObjectEntry.getValue();
                if (v instanceof Integer) {
                    contentValues.put((String) k, (int) v);
                } else if (v instanceof String) {
                    contentValues.put((String) k, (String) v);
                } else if (v instanceof Boolean) {
                    contentValues.put((String) k, ((Boolean) v) ? 1 : 0);
                } else if (v instanceof Double) {
                    contentValues.put((String) k, (Double) v);
                }
            });
        }

        return contentValues;
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(String tableName, Object getData) {
        final SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        AtomicReference<T> result = new AtomicReference<>();
        boolean isList = getData instanceof ArrayList;

        if (isList){
            for (Object obj : (ArrayList) getData){
                maps.add(convertObjectToMap(obj));
            }
        }else maps.add(convertObjectToMap(getData));

        final Cursor res = db.rawQuery("SELECT * FROM "+ tableName, null);

        while (res.moveToNext()) {
            for (Map<String, Object> map : maps) {
                Stream.of(map).forEach(stringObjectEntry -> {
                    String k = stringObjectEntry.getKey();
                    Object v = stringObjectEntry.getValue();
                    if (v instanceof Integer) {
                        map.put(k, res.getInt(res.getColumnIndex(k)));
                    } else if (v instanceof String) {
                        map.put(k, res.getString(res.getColumnIndex(k)));
                    } else if (v instanceof Boolean) {
                        map.put(k, (res.getInt(res.getColumnIndex(k))) == 1);
                    } else if (v instanceof Double) {
                        map.put(k, res.getDouble(res.getColumnIndex(k)));
                    } else if (v instanceof Byte[]) {
                        map.put(k, res.getBlob(res.getColumnIndex(k)));
                    }
                    Object object = convertMapToObject(map, getData);
                    if (isList) ((ArrayList) getData).add(object);
                    else result.set((T) object);
                });
            }
        }
        res.close();
        if (isList) return (T) getData;
        else return result.get();
    }

    @SuppressWarnings("unchecked")
    public Map convertObjectToMap(Object obj) {
        Map map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = fields.length - 1; i >= 0; i--) {
            fields[i].setAccessible(true);
            try {
                if (fields[i].getType() == Integer.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : 0);
                } else if (fields[i].getType() ==  String.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : "");
                } else if (fields[i].getType() ==  Boolean.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : true);
                } else if (fields[i].getType() ==  Double.class){
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }


    public Object convertMapToObject(Map<String, Object> map, Object obj) {
        String keyAttribute;
        String setMethodString = "set";
        String methodString;

        for (String s : map.keySet()) {
            keyAttribute = s;
            methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (methodString.equals(method.getName())) {
                    try {
                        method.invoke(obj, map.get(keyAttribute));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }
}
