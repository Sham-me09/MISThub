package ui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResearchStore {

    private static final List<Research> researchList =
            new ArrayList<>();

    public static List<Research> getResearch() {
        return researchList;
    }

    public static void loadFromDatabase() {

        researchList.clear();

        String sql = "SELECT * FROM research";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                researchList.add(new Research(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("faculty"),
                        rs.getString("department"),
                        rs.getString("description"),
                        rs.getString("created_by")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addResearch(Research r) {

        String sql =
            "INSERT INTO research(title,faculty,department,description,created_by) VALUES(?,?,?,?,?)";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getTitle());
            ps.setString(2, r.getFaculty());
            ps.setString(3, r.getDepartment());
            ps.setString(4, r.getDescription());
            ps.setString(5, r.getCreatedBy());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteResearch(int id) {

    String sql = "DELETE FROM research WHERE id=?";

    try (Connection conn = DB.connect();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, id);
        pst.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
}