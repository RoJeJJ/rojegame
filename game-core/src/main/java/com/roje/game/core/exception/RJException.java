package com.roje.game.core.exception;

import lombok.Getter;

public class RJException extends Exception {
    @Getter
    private ErrorData errorData;


    public RJException(ErrorData errorData){
        super();
        this.errorData = errorData;
    }
}
