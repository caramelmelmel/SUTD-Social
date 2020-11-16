package com.example.sutd_social;

public class BillBoard {
    private static final BillBoard ourInstance = new BillBoard();

    static BillBoard getInstance() {
        return ourInstance;
    }

    private BillBoard() {
    }
}
