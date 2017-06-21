/*
MIT License

Copyright (c) 2017 Aaron Coplan
Copyright (c) 2017 Andrew Adalian
Copyright (c) 2017 Devin Kopp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.tictactoebot.dataHandler.read;

import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.query.FileNameParser;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * Created by afcoplan on 2/12/17.
 */
public class DataReaderImpl implements DataReader {

    private final File storageDirectory;

    private final FileFilter moveFileFilter;
    private final FileFilter resultFileFilter;
    private final FileFilter statsFileFilter;

    public DataReaderImpl() throws StorageAccessException{

        // instantiate a filter to ignore hidden files when accessing the data
        this.moveFileFilter = new FileFilter(){
            @Override
            public boolean accept(File file){
                // both of these conditions are necessary because a hidden file could end with this extension
                return file.getName().endsWith(DataHandler.FILE_EXTENSION) && !file.isHidden();
            }
        };

        this.resultFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file){
                return file.getName().endsWith(DataHandler.RESULT_EXTENSION) && !file.isHidden();
            }
        };

        this.statsFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file){
                return !file.isHidden() && file.getName().endsWith(DataHandler.STATS_EXTENSION);
            }
        };

        this.storageDirectory = new File(DataHandler.DIRECTORY_PATH);

        // check to ensure it can access the data, if not, throw an exception
        if(storageDirectory == null || !storageDirectory.exists()){
            throw new StorageAccessException("Error accessing getMove data files!");
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
    public List<File> getResultFileList(){
        File[] resultFiles = storageDirectory.listFiles(resultFileFilter);
        return new ArrayList<File>(Arrays.asList(resultFiles));
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
    public List<String> getResultFilesFromSearchTerm(String searchTerm){
        FileFilter searchTermFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return  file.getName().contains(searchTerm) && file.getName().endsWith(DataHandler.RESULT_EXTENSION) && !file.isHidden();
            }
        };

        File[] resultFiles = storageDirectory.listFiles(searchTermFilter);
        return fileListToStringList(resultFiles);
    }

    @Override
    public List<String> getMoveFilesFromSearchTerm(String searchTerm){
        FileFilter searchTermFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return  file.getName().contains(searchTerm) && file.getName().endsWith(DataHandler.FILE_EXTENSION) && !file.isHidden();
            }
        };

        File[] resultFiles = storageDirectory.listFiles(searchTermFilter);
        return fileListToStringList(resultFiles);
    }

    private List<String> fileListToStringList (File[] files){
        List<String> strings = new ArrayList<>();

        for (File i : files){
            strings.add(i.getName());
        }

        return strings;
    }


    @Override
    public int getNumGamesFromBoardHash(String boardHash){
        final String searchString = "_" + boardHash;
        int totalGames = 0;
        FileFilter searchTermFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return  file.getName().contains(searchString) && file.getName().endsWith(DataHandler.FILE_EXTENSION) && !file.isHidden();
            }
        };

        HashMap<Integer, Boolean> alreadyFoundGames = new HashMap<>();
        File[] resultFiles = storageDirectory.listFiles(searchTermFilter);

        int currentGameNumber;
        if(resultFiles != null) {
            for (int i = 0; i < resultFiles.length; i++) {
                currentGameNumber = FileNameParser.getGameNumber(resultFiles[i].getName());

                if(alreadyFoundGames.get(currentGameNumber) == null){
                    alreadyFoundGames.put(currentGameNumber, true);

                    totalGames++;
                }
            }

            return totalGames;
        } else {
            return 0;
        }
    }

    @Override
    public HashMap<Integer, Boolean> getHashMapGamesFromBoardHash(String boardHash){
        final String searchString = "_" + boardHash;
        FileFilter searchTermFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return  file.getName().contains(searchString) && file.getName().endsWith(DataHandler.FILE_EXTENSION) && !file.isHidden();
            }
        };

        HashMap<Integer, Boolean> alreadyFoundGames = new HashMap<>();
        File[] resultFiles = storageDirectory.listFiles(searchTermFilter);

        int currentGameNumber;
        if(resultFiles != null) {
            for (int i = 0; i < resultFiles.length; i++) {
                currentGameNumber = FileNameParser.getGameNumber(resultFiles[i].getName());

                alreadyFoundGames.putIfAbsent(currentGameNumber, true);
            }

            return alreadyFoundGames;
        } else {
            return null;
        }
    }

    /*public int getNumGamesFromBoardHash(String boardHash){
        final String searchString = "_" + boardHash;
        FileFilter searchTermFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return  file.getName().contains(searchString) && file.getName().endsWith(DataHandler.FILE_EXTENSION) && !file.isHidden();
            }
        };

        HashMap<Integer, Boolean> alreadyFoundGames = new HashMap<>();
        File[] resultFiles = storageDirectory.listFiles(searchTermFilter);

        int currentGameNumber;
        if(resultFiles != null) {
            for (int i = 0; i < resultFiles.length; i++) {
                currentGameNumber = FileNameParser.getGameNumber(resultFiles[i].getName());

                if(alreadyFoundGames.get(currentGameNumber) == null){
                    alreadyFoundGames.put(currentGameNumber, true);
                    this.getResultFilesFromSearchTerm();
                }
            }

            return alreadyFoundGames;
        } else {
            return null;
        }
    }*/

    @Override
    public String getStatsFileName(){
        File[] files = storageDirectory.listFiles(statsFileFilter);

        if(files.length == 0){
            return null;
        }else if(files.length == 1){
            return files[0].getName();
        }else{
            System.out.println("INVALID NUMBER OF STATS FILES!");
            System.exit(-1);
            return null;
        }
    }
}
