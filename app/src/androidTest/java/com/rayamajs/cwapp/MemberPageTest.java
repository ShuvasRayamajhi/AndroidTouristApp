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

public class MemberPageTest {

    @Rule
    public ActivityTestRule<MemberPage> mActivityTestRule = new ActivityTestRule<MemberPage>(MemberPage.class); //declare the main activity you want to set
    private MemberPage mActivity = null;

    //declare the main activities inside  activity
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(review.class.getName(),null,false); //creating a monitor
    Instrumentation.ActivityMonitor monitor3 = getInstrumentation().addMonitor(MapsActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor4 = getInstrumentation().addMonitor(TextToSpeech.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor5 = getInstrumentation().addMonitor(ImageText.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch()
    {
        View view = mActivity.findViewById(R.id.tvMainText);//check the main activity works
        assertNotNull(view);
    }

    @Test
    public void testLaunceNotes()
    {
        assertNotNull(mActivity.findViewById(R.id.review_btn)); //check the main activity opens
        onView(withId(R.id.review_btn)).perform(click()); //use espresso method and click the button
        Activity noteActivity = getInstrumentation().waitForMonitorWithTimeout(monitor2, 5000); //use monitor to ensure that the activity opened
        assertNotNull(noteActivity);
        noteActivity.finish();
    }

    @Test
    public void testLaunchMaps()
    {
        assertNotNull(mActivity.findViewById(R.id.location_btn));
        onView(withId(R.id.location_btn)).perform(click());
        Activity mapsActivity = getInstrumentation().waitForMonitorWithTimeout(monitor3, 5000);
        assertNotNull(mapsActivity);
        mapsActivity.finish();
    }

    @Test
    public void testLaunchToSpeech()
    {
        assertNotNull(mActivity.findViewById(R.id.speech_btn));
        onView(withId(R.id.speech_btn)).perform(click());
        Activity speechActivity = getInstrumentation().waitForMonitorWithTimeout(monitor4, 5000);
        assertNotNull(speechActivity);
        speechActivity.finish();
    }
    public void testLaunchImage()
    {
        assertNotNull(mActivity.findViewById(R.id.btn_image));
        onView(withId(R.id.btn_image)).perform(click());
        Activity imageActivity = getInstrumentation().waitForMonitorWithTimeout(monitor5, 5000);
        assertNotNull(imageActivity);
        imageActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}