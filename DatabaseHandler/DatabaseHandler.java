public class DatabaseHandler {
    Connection connection;

    public Connection getConnection() throws SQLException, ClassNotFoundException {

        String url = "jdbc:mysql://127.0.0.1:3306/kursach?serverTimezone=Europe/Moscow&useSSL=false";
        String user = "root";
        String password = "lfhbyf99";
        Class.forName ("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection (url, user, password);
        return connection;
    }

    public void SignUpUser(String firstName, String lastName, String userName, String password) {

        String insert = "INSERT INTO kursach.users(firstname, lastname, username, password)\n"
                + "VALUES ('" + firstName + "', '" + lastName + "', '" + userName + "', '" + password + "')";
        try {
            PreparedStatement prSt = getConnection ().prepareStatement (insert);
            prSt.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        }

    }

    public ResultSet getUser(String userName, String password) {
        ResultSet resSet = null;
        String select = "SELECT * FROM kursach.users " +
                " WHERE users.username='" + userName + "' AND users.password='" + password + "'";

        try {
            PreparedStatement prSt = getConnection ().prepareStatement (select);
            resSet = prSt.executeQuery ();
        } catch (SQLException e) {
            e.printStackTrace ();
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        }
        return resSet;
    }

    public ResultSet UnicUser(String userName) {

        ResultSet resSet = null;
        String select = "SELECT * FROM kursach.users" +
                " WHERE users.username='" + userName + "'";

        try {
            PreparedStatement prSt = getConnection ().prepareStatement (select);
            resSet = prSt.executeQuery ();
        } catch (SQLException e) {
            e.printStackTrace ();
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        }
        return resSet;
    }

    public int idUser(String userName, String password) {
        String select = "SELECT users.iduser FROM kursach.users " +
                " WHERE users.username='" + userName + "' AND users.password='" + password + "'";
        Statement statement = null;
        ResultSet rs;
        int id = 0;
        try {

            statement = getConnection ().createStatement ();
            rs = statement.executeQuery (select);
            rs.next ();
            id = rs.getInt (1);
        } catch (SQLException e) {
            e.printStackTrace ();
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        }
        return id;
    }


    public void addDataUser(String birth, String death, String in, String out, String result, int id) {
        String insert = "INSERT INTO kursach.user_data(birth, death, inm, outm, result, user_id) \n"
                + "VALUES ('" + birth + "', '" + death + "', '" + in + "', '" + out + "', '" + result + "', '" + id + "')";
        try {
            PreparedStatement prSt = getConnection ().prepareStatement (insert);
            prSt.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        }
    }
}


