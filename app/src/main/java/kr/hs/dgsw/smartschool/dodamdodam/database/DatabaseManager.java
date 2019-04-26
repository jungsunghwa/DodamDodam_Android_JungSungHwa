package kr.hs.dgsw.smartschool.dodamdodam.database;

public final class DatabaseManager {
    public static final String TABLE_TOKEN = "token";
    public static final String TABLE_TIME = "time";
    public static final String TABLE_PLACE = "place";

    private DatabaseManager() {}

    static String getCreateTableToken() {
        return "CREATE TABLE " + TABLE_TOKEN +" ( "
                + "idx INTEGER UNIQUE, "
                + "token STRING, "
                + "refreshToken STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }

    static String getCreateTableTime() {
        return "CREATE TABLE "+ TABLE_TIME +" ( "
                + "idx INTEGER, "
                + "name STRING, "
                + "type INTEGER, "
                + "startTime STRING, "
                + "endTime STRING"
                + ")";
    }

    static String getCreateTablePlace() {
        return "CREATE TABLE "+ TABLE_PLACE +" ( "
                + "idx INTEGER, "
                + "name STRING"
                + ")";
    }
}
