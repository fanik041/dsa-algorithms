class User {
    private boolean active;

    public User(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}

public class TernaryOperator {

    private String status;

    public void updateStatus(User user) {
        if(user.isActive())
        {
            status = "ACTIVE";
        } else {
            status = "INACTIVE";
        }
    }

    //Do this instead:
    public void updateStatusProper(User user) {
        String status = user.isActive() ? "ACTIVE" : "INACTIVE";
    }

}
