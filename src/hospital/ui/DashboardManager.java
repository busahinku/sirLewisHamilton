package hospital.ui;

import hospital.objects.*;
import javafx.stage.Stage;
import java.util.List;

public class DashboardManager {
    private Stage primaryStage;
    private List<Person> users;
    private FounderDashboard founderDashboard;
    private DoctorDashboard doctorDashboard;
    private PrivateDoctorDashboard privateDoctorDashboard;
    private AssistantDashboard assistantDashboard;
    private PatientDashboard patientDashboard;
    private PharmacistDashboard pharmacistDashboard;

    public DashboardManager(Stage primaryStage, List<Person> users) {
        this.primaryStage = primaryStage;
        this.users = users;
        this.founderDashboard = new FounderDashboard(primaryStage, this);
        this.doctorDashboard = new DoctorDashboard(primaryStage, this);
        this.privateDoctorDashboard = new PrivateDoctorDashboard(primaryStage, this);
        this.assistantDashboard = new AssistantDashboard(primaryStage, this);
        this.patientDashboard = new PatientDashboard(primaryStage, this);
        this.pharmacistDashboard = new PharmacistDashboard(primaryStage, this);
    }

    public void showAppropriateDashboard(Person user) {
        if (user instanceof Founder) {
            founderDashboard.show((Founder) user);
        } else if (user instanceof Doctor) {
            doctorDashboard.show((Doctor) user);
        } else if (user instanceof PrivateDoctors) {
            privateDoctorDashboard.show((PrivateDoctors) user);
        } else if (user instanceof Assistant) {
            assistantDashboard.show((Assistant) user);
        } else if (user instanceof Patient) {
            patientDashboard.show((Patient) user);
        } else if (user instanceof Pharmacist) {
            pharmacistDashboard.show((Pharmacist) user);
        }
    }

    public List<Person> getUsers() {
        return users;
    }
} 