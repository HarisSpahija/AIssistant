package nl.spahija.dialogflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener{
    AIService aiService;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        final AIConfiguration config = new AIConfiguration("6d138f04d3dc45b1bc15f81741b05da1",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }

    public void buttonClicked(View view){
        aiService.startListening();
    }

    @Override // here process response
    public void onResult(AIResponse result) {

        Log.d("spahija", result.toString());
        Result result1 = result.getResult();
        textView.setText(result1.getFulfillment().getSpeech());
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
