package com.example.parkinglot.util;

public class Constants {

    public static final double NONE_DISTANCE = 999999999;  // 거리 정보가 없음 (정렬시 하단에 표시하기 위함)

    /* 로컬 DB 테이블 이름 */
    public static class DataBaseTableName {
        public static final String HISTORY = "History";
        public static final String BOOKMARK = "Bookmark";
    }

    /* 로딩 딜레이 */
    public static class LoadingDelay {
        public static final int SHORT = 500;
        public static final int LONG = 1000;
    }
}
