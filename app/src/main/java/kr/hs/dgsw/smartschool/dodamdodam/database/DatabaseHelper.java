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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dodam.db";

    private static volatile DatabaseHelper INSTANCE;

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
        db.execSQL(DatabaseManager.getCreateTableToken());
        db.execSQL(DatabaseManager.getCreateTableTime());
        db.execSQL(DatabaseManager.getCreateTablePlace());
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

        for (ContentValues contentValues :
                getContentValuesByInsertValue(insertValue)) {
            db.insert(tableName, null, contentValues);
        }
    }

    private List<ContentValues> getContentValuesByInsertValue(Object insertValue) {
        final List<ContentValues> contentValuesList = new ArrayList<>();

        List<Map<String, Object>> maps = new ArrayList<>();

        if (insertValue instanceof List)
            for (Object obj : (List) insertValue)
                maps.add(convertObjectToMap(obj));
        else maps.add(convertObjectToMap(insertValue));

        for (Map<String, Object> map : maps) {
            ContentValues contentValues = new ContentValues();
            Stream.of(map).forEach(stringObjectEntry -> {
                String k = stringObjectEntry.getKey();
                Object v = stringObjectEntry.getValue();
                if (v instanceof Integer) {
                    contentValues.put(k, (int) v);
                } else if (v instanceof String) {
                    contentValues.put(k, (String) v);
                } else if (v instanceof Boolean) {
                    contentValues.put(k, ((Boolean) v) ? 1 : 0);
                } else if (v instanceof Double) {
                    contentValues.put(k, (Double) v);
                }
            });
            contentValuesList.add(contentValues);
        }

        return contentValuesList;
    }

    public Token getToken() {
        final SQLiteDatabase db = this.getWritableDatabase();

        final Cursor res = db.rawQuery("SELECT * FROM token limit 1", null);

        Token result = new Token();

        while (res.moveToNext()) {
            result.setToken(res.getString(res.getColumnIndex("token")));
            result.setRefreshToken(res.getString(res.getColumnIndex("refreshToken")));
        }

        res.close();

        return result;
    }

    public <T> T getData(String tableName, String where, String value, DatabaseGetDataType<T> getDataType) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + where + " = " + value, null);

        return selectData(res, getDataType);
    }

    public <T> List<T> getData(String tableName, DatabaseGetDataType<T> getDataType) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res = db.rawQuery("SELECT * FROM " + tableName, null);

        return selectDataList(res, getDataType);
    }


    private <T> T selectData(Cursor res, DatabaseGetDataType<T> getDataType) {
        List<T> result = selectDataFromMap(res, getDataType);
        return result.get(0);
    }

    private <T> List<T> selectDataList(Cursor res, DatabaseGetDataType<T> getDataType) {
        return selectDataFromMap(res, getDataType);
    }

    private <T> List<T> selectDataFromMap(Cursor res, DatabaseGetDataType<T> getDataType) {
        List<T> result;
        List<Map<String, Object>> maps = new ArrayList<>();

        while (res.moveToNext()) {
            Map<String, Object> map = convertObjectToMap(getDataType.get());
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
            maps.add(map);
        }

        res.close();

        if (maps.size() == 1)
            result = Collections.singletonList(convertMapToObject(maps.get(0), getDataType.get()));
        else {
            result = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                result.add(this.convertMapToObject(map, getDataType.get()));
            }
        }

        return result;
    }

    private Map<String, Object> convertObjectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = fields.length - 1; i >= 0; i--) {
            fields[i].setAccessible(true);
            try {
                if (fields[i].getType() == int.class || fields[i].getType() == Integer.class) {
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : 0);
                } else if (fields[i].getType() == String.class) {
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : "");
                } else if (fields[i].getType() == Boolean.class) {
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : true);
                } else if (fields[i].getType() == Double.class) {
                    map.put(fields[i].getName(), fields[i].get(obj) != null ? fields[i].get(obj) : 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }


    private <T> T convertMapToObject(Map<String, Object> map, T t) {
        String setMethodString = "set";
        String methodString;

        for (String keyAttribute : map.keySet()) {
            methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
            Method[] methods = t.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (methodString.equals(method.getName())) {
                    try {
                        method.invoke(t, map.get(keyAttribute));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return t;
    }
}


