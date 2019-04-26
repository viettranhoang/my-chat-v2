package com.vit.mychat.data.model;

import java.io.Serializable;

public class Tesst implements Serializable {

    private String name;

    public Tesst() {
    }

    public Tesst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
