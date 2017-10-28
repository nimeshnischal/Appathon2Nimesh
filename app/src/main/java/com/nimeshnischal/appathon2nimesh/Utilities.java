package com.nimeshnischal.appathon2nimesh;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Nimesh Nischal on 15-10-2017.
 */

public class Utilities {
    static final int NONE_SELECTED = -1;
    static final int TRIVIA = 0;
    static final int MATH = 1;
    static final int DATE = 2;
    static final int YEAR = 3;

    static final int RANDOM = 4;
    static final int QUEST = 5;

    //static final String SELECTED_OPTION = "selected_option";
    static final String RANDOM_OR_QUEST = "random_or_quest";
    static final String URL = "url";

    static final String RANDOM_TRIVIA_URL = "http://numbersapi.com/random/trivia";
    static final String RANDOM_MATH_URL = "http://numbersapi.com/random/math";
    static final String RANDOM_DATE_URL = "http://numbersapi.com/random/date";
    static final String RANDOM_YEAR_URL = "http://numbersapi.com/random/year";

    static final String QUEST_TRIVIA_URL = "http://numbersapi.com/*/trivia";
    static final String QUEST_MATH_URL = "http://numbersapi.com/*/math";
    static final String QUEST_DATE_URL = "http://numbersapi.com/*/date";
    static final String QUEST_YEAR_URL = "http://numbersapi.com/*/year";

    static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
