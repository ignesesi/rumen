import java.time.LocalDate;

public class Issue extends BoardItem {
    private final String description;
    public Issue(String title, String description, LocalDate dueDate) {
        super(title, dueDate);
        if (description == null) {
            this.description = "No description";
        } else if (description.length() < 5 || description.length() > 30) {
            throw new IllegalArgumentException("Next time type a correct description.");
        } //We are not asked for this check, but I feel it is a reasonable thing to add.
        else {
            this.description = description;
        }
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String viewInfo() {
         // get the common info
         String baseInfo = super.viewInfo();

         // add additional info, based on which subclass you are in
         return String.format("Issue: %s, Description: %s", baseInfo, this.getDescription());
    }
}
