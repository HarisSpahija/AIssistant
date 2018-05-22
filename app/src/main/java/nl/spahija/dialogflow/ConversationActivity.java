package nl.spahija.dialogflow;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

public class ConversationActivity extends AppCompatActivity{

    ListView listView;
    Button buttonSpeak;

    String[] chatMessages = {"Test1, Test2, Test3, Test4, Test5, Test6"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        listView = (ListView) findViewById(R.id.listView);
        buttonSpeak = (Button) findViewById(R.id.buttonSpeak);

    }
}
