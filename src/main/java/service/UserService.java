package service;

import entity.User;
import exception.IncorrectExistingPassword;
import repository.IUserRepo;
import repository.UserRepoInMemory;

import java.util.UUID;


public class UserService {
    private final IUserRepo userRepo;
    private static UserService userService = null;

    private UserService(IUserRepo userRepo) {
       this.userRepo = userRepo;
    }

    public static UserService getInstance() {
        if (userService == null) {
            synchronized (UserService.class) {
                if (userService == null) {
                    IUserRepo userRepo = UserRepoInMemory.getInstance();
                    userService = new UserService(userRepo);
                }
            }
        }
        return userService;
    }


    public User registerUser(String name, String phone, String password) {
        User user = new User(name, phone, password);
        userRepo.save(user);
        return user;
    }


    public boolean updatePassword(UUID userId, String oldPassword, String newPassword) throws IncorrectExistingPassword {
        User user = userRepo.get(userId);
        return user.updatePassword(oldPassword, newPassword);
    }
}
