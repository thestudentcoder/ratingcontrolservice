package com.tdd.book.rating.control.exception;

import com.tdd.book.rating.control.exception.BookNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
public class BookNotFoundExceptionTest {

    @Test
    public void shouldThrowBookNOtFoundExceptionWithMessage_whenBookIsNotFound() {
        // After this test passes, our service implementation is ready
        // and we can move on to writing test cases for exceptions in our service class
        assertThatThrownBy(() -> {
           throw new BookNotFoundException("Book not found");
        }).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

}