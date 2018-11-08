package com.roje.game.core.exception;

import lombok.Getter;

public class RJException extends Exception {
    private static final long serialVersionUID = -278062790813397716L;
    @Getter
    private ErrorData errorData;


    public RJException(ErrorData errorData){
        super();
        this.errorData = errorData;
    }
}
