package hospital.ui;

import hospital.objects.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class LoginScreen {
    private Stage primaryStage;
    private List<Person> users;
    private DashboardManager dashboardManager;

    public LoginScreen(Stage primaryStage, List<Person> users) {
        this.primaryStage = primaryStage;
        this.users = users;
        this.dashboardManager = new DashboardManager(primaryStage, users);
    }

    public void show() {
        primaryStage.setTitle("Hospital Management System - Login");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome to Hospital Management System");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        Button signUpBtn = new Button("Sign Up as Patient");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(signUpBtn, btn);
        grid.add(hbBtn, 1, 4);

        Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();
            
            boolean authenticated = false;
            Person authenticatedUser = null;
            
            for (Person user : users) {
                if (user.authenticate(username, password)) {
                    authenticated = true;
                    authenticatedUser = user;
                    break;
                }
            }
            
            if (authenticated) {
                dashboardManager.showAppropriateDashboard(authenticatedUser);
            } else {
                actiontarget.setText("Invalid credentials!");
            }
        });

        signUpBtn.setOnAction(e -> {
            PatientSignUpScreen signUpScreen = new PatientSignUpScreen(primaryStage, users);
            signUpScreen.show();
        });

        Scene scene = new Scene(grid, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
} 