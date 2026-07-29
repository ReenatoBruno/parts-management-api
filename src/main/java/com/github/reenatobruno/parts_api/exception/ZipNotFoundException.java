package com.github.reenatobruno.parts_api.exception;

public class ZipNotFoundException extends RuntimeException {
    public ZipNotFoundException(String zip) {
      super("Zip code not found: " + zip);
    }
}
