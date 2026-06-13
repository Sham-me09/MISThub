package ui;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectStore {

    private static final ObservableList<Project> projects =
            FXCollections.observableArrayList();

    public static ObservableList<Project> getProjects() {
        return projects;
    }

    // ================= LOAD =================
    public static void loadFromDatabase() {

        projects.clear();

        String sql = "SELECT * FROM projects";

        try (Connection conn = DB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Project p = new Project(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("skills"),
                        rs.getString("status"),
                        rs.getString("created_by")
                );

                projects.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= INSERT =================
    public static void addProject(Project p) {

        String sql =
        "INSERT INTO projects(title,skills,status,created_by) VALUES(?,?,?,?)";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getSkills());
            ps.setString(3, p.getStatus());
            ps.setString(4, p.getCreatedBy());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void deleteProject(int id) {

    String sql = "DELETE FROM projects WHERE id=?";

    try (Connection conn = DB.connect();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, id);
        pst.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
}