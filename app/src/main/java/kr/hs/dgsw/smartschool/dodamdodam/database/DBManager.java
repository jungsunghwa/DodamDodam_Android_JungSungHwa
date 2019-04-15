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

    String getCreateTableTime() {
        return "CREATE TABLE time ( "
                + "idx INTEGER UNIQUE, "
                + "name STRING, "
                + "type INTEGER, "
                + "startTime STRING, "
                + "endTime STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }

    String getCreateTablePlace() {
        return "CREATE TABLE place ( "
                + "idx INTEGER UNIQUE, "
                + "name STRING, "
                + "PRIMARY KEY('idx')"
                + ")";
    }
}
