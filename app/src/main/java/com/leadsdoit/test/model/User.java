package com.leadsdoit.test.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.lang.reflect.GenericArrayType;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long balance;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
