package repository;

import entity.Group;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GroupRepoInMemory implements IGroupRepo {

    private ConcurrentMap<UUID, Group> groups;

    public GroupRepoInMemory() {
        groups = new ConcurrentHashMap<>();
    }


    @Override
    public Group get(UUID groupId) {
        return groups.get(groupId);
    }

    @Override
    public Group create(Group group) {
        groups.put(group.getId(), group);
        return group;
    }
}
