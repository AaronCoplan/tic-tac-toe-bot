package com.tictactoebot.dataHandler.dao;

import com.tictactoebot.dataHandler.model.TestEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by afcoplan on 2/22/17.
 */
@Transactional
public interface TestEntityDao extends CrudRepository<TestEntity, Long> {

    List<TestEntity> findAll();
}
