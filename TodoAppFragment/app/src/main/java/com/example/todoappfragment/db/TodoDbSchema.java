package com.example.todoappfragment.db;

public class TodoDbSchema {

    public static final class TodoTable {
        public static final String NAME = "todos";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final  String TITLE = "title";
            public static final  String DETAIL = "detail";
            public static final  String DATE = "date";
            public static final  String IS_COMPLETE = "isComplete";
        }
    }
}
