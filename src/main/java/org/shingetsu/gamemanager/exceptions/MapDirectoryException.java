package org.shingetsu.gamemanager.exceptions;

public class MapDirectoryException extends Exception{
    public MapDirectoryException() {
        super("Something went wrong while parsing information for maps folder.");
    }
}
