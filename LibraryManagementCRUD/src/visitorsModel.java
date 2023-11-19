public class visitorsModel {
    int id;
    private String visitorName;
    private String visitorReference;

    public int getId() {
        return id;
    }

    public String getVisitorName() {
        return visitorName;
    }
    public String getVisitorReference() {
        return visitorReference;
    }

    public visitorsModel(int id, String visitorName, String visitorReference) {
        this.id = id;
        this.visitorName = visitorName;
        this.visitorReference = visitorReference;
    }
}

