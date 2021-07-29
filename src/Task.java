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

    @Override
    public String viewInfo() {
         // get the common info
         String baseInfo = super.viewInfo();

         // add additional info, based on which subclass you are in
         return String.format("Task: %s, Assignee: %s", baseInfo, this.getAssignee());
    }

    @Override
    public void advanceStatus() {
        switch(status) {
            case Open :
                status = Status.ToDo;
                history.add(new EventLog("Status changed from Open to To Do"));
                break;
            case ToDo :
                status = Status.InProgress;
                history.add(new EventLog("Status changed from To Do to In Progress"));
                break;
            case InProgress :
                status = Status.Done;
                history.add(new EventLog("Status changed from In Progress to Done"));
                break;
            case Done :
                status = Status.Verified;
                history.add(new EventLog("Status changed from Done to Verified"));
                break;
            case Verified :
                history.add(new EventLog("Can't advance, already at Verified"));
                break;
        }
    }

    @Override
    public void revertStatus() {
        switch(status) {
            case Open :
                history.add(new EventLog("Can't revert, already at Open"));
                break;
            case ToDo :
                status = Status.Open;
                history.add(new EventLog("Status changed from To Do to Open"));
                break;
            case InProgress :
                status = Status.ToDo;
                history.add(new EventLog("Status changed from In Progress to To Do"));
                break;
            case Done :
                status = Status.InProgress;
                history.add(new EventLog("Status changed from Done to In Progress"));
                break;
            case Verified :
                status = Status.Done;
                history.add(new EventLog("Status changed from Verified to Done"));
                break;
        }
    }
}
