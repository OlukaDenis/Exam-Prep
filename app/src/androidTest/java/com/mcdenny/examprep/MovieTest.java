package com.mcdenny.examprep;

import com.mcdenny.examprep.model.Movie;

import org.junit.BeforeClass;

public class MovieTest {
    static Movie movie;

    @BeforeClass
    public static void setUp(){
        movie = new Movie();
    }
}
