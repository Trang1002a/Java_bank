package com.example.dbsservice.exception;

public class ProjectException extends RuntimeException {

    private MessageException messageException;

    public ProjectException (String errorCode) {
        super(errorCode);
        this.messageException = MessageException.builder()
                .errorCode(errorCode)
                .errorDesc(errorCode)
                .message(MessageLoader.getMessage(errorCode))
                .build();
    }

    public ProjectException (String errorCode, String errorMessage) {
        super(errorCode);
        this.messageException = MessageException.builder()
                .errorCode("CLV-00-000")
                .errorDesc(errorCode)
                .message(new Message(errorMessage, null))
                .build();
    }

    public MessageException getMessageException() {
        return messageException;
    }
}
