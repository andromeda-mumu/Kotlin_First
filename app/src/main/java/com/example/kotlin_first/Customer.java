package com.example.kotlin_first;

/**
 * Created by mumu on 2020/5/19.
 * 功能描述：
 */
public class Customer {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer(String name) {
        this.name = name;
    }

    private String name;
}

