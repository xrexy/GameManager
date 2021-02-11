package org.shingetsu.gamemanager.exceptions;

public class FileCreationFailedException extends Exception {
    public FileCreationFailedException() {
        super("Something went wrong while creating file!");
    }
}
