package io.github.dinosage.agilityrush.util;

public class KeyValue<T> {

    String key;
    T value;

    public KeyValue(String key, T value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public T getValue(){
        return value;
    }

    public void setValue(T value){
        this.value = value;
    }
}
