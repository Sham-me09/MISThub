package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminStats {

    // Return total number of rows in any table
    private static int getCount(String tableName) {
        String sql = "SELECT COUNT(*) AS total FROM " + tableName;
        try (Connection conn = DB.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ===== Public methods for each stat =====
    public static int totalUsers() {
        return getCount("users");
    }

    public static int totalProjects() {
        return getCount("projects");
    }

    public static int totalProjectRequests() {
        return getCount("project_requests");
    }

    public static int totalProjectMembers() {
        return getCount("project_members");
    }

    public static int totalResearch() {
        return getCount("research");
    }

    public static int totalResearchRequests() {
        return getCount("research_requests");
    }

    public static int totalResearchMembers() {
        return getCount("research_members");
    }

    public static int totalStudyMaterials() {
        return getCount("study_materials");
    }

    public static int totalAlumni() {
        return getCount("alumni");
    }
}