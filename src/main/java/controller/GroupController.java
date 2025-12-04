package controller;

import entity.Group;
import entity.User;
import service.GroupService;

import java.util.List;

public class GroupController {

    private GroupService groupService;
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public Group createGroup(User creator, String name, List<User> participants) {
        return groupService.createGroup(creator, name, participants);
    }

}
