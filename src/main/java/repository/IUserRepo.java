package repository;

import entity.User;

import java.util.UUID;

public interface IUserRepo {

    User get(UUID userId);
    User save(User user);
    User update(User user);

}
