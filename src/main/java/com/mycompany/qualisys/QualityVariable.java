package com.mycompany.qualisys;

public class QualityVariable {

    String id;
    float value;

    public QualityVariable(String id, float value){
        this.id=id;
        this.value=value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
