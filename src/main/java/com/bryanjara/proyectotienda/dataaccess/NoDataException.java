package com.bryanjara.proyectotienda.dataaccess;

public class NoDataException extends Exception {
    public NoDataException() {
    }

    public NoDataException(String msg) {
        super(msg);
    }
}
