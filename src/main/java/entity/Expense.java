package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString(exclude = {"participants"})
public class Expense {
    private UUID id;
    private String desc;
    private User creator;
    private Set<User> participants;
    private UUID groupId;

    // all positive balances
    Map<User, Double> paidAmounts;

    // all negative balances
    Map<User, Double> splitAmounts;

    public Expense(User creator, String desc, Set<User> participants, UUID groupId,
                   Map<User, Double> paidAmounts, Map<User, Double> splitAmounts) {
        this.id = UUID.randomUUID();
        this.desc = desc;
        this.creator = creator;
        this.groupId = groupId;
        this.participants = new HashSet<>(participants);
        this.paidAmounts = new HashMap<>(paidAmounts);
        this.splitAmounts = new HashMap<>(splitAmounts);
    }

}
