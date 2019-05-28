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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.b1nd.service.model.ChildrenLog;
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.DepartmentLog;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.b1nd.service.model.Parent;
import kr.hs.dgsw.b1nd.service.model.ParentLog;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dodam.db";

    private static final int DATABASE_VERSION = 1;

    private static volatile DatabaseHelper INSTANCE;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        db.execSQL(DatabaseManager.getCreateTableToken());
        db.execSQL(DatabaseManager.getCreateTableTime());
        db.execSQL(DatabaseManager.getCreateTablePlace());
        db.execSQL(DatabaseManager.getCreateTableClass());
        db.execSQL(DatabaseManager.getCreateTableLocation());
        db.execSQL(DatabaseManager.getCreateTableStudent());
        db.execSQL(DatabaseManager.getCreateTableTeacher());
        db.execSQL(DatabaseManager.getCreateTableMember());
        db.execSQL(DatabaseManager.getCreateTableParentsLogs());
        db.execSQL(DatabaseManager.getCreateTableDepartmentLog());
        db.execSQL(DatabaseManager.getCreateTableChildrenLog());
        db.execSQL(DatabaseManager.getCreateTableParent());
        db.execSQL(DatabaseManager.getCreateTableBus());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_TOKEN);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_TIME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_PARENTS_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_DEPARTMENT_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_PARENT);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_CHILDREN_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_BUS);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void insert(String tableName, Object insertValue) {
        final SQLiteDatabase db = this.getWritableDatabase();

        for (ContentValues contentValues :
                getContentValuesByInsertValue(insertValue)) {
            db.insert(tableName, null, contentValues);
        }
    }

    public Member getMyInfo(){
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor res = db.rawQuery("SELECT * FROM member WHERE id =  '" +Utils.myId +"'",null);

        return getMemberInfo(res);
    }

    public Member getStudentByIdx(int idx){
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM student WHERE idx =  '" + idx +"'",null);

        String memberId = null;

        while (res.moveToNext()) {
            memberId = res.getString(res.getColumnIndex("memberId"));
        }

        if (memberId == null) return null;
        res = db.rawQuery("SELECT * FROM member WHERE id =  '" + memberId +"'",null);

        return getMemberInfo(res);
    }

    public Member getMemberInfo(Cursor res){

        final SQLiteDatabase db = this.getReadableDatabase();
        final Member member = new Member();

        while (res.moveToNext()) {
            member.setId(res.getString(res.getColumnIndex("id")));
            member.setName(res.getString(res.getColumnIndex("name")));
            member.setMobile(res.getString(res.getColumnIndex("mobile")));
            member.setEmail(res.getString(res.getColumnIndex("email")));
            member.setProfileImageWithout(res.getString(res.getColumnIndex("profileImage")));
            member.setStatusMessage(res.getString(res.getColumnIndex("statusMessage")));
            member.setAuth(res.getInt(res.getColumnIndex("auth")));
        }

        switch (member.getAuth()) {
            case 1: /* STUDENT */
                return getStudentByMember(new Student(member));
            case 2: /* TEACHER */
                return getTeacherByMember(new Teacher(member));
            case 3: /* PARENT */
                return getParentByMember(new Parent(member));
        }

        res.close();

        return member;
    }

    private Parent getParentByMember(Member member) {
        Parent parent = new Parent(member);
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res =
                db.rawQuery(
                        "SELECT * FROM parent WHERE memberId = '" + member.getId() + "'", null);

        while (res.moveToNext()) {
            parent.setIdx(res.getInt(res.getColumnIndex("idx")));
        }

        parent.setChildrenLogs(getChildrenLogs(parent.getIdx()));

        res.close();

        return parent;
    }

    private ChildrenLog[] getChildrenLogs(int parentIdx) {
        ArrayList<ChildrenLog> childrenLogs = new ArrayList<>();

        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res =
                db.rawQuery("SELECT * FROM childrenLog WHERE studentIdx = " + parentIdx, null);

        while (res.moveToNext()) {
            ChildrenLog childrenLog = new ChildrenLog();

            childrenLog.setIdx(res.getInt(res.getColumnIndex("idx")));
            childrenLog.setChildIdx(res.getInt(res.getColumnIndex("parentIdx")));

            childrenLogs.add(childrenLog);
        }

        res.close();

        return childrenLogs.toArray(new ChildrenLog[0]);
    }

    private Student getStudentByMember(Member member) {
        Student student = new Student(member);
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res =
                db.rawQuery(
                        "SELECT * FROM student WHERE memberId = '" + member.getId() + "'", null);

        while (res.moveToNext()) {
            student.setIdx(res.getInt(res.getColumnIndex("idx")));
            student.setClassIdx(res.getInt(res.getColumnIndex("classIdx")));
            student.setNumber(res.getInt(res.getColumnIndex("number")));
        }

        student.setClassInfo(
                getData("class","idx",student.getClassIdx()+"",
                        new DatabaseGetDataType<>(ClassInfo.class)));

        student.setParentsLogs(getParentsLogsByStudentIdx(student.getIdx()));

        res.close();

        return student;
    }

    public ClassInfo getClassByClassIdx(int classIdx) {
        ClassInfo aClass = new ClassInfo();
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res = db.rawQuery("SELECT * FROM class WHERE idx = " + classIdx, null);

        while (res.moveToNext()) {
            aClass.setIdx(res.getInt(res.getColumnIndex("idx")));
            aClass.setGrade(res.getInt(res.getColumnIndex("grade")));
            aClass.setRoom(res.getInt(res.getColumnIndex("room")));
        }

        res.close();

        return aClass;
    }

    @NonNull
    public ArrayList<Member> getAllMembers() {
        final SQLiteDatabase db = this.getWritableDatabase();

        final Cursor res = db.rawQuery("SELECT * FROM member", null);
        final ArrayList<Member> memberArrayList = new ArrayList<Member>();

        while (res.moveToNext()) {

            Member member = new Member();

            member.setId(res.getString(res.getColumnIndex("id")));
            member.setName(res.getString(res.getColumnIndex("name")));
            member.setMobile(res.getString(res.getColumnIndex("mobile")));
            member.setEmail(res.getString(res.getColumnIndex("email")));
            member.setProfileImageWithout(res.getString(res.getColumnIndex("profileImage")));
            member.setStatusMessage(res.getString(res.getColumnIndex("statusMessage")));
            member.setAuth(res.getInt(res.getColumnIndex("auth")));

            switch (member.getAuth()) {
                case 1:
                    member = getStudentByMember(new Student(member));
                    break;
                case 2:
                    member = getTeacherByMember(new Teacher(member));
                    break;
                case 3:
                    member = getParentByMember(new Parent(member));
                    break;
            }

            memberArrayList.add(member);
        }

        res.close();

        return memberArrayList;
    }

    private ParentLog[] getParentsLogsByStudentIdx(int studentIdx) {
        ArrayList<ParentLog> parentLogs = new ArrayList<>();

        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res =
                db.rawQuery("SELECT * FROM parentsLogs WHERE studentIdx = " + studentIdx, null);

        while (res.moveToNext()) {
            ParentLog parentLog = new ParentLog();

            parentLog.setParentIdx(res.getInt(res.getColumnIndex("parentIdx")));

            parentLogs.add(parentLog);
        }

        res.close();

        return parentLogs.toArray(new ParentLog[0]);
    }

    private Teacher getTeacherByMember(Member member) {
        Teacher teacher = new Teacher(member);
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res =
                db.rawQuery(
                        "SELECT * FROM teacher WHERE memberId = '" + teacher.getId() + "'", null);

        while (res.moveToNext()) {
            teacher.setIdx(res.getInt(res.getColumnIndex("idx")));
            teacher.setTel(res.getString(res.getColumnIndex("tel")));
        }

        teacher.setDepartmentsLogs(getDepartmentLogsByTeacherIdx(teacher.getIdx()));

        res.close();

        return teacher;
    }

    private DepartmentLog[] getDepartmentLogsByTeacherIdx(int teacherIdx) {
        ArrayList<DepartmentLog> departmentLogs = new ArrayList<>();

        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor res =
                db.rawQuery("SELECT * FROM departmentLog WHERE teacherIdx = " + teacherIdx, null);

        while (res.moveToNext()) {
            DepartmentLog departmentLog =
                    new DepartmentLog(
                            res.getInt(res.getColumnIndex("departmentIdx")),
                            res.getInt(res.getColumnIndex("type")),
                            res.getString(res.getColumnIndex("position")),
                            res.getInt(res.getColumnIndex("classIdx")));

            departmentLogs.add(departmentLog);
        }

        res.close();

        return departmentLogs.toArray(new DepartmentLog[0]);
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
                    if (contentValues.get(k).equals("")) contentValues.put(k, (Integer) null);
                } else if (v instanceof String) {
                    contentValues.put(k, (String) v);
                    if (contentValues.get(k).equals("")) contentValues.put(k, (String) null);
                } else if (v instanceof Boolean) {
                    contentValues.put(k, ((Boolean) v) ? 1 : 0);
                    if (contentValues.get(k).equals("")) contentValues.put(k, (Boolean) null);
                } else if (v instanceof Double) {
                    contentValues.put(k, (Double) v);
                    if (contentValues.get(k).equals("")) contentValues.put(k, (Double) null);
                } /*else if (v.getClass().isArray()) {
                    insert(k, v);
                }*/
            });
            contentValuesList.add(contentValues);
        }

        return contentValuesList;
    }

    public Token getToken() {
        final SQLiteDatabase db = this.getWritableDatabase();

        final Cursor res = db.rawQuery("SELECT * FROM token ORDER BY idx DESC limit 1", null);

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
        if (result.isEmpty()) return null;
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

        List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getType() == int.class || field.getType() == Integer.class) {
                    map.put(field.getName(), field.get(obj) != null ? field.get(obj) : 0);
                } else if (field.getType() == String.class) {
                    map.put(field.getName(), field.get(obj) != null ? field.get(obj) : "");
                } else if (field.getType() == Boolean.class) {
                    map.put(field.getName(), field.get(obj) != null ? field.get(obj) : true);
                } else if (field.getType() == Double.class) {
                    map.put(field.getName(), field.get(obj) != null ? field.get(obj) : 0);
                } else if (field.getType().isArray()) {
                    map.put(field.getName(), field.get(obj));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        Object superclass = obj.getClass().getSuperclass();
//
//        if (!(((Class) superclass).getName().equals("java.lang.Object"))) {
//            insert(((Class) superclass).getName(), superclass);
//        }

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