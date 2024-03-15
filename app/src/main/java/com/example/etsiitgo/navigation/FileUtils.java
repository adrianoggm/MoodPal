package com.example.etsiitgo.navigation;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String readFileFromAssets(Context context, String filename) {
        AssetManager assetManager = context.getAssets();

        try {
            // Open the file
            InputStream inputStream = assetManager.open(filename);

            // Read the content of the file
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }

            // Close the streams
            bufferedReader.close();
            inputStream.close();

            // Return the content of the file
            return content.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if there's an error
        }
    }
}
