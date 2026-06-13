package ui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;



public class UserDashboardController {

    @FXML
    private VBox contentArea;
    
    @FXML
    private ScrollPane scrollPane;
    
    private VBox projectList;
    private VBox joinedProjectsBox;
    private Set<Integer> joinedIds = new HashSet<>();
    private Set<String> joinedProjectNames = new HashSet<>();
    
 // private final List<StudyMaterial> materials = new ArrayList<>();
    private VBox materialsListBox;
private TextField materialSearchField;
private ComboBox<String> deptFilter;
private ComboBox<String> semFilter;
private ComboBox<String> typeFilter;
//private Label usernameLabel;
private VBox researchListBox;
private VBox joinedResearchBox;
private Set<Integer> joinedResearchIds = new HashSet<>();


private VBox alumniContainer;

@FXML
private Button profileBtn;

@FXML
public void initialize() {
   
    showDashboard();
    
//usernameLabel.setText(
  //      "Welcome, " + Session.getUser()
    //);
    
    MaterialStore.loadFromDatabase();
    ProjectStore.loadFromDatabase();
    profileBtn.setText(getProfileButtonText());
}

   
   
   
   @FXML
private void showDashboard() {
    contentArea.getChildren().clear();

    VBox container = new VBox(20);
    container.setMaxWidth(600);

    Label title = new Label("Welcome to MISThub, " + Session.getUser());
    title.getStyleClass().add("card-title");
    title.setStyle("-fx-font-size: 22px;");
    VBox card1 = createInfoCard(
            "📚 Study Materials",
            "Access notes, questions, and resources shared by students."
    );

    VBox card2 = createInfoCard(
            "🛠 Projects",
            "Post ideas, join teams, and collaborate on academic projects."
    );

    VBox card3 = createInfoCard(
            "🔬 Research Collaboration",
            "Explore research opportunities with faculty and alumni."
    );

    VBox card4 = createInfoCard(
            "🎓 Alumni Corner",
            "Connect with alumni for guidance and academic support."
    );

    container.getChildren().addAll(title, card1, card2, card3, card4);
    contentArea.getChildren().add(container);
}



private VBox createInfoCard(String heading, String description) {
    VBox card = new VBox(6);
    card.getStyleClass().add("card");

    Label h = new Label(heading);
    h.getStyleClass().add("card-title");

    Label d = new Label(description);
    d.setWrapText(true);

    card.getChildren().addAll(h, d);

    // ✅ MAKE CLICKABLE
    card.setOnMouseClicked(e -> {
        if (heading.contains("Study")) showStudyMaterials();
        else if (heading.contains("Projects")) showProjects();
        else if (heading.contains("Research")) showResearch();
        else if (heading.contains("Alumni")) showAlumni();
    });

    card.setCursor(javafx.scene.Cursor.HAND);

    return card;
}

   

