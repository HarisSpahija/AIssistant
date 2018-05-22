package nl.spahija.dialogflow;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener {
    AIService aiService;
    TextView textAI;
    TextView textClient;
    private Button mButtonSpeak;
    private TextToSpeech ttsAI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textClient = findViewById(R.id.textClient);
        textAI = findViewById(R.id.textAI);
        final AIConfiguration config = new AIConfiguration("5d864df926f344eeb4a1055dac0624e9",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        mButtonSpeak = findViewById(R.id.button_speak);

        ttsAI = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = ttsAI.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported.");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed.");
                }
            }
        });

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    public void buttonClicked(View view) {
        aiService.startListening();
    }

    @Override // here process response
    public void onResult(AIResponse result) {

        Log.d("spahija", result.toString());
        Result result1 = result.getResult();
        textClient.setText(result1.getResolvedQuery());
        textAI.setText(result1.getFulfillment().getSpeech());
    }

    private void speak() {
        String text = textAI.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsAI.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            ttsAI.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

        @Override // here process error
    public void onError(AIError error) {

    }

    @Override // callback for sound level visualization
    public void onAudioLevel(float level) {

    }

    @Override // indicate start listening here
    public void onListeningStarted() {

    }

    @Override // indicate stop listening here
    public void onListeningCanceled() {

    }

    @Override // indicate stop listening here
    public void onListeningFinished() {
    }
}