import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;


    public static void main(String[] args) throws SQLException {

        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        String name = "bob1; drop table students";
//
//        String sql = String.format("SELECT id, name, score FROM students" +
//                "where name = '%s'", name);
//
//        ResultSet rs = stmt.executeQuery(sql);
//
//        ResultSetMetaData rsmd = rs.getMetaData();
//
//        for (int i = 1; i < rsmd.getColumnCount(); i++) {
//            System.out.println(rsmd.getColumnName(i) + " " + rsmd.getColumnType(i) + " " + rsmd.getTableName(i));
//        }
//
//        while (rs.next()) {
//            System.out.println(rs.getInt(1) + " " + rs.getString("name") + " " + rs.getString("score"));
//        }

//        int res = stmt.executeUpdate("CREATE TABLE students (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "name TEXT," +
//                "score TEXT)");
//
//        System.out.println(res);

//        long t = System.currentTimeMillis();
//
//        connection.setAutoCommit(false);
//
//        for (int i = 0; i < 1000; i++) {
//            stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('unknow', " + i +")");
//        }
//
//        connection.setAutoCommit(true);
//
//        System.out.println(System.currentTimeMillis() - t);


//        pstmt = connection.prepareStatement("INSERT INTO students (name, score)" +
//                "VALUES (?, ?);");
//
//        connection.setAutoCommit(false);
//        for (int i = 0; i < 500; i++) {
//            pstmt.setString(1, "Bob " + i);
//            pstmt.setInt(2, 10 + i);
//            pstmt.addBatch();
//        }
//
//        pstmt.executeBatch();
//
//        connection.setAutoCommit(true);

        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob1', 10)");
        Savepoint sp = connection.setSavepoint();
        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob2', 20)");
        connection.rollback(sp);
        connection.setAutoCommit(true);
        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob3', 30)");


        disconnect();

    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
