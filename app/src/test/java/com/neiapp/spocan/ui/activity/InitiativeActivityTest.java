package com.neiapp.spocan.ui.activity;

import android.graphics.Bitmap;
import com.neiapp.spocan.util.Base64Converter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.neiapp.spocan.ui.activity.InitiativeActivity.controlImageSize;


@RunWith(RobolectricTestRunner.class)
public class InitiativeActivityTest {

    private  String leerBase64() {
        String  resultado = "";

        try {
            File myObj = new File("/home/l30/corazonBase64");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                resultado = resultado + data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return  resultado;
    }
    @Test
    public void controlImageSizeTest(){

        Bitmap bmp = Base64Converter.base64ToBitmap(leerBase64());
        final Bitmap resultingBitmap = controlImageSize(bmp);

        assertTrue(bmp.getHeight() > resultingBitmap.getHeight());
        assertTrue(bmp.getWidth() > resultingBitmap.getWidth());
    }
}