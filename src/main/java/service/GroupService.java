package service;

import entity.Group;
import entity.User;
import repository.IGroupRepo;
import java.util.List;

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
}
