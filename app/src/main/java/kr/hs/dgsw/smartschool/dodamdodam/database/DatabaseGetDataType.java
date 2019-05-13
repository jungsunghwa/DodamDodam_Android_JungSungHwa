package kr.hs.dgsw.smartschool.dodamdodam.database;

public class DatabaseGetDataType<T> {
    private Class<T>     mClass;

    public DatabaseGetDataType(Class<T> cls) {
        mClass = cls;
    }

    public T get() {
        try {
            return mClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
