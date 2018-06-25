package org.nusabit.bidan;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void onCreate() {
    }

//    @Ignore
//    @Test
//    public void bar() {
//        fail("Not yet implemented!");
//    }

    @Test
    public  void bar()
    {
        MainActivity bar = new MainActivity();
        assertEquals("bar called", bar.bar());
    }

    @Test
    public  void foobar()
    {
        MainActivity main = new MainActivity();
        assertEquals("foo bar called", main.fooBar());
    }

}