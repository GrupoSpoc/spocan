package com.neiapp.spocan.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class Base64ConverterTest {

    @Test
    public void testBitmapToBase64() {
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);
        final String base64 = Base64Converter.bitmapToBase64(bitmap);
        assertFalse(TextUtils.isEmpty(base64));
    }

    @Test
    public void testBase64ToBitmap() {
        final String base64 = "Qml0bWFwICgxMCB4IDEwKSBjb21wcmVzc2VkIGFzIFBORyB3aXRoIHF1YWxpdHkgMTAw";
        Bitmap bitmap = Base64Converter.base64ToBitmap(base64);
        assertNotNull(bitmap);
    }
}