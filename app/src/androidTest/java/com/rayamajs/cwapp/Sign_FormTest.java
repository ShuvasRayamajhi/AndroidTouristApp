package com.rayamajs.cwapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class Sign_FormTest {

    @Rule //create test rule
    public ActivityTestRule<Sign_Form> mActivityTestRule = new ActivityTestRule<Sign_Form>(Sign_Form.class); //declare class name

    private Sign_Form mActivity = null; //reference to activity

    @Before
    public void setUp() throws Exception { //before test launch setUp will be executed,
        mActivity = mActivityTestRule.getActivity(); //activity = get activity
    }

    @Test
    public void testLaunch()   //the test to check the launch of register form
    {
        View view = mActivity.findViewById(R.id.tvMainText); //find view by id of the text view on the register page
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception { //after test launch this will be executed, tear down
        mActivity = null; //in the tear down nullified activity
    }
}