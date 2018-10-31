package com.karaush.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dump")
public class Dump {

    @Id
    Long id;

    private int data;
    public Dump(){
        this.data = 5;

    }
}
