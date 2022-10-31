package com.shop.exception;

/**
 * 재고 부족시 발생하는 예외 처리
 */
public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String message) {
        super(message);
    }
}
