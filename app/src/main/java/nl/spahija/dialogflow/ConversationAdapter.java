package nl.spahija.dialogflow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

public class ConversationAdapter extends ArrayAdapter<String>{

    public ConversationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
