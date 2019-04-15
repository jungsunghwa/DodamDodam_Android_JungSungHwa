package kr.hs.dgsw.smartschool.dodamdodam.database;

public class DatabaseManager {
    public static final String TABLE_TOKEN = "token";
    public static final String TABLE_TIME = "time";
    public static final String TABLE_PLACE = "place";

    String getCreateTableToken() {
        return "CREATE TABLE " + TABLE_TOKEN +" ( "
                + "idx INTEGER UNIQUE, "
                + "token STRING, "
                + "refreshToken STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }

    String getCreateTableTime() {
        return "CREATE TABLE "+ TABLE_TIME +" ( "
                + "idx INTEGER UNIQUE, "
                + "name STRING, "
                + "type INTEGER, "
                + "startTime STRING, "
                + "endTime STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }

    String getCreateTablePlace() {
        return "CREATE TABLE "+ TABLE_PLACE +" ( "
                + "idx INTEGER UNIQUE, "
                + "name STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }
}
