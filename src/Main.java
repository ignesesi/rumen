import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Issue issue = new Issue(
                "App flow tests?",
                "We need to test the App!",
                LocalDate.now().plusDays(1));
        issue.advanceStatus();
        issue.setDueDate(issue.getDueDate().plusDays(1));
        issue.displayHistory();
    }
}
