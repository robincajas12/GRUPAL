package application.Models;

public class User implements IValidosParaCrud {
    private final int id;
    private final String email;
    private final String password;
    private final String name;
    private final String role;
    private final String avatar;

    public User(String email, String password, String name, String avatar) {
        this(0, email, password, name, null, avatar);
    }

    public User(int id, String email, String password, String name, String avatar) {
        this(id, email, password, name, null, avatar);
    }

    public User(int id, String email, String password, String name, String role, String avatar) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.avatar = avatar;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getAvatar() { return avatar; }

    public int id() { return id; }
    public String email() { return email; }
    public String password() { return password; }
    public String name() { return name; }
    public String role() { return role; }
    public String avatar() { return avatar; }
}
