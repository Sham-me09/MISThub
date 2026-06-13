package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectRequestStore {

    // SEND REQUEST
    public static void sendRequest(String username, int projectId) {

        String sql =
        "INSERT INTO project_requests(project_id, requester_username) VALUES(?,?)";

        try (Connection conn = DB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setString(2, username);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    
   public static List<ProjectRequest> getRequestsForCreator(String creator){

    List<ProjectRequest> list = new ArrayList<>();

    String sql =
    "SELECT pr.project_id, pr.requester_username, p.title " +
"FROM project_requests pr " +
"JOIN projects p ON pr.project_id = p.id " +
"WHERE p.created_by=? AND pr.status='PENDING' " ;

    try(Connection conn = DB.connect();
        PreparedStatement ps = conn.prepareStatement(sql)){

        ps.setString(1, creator);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){

            list.add(new ProjectRequest(
    rs.getInt("project_id"),
    rs.getString("requester_username"),
    rs.getString("title")
));
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return list;
}
   
   
   public static void approveRequest(String username,int projectId){

    try(Connection conn = DB.connect()){

        String joinSQL =
        "INSERT INTO project_members(username,project_id) VALUES(?,?)";

        PreparedStatement ps1 = conn.prepareStatement(joinSQL);
        ps1.setString(1, username);
        ps1.setInt(2, projectId);
        ps1.executeUpdate();

        String updateSQL =
        "UPDATE project_requests SET status='APPROVED' WHERE project_id=? AND requester_username=?";

        PreparedStatement ps2 = conn.prepareStatement(updateSQL);
        ps2.setInt(1, projectId);
        ps2.setString(2, username);
        ps2.executeUpdate();

    }catch(Exception e){
        e.printStackTrace();
    }
}
   
   public static void declineRequest(String username,int projectId){

    String sql =
    "UPDATE project_requests SET status='DECLINED' WHERE project_id=? AND requester_username=?";

    try(Connection conn = DB.connect();
        PreparedStatement ps = conn.prepareStatement(sql)){

        ps.setInt(1, projectId);
        ps.setString(2, username);
        ps.executeUpdate();

    }catch(Exception e){
        e.printStackTrace();
    }
}
   
   
}