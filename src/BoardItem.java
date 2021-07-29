import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class BoardItem {
    private String title;
    private LocalDate dueDate;
    private Status status;
    protected List<EventLog> history;

    public BoardItem(String title, LocalDate dueDate) {
        this(title, dueDate, Status.Open);
    }

    public BoardItem(String title, LocalDate dueDate, Status status) {
        if (title == null || title.length() < 5 || title.length() > 30) {
            throw new IllegalArgumentException("Next time type a correct title.");
        }
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Next time give a correct date.");
        }
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        history = new ArrayList<>();
        history.add(new EventLog(String.format("Item created: '%s', [%s | %s])",this.title, this.status, this.dueDate)));
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Please provide a non-empty title");
        }
        if (title.length() < 5 || title.length() > 30) {
            throw new IllegalArgumentException("Please provide a title with length between 5 and 30 chars");
        }
        history.add(new EventLog(String.format("Title changed from %s to %s", this.title, title)));
        this.title = title;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }
        history.add(new EventLog(String.format("DueDate changed from %s to %s", this.dueDate, dueDate)));
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public abstract void revertStatus();

    public abstract void advanceStatus();

    public String viewInfo() {
        return String.format("'%s', [%s | %s]", title, status, dueDate);
    }

    public void displayHistory() {
        for (EventLog log : history) {
            System.out.println(log.viewInfo());
        }
    }

    public String getHistory() {
        StringBuilder builder = new StringBuilder();

        for (EventLog event : history) {
          builder.append(event.viewInfo()).append(System.lineSeparator());
        }

        return builder.toString();
    }
}
