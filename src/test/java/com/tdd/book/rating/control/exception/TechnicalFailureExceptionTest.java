package com.tdd.book.rating.control.exception;

import com.tdd.book.rating.control.exception.TechnicalFailureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
public class TechnicalFailureExceptionTest {

    @Test
    public void shouldThrowTechnicalFailureExceptionWithMessage_whenTechnicalFailureIsThrown() {
        assertThatThrownBy(() -> {
            throw new TechnicalFailureException("System error");
        }).isInstanceOf(TechnicalFailureException.class)
                .hasMessageContaining("System error");
    }
}