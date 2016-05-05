package com.example.guest.chattinu.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.guest.chattinu.Constants;
import com.example.guest.chattinu.R;
import com.example.guest.chattinu.models.Chat;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    private Firebase mFirebaseRef;
    private Spinner recipientSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);

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

//        final EditText mSenderEditText = (EditText) dialogView.findViewById();

        dialogBuilder.setTitle("Start a new chat!");
//        dialogBuilder.setPositiveButton("Send", new DialogInterface.onClickListener() {
//           public void onClick(DialogInterface dialog, int whichButton) {
//
//           }
//        });
//        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.onClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//
//            }
//        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }



//    public void addNewChat(sender, recipient, content ) {
//
//        Chat chat = new Chat(sender, recipient, content);
//
//    }

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
