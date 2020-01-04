package com.mcdenny.examprep;

import com.mcdenny.examprep.model.Movie;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MovieTest {
    static Movie movie;

    @BeforeClass
    public static void setUp(){
        movie = new Movie();
    }

    @Test
    public void adding_a_new_movie(){
        movie = new Movie();
        final String title = "6 Underground";
        final double popularity = 20.5;
        movie.setTitle(title);
        movie.setPopularity(popularity);

        assertEquals(title, movie.getTitle());
        assertEquals(popularity, movie.getPopularity());
    }
}
