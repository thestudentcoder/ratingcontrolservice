package com.tdd.book.rating.control.service;

import com.tdd.book.rating.control.config.RatingControlServiceConfig;
import com.tdd.book.rating.control.exception.BookNotFoundException;
import com.tdd.book.rating.control.exception.TechnicalFailureException;
import com.tdd.book.rating.control.service.RatingControlServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class RatingControlServiceImplTest {

    private static final String VALID_URL_BOOK_SERVICE = "https://my-third-party.service.com/fetch/book/rating/{book_Id}";
    private static final String CUSTOMER_RATING_LEVEL_CODE_12 = "12";
    private static final String TEST_BOOK_ID = "M1211";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_12 = "12";

    private static final String CUSTOMER_RATING_LEVEL_CODE_U = "U";
    private static final String CUSTOMER_RATING_LEVEL_CODE_8 = "8";
    private static final String CUSTOMER_RATING_LEVEL_CODE_15 = "15";
    private static final String CUSTOMER_RATING_LEVEL_CODE_18 = "18";

    private static final String TEST_SAMPLE_BOOK_ID = "S1211";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_U = "U";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_8 = "8";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_15 = "15";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_18 = "18";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_XX = "XX";

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RatingControlServiceConfig ratingControlServiceConfig;

    private RatingControlServiceImpl ratingControlService;

    @Before
    public void setUp() throws Exception {
        Mockito.when(ratingControlServiceConfig.getBookServiceEndpoint()).thenReturn(VALID_URL_BOOK_SERVICE);
        ratingControlService = new RatingControlServiceImpl(restTemplate, ratingControlServiceConfig);
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturns12_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), any(), any(),
                Mockito.<Class<String>>any())).thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_12,
                HttpStatus.OK));

        assertTrue("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID));
    }

    @Test
    public void shouldReturnFalse_whenTechnicalFailureExceptionIsThrownFromBookService() throws Exception {
        // This will fail if there is nothing in the service code to handle the exception.
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(),
                Mockito.<Class<String>>any())).thenThrow(TechnicalFailureException.class);

        assertFalse("Read book eligibility is true", ratingControlService
                .canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID));
    }

    @Test(expected = BookNotFoundException.class)
    public void shouldReturnBookNotFoundExcepion_whenExceptionIsThrownFromBookServiceForBookNotFound() {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(),
                Mockito.<Class<String>>any())).thenThrow(BookNotFoundException.class);

        ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID);
    }

    // edge cases

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAsU_andCustomerProvidedRatingCodeIsU() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_U, HttpStatus.OK));

        assertTrue("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_U, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAsU_andCustomerProvidedRatingCodeIs8() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_U, HttpStatus.OK));

        assertTrue("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_8, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs8_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_8, HttpStatus.OK));

        assertTrue("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs12_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_12, HttpStatus.OK));

        assertTrue("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID));
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAs15_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_15, HttpStatus.OK));

        assertFalse("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAs18_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_18, HttpStatus.OK));

        assertFalse("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs15_andCustomerProvidedRatingCodeIs18() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_15, HttpStatus.OK));

        assertTrue("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_18, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAsXX_whichIsUnknown_andCustomerProvidedRatingCodeIs18() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_XX, HttpStatus.OK));

        assertFalse("Read book eligibility is false",
                ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_18, TEST_SAMPLE_BOOK_ID));
    }
}