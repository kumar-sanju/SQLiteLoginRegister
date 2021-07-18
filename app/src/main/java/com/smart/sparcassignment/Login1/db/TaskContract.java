package com.smart.sparcassignment.Login1.db;

import android.provider.BaseColumns;

public class TaskContract { //task contract class defines constants which are used to access the data in the database
    // Helper class TaskDbHelper is nedded to open the database
    public static final String DB_NAME = "com.smart.sparcassignment.Login1.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns{
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DESCRIPTION = "description";
    }
}
