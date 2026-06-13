package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAO {

    // REGISTER USER
    public static boolean registerUser(
        String username,
        String email,
        String studentId,
        String password,
        String department) {

        String sql =
"INSERT INTO users(username,email,student_id,password,department) VALUES(?,?,?,?,?)";

        try (Connection conn = DB.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
pst.setString(2, email);
pst.setString(3, studentId);
pst.setString(4, password);
pst.setString(5, department);

            pst.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LOGIN USER
    public static boolean loginUser(String username,
                                    String password) {

        String sql =
            "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DB.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
  public static User getUserProfile(String username) {

    String sql =
        "SELECT student_id, username, email, department FROM users WHERE username=?";

    try (
        Connection conn = DB.connect();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {

        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getString("student_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("department")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
    
    
    
    // UPDATE PROFILE 
public static boolean updateProfile(
        String username,
        String email,
        String department) {

    String sql =
        "UPDATE users SET email=?, department=? WHERE username=?";

    try (
        Connection conn = DB.connect();
        PreparedStatement pst = conn.prepareStatement(sql)
    ) {

        pst.setString(1, email);
        pst.setString(2, department);
        pst.setString(3, username);

        pst.executeUpdate();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


// GET ALL USERS (ADMIN)
public static ObservableList<User> getAllUsers() {

    ObservableList<User> users =
            FXCollections.observableArrayList();

    String sql =
        "SELECT student_id, username, email, department FROM users";

    try (Connection conn = DB.connect();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {

            users.add(new User(
                    rs.getString("student_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("department")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return users;
}



// DELETE USER (ADMIN)
public static void deleteUser(String studentId) {

    String sql = "DELETE FROM users WHERE student_id=?";

    try (Connection conn = DB.connect();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, studentId);
        pst.executeUpdate();

        System.out.println("User deleted");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}