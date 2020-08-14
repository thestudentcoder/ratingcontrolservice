package com.tdd.book.rating.control.common;

import com.tdd.book.rating.control.common.RatingLevels;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class RatingLevelsTest {

    private Map<String, Integer> actualRatingCodeMap = RatingLevels.RATING_CODE_LEVEL;

    @Test
    public void shouldReturnParentalCodeLevelsBasedOnRequestedParentalCode() {
        assertThat("Mismatch rating code level order for U", actualRatingCodeMap.get("U"),
                IsEqual.equalTo(0));
        assertThat("Mismatch rating code level order for 8", actualRatingCodeMap.get("8"),
                IsEqual.equalTo(1));
        assertThat("Mismatch rating code level order for 12", actualRatingCodeMap.get("12"),
                IsEqual.equalTo(2));
        assertThat("Mismatch rating code level order for 15", actualRatingCodeMap.get("15"),
                IsEqual.equalTo(3));
        assertThat("Mismatch rating code level order for 18", actualRatingCodeMap.get("18"),
                IsEqual.equalTo(4));
    }

}