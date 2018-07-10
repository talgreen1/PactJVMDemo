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

    private static final int MOCK_PORT = 7979;

    private static WireMockServer wireMockServer;

    @BeforeClass
    public static void initMock() {

        wireMockServer = new WireMockServer(wireMockConfig().port(MOCK_PORT));
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockServer.port());

        String responseForSpecificUser = "{\"id\":\"1\",\"username\":\"talgreen\",\"role\":\"admin\"}";
        stubFor(get(urlEqualTo("/user/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseForSpecificUser)));

        String responseForAllUsers = "[" +
                "{\"id\":\"1\",\"username\":\"talgreen\",\"role\":\"admin\"}," +
                "{\"id\":\"2\",\"username\":\"ClarkKent\",\"role\":\"user\"}]";

        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseForAllUsers)));

    }

    @Test
    public void allUsersAPITest() {

        Response response = RestAssured.get("http://localhost:" + MOCK_PORT + "/users");
        JsonPath jsonPath = response.jsonPath();

        List<User> userList = jsonPath.getList(".", User.class);
        assertThat(userList.size()).isGreaterThanOrEqualTo(2);

    }

    @Test
    public void singleUserAPITest() {

        Response response = RestAssured.get("http://localhost:" + MOCK_PORT + "/user/1");
        JsonPath jsonPath = response.jsonPath();

        User user = jsonPath.getObject(".", User.class);

        assertThat(user.getId()).isEqualTo("1");


    }

    @AfterClass
    public static void stopMock() {
        wireMockServer.stop();
    }


}