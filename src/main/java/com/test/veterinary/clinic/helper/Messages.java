package com.test.veterinary.clinic.helper;

import org.springframework.context.MessageSource;

import java.util.Locale;

public abstract class Messages {
    public static String getMessage(MessageSource messageSource, String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }
}
