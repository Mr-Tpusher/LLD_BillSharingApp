package repository;

import entity.Group;

import java.util.UUID;

public interface IGroupRepo {

    Group get(UUID groupId);

    Group create(Group group);

}