    @FXML
private void showStudyMaterials() {
    MaterialStore.loadFromDatabase();
    contentArea.getChildren().clear();

    VBox layout = new VBox(15);
    layout.setMaxWidth(700);

    // Title
    Label title = new Label("Study Materials");
    title.getStyleClass().add("card-title");

    // Filters
    HBox filters = new HBox(10);

    materialSearchField = new TextField();
    materialSearchField.setPromptText("Search materials...");

    deptFilter = new ComboBox<>();
    deptFilter.getItems().addAll("All", "CSE", "EECE", "ME", "CE", "AE", "ARCH", "BME", "PME", "EWCE", "NSE", "URP", "IPE", "NAME");
    deptFilter.setValue("All");

    semFilter = new ComboBox<>();
    semFilter.getItems().addAll("All", "1", "2", "3", "4", "5", "6", "7", "8");
    semFilter.setValue("All");

    typeFilter = new ComboBox<>();
    typeFilter.getItems().addAll("All", "Lecture Notes", "Question Bank", "Suggestions");
    typeFilter.setValue("All");

    filters.getChildren().addAll(materialSearchField, deptFilter, semFilter, typeFilter);
    
materialSearchField.textProperty().addListener((obs, o, n) -> applyMaterialFilters());
deptFilter.setOnAction(e -> applyMaterialFilters());
semFilter.setOnAction(e -> applyMaterialFilters());
typeFilter.setOnAction(e -> applyMaterialFilters());

    // Materials list
    materialsListBox = new VBox(10);
    renderMaterials(MaterialStore.getMaterials());


    // Upload button
    Button uploadBtn = new Button("+ Upload Material");
    uploadBtn.getStyleClass().add("login-button");
    uploadBtn.setOnAction(e -> openUploadMaterialDialog());


    layout.getChildren().addAll(title, filters, materialsListBox, uploadBtn);

    contentArea.getChildren().add(layout);
}




private void applyMaterialFilters() {

    String search = materialSearchField.getText().toLowerCase();
    String dept = deptFilter.getValue();
    String sem = semFilter.getValue();
    String type = typeFilter.getValue();

    List<StudyMaterial> filtered = MaterialStore.getMaterials().stream()
            .filter(m -> m.getTitle().toLowerCase().contains(search))
            .filter(m -> dept.equals("All") || m.getDepartment().equals(dept))
            .filter(m -> sem.equals("All") || m.getSemester().equals(sem))
            .filter(m -> type.equals("All") || m.getType().equals(type))
            .toList();

    renderMaterials(filtered);
}



private void openUploadMaterialDialog() {
    File[] selectedFile = new File[1];

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Upload Study Material");

    dialog.getDialogPane().getStylesheets().add(
    getClass().getResource("/ui/dashboard.css").toExternalForm()
);

dialog.getDialogPane().getStyleClass().add("custom-dialog");

    ButtonType uploadBtnType =
            new ButtonType("Upload", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes()
            .addAll(uploadBtnType, ButtonType.CANCEL);

    VBox form = new VBox(10);
    form.setPrefWidth(380);

    TextField titleField = new TextField();
    titleField.setPromptText("Material Title");

    ComboBox<String> dept = new ComboBox<>();
    dept.getItems().addAll("CSE", "EECE", "ME", "CE" ,"AE", "ARCH", "BME", "PME", "EWCE", "NSE", "URP", "IPE", "NAME");
    dept.setPromptText("Department");

    ComboBox<String> sem = new ComboBox<>();
    sem.getItems().addAll("1","2","3","4","5","6","7","8");
    sem.setPromptText("Semester");

    ComboBox<String> type = new ComboBox<>();
    type.getItems().addAll("Lecture Notes", "Question Bank", "Suggestions");
    type.setPromptText("Type");

    Button fileBtn = new Button("📂 Choose File");
Label fileLabel = new Label("No file selected");

fileBtn.setOnAction(e -> {
    FileChooser chooser = new FileChooser();
    chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.docx")
    );

    File file = chooser.showOpenDialog(null);
    if (file != null) {
        selectedFile[0] = file;
        fileLabel.setText(file.getName());
    }
});

   form.getChildren().addAll(
        titleField,
        dept,
        sem,
        type,
        fileBtn,
        fileLabel
);

    dialog.getDialogPane().setContent(form);

    dialog.showAndWait().ifPresent(response -> {
        if (response == uploadBtnType) {

            if (titleField.getText().isBlank() ||
                dept.getValue() == null ||
                sem.getValue() == null ||
                type.getValue() == null) {
                return;
            }

            StudyMaterial material = new StudyMaterial(
                    titleField.getText(),
                    dept.getValue(),
                    sem.getValue(),
                    type.getValue(),
                    Session.getUser()
            );
 material.setFile(selectedFile[0]);
 material.setFilePath(selectedFile[0].getAbsolutePath());

           MaterialStore.addMaterial(material);
MaterialStore.loadFromDatabase();
applyMaterialFilters();
        }
    });
}


//renderMaterials(List<StudyMaterial> list)

private void renderMaterials(List<StudyMaterial> list) {
    materialsListBox.getChildren().clear();

    for (StudyMaterial m : list) {
        materialsListBox.getChildren().add(
                createMaterialCard(m)
        );
    }
}




private VBox createMaterialCard(StudyMaterial material) {

    VBox card = new VBox(6);
    card.getStyleClass().add("card");

    Label title = new Label(material.getTitle());
    title.getStyleClass().add("card-title");

    Label info = new Label(
            material.getDepartment() + " • Semester " +
            material.getSemester() + " • " +
            material.getType()
    );

    Button viewBtn = new Button("📄 View Material");
    viewBtn.getStyleClass().add("login-button");

    viewBtn.setOnAction(e -> {
       try {
    File file = new File(material.getFilePath());
    if (file.exists()) {
        Desktop.getDesktop().open(file);
    }
} catch (Exception ex) {
    ex.printStackTrace();
}
    });

    card.getChildren().addAll(title, info, viewBtn);
    return card;
}


  

@FXML
private void showProjects() {

    joinedIds.clear();

    joinedIds = ProjectMemberStore
            .getJoinedProjects(Session.getUser());

    contentArea.getChildren().clear();

    VBox layout = new VBox(20);
    layout.setMaxWidth(700);

    Label title = new Label("🛠 Student Projects");
    title.getStyleClass().add("card-title");

    Button createBtn = new Button("+ Create Project");
    createBtn.getStyleClass().add("login-button");
    createBtn.setOnAction(e -> openCreateProjectDialog());

    
    Label joinedTitle = new Label("⭐ My Joined Projects");
    joinedTitle.getStyleClass().add("card-title");

    joinedProjectsBox = new VBox(15);

    
    projectList = new VBox(15);

    ProjectStore.loadFromDatabase();

    for (Project p : ProjectStore.getProjects()) {
        projectList.getChildren().add(
                createProjectCard(
                        p.getId(),
                        p.getTitle(),
                        p.getSkills(),
                        p.getStatus()
                )
        );
    }

  
    layout.getChildren().addAll(
            title,
            createBtn,
            projectList,
            joinedTitle,
            joinedProjectsBox
    );

    contentArea.getChildren().add(layout);
}





private void openCreateProjectDialog() {

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Create New Project");

    dialog.getDialogPane().getStylesheets().add(
    getClass().getResource("/ui/dashboard.css").toExternalForm()
);

dialog.getDialogPane().getStyleClass().add("custom-dialog");

    ButtonType createBtnType =
            new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes()
            .addAll(createBtnType, ButtonType.CANCEL);

    VBox form = new VBox(10);
    form.setPrefWidth(380);

    TextField titleField = new TextField();
    titleField.setPromptText("Project Title");

    TextArea descField = new TextArea();
    descField.setPromptText("Project Description");
    descField.setPrefRowCount(3);

    TextField skillsField = new TextField();
    skillsField.setPromptText("Required Skills");

    ComboBox<String> dept = new ComboBox<>();
    dept.getItems().addAll("CSE", "EECE", "ME", "CE" ,"AE", "ARCH", "BME", "PME", "EWCE", "NSE", "URP", "IPE", "NAME");
    dept.setPromptText("Department");

    form.getChildren().addAll(
            titleField,
            descField,
            skillsField,
            dept
    );

    dialog.getDialogPane().setContent(form);

    dialog.showAndWait().ifPresent(response -> {
        if (response == createBtnType) {

            String name = titleField.getText();
            String tech = skillsField.getText();
            String status = descField.getText();
            if (name.isBlank() || tech.isBlank()) return;

            Project newProject = new Project(
        0,
        name,
        tech,
        status,
        Session.getUser()
);

ProjectStore.addProject(newProject);
ProjectStore.loadFromDatabase();

showProjects(); 
        }
    });
}

    

private VBox createProjectCard(int projectId,
                               String name,
                               String tech,
                               String status) {
    
    VBox card = new VBox(8);
    card.getStyleClass().add("card");
    card.setMaxWidth(Double.MAX_VALUE);

    Label title = new Label(name);
    title.getStyleClass().add("card-title");

    Label techLabel = new Label("🔧 " + tech);

    Label statusLabel = new Label("👥 " + status);
    VBox membersBox = new VBox(3);

List<String> members =
        ProjectMemberStore.getMembers(projectId);

if(!members.isEmpty()){

    Label memberTitle = new Label("👥 Members:");

    membersBox.getChildren().add(memberTitle);

    for(String m : members){
        membersBox.getChildren().add(new Label("• " + m));
    }
}
    statusLabel.setStyle("-fx-text-fill: #64748b;");

    Button joinBtn = new Button("Join Project");
    joinBtn.getStyleClass().add("login-button");
    
  
    //if(Session.getUser().equals(getProjectCreator(projectId))){
    //card.getChildren().add(membersBox);
//}
    
  if (joinedIds.contains(projectId)) {

    joinBtn.setText("Joined ✔");
    joinBtn.setDisable(true);

    
    if (joinedProjectsBox != null) {

        VBox joinedCard = new VBox(6);
        joinedCard.getStyleClass().add("card");

        Label jTitle = new Label(name);
        jTitle.getStyleClass().add("card-title");

        Label jTech = new Label("🔧 " + tech);
        Label jStatus = new Label("✔ Joined");

        joinedCard.getChildren().addAll(jTitle, jTech, jStatus);

        joinedProjectsBox.getChildren().add(joinedCard);
    }
}

    
  joinBtn.setOnAction(e -> {

    if (Session.getUser().equals(getProjectCreator(projectId))) {
        showAlert("You cannot join your own project.");
        return;
    }

    //  store request
    ProjectRequestStore.sendRequest(
            Session.getUser(),
            projectId
    );

    // THEN update UI
    profileBtn.setText(getProfileButtonText());

    joinBtn.setText("Request Sent ⏳");
    joinBtn.setDisable(true);
});
    


    
    card.setOnMouseEntered(e ->
            card.setStyle("-fx-scale-x:1.02; -fx-scale-y:1.02;")
    );

    card.setOnMouseExited(e ->
            card.setStyle("-fx-scale-x:1.0; -fx-scale-y:1.0;")
    );

    card.getChildren().addAll(
    title,
    techLabel,
    statusLabel
);

//add members AFTER title section
if(Session.getUser().equals(getProjectCreator(projectId))){
    card.getChildren().add(membersBox);
}

card.getChildren().add(joinBtn);

    return card;
}


private String getProjectCreator(int projectId) {

    for (Project p : ProjectStore.getProjects()) {
        if (p.getId() == projectId) {
            return p.getCreatedBy();
        }
    }

    return "";
}



