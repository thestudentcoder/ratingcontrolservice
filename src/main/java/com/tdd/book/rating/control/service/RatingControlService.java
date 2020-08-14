package com.tdd.book.rating.control.service;

public interface RatingControlService {

    boolean canReadBook(String customerRatingControlLevel, String bookId);
}
