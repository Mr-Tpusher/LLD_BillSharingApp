package controller;
import entity.User;
import service.UserService;

import java.util.UUID;

public class UserController {

    private static UserController instance = null;
    UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    public static UserController getInstance() {
        if (instance == null) {
            synchronized (UserController.class) {
                if (instance == null) {
                    UserService userService = UserService.getInstance();
                    instance = new UserController(userService);
                }
            }
        }
        return instance;
    }

    public User registerUser(String name, String phone, String password) {
        return userService.registerUser(name, phone, password);
    }

    public boolean updatePassword(UUID userId, String oldPassword, String newPassword) {
        return userService.updatePassword(userId, oldPassword, newPassword);
    }

}

