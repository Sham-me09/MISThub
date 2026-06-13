package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    public static boolean loginAdmin(String username, String password) {

        String sql =
            "SELECT * FROM admins WHERE username=? AND password=?";

        try (
            Connection conn = DB.connect();
            PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            return rs.next(); // TRUE if found

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}