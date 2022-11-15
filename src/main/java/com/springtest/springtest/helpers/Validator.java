package com.springtest.springtest.helpers;

import com.springtest.springtest.model.User;



public class Validator {

    private static final int MIN_LENGTH = 3;

    public Validator() {
    }

    public static void validateUser(User user) {
        validateNameLenght(user.getName());
        validateNameStartWithAlphabet(user.getName());
        validateAge(user.getAge());
    }

    private static void validateNameLenght(String name) {
        if (name.length() <= MIN_LENGTH) {
            throw new IllegalArgumentException("Lenght of Name must be min. " + MIN_LENGTH);
        }
    }

    private static void validateNameStartWithAlphabet(String name) {
        if (!Character.isAlphabetic(name.charAt(0))) {
            throw new IllegalArgumentException("Name must start with letter.");
        }
    }

    private static void validateAge(int age){
        if (age <= 0 || age >= 110){
            throw new IllegalArgumentException("Age must be over 0 and under 110");
        }
    }
}
