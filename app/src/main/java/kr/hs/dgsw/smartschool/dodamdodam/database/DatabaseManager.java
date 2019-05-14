package kr.hs.dgsw.smartschool.dodamdodam.database;

public final class DatabaseManager {
    public static final String TABLE_TOKEN = "token";
    public static final String TABLE_TIME = "time";
    public static final String TABLE_PLACE = "place";
    public static final String TABLE_CLASS = "class";
    public static final String TABLE_LOCATION = "location";
    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_MEMBER = "member";
    public static final String TABLE_TEACHER = "teacher";
    public static final String TABLE_PARENT = "parent";
    public static final String TABLE_PARENTS_LOG = "parentsLogs";
    public static final String TABLE_DEPARTMENT_LOG = "departmentLog";
    public static final String TABLE_CHILDREN_LOG = "childrenLog";

    private DatabaseManager() {}

    static String getCreateTableToken() {
        return "CREATE TABLE " + TABLE_TOKEN +" ( "
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "token STRING, "
                + "refreshToken STRING"
                + ");";
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

    static String getCreateTableClass() {
        return "CREATE TABLE "+ TABLE_CLASS +" ( "
                + "idx INTEGER, "
                + "grade INTEGER, "
                + "room STRING"
                + ")";
    }

    static String getCreateTableLocation() {
        return "CREATE TABLE "+ TABLE_LOCATION +" ( "
                + "idx INTEGER, "
                + "studentIdx INTEGER, "
                + "placeIdx INTEGER,"
                + "isChecked INTEGER,"
                + "checkTeacherIdx INTEGER,"
                + "date STRING"
                + ")";
    }

    static String getCreateTableStudent() {
        return "CREATE TABLE "+ TABLE_STUDENT+" ("
                + "  idx INTEGER UNIQUE,"
                + "  memberId STRING,"
                + "  classIdx INTEGER,"
                + "  number INTEGER,"
                + "  PRIMARY KEY (idx, memberId)"
                + ")";
    }

   static String getCreateTableDepartmentLog() {
        return "CREATE TABLE "+ TABLE_DEPARTMENT_LOG +" (" +
                "departmentIdx INTEGER," +
                "teacherIdx INTEGER," +
                "type INTEGER," +
                "position STRING ," +
                "class_idx INTEGER," +
                "primary key(departmentIdx, teacherIdx)" +
                ")";
    }

    static String getCreateTableChildrenLog() {
        return "CREATE TABLE "+ TABLE_CHILDREN_LOG +" (" +
                "  idx INTEGER UNIQUE," +
                "  parentIdx INTEGER," +
                "  studentIdx INTEGER," +
                "  PRIMARY KEY (idx)" +
                ")";
    }

    static String getCreateTableParent() {
        return "CREATE TABLE "+TABLE_PARENT+" ("
                + "idx INTEGER UNIQUE,"
                + "member_id STRING,"
                + "PRIMARY KEY (idx)"
                + ")";
    }

    static String getCreateTableMember() {
        return "CREATE TABLE "+ TABLE_MEMBER+" ( "
                + "id STRING UNIQUE, "
                + "inviteMember STRING UNIQUE, "
                + "name STRING, "
                + "mobile STRING, "
                + "email STRING, "
                + "profileImage STRING, "
                + "statusMessage STRING, "
                + "auth INTEGER, "
                + "isMe BOOL,"
                + "PRIMARY KEY('id')"
                + ")";
    }

    static String getCreateTableTeacher() {
        return "CREATE TABLE "+ TABLE_TEACHER +" (" +
                "idx INTEGER UNIQUE," +
                "memberId STRING," +
                "tel STRING," +
                "PRIMARY KEY ('idx')" +
                ")";
    }

    static String getCreateTableParentsLogs() {
        return "CREATE TABLE "+ TABLE_PARENTS_LOG +" (" +
                "  parentIdx INTEGER," +
                "  studentIdx INTEGER," +
                "  primary key(parentIdx, studentIdx)" +
                ")";
    }
}
