import java.time.LocalDate;

public class Task extends BoardItem {
    private String assignee;

    public Task(String title, String assignee, LocalDate dueDate) {
        super(title, dueDate, Status.ToDo);
        if (assignee == null || assignee.length() < 5 || assignee.length() > 30) {
            throw new IllegalArgumentException("Next time type a correct name of assignee.");
        }
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        if (assignee == null) {
            throw new IllegalArgumentException("Please provide a non-empty name of assignee");
        }
        if (assignee.length() < 5 || assignee.length() > 30) {
            throw new IllegalArgumentException("Please provide a name of assignee with length between 5 and 30 chars");
        }
        history.add(new EventLog(String.format("Assignee changed from %s to %s", this.assignee, assignee)));
        this.assignee = assignee;
    }


}
