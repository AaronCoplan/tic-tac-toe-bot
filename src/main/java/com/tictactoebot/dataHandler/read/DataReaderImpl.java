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

    private final FileFilter moveFileFilter;
    private final FileFilter resultFileFilter;

    public DataReaderImpl() throws StorageAccessException{

        // instantiate a filter to ignore hidden files when accessing the data
        this.moveFileFilter = new FileFilter(){
            @Override
            public boolean accept(File file){
                // both of these conditions are necessary because a hidden file could end with this extension
                return !file.isHidden() && file.getName().endsWith(DataHandler.FILE_EXTENSION);
            }
        };

        this.resultFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file){
                return !file.isHidden() && file.getName().endsWith(DataHandler.RESULT_EXTENSION);
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
        File[] moveFiles = storageDirectory.listFiles(moveFileFilter);
        return new ArrayList<File>(Arrays.asList(moveFiles));
    }

    @Override
    public List<String> getMoveFileNames(){
        List<File> moveFiles = this.getMoveFileList();

        List<String> names = new ArrayList<String>();
        for(File f : moveFiles){
            names.add(f.getName());
        }

        return names;
    }

    @Override
    public List<String> getResultFileNames(){
        List<File> resultFiles = this.getResultFileList();

        List<String> names = new ArrayList<String>();
        for(File f : resultFiles){
            names.add(f.getName());
        }

        return names;
    }

    @Override
    public List<File> getResultFileList(){
        File[] resultFiles = storageDirectory.listFiles(resultFileFilter);
        return new ArrayList<File>(Arrays.asList(resultFiles));
    }
}
