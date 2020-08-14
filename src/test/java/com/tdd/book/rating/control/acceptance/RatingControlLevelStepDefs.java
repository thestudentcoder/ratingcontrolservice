package com.tdd.book.rating.control.acceptance;

import com.tdd.book.rating.control.fixtures.BookServiceFixture;
import cucumber.api.java8.En;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@AutoConfigureWireMock(port = 9999)
public class RatingControlLevelStepDefs implements En {

    private String customerSetControlLevel;
    private ResponseEntity<Boolean> responseEntity;

    @Autowired
    private TestRestTemplate restTemplate;

    public RatingControlLevelStepDefs() {
        Given("^I am a customer with a rating control level of (.*)$", (String customerSetControlLevel) -> {
            this.customerSetControlLevel = customerSetControlLevel;
        });

        When("^I request to read an equal level book (.*)$", (String book_id) -> {
            BookServiceFixture.stubBookServiceResponseForBook_B1234_Rating12(book_id);
            HttpEntity httpEntity = new HttpEntity(generateHeader());
            responseEntity = restTemplate.exchange("/rcl/book/v1/read/eligibility/" +
                            this.customerSetControlLevel + "/" + book_id,
                    HttpMethod.GET, httpEntity, Boolean.class);
        });

        Then("^I should get a decision granting access to read$", () -> {
            assertThat("responseEntity status code is not 200", responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat("responseEntity body is not true", responseEntity.getBody(), is(Boolean.TRUE));
        });

        When("^I request to read a higher level book (.*)$", (String book_id) -> {
            BookServiceFixture.stubBookServiceResponseForBook_BH1234_Rating15(book_id);
            HttpEntity httpEntity = new HttpEntity(generateHeader());
            responseEntity = restTemplate.exchange("/rcl/book/v1/read/eligibility/" +
                            this.customerSetControlLevel + "/" + book_id,
                    HttpMethod.GET, httpEntity, Boolean.class);
        });

        Then("^I should get a decision to deny access$", () -> {
            assertThat("responseEntity status code is not 200", responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat("responseEntity body is true", responseEntity.getBody(), is(Boolean.FALSE));
        });
    }

    private MultiValueMap<String, String> generateHeader() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        return headers;
    }

}
