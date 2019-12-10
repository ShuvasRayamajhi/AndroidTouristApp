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
    public ActivityTestRule<Sign_Form> mActivityTestRule = new ActivityTestRule<Sign_Form>(Sign_Form.class);

    private Sign_Form mActivity = null; //reference to activity

    //Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MemberPage.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity(); //activity = get activity
    }

    @Test
    public void testLaunch()  //before test launch setUp will be executed, after test launch teardown
    {
        View view = mActivity.findViewById(R.id.tvMainText); //find view by id
        assertNotNull(view);
    }

   // @Test
//    public void testLaunceSecondActivity()
//    {
//       assertNotNull(mActivity.findViewById(R.id.register_btn));
//       onView(withId(R.id.register_btn)).perform(click());
//      Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
//      assertNotNull(secondActivity);
//      secondActivity.finish();
//
//    }

    @After
    public void tearDown() throws Exception {
        mActivity = null; //in the tear down nullified activity
    }
}