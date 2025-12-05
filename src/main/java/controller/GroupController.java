package controller;

import dto.response.Transaction;
import entity.Expense;
import entity.Group;
import entity.User;
import service.GroupService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GroupController {

    private GroupService groupService;
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public Group createGroup(User creator, String name, List<User> participants) {
        return groupService.createGroup(creator, name, participants);
    }

    public Set<Expense> getAllGroupExpense(UUID userId, UUID groupId) throws IllegalAccessException {
        return groupService.getAllGroupExpense(userId, groupId);
    }

    public List<Transaction> settleUp(UUID groupId) {
        return groupService.settleUp(groupId);
    }
}
