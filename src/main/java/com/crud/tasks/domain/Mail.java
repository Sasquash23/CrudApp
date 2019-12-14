package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
    private String mailTo;
    private String mailCc;
    private String subject;
    private String message;

    public Mail(String mailTo, String subject, String message) {
        this(mailTo, null, subject, message);
    }
}
