import controller.GroupController;
import controller.UserController;
import entity.Group;
import entity.User;
import exception.IncorrectExistingPassword;
import repository.GroupRepoInMemory;
import service.GroupService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //---------------------------------------------------------------
        UserController userController = UserController.getInstance();

        User alan = userController.registerUser("Alan", "1234", "Turing");
        System.out.println(alan);

        try {
            userController.updatePassword(alan.getId(), "Turing", "Enigma");
            System.out.println(alan);
        } catch (IncorrectExistingPassword e) {
            System.out.println(e.getMessage());
        }

        User charles = userController.registerUser("Charles", "4567", "Babbage");
        System.out.println(charles);

        System.out.println("------------------------------------------------");
        //---------------------------------------------------------------


        // not ideal - just for the testing purpose
        GroupController groupController = new GroupController(new GroupService(new GroupRepoInMemory()));
        User grace = new User("Grace", "1111", "Hopper");
        User ada = new User("Ada", "2222", "Lovelace");
        User claude = new User("Claude", "3333", "Shannon");

        Group group = groupController.createGroup(charles, "OGs",
                List.of(grace, alan, grace, ada, claude));
        System.out.println(group);
    }
}
