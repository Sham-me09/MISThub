package ui;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class ProjectMemberStore {

    // ================= JOIN PROJECT =================
    public static void joinProject(String username, int projectId) {

        String sql =
        "INSERT INTO project_members(username, project_id) VALUES(?,?)";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, projectId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOAD USER JOINS =================
    public static Set<Integer> getJoinedProjects(String username) {

        Set<Integer> joined = new HashSet<>();

        String sql =
        "SELECT project_id FROM project_members WHERE username=?";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                joined.add(rs.getInt("project_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return joined;
    }
    
    public static List<String> getMembers(int projectId){

    List<String> members = new ArrayList<>();

    String sql = "SELECT username FROM project_members WHERE project_id=?";

    try(Connection conn = DB.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){

        stmt.setInt(1, projectId);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            members.add(rs.getString("username"));
        }

    } catch(Exception e){
        e.printStackTrace();
    }

    return members;
}
}