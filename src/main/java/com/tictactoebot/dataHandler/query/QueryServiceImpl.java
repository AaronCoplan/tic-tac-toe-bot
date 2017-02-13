package com.tictactoebot.dataHandler.query;

import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.read.DataReader;
import com.tictactoebot.dataHandler.read.DataReaderImpl;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public class QueryServiceImpl implements QueryService {

    private final DataReader dataReader;

    public QueryServiceImpl() throws StorageAccessException {

        this.dataReader = new DataReaderImpl();
    }

    @Override
    public int getNextGameNumber(){
        List<String> moveFileNames = dataReader.getMoveFileNames();
        for(String s : moveFileNames){
            System.out.println(s);
        }
        System.out.println();

        if(moveFileNames.size() == 0) return 1;

        Collections.sort(moveFileNames);

        for(String s : moveFileNames){
            System.out.println(s);
        }
        System.out.println();

        String fileName  = moveFileNames.get(moveFileNames.size() - 1);
        int gameNumber = FileNameParser.getGameNumber(fileName); // the highest game number currently saved on disk

        return gameNumber + 1; // return the next game number in the sequence
    }

    @Override
    public List<File> getMoveFileList(){
        return dataReader.getMoveFileList();
    }
}
