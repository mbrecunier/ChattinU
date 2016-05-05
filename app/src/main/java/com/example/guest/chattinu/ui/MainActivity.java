package com.example.guest.chattinu.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.guest.chattinu.Adapters.RecipientSpinnerAdapter;
import com.example.guest.chattinu.Constants;
import com.example.guest.chattinu.R;
import com.example.guest.chattinu.models.Chat;
import com.example.guest.chattinu.models.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Firebase mFirebaseRef;
    private Firebase mFirebaseChatsRef;
    private Firebase mFirebaseUserRef;
    private SharedPreferences mSharedPreferences;
    private String mUserId;
    List<String> userNames = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    private Spinner mRecipientSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        mFirebaseUserRef = new Firebase(Constants.FIREBASE_URL_USERS);
        mFirebaseChatsRef = new Firebase(Constants.FIREBASE_URL_CHATS);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserId = mSharedPreferences.getString(Constants.KEY_UID, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_add_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_add_chat) {
            showAddChatDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void showAddChatDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_chat_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText mContentEditText = (EditText) dialogView.findViewById(R.id.contentEditText);
        mRecipientSpinner = (Spinner) dialogView.findViewById(R.id.recipient_spinner);

        mFirebaseUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                    setUserNames(users);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        dialogBuilder.setTitle("Start a new chat!");
        dialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int whichButton) {
               String sender = mUserId;
               String recipient = mRecipientSpinner.getSelectedItem().toString();
               String content = mContentEditText.getText().toString();
               Chat chat = new Chat(sender, recipient, content);

               saveChatToFirebase(chat);
           }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void setUserNames(List<User> users) {
        for (User user : users) {
            userNames.add(user.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, userNames);
        mRecipientSpinner.setAdapter(adapter);
    }

    private void saveChatToFirebase(Chat chat) {
        mFirebaseChatsRef.push().setValue(chat);
    }

    protected void logout() {
        mFirebaseRef.unauth();
        takeUserToLoginScreenOnUnAuth();

    }

    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
