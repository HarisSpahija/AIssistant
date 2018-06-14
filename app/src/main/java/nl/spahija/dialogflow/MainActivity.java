package nl.spahija.dialogflow;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import java.util.Locale;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener {

    AIService aiService;
    TextView textAI;
    TextView textClient;
    ImageView button_record;

    //TODO: editText is not functional
    EditText editText;

    public boolean recording = false;
    private Button mButtonSpeak;

    //Text Box contains this ttsAI
    private TextToSpeech ttsAI;

    private static final String tagDialogFlow = "DialogFlow: ";
    private static final String tagTextToSpeech = "TextToSpeech: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textClient = findViewById(R.id.textClient);
        textAI = findViewById(R.id.textAI);
        button_record = findViewById(R.id.button_record);

        final AIConfiguration config = new AIConfiguration("5d864df926f344eeb4a1055dac0624e9",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        /**[ttsAI] create new Text to speech client.
         *
         */
        //Text to speech init
        ttsAI = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            /**When the client gets initialized. Use context to set the string to text to speech
             *
             */
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = ttsAI.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(tagTextToSpeech, "Language not supported.");
                    } else {
//                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e(tagTextToSpeech, "Initialization failed.");
                }
            }
        });

        //Repeat speech on textAI press
        textAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    //RESPONSE RECEIVED
    @Override // here process response
    public void onResult(AIResponse result) {

        Result result1 = result.getResult();
        textClient.setText(result1.getResolvedQuery());
        textAI.setText(result1.getFulfillment().getSpeech());

        speak();

    }

    //TEXT TO SPEECH FUNCTION
    private void speak() {
        boolean checker = false;
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
        button_record.setImageResource(R.mipmap.logo_in);
        recording = true;
    }

    @Override // indicate stop listening here
    public void onListeningCanceled() {
        button_record.setImageResource(R.mipmap.logo_in);
        recording = false;
    }

    @Override // indicate stop listening here
    public void onListeningFinished() {
        button_record.setImageResource(R.mipmap.logo);
        recording = false;
    }

    public void talkToAi(View view) {

        if (recording) {
            aiService.stopListening();
        } else {
            ttsAI.stop();
            aiService.startListening();
        }
    }

    //TODO: Debug text to AI
    public void textToAi(View view) {
        AIRequest aiRequest = new AIRequest();
        aiRequest.setQuery(editText.getText().toString());
    }
}