  @FXML
private void showResearch() {

    joinedResearchIds =
        ResearchMemberStore.getJoinedResearch(Session.getUser());

    contentArea.getChildren().clear();

    VBox layout = new VBox(20);
    layout.setMaxWidth(700);

    Label title = new Label("🔬 Research Collaboration");
    title.getStyleClass().add("card-title");

    Button createBtn = new Button("+ Create Research");
    createBtn.getStyleClass().add("login-button");
    createBtn.setOnAction(e -> openCreateResearchDialog());

    researchListBox = new VBox(15);
    joinedResearchBox = new VBox(15);

    Label joinedTitle = new Label("⭐ My Joined Research");
    joinedTitle.getStyleClass().add("card-title");

    ResearchStore.loadFromDatabase();

   for (Research r : ResearchStore.getResearch()) {

    VBox card = createResearchCard(r);

    
    if (joinedResearchIds.contains(r.getId())) {
        joinedResearchBox.getChildren().add(card);
    } else {
        researchListBox.getChildren().add(card);
    }
}

    layout.getChildren().addAll(
            title,
            createBtn,
            researchListBox,
            joinedTitle,
            joinedResearchBox
    );

    contentArea.getChildren().add(layout);
}




private VBox createResearchCard(Research r) {

    VBox card = new VBox(8);
    card.getStyleClass().add("card");

    Label title = new Label(r.getTitle());
    title.getStyleClass().add("card-title");

    Label faculty =
            new Label("👨‍🏫 " + r.getFaculty());

    Label dept =
            new Label("🏫 " + r.getDepartment());

    Label desc =
            new Label("📌 " + r.getDescription());
    
    VBox membersBox = new VBox(3);

List<String> members =
        ResearchMemberStore.getMembers(r.getId());

if(!members.isEmpty()){

    Label memberTitle = new Label("👥 Members:");

    membersBox.getChildren().add(memberTitle);

    for(String m : members){
        membersBox.getChildren().add(new Label("• " + m));
    }
}

    Button joinBtn = new Button("Join Research");
    joinBtn.getStyleClass().add("login-button");

   // if(Session.getUser().equals(r.getCreatedBy())){
    //card.getChildren().add(membersBox);
//}
    
    if (joinedResearchIds.contains(r.getId())) {

        joinBtn.setText("Joined ✔");
        joinBtn.setDisable(true);
    }

    
    joinBtn.setOnAction(e -> {

    if (Session.getUser().equals(r.getCreatedBy())) {
        showAlert("You cannot join your own research.");
        return;
    }

    //FIRST save
    ResearchRequestStore.sendRequest(
            Session.getUser(),
            r.getId()
    );

    // THEN update button
    profileBtn.setText(getProfileButtonText());

    joinBtn.setText("Request Sent ⏳");
    joinBtn.setDisable(true);
});
    
    

    card.getChildren().addAll(
    title,
    faculty,
    dept,
    desc
);

// add members AFTER details
if(Session.getUser().equals(r.getCreatedBy())){
    card.getChildren().add(membersBox);
}

card.getChildren().add(joinBtn);

    return card;
}



    
private void openCreateResearchDialog() {

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Create Research");

    dialog.getDialogPane().getStylesheets().add(
    getClass().getResource("/ui/dashboard.css").toExternalForm()
);

dialog.getDialogPane().getStyleClass().add("custom-dialog");

    TextField titleField = new TextField();
    titleField.setPromptText("Research Title");

    TextField facultyField = new TextField();
    facultyField.setPromptText("Faculty Name");

    TextField deptField = new TextField();
    deptField.setPromptText("Department");

    TextArea descArea = new TextArea();
    descArea.setPromptText("Description");

    VBox box = new VBox(10,
            new Label("Title:"), titleField,
            new Label("Faculty:"), facultyField,
            new Label("Department:"), deptField,
            new Label("Description:"), descArea
    );

    dialog.getDialogPane().setContent(box);

    ButtonType createBtn =
            new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);

