package com.openclassrooms.safetynetalert.exception;

import java.io.IOException;

public class FileNotReadException extends Exception {
    public FileNotReadException(IOException e) {

          super(e);

    }
}
