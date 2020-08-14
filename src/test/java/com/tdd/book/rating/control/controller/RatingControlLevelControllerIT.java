package com.tdd.book.rating.control.controller;

import com.tdd.book.rating.control.exception.BookNotFoundException;
import com.tdd.book.rating.control.service.RatingControlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RatingControlLevelControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingControlService ratingControlService;

    @Test
    public void shouldReturnNotFound_whenCustomerControlLevelAndBookIdNotProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequest_whenInvalidCustomerControlLevelAndBookIdIsProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility/CONTROLXXXX*/ID**")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnTrue_whenBookServiceControlLevelIsEqualTo_CustomerRatingControlLevel_ForRequestedBookId()
            throws Exception {
        // Service implementation is needed here to pass this test
        // We ned to mock the service because IT tests should care if your service is implemented or not.
        // After this test passes, we have to move on to our service test now because our service is not implemented.
        // Acceptance test -> Integration Test -> Service Test (JUnit) (We will have one scenario end to end working)
        given(ratingControlService.canReadBook(anyString(), anyString())).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility/12/B1234")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void shouldINoeRatingControlService_whenValidCustomerRatingControlLevelAndBookIsProvided() throws Exception {
        // This test verifies service invocation when the controller method is invoked.
        // Just in case someone changes the service class by mistake.
        given(ratingControlService.canReadBook(anyString(), anyString())).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility/12/B1234")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(ratingControlService).canReadBook(anyString(), anyString());
    }

    @Test
    public void shouldReturnNotFound_whenBookNotFoundExceptionIsThrown() throws Exception {
        // fail initially because you are getting the exception bot not communicating to the customer
        // in order to make this pass, we have to communicate to the customer than the book was not found
        // We can get this test to pass using controller advice
        given(ratingControlService.canReadBook(anyString(), anyString()))
                .willThrow(new BookNotFoundException("Book not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility/12/B1234")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"code\":\"404\",\"message\":\"Book not found\"}"));
    }
}
