package com.openclassrooms.safetynetalert.exception;

import java.io.IOException;

public class FileNotReadException extends RuntimeException {
    public FileNotReadException(IOException e) {

          super(e);
          // invoque constructeur de la classe parent
    }
}