    dialog.getDialogPane().getButtonTypes().addAll(
            createBtn,
            ButtonType.CANCEL
    );

    dialog.showAndWait().ifPresent(response -> {

        if (response == createBtn) {

            Research r = new Research(
                    0,
                    titleField.getText(),
                    facultyField.getText(),
                    deptField.getText(),
                    descArea.getText(),
                    Session.getUser()
            );

            ResearchStore.addResearch(r);

            ResearchStore.loadFromDatabase();
            showResearch();
        }
    });
}


private void loadAlumni() {

    alumniContainer.getChildren().clear();

    try {

        for (Alumni alumni : AlumniDAO.getAllAlumni()) {

            FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/ui/AlumniCard.fxml"));

            Parent card = loader.load();

            AlumniCardController controller =
                    loader.getController();

            controller.setData(alumni);

            alumniContainer.getChildren().add(card);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

   @FXML
private void showAlumni() {

    contentArea.getChildren().clear();

    VBox layout = new VBox(20);
    layout.setMaxWidth(700);

    Label title = new Label("🎓 Alumni Corner");
    title.getStyleClass().add("card-title");

    
    alumniContainer = new VBox(15);

    layout.getChildren().addAll(title, alumniContainer);
    contentArea.getChildren().add(layout);

    
    loadAlumni();
}
        




private VBox createAlumniCard(
        String name,
        String role,
        String background
) {

    VBox card = new VBox(8);
    card.getStyleClass().add("card");
    card.setMaxWidth(Double.MAX_VALUE);

    Label nameLabel = new Label(name);
    nameLabel.getStyleClass().add("card-title");

    Label roleLabel = new Label("💼 " + role);
    Label bgLabel = new Label("🎓 " + background);
    bgLabel.setStyle("-fx-text-fill: #64748b;");

    Button connectBtn = new Button("Connect");
    connectBtn.getStyleClass().add("login-button");

    connectBtn.setOnAction(e -> {
        connectBtn.setText("Request Sent ✔");
        connectBtn.setDisable(true);
    });

    card.getChildren().addAll(
            nameLabel,
            roleLabel,
            bgLabel,
            connectBtn
    );

    return card;
}



@FXML
private void showProfile() {
    profileBtn.setText(getProfileButtonText());
    contentArea.getChildren().clear();

    VBox layout = new VBox(20);
    layout.setMaxWidth(600);

    Label title = new Label("👤 My Profile");
    title.getStyleClass().add("card-title");
    title.setStyle("-fx-font-size:20px;");

    VBox profileCard = new VBox(12);
    profileCard.getStyleClass().add("card");
    profileCard.setPrefWidth(500);
profileCard.setStyle("-fx-padding:20;");

    
    String username = Session.getUser();

    
    User user = UserDAO.getUserProfile(Session.getUser());

if (user == null) {
    Label error = new Label("Profile not found.");
    layout.getChildren().add(error);
    contentArea.getChildren().add(layout);
    return;
}

Label name = new Label("👤 Name: " + user.getUsername());
Label email = new Label("📧 Email: " + user.getEmail());
Label dept = new Label("🏫 Department: " + user.getDepartment());
Label id = new Label("🆔 User ID: " + user.getId());

    Button editBtn = new Button("Edit Profile");
    editBtn.getStyleClass().add("login-button");
    
    editBtn.setOnAction(e -> openEditProfileDialog(user));

   profileCard.getChildren().addAll(
        name,
        email,
        dept,
        id,
        editBtn
);

   // layout.getChildren().addAll(title, profileCard);
   VBox notificationsBox = createNotifications();

layout.getChildren().addAll(title, profileCard, notificationsBox);
    contentArea.getChildren().add(layout);
}




private VBox createNotifications(){

    VBox box = new VBox(10);

    Label title = new Label("🔔 Join Requests");
    title.getStyleClass().add("card-title");

    box.getChildren().add(title);

    // ===== PROJECT REQUESTS =====
    List<ProjectRequest> projectRequests =
        ProjectRequestStore.getRequestsForCreator(Session.getUser());

    for(ProjectRequest r : projectRequests){

        HBox row = new HBox(10);

        Label text = new Label(
            r.getUsername() + " wants to join " + r.getProjectTitle()
        );

        Button approve = new Button("Approve");
        Button decline = new Button("Decline");

        approve.setOnAction(e -> {
    ProjectRequestStore.approveRequest(
        r.getUsername(), r.getProjectId()
    );
    profileBtn.setText(getProfileButtonText());
    showProfile();
});

        decline.setOnAction(e -> {
            ProjectRequestStore.declineRequest(
                r.getUsername(), r.getProjectId()
            );
            profileBtn.setText(getProfileButtonText());
            showProfile();
        });

        row.getChildren().addAll(text, approve, decline);
        box.getChildren().add(row);
    }

    // ===== RESEARCH REQUESTS =====
    List<ResearchRequest> researchRequests =
        ResearchRequestStore.getRequestsForCreator(Session.getUser());

    for(ResearchRequest r : researchRequests){

        HBox row = new HBox(10);

        Label text = new Label(
            r.getUsername() + " wants to join research: " + r.getResearchTitle()
        );

        Button approve = new Button("Approve");
        Button decline = new Button("Decline");

        approve.setOnAction(e -> {
            ResearchRequestStore.approveRequest(
                r.getUsername(), r.getResearchId()
            );
            profileBtn.setText(getProfileButtonText());
            showProfile();
        });

        decline.setOnAction(e -> {
            ResearchRequestStore.declineRequest(
                r.getUsername(), r.getResearchId()
            );
            profileBtn.setText(getProfileButtonText());
            showProfile();
        });

        row.getChildren().addAll(text, approve, decline);
        box.getChildren().add(row);
    }

    // ===== EMPTY CASE =====
    if (projectRequests.isEmpty() && researchRequests.isEmpty()) {
        box.getChildren().add(new Label("No pending requests"));
    }

    return box;
}
    



private void openEditProfileDialog(User user) {

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Edit Profile");

    dialog.getDialogPane().getStylesheets().add(
    getClass().getResource("/ui/dashboard.css").toExternalForm()
);

dialog.getDialogPane().getStyleClass().add("custom-dialog");

    ButtonType saveBtn =
            new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

    dialog.getDialogPane().getButtonTypes()
            .addAll(saveBtn, ButtonType.CANCEL);

    VBox form = new VBox(10);
    form.setPrefWidth(350);

    
    TextField emailField = new TextField(user.getEmail());
    emailField.setPromptText("Email");

    // Department
    ComboBox<String> deptCombo = new ComboBox<>();
    deptCombo.getItems().addAll(
            "CSE","EECE","ME","CE","IPE","NAME",
            "ARCH","BME","EWCE","URP","AE","NSE","PME"
    );
    deptCombo.setValue(user.getDepartment());

    form.getChildren().addAll(
            new Label("Email"),
            emailField,
            new Label("Department"),
            deptCombo
    );

    dialog.getDialogPane().setContent(form);

    dialog.showAndWait().ifPresent(response -> {

        if (response == saveBtn) {

            boolean updated = UserDAO.updateProfile(
                    user.getUsername(),
                    emailField.getText(),
                    deptCombo.getValue()
            );

            if (updated) {
              showAlert("Profile updated successfully");
                //Alert alert = new Alert(Alert.AlertType.INFORMATION);
//alert.setTitle("Success");
//alert.setHeaderText(null);
//alert.setContentText("Profile updated successfully!");
//alert.showAndWait();
                showProfile(); 
                
            }
        }
    });
}




private String getProfileButtonText(){

    int count = getTotalPendingRequests();

    if(count == 0){
        return "👤 Profile";
    }

    return "👤 Profile 🔴 " + count;
}


// notification count 

private int getTotalPendingRequests(){

    int projectCount =
        ProjectRequestStore
        .getRequestsForCreator(Session.getUser())
        .size();

    int researchCount =
        ResearchRequestStore
        .getRequestsForCreator(Session.getUser())
        .size();

    return projectCount + researchCount;
}


 //  LOGOUT 
  @FXML
private void handleLogout(ActionEvent event) throws Exception {

    Session.clear(); // ⭐ clear login session

    Stage stage =
        (Stage) contentArea.getScene().getWindow();

    SceneSwitcher.switchScene(stage, "/ui/Welcome.fxml");
}



private void showAlert(String msg) {

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText(msg);

    DialogPane pane = alert.getDialogPane();

    pane.setStyle(
        "-fx-background-color: rgba(0,107,128,0.95);" +
        "-fx-font-size: 14px;" +
        "-fx-text-fill: white;"
    );

    // Blur background (use contentArea as root)
    javafx.scene.effect.GaussianBlur blur =
            new javafx.scene.effect.GaussianBlur(10);

    contentArea.setEffect(blur);

    alert.setOnHidden(e -> contentArea.setEffect(null));

    // Fade animation
    pane.setOpacity(0);

    alert.setOnShown(e -> {
        javafx.animation.FadeTransition ft =
            new javafx.animation.FadeTransition(
                javafx.util.Duration.millis(400), pane
            );
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    });

    alert.showAndWait();
}
}


