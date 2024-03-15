package com.example.etsiitgo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class GusiAssistant implements TextToSpeech.OnInitListener, RecognitionListener {

    private static final String DEBUG_TAG = "GusiAssistantDEBUG";

    private static final int REQUEST_TTS_PERMISSION = 1;
    private static final int REQUEST_STT_PERMISSION = 2;

    public static final Locale LANGUAGE_SPANISH = new Locale("es","ES");
    public static final Locale LANGUAGE_ENGLISH = Locale.ENGLISH;
    public static final Locale LANGUAGE_FRENCH = Locale.FRENCH;

    public static final int REPEAT_INSTRUCTIONS_THRESHOLD = 5;

    private boolean enabled = true;

    private static GusiAssistant instance;
    private final Context context;
    private final TextToSpeech textToSpeech;
    private boolean isTextToSpeechReady = false;
    private OnSpeechRecognitionListener speechRecognitionListener;

    // Private constructor to prevent instantiation outside of the class
    private GusiAssistant(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, this);
    }

    // Singleton getInstance method
    public static synchronized GusiAssistant getInstance(Context context) {
        if (instance == null) {
            instance = new GusiAssistant(context);
        }
        return instance;
    }

    // Method to handle permissions for TTS and STT
    public void requestPermissions(Activity activity) {
        // Check TTS permission
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.INTERNET},
                    REQUEST_TTS_PERMISSION);
        }

        // Check STT permission
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_STT_PERMISSION);
        }
    }

    // Method to set the language for Text-to-Speech
    public void setLanguage(Locale locale) {
        if (textToSpeech != null && isTextToSpeechReady) {
            int result = textToSpeech.setLanguage(locale);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle language not supported or missing data
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean value) {
        enabled = value;
    }

    // Method to read a String aloud
    public void speak(String text) {

        if (!enabled) return;

        if (isTextToSpeechReady) {
            // Using the default speech queue and adding a unique identifier to the utterance
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "unique-id");
        }
    }

    // Method to start speech recognition
    public void startSpeechRecognition() {
        if (!enabled) return;

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        context.startActivity(intent);
    }

    // TextToSpeech OnInitListener method
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.d(DEBUG_TAG,"Init");
            int result = textToSpeech.setLanguage(LANGUAGE_SPANISH);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle language not supported or missing data
            } else {
                isTextToSpeechReady = true;
            }
        } else {
            // Handle initialization failure
        }
    }

    // RecognitionListener methods
    @Override
    public void onBeginningOfSpeech() {
        // Called when the user starts to speak
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        // Called when sound has been received
    }

    @Override
    public void onEndOfSpeech() {
        // Called when the user stops speaking
    }

    @Override
    public void onError(int error) {
        // Called when there is an error in speech recognition
        speechRecognitionListener.onError("Speech recognition error: " + error);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        // Called when partial recognition results are available
    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // Called when the recognition engine is ready for speech input
    }

    @Override
    public void onResults(Bundle results) {
        // Called when recognition results are available
        ArrayList<String> matches = results.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);
            speechRecognitionListener.onSpeechRecognized(recognizedText);
        } else {
            speechRecognitionListener.onError("No speech recognition results");
        }
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        // Called when the RMS (Root Mean Square) value of the audio changes
    }

    // Cleanup method to release resources
    public void cleanup() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        instance = null;
    }

    // Interface for speech recognition callbacks
    public interface OnSpeechRecognitionListener {
        void onSpeechRecognized(String recognizedText);
        void onError(String errorMessage);
    }
}
