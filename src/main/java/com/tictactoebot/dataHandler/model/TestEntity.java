package com.tictactoebot.dataHandler.model;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

/**
 * Created by afcoplan on 2/22/17.
 */

@Entity
@Table(name = "test")
public class TestEntity {

    @Id
    @GeneratedValue
    @Column(name = "test_id")
    private Integer testId;

    @NotNull
    @Column(name = "name")
    private String name;

    public TestEntity(){}

    public TestEntity(String name){
        this.name = name;
    }

    public Integer getTestId(){
        return testId;
    }

    public void setTestId(Integer testId){
        this.testId = testId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
