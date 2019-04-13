package kr.hs.dgsw.smartschool.dodamdodam.database;

public class DatabaseManager {
    public static final String TABLE_TOKEN = "token";

    String getCreateTableToken() {
        return "CREATE TABLE " + TABLE_TOKEN +" ( "
                + "idx INTEGER UNIQUE, "
                + "token STRING, "
                + "refreshToken STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }
}
