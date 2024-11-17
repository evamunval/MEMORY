package edu.fje.memorygame;

import android.provider.BaseColumns;

public class DBStructure{
    private DBStructure(){}

    public static class DataEntry implements BaseColumns{
        public static final String TABLE_NAME = "scoreTable";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_PLAY_TIME = "play_time";
    }
}
