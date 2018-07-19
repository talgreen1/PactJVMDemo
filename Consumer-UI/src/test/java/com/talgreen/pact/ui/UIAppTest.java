package com.talgreen.pact.ui;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.*;
import au.com.dius.pact.model.RequestResponsePact;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StringType;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class UIAppTest {

    private static final int MOCK_PORT = 8080;

    @Rule
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2("UserManagement", "localhost", MOCK_PORT, this);

    @DefaultResponseValues
    public void defaultResponseValues(PactDslResponse response) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        response.headers(headers);
    }

    @Pact(consumer = "UI")
    public RequestResponsePact createPactForAllUsers(PactDslWithProvider builder) {
        DslPart body = new PactDslJsonArray()
                .object()
                    .stringType("id","1")
                    .stringType("username","superman")
                    .stringType("role","admin")
                .closeObject()
                .object()
                    .stringType("id","2")
                    .stringType("username","ClarkKent")
                    .stringType("role","user")
                .closeObject();


        return builder
                .given("There are 2 users in the system")
                .uponReceiving("A request for all users")
                .path("/users")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body).toPact();
    }

    @Pact(consumer = "UI")
    public RequestResponsePact createPactForSingleUser(PactDslWithProvider builder) {
        DslPart body = new PactDslJsonBody()
                    .stringType("id","1")
                    .stringType("username","superman")
                    .stringType("role","admin")
                    .asBody();


        return builder
                .given("There is at least one users in the system")
                .uponReceiving("A request for user with id 1")
                .path("/user/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body).toPact();
    }

    @Test
    @PactVerification(value ="UserManagement", fragment = "createPactForAllUsers")
    public void getNumOfUserTest(){
        int numOfUser = UIApp.getNumOfUser();
        assertThat(numOfUser).isEqualTo(2);
    }

    @Test
    @PactVerification(value ="UserManagement", fragment = "createPactForAllUsers")
    public void getUsernamesTest(){
        List<String> usernames = UIApp.getUserNames();
        assertThat(usernames).containsExactly("superman", "ClarkKent");
    }

    @Test
    @PactVerification(value ="UserManagement", fragment = "createPactForSingleUser")
    public void getRoleTest(){
        String role = UIApp.getRole("1");
        assertThat(role).isEqualTo("admin");
    }
}