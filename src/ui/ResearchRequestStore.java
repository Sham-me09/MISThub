package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResearchRequestStore {

    public static void sendRequest(String username, int researchId) {

        String sql =
        "INSERT INTO research_requests(research_id, requester_username) VALUES(?,?)";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, researchId);
            ps.setString(2, username);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<ResearchRequest> getRequestsForCreator(String creator){

    List<ResearchRequest> list = new ArrayList<>();

    String sql =
    "SELECT rr.research_id, rr.requester_username, r.title " +
    "FROM research_requests rr " +
    "JOIN research r ON rr.research_id = r.id " +
    "WHERE r.created_by=? AND rr.status='PENDING'";

    try(Connection conn = DB.connect();
        PreparedStatement ps = conn.prepareStatement(sql)){

        ps.setString(1, creator);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            list.add(new ResearchRequest(
                rs.getInt("research_id"),
                rs.getString("requester_username"),
                rs.getString("title")
            ));
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return list;
}
    
    
    public static void approveRequest(String username,int researchId){

    try(Connection conn = DB.connect()){

        String joinSQL =
        "INSERT INTO research_members(username,research_id) VALUES(?,?)";

        PreparedStatement ps1 = conn.prepareStatement(joinSQL);
        ps1.setString(1, username);
        ps1.setInt(2, researchId);
        ps1.executeUpdate();

        String updateSQL =
        "UPDATE research_requests SET status='APPROVED' WHERE research_id=? AND requester_username=?";

        PreparedStatement ps2 = conn.prepareStatement(updateSQL);
        ps2.setInt(1, researchId);
        ps2.setString(2, username);
        ps2.executeUpdate();

    }catch(Exception e){
        e.printStackTrace();
    }
}
    
    
    public static void declineRequest(String username,int researchId){

    String sql =
    "UPDATE research_requests SET status='DECLINED' WHERE research_id=? AND requester_username=?";

    try(Connection conn = DB.connect();
        PreparedStatement ps = conn.prepareStatement(sql)){

        ps.setInt(1, researchId);
        ps.setString(2, username);
        ps.executeUpdate();

    }catch(Exception e){
        e.printStackTrace();
    }
}
}