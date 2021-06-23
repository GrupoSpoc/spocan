package com.neiapp.spocan.ui.activity;

import android.graphics.Bitmap;
import com.neiapp.spocan.backend.MockedImagesBase64;
import com.neiapp.spocan.util.Base64Converter;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.neiapp.spocan.ui.activity.InitiativeActivity.controlImageSize;


@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class InitiativeActivityTest extends TestCase {

    @Test
    public void controlImageSizeTest(){
        String base64 = MockedImagesBase64.RED_800_X_600;

        Bitmap bmp = Base64Converter.base64ToBitmap(base64);
        final Bitmap resultingBitmap = controlImageSize(bmp);

        assertTrue(bmp.getHeight() > resultingBitmap.getHeight());
        assertTrue(bmp.getWidth() > resultingBitmap.getWidth());
    }
}