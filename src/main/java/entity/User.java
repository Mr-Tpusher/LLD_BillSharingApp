package entity;
import exception.IncorrectExistingPassword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@ToString(exclude = {"password", "groups", "expenses"})
public class User {
    private UUID id;
    private String userName;
    private String phoneNumber;
    private String password;

    @Setter(AccessLevel.NONE)
    private Set<Group> groups;

    @Setter(AccessLevel.NONE)
    private Set<Expense> expenses;


    public User(String name, String phoneNumber, String password) {
        this.id = UUID.randomUUID();
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.password = hashPassword(password);
        this.groups = ConcurrentHashMap.newKeySet();
        this.expenses = ConcurrentHashMap.newKeySet();
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

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

}


