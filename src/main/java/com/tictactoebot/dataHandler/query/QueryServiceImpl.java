package com.tictactoebot.dataHandler.query;

import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.read.DataReader;
import com.tictactoebot.dataHandler.read.DataReaderImpl;

import java.io.File;
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
    public int getGameCount(){
        List<File> files = dataReader.getMoveFileList();

        System.out.println(files.size());

        // TODO: this needs actually implemented instead of returning zero

        return 0;
    }
}
