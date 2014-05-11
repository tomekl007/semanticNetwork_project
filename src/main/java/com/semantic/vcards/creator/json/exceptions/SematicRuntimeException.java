package com.semantic.vcards.creator.json.exceptions;

import java.io.IOException;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
public class SematicRuntimeException extends RuntimeException {
    public SematicRuntimeException(String msg, IOException e) {
            super(msg,e);
    }
}
