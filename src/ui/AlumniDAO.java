package ui;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumniDAO {

    public static List<Alumni> getAllAlumni() {

        List<Alumni> list = new ArrayList<>();

        String sql = "SELECT * FROM alumni";

        try (Connection con = DB.connect();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                list.add(new Alumni(
                        rs.getString("name"),
                        rs.getString("education"),
                        rs.getString("current_position"),
                        rs.getString("research"),
                        rs.getString("email"),
                        rs.getString("previous_jobs"),
                        rs.getString("summary"),
                        rs.getString("image_path")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    
    public static void addAlumni(Alumni a) {

    String sql = "INSERT INTO alumni(name,education,current_position,research,email,previous_jobs,summary,image_path) VALUES(?,?,?,?,?,?,?,?)";

    try (Connection conn = DB.connect();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, a.getName());
        pst.setString(2, a.getEducation());
        pst.setString(3, a.getPosition());
        pst.setString(4, a.getResearch());
        pst.setString(5, a.getEmail());
        pst.setString(6, a.getJobs());
        pst.setString(7, a.getSummary());
        pst.setString(8, a.getImagePath());

        pst.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
  public static void deleteAlumni(String email) {

    String sql = "DELETE FROM alumni WHERE email=?";

    try (Connection con = DB.connect();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, email);
        pst.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
}