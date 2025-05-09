package hospital.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.util.List;

import hospital.objects.Person;

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
        primaryStage.setTitle("SirLewis & Hamilton - Login");

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(0);
        root.setStyle("-fx-background-color: white;");

        // Logo only (no brand text)
        VBox logoBox = new VBox();
        logoBox.setAlignment(Pos.CENTER);
        ImageView logo = new ImageView(new Image("file:src/resources/logo.png"));
        logo.setFitWidth(230);
        logo.setPreserveRatio(true);
        logoBox.getChildren().add(logo);
        logoBox.setPadding(new Insets(72, 0, 20, 0));

        // Card (wider for text)
        VBox card = new VBox(14);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(20, 22, 20, 22));
        card.setStyle("-fx-background-color: #f2f2f2; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, #f1f1f1, 10, 0, 0, 2);");
        card.setMaxWidth(370);

        Text welcome = new Text("Welcome,");
        welcome.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
        VBox welcomeBox = new VBox(welcome);
        welcomeBox.setAlignment(Pos.CENTER_LEFT);

        Text subtitle = new Text("Please sign in to access your features.");
        subtitle.setFont(Font.font("Inter", FontWeight.NORMAL, 12));
        subtitle.setFill(Color.GRAY);
        subtitle.setTranslateY(-7);
        VBox subtitleBox = new VBox(subtitle);
        subtitleBox.setAlignment(Pos.CENTER_LEFT);
        subtitleBox.setPadding(new Insets(0, 0, 4, 0));

        Separator sep = new Separator();

        // Form (labels above fields)
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER_LEFT);

        VBox userBox = new VBox(2);
        Label userName = new Label("Username:");
        userName.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        userName.setTextFill(Color.DARKGRAY);
        TextField userTextField = new TextField();
        userTextField.setPrefWidth(220);
        userTextField.setPrefHeight(35);
        userBox.getChildren().addAll(userName, userTextField);

        VBox pwBoxContainer = new VBox(2);
        Label pw = new Label("Password:");
        pw.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        pw.setTextFill(Color.DARKGRAY);
        PasswordField pwBox = new PasswordField();
        pwBox.setPrefWidth(220);
        pwBox.setPrefHeight(35);
        pwBoxContainer.getChildren().addAll(pw, pwBox);

        formBox.getChildren().addAll(userBox, pwBoxContainer);

        // Login Button (same width as input fields)
        Button btn = new Button("Login");
        btn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 6;");
        btn.setPrefWidth(325);
        btn.setPrefHeight(35);

        Separator sep2 = new Separator();

        // Sign Up Link (inline, wrap if needed)
        TextFlow signUpFlow = new TextFlow();
        signUpFlow.setPrefWidth(300);
        signUpFlow.setLineSpacing(0);
        Text signUpText = new Text("If you are a patient & don't have an account, ");
        signUpText.setFont(Font.font("Inter", 12));
        Hyperlink signUpLink = new Hyperlink("Sign Up");
        signUpLink.setStyle("-fx-text-fill: #8e44ad; -fx-font-size: 12; -fx-underline: true;");
        signUpFlow.getChildren().addAll(signUpText, signUpLink);

        // Add all to card
        card.getChildren().clear();
        card.getChildren().addAll(welcomeBox, subtitleBox, sep, formBox, btn, sep2, signUpFlow);

        // Exit Button (smaller)
        Button exitBtn = new Button("Exit the Program");
        exitBtn.setStyle("-fx-background-color: #f2f2f2; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 8; ");
        exitBtn.setPadding(new Insets(0, 0, 0, 10));
        exitBtn.setPrefWidth(370);
        exitBtn.setPrefHeight(40);

        // Footer (smaller)
        Text footer = new Text("SirLew&Ham is the biggest private healthcare start-up with its own hospitals.");
        footer.setFont(Font.font("Arial", FontPosture.ITALIC, 12));
        footer.setFill(Color.GRAY);
        footer.setWrappingWidth(340);

        VBox bottomBox = new VBox(6, exitBtn, footer);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(18, 0, 0, 0));

        // Add everything to root
        root.getChildren().clear();
        root.getChildren().addAll(logoBox, card, bottomBox);
        root.setPadding(new Insets(0, 0, 0, 0));
        root.setSpacing(0);

        // --- Event Handlers ---
        Text actiontarget = new Text();
        card.getChildren().add(actiontarget);

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
                actiontarget.setText("Invalid Access, Password or Username is Incorrect.");
                actiontarget.setFill(Color.FIREBRICK);
            }
        });

        signUpLink.setOnAction(e -> {
            PatientSignUpScreen signUpScreen = new PatientSignUpScreen(primaryStage, users);
            signUpScreen.show();
        });

        exitBtn.setOnAction(e -> {
            primaryStage.close();
        });

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
} 