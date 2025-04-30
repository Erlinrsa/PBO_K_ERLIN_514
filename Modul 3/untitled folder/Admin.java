public class Admin extends User {
    private String username;
    private String password;

    // Constructor
    public Admin(String nama, String nim, String username, String password) {
        super(nama, nim); // Panggil constructor dari User
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    @Override
    public void displayInfo() {
        System.out.println("Login Admin berhasil!");
        System.out.println("Selamat datang, " + getNama());
    }
}