package com.talgreen.pact.ui;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

public class UIApp {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            printMenu();
            String line = in.nextLine();
            parseCommand(line);
        }
    }

    private static void printMenu() {
        String menuText = "UI Menu\n" +
                "-------\n" +
                "NumOfUsers:\t\t\tGet the number of users\n" +
                "Usernames:\t\t\tList all usernames\n" +
                "Role <User ID>:\t\tGet role of specific user. For example: Role 2\n" +
                "Exit:\t\t\t\tExit menu\n" +
                "\n\nPlease type a command:";
        System.out.println(menuText);

    }

    private static void parseCommand(String line) {
        String[] lineParts = line.split("\\s+");
        switch (lineParts[0].toLowerCase()) {
            case ("numofusers"):
                System.out.println(getNumOfUser());
                break;
            case ("usernames"):
                System.out.println(getUserNames());
                break;
            case ("role"):
                System.out.println(getRole(lineParts[1]));
                break;
            case ("exit"):
                System.exit(0);
            default:
                System.out.println("Invalid command");
        }
    }

    public static String getRole(String userId) {
        Response response = get("http://localhost:" + PORT + "/user/" + userId);
        if (response.getStatusCode() != 200) {
            return ("Invalid user id");
        }
        JsonPath jsonPath = response.jsonPath();

        User user = jsonPath.getObject(".", User.class);
        return user.getRole();
    }

    public static List<String> getUserNames() {
        List<User> users = getAllUsers();
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }

    public static int getNumOfUser() {
        List<User> users = getAllUsers();
        return users.size();
    }

    private static List<User> getAllUsers() {
        Response response = get("http://localhost:" + PORT + "/users");
        JsonPath jsonPath = response.jsonPath();

        return jsonPath.getList(".", User.class);
    }


}
