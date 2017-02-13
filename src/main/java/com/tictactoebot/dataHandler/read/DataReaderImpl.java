package com.tictactoebot.dataHandler.read;

import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.StorageAccessException;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public class DataReaderImpl implements DataReader {

    private final File storageDirectory;
    private final FileFilter hiddenFileFilter;

    public DataReaderImpl() throws StorageAccessException{

        // instantiate a filter to ignore hidden files when accessing the data
        this.hiddenFileFilter = new FileFilter(){
            @Override
            public boolean accept(File file){
                // both of these conditions are necessary because a hidden file could end with this extension
                return !file.isHidden() && file.getName().endsWith(DataHandler.FILE_EXTENSION);
            }
        };

        this.storageDirectory = new File(DataHandler.DIRECTORY_PATH);

        // check to ensure it can access the data, if not, throw an exception
        if(storageDirectory == null || !storageDirectory.exists()){
            throw new StorageAccessException("Error accessing move data files!");
        }
    }

    /*
     *  TODO: IMPLEMENT CACHING -- this will greatly improve runtime
     */

    @Override
    public List<File> getMoveFileList(){
        File[] moveFiles = storageDirectory.listFiles(hiddenFileFilter);
        return new ArrayList<File>(Arrays.asList(moveFiles));
    }
}
