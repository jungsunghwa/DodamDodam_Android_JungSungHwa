package kr.hs.dgsw.smartschool.dodamdodam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annimon.stream.Stream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dodam_db.db";

    @Nullable
    private static DatabaseHelper databaseHelper = null;
    private final DatabaseManager dbManager = new DatabaseManager();

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Nullable
    public static DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    @NonNull
    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = DatabaseHelper.getDatabaseHelper(context);
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
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_TOKEN);
        onCreate(db);
    }

    public void insert(String tableName, Object insertValue) {
        final SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues;
        contentValues = getContentValuesByInsertValue(insertValue);

        db.insert(tableName, null, contentValues);
    }

    private ContentValues getContentValuesByInsertValue(Object insertValue) {
        final ContentValues contentValues = new ContentValues();

        Map<Object, Object> map = convertObjectToMap(insertValue);

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
            } else if (v instanceof byte[]) {
                contentValues.put((String) k, (byte[]) v);
            }
        });

        return contentValues;
    }

    public <T> T getData(String tableName, Object getData) {
        final SQLiteDatabase db = this.getWritableDatabase();
        Map<String, Object> map;
        map = convertObjectToMap(getData);

        final Cursor res = db.rawQuery("SELECT * FROM " + tableName, null);

        while (res.moveToNext()) {
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
            });
        }

        res.close();

        Object object = convertMapToObject(map, getData);

        return (T) object;
    }


    public Map convertObjectToMap(Object obj) {
        Map map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = fields.length - 1; i >= 0; i--) {
            fields[i].setAccessible(true);
            try {
                map.put(fields[i].getName(), fields[i].get(obj));
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
