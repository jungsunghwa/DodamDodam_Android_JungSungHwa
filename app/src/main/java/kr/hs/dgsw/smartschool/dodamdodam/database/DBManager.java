package kr.hs.dgsw.smartschool.dodamdodam.database;

public class DBManager {
    String getCreateTableToken() {
        return "CREATE TABLE token ( "
                + "idx INTEGER UNIQUE, "
                + "token STRING, "
                + "refreshToken STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }
}
