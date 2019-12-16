package com.example.notepads;

import android.provider.BaseColumns;

public class Notes {
    //创建一个合约类，在合约类中定义表名，列名等特征，减少模块间的耦合度
    public Notes(){}
    static abstract class Note implements BaseColumns{
        static final String TABLE_NAME = "notes";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_CONTEXT = "context";
        static final String COLUMN_NAME_TIME = "time";
    }
}

