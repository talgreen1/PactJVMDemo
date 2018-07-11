package com.talgreen.pact.ui;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

public class UIApp {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            printMenu();
            String line = in.nextLine();
            parseCommand(line);
        }
    }

    private static void printMenu() {
        StringBuffer menuText = new StringBuffer();
        menuText.append("UI Menu\n");
        menuText.append("-------\n");
        menuText.append("NumOfUsers:\t\t\tGet the number of users\n");
        menuText.append("AllUsers:\t\t\tList all users\n");
        menuText.append("User <User ID>:\t\tShow specific user. For example: User 2\n");
        menuText.append("Usernames:\t\tList all usernames\n");
        menuText.append("Exit:\t\t\t\tExit menu\n");
        menuText.append("\n\nPlease type a command:");
        System.out.println(menuText.toString());

    }

    private static void parseCommand(String line) {
        String[] lineParts = line.split("\\s+");
        switch (lineParts[0].toLowerCase()) {
            case ("numofusers"):
                System.out.println(getNumOfUser());
                break;
            case ("allusers"):
                printAllUser();
                break;
            case ("user"):
                printUser(lineParts[1]);
                break;
            case ("usernames"):
                System.out.println(getUserNames());
                break;
            case ("exit"):
                System.exit(0);
            default:
                System.out.println("Invalid command");
        }
    }

    private static List<String> getUserNames() {
        List<User> users = getAllUsers();
        List<String> usernames = users.stream().map(user -> user.getUsername()).collect(Collectors.toList());
        return usernames;
    }

    private static int getNumOfUser() {
        List<User> users = getAllUsers();
        return users.size();
    }

    private static List<User> getAllUsers() {
        Response response = get("http://localhost:" + PORT + "/users");
        JsonPath jsonPath = response.jsonPath();

        List<User> users = jsonPath.getList(".", User.class);

        return users;
    }

    private static void printUser(String userId) {
        Response response = get("http://localhost:" + PORT + "/user/" + userId);
        if (response.getStatusCode() != 200) {
            System.out.println("Invalid user id");
            return;
        }
        JsonPath jsonPath = response.jsonPath();

        User user = jsonPath.getObject(".", User.class);
        System.out.println(user);

    }

    private static void printAllUser() {
        Response response = get("http://localhost:" + PORT + "/users");
        JsonPath jsonPath = response.jsonPath();

        List<User> users = jsonPath.getList(".", User.class);

        users.forEach(System.out::println);
    }
}
