package org.nusabit.vaksin;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public  void foo()
    {
        MainActivity foo = new MainActivity();
        assertEquals("foo called", foo.foo());
    }
}