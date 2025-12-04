package entity;
import exception.IncorrectExistingPassword;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude = {"password", "groups", "expenses"})
public class User {
    private UUID id;
    private String userName;
    private String phoneNumber;
    private String password;
    private Set<Group> groups;
    private Set<Expense> expenses;


    public User(String name, String phoneNumber, String password) {
        this.id = UUID.randomUUID();
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.password = hashPassword(password);
    }


    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    private boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }


    public boolean updatePassword(String oldPassword, String newPassword) throws IncorrectExistingPassword {
        String oldHashedPassword = this.getPassword();
        if (verifyPassword(oldPassword, oldHashedPassword)) {
            this.password = hashPassword(newPassword);
            return true;
        } else {
            // return false;
            throw new IncorrectExistingPassword("Incorrect existing password.");
        }
    }

}


