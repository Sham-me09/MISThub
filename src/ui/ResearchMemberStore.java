package ui;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class ResearchMemberStore {

    public static void joinResearch(String username, int researchId) {

        String sql =
            "INSERT INTO research_members(username,research_id) VALUES(?,?)";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, researchId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<Integer> getJoinedResearch(String username) {

        Set<Integer> joined = new HashSet<>();

        String sql =
            "SELECT research_id FROM research_members WHERE username=?";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                joined.add(rs.getInt("research_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return joined;
    }
    
    public static List<String> getMembers(int researchId){

    List<String> members = new ArrayList<>();

    String sql = "SELECT username FROM research_members WHERE research_id=?";

    try(Connection conn = DB.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){

        stmt.setInt(1, researchId);

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