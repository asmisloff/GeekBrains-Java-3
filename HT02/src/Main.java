import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    private static HashMap<String, String[]> newData = new HashMap<>();

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
//
//        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob1', 10)");
//        Savepoint sp = connection.setSavepoint();
//        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob2', 20)");
//        connection.rollback(sp);
//        connection.setAutoCommit(true);
//        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob3', 30)");

//        createBooksTable();
//        insertBook("111", "Л. Н. Толстой", "Война и мир");
//        insertBook("112", "Л. Н. Толстой", "Хаджи Мурат");
//        insertBook("113", "Ж. Верн", "Дети капитана Гранта");
//        updateBookName("111", "Детство");
//        ResultSet rs = selectBooksByAuthor("Л. Н. Толстой");
//        while (rs.next()) {
//            System.out.printf("%s -- %s -- %s\n",
//                    rs.getString("ISBN"), rs.getString("Author"), rs.getString("Name"));
//        }
//        deleteBook("113");
//
//        disconnect();

        readFile("studs.txt");
        ResultSet rs = selectByIDs(
                newData.keySet().toString()
                        .replace('[', '(')
                        .replace(']', ')'),
                stmt);
        System.out.printf("%d records have been updated\n", updateScores(rs));
        for (String id : newData.keySet()) {
            System.out.printf("Warning: student %s (id %s) doesn't not exist in the DB! Ignored.\n", newData.get(id)[0], id);
        }
    }

    public static int updateScores(ResultSet rs) throws SQLException {
        pstmt = connection.prepareStatement("UPDATE students SET score = ? WHERE id = ?");
        while (rs.next()) {
            String id = rs.getString("id");
            pstmt.setString(1, newData.get(id)[1]);
            pstmt.setString(2, id);
            pstmt.addBatch();
            newData.remove(id);
        }
        return pstmt.executeBatch().length;
    }

    public static void readFile(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            for (int i = 0; i < 3; i++) { //skip target and headers
                sc.nextLine();
            }

            String[] fields = new String[3];
            while (sc.hasNext()) {
                fields = sc.nextLine().split("  ");
                newData.put(fields[0], new String[]{fields[1], fields[2]});
            }

            System.out.println("------------------ New data about students ------------------");
            for (String key : newData.keySet()) {
                System.out.printf("%s : [%s, %s]\n", key, newData.get(key)[0], newData.get(key)[1]);
            }
            System.out.println("-------------------------------------------------------------");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet selectByIDs(String ids, Statement stmt) throws SQLException {
        return stmt.executeQuery(String.format("SELECT Id FROM Students WHERE Id in %s", ids));
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

    public static int createBooksTable() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Books';");
        if (rs.next()) return -1;

        stmt.executeUpdate("CREATE TABLE Books (" +
                "ISBN STRING (13) PRIMARY KEY," +
                "Author STRING (64)," +
                "Name STRING (256));");
        return 0;
    }

    public static int insertBook(String isbn, String author, String name) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("SELECT isbn FROM Books WHERE ISBN = %s", isbn));
        if (rs.next()) return -1;

        pstmt = connection.prepareStatement("INSERT INTO Books (ISBN, Author, Name) VALUES (?, ?, ?);");
        pstmt.setString(1, isbn);
        pstmt.setString(2, author);
        pstmt.setString(3, name);
        pstmt.executeUpdate();
        return 0;
    }

    public static int updateBookName(String isbn, String name) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("SELECT isbn FROM Books WHERE ISBN = %s", isbn));
        if (!rs.next()) return -1;

        pstmt = connection.prepareStatement("UPDATE Books " +
                "SET Name = ? WHERE ISBN = ?;");
        pstmt.setString(1, name);
        pstmt.setString(2, isbn);
        pstmt.executeUpdate();
        return 0;
    }

    public static ResultSet selectBooksByAuthor(String author) throws SQLException {
        return stmt.executeQuery(String.format("SELECT * FROM Books WHERE Author = '%s'", author));
    }

    public static int deleteBook(String isbn) throws SQLException {
        pstmt = connection.prepareStatement("DELETE FROM Books WHERE ISBN = ?");
        pstmt.setString(1, isbn);
        return pstmt.executeUpdate();
    }
}
