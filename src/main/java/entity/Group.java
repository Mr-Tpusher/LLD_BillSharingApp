package entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@ToString
// @ToString(exclude = {"participants", "expenses"})
public class Group {
    private UUID id;
    private String name;
    private User admin;

    @Setter(AccessLevel.NONE)
    private Set<User> participants;

    @Setter(AccessLevel.NONE)
    private Set<Expense> expenses;

    public Group(User creator, String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.admin = creator;
        // use concurrent set
        this.participants = ConcurrentHashMap.newKeySet();
        this.expenses = ConcurrentHashMap.newKeySet();
    }

    public void addParticipant(User user) {
        participants.add(user);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}
