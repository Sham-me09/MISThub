package ui;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MaterialStore {

    //  Observable list
    private static final ObservableList<StudyMaterial> materials =
            FXCollections.observableArrayList();

    public static ObservableList<StudyMaterial> getMaterials() {
        return materials;
    }

    public static void addMaterial(StudyMaterial material) {

    materials.add(material); 

    String sql =
        "INSERT INTO study_materials(title,department,semester,type,uploader,filepath) VALUES(?,?,?,?,?,?)";

    try (Connection conn = DB.connect();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, material.getTitle());
        pst.setString(2, material.getDepartment());
        pst.setString(3, material.getSemester());
        pst.setString(4, material.getType());
        pst.setString(5, material.getUploadedBy());
        pst.setString(6, material.getFilePath());

        pst.executeUpdate();

        System.out.println("✅ Material saved to DB");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void removeMaterial(StudyMaterial material) {
        materials.remove(material);
    }
    
    
    
    
    public static void loadFromDatabase() {

    materials.clear();

    String sql = "SELECT * FROM study_materials";

    try (Connection conn = DB.connect();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {

            StudyMaterial m = new StudyMaterial(
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("semester"),
                    rs.getString("type"),
                    rs.getString("uploader")
            );
             m.setId(rs.getInt("id"));
            String path = rs.getString("filepath");
            m.setFilePath(path);

            if (path != null)
                m.setFile(new java.io.File(path));

            materials.add(m);
        }

        System.out.println("✅ Materials loaded from DB");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
    public static void deleteMaterial(int id) {

    String sql = "DELETE FROM study_materials WHERE id=?";

    try (Connection conn = DB.connect();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, id);
        pst.executeUpdate();

        System.out.println("✅ Material deleted");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}