package com.tdd.book.rating.control.config;

import com.tdd.book.rating.control.config.RatingControlServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatingControlServiceConfigTest {

    private static final String BOOK_SERVICE_URL = "https://my-third-party.service.com/fetch/book/rating/";

    @Autowired
    private RatingControlServiceConfig ratingControlService;

    @Test
    public void loadContext() { }

    @Test
    public void shouldLoadBookServiceEndPoint() {
        assertThat("Book Service Endpoint value is null", ratingControlService.getBookServiceEndpoint(),
                not(emptyOrNullString()));
        assertThat("Book Service Endpoint value mismatch", ratingControlService.getBookServiceEndpoint(),
                is(BOOK_SERVICE_URL));

    }

}