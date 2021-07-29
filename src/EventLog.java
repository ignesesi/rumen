import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventLog {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
    private final String description;
    private final LocalDateTime timestamp;

    public EventLog(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = description;
        timestamp = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String viewInfo() {
        return String.format("[%s] %s", timestamp.format(formatter), description);
    }

}
