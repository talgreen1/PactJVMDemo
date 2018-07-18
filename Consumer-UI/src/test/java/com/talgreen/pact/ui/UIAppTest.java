package com.talgreen.pact.ui;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
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
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2("UserManagement", "localhost", 8080, this);

    @Pact(consumer = "UI")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");

        DslPart body = new PactDslJsonArray()
                .object()
                    .stringType("id","1")
                    .stringType("username","ClarkKent")
                    .stringType("role","admin")
                .closeObject()
                .object()
                    .stringType("id","2")
                    .stringType("username","Superman")
                    .stringType("role","user")
                .closeObject();


        return builder
                .given("There are 2 users in the system")
                .uponReceiving("A request for all users")
                .path("/users")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(body).toPact();

    }

    @Test
    @PactVerification()
    public void getNumOfUserTest(){
        int numOfUser = UIApp.getNumOfUser();
        assertThat(numOfUser).isEqualTo(2);
    }

    @Test
    @PactVerification()
    public void getUsernamesTest(){
        List<String> usernames = UIApp.getUsernames();
        assertThat(usernames).containsExactly("ClarkKent", "Superman");
    }
}