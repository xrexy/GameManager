package org.shingetsu.gamemanager.files;

import org.shingetsu.gamemanager.GameManager;
import org.shingetsu.gamemanager.files.files.DataFile;
import org.shingetsu.gamemanager.maps.MapManager;

import java.util.HashMap;

public class FileAPI {
    private final HashMap<String, AbstractYamlFile> files = new HashMap<>();

    /**
     * Adds file to local library
     *
     * @param file File to save
     */
    private void saveFile(AbstractYamlFile file) {
        if (!files.containsKey(file.getFileName())) files.put(file.getFileName(), file);
    }

    /**
     * Loops through all files and creates them.
     * <br><br>
     * <b>Must be loaded and saved with method "loadFiles()"</b>
     */
    public void createAllFiles() {
        files.forEach((s, file) -> file.create());
    }

    /**
     * Loads and saves all mentioned files
     * <br><br>
     * After creating a new file <b>(Must extend "AbstractYamlFile")</b>
     * you must save the file from this method
     */
    public void loadFiles() {
        saveFile(new DataFile());
        saveFile(GameManager.getMapManager());
    }


    /**
     * @return All registered files
     */
    public HashMap<String, AbstractYamlFile> getFiles() {
        return files;
    }
}
