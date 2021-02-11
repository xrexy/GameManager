package org.shingetsu.gamemanager.files.files;

import org.shingetsu.gamemanager.files.AbstractYamlFile;

public class DataFile extends AbstractYamlFile {
    @Override
    public String getFileName() {
        return "data/data.yml";
    }

    @Override
    public boolean isResource() {
        return false;
    }
}
