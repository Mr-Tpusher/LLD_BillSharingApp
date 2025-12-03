package repository;

import entity.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserRepoInMemory implements IUserRepo {
    private static IUserRepo userRepo = null;
    private ConcurrentMap<UUID, User> users;

    private UserRepoInMemory() {
        this.users = new ConcurrentHashMap<>();
    }

    public static IUserRepo getInstance() {
        if (userRepo == null) {
            synchronized (UserRepoInMemory.class) {
                if (userRepo == null) {
                    userRepo = new UserRepoInMemory();
                }
            }
        }
        return userRepo;
    }


    @Override
    public User get(UUID userId) {
        return users.get(userId);
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
       return null;
    }
}
