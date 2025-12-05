package service;

import entity.Expense;
import entity.Group;
import entity.User;
import repository.IGroupRepo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GroupService {
    // applying dependency inversion principle
    private IGroupRepo groupRepo;

    // Ideally frameworks like spring boot take care of Dependency Injection
    public GroupService(IGroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Group createGroup(User creator, String name, List<User> participants) {
        Group group = new Group(creator, name);

        participants.forEach(user -> {
            group.addParticipant(user);
            user.addGroup(group);
        });

        groupRepo.create(group);
        return group;
    }

    public Set<Expense> getAllGroupExpense(UUID userId, UUID groupId) throws IllegalAccessException {
        Group group = groupRepo.get(groupId);

        boolean doesBelongToGroup = group.getParticipants()
                .stream()
                .anyMatch(user -> user.getId() == userId);

        if (!doesBelongToGroup) {
            throw new IllegalAccessException("user not allowed to query this group.");
        }

        return group.getExpenses();

    }
}
