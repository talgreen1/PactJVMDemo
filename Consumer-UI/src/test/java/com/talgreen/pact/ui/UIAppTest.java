package com.talgreen.pact.ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;


public class UIAppTest {

    private static final int MOCK_PORT = 8080;

    private static WireMockServer wireMockServer;

    @BeforeClass
    public static void initMock() {

        wireMockServer = new WireMockServer(wireMockConfig().port(MOCK_PORT));
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockServer.port());

        String responseForSpecificUser = "{\"id\":\"1\",\"username\":\"spiderman\",\"role\":\"admin\"}";
        stubFor(get(urlEqualTo("/user/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseForSpecificUser)));

        String responseForAllUsers = "[" +
                "{\"id\":\"1\",\"username\":\"superman\",\"role\":\"admin\"}," +
                "{\"id\":\"2\",\"username\":\"ClarkKent\",\"role\":\"user\"}]";

        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseForAllUsers)));

    }

    @Test
    public void getNumOfUserTest(){
        int numOfUser = UIApp.getNumOfUser();
        assertThat(numOfUser).isEqualTo(2);
    }

    @Test
    public void getUsernamesTest(){
        List<String> usernames = UIApp.getUserNames();
        assertThat(usernames).containsExactly("superman", "ClarkKent");
    }

    @Test
    public void getRoleTest(){
        String role = UIApp.getRole("1");
        assertThat(role).isEqualTo("admin");
    }



    @AfterClass
    public static void stopMock() {
        wireMockServer.stop();
    }


}