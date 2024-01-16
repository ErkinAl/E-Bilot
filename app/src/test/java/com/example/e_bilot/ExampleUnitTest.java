package com.example.e_bilot;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;
import android.view.View;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // DENİZ BİLGİN
    @Test
    public void isSeatValidTest(){
        SeatChoosingFragment seatChoosingFragment = new SeatChoosingFragment();
        MovieGetter movieGetter = new MovieGetter();

        movieGetter.getMovieById("movies", "1", new MovieGetter.MovieGetterCallback() {
            @Override
            public View onMovieReceived(Movie movie) {
                Boolean result = seatChoosingFragment.isSelectedSeatValid("A1,A2", movie);
                assertTrue(result);
                return null;
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("UnitTest", errorMessage);
            }
        });
    }
}