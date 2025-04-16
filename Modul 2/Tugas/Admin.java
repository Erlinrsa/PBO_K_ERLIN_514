public class Admin {
    private String username = "admin";
    private String password = "1234";

    // Metode untuk login admin
    public boolean login(String inputUsername, String inputPassword) {
        return inputUsername.equals(username) && inputPassword.equals(password);
    }
}