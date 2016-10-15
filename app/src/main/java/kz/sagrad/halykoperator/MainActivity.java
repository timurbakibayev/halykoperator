package kz.sagrad.halykoperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity.java";

    EditText nameET;
    EditText serviceET;
    EditText windowET;
    Button login;
    Button nextClient;
    Button completeClient;
    TextView waitingTV;
    TextView currentTicketTV;

    String[] services;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameET = (EditText)findViewById(R.id.nameET);
        serviceET = (EditText) findViewById(R.id.servicesET);
        windowET = (EditText) findViewById(R.id.windowET);
        login = (Button) findViewById(R.id.login);
        nextClient = (Button) findViewById(R.id.nextClient);
        completeClient = (Button) findViewById(R.id.completeClient);
        waitingTV = (TextView) findViewById(R.id.waitingTV);
        currentTicketTV = (TextView) findViewById(R.id.currentTicketTV);
        Random rnd = new Random();
        nameET.setText(HalykOperator.sharedPref.getString("nameET","SAGRAD_" + rnd.nextInt(20)));
        serviceET.setText(HalykOperator.sharedPref.getString("serviceET","0,1,2"));
        windowET.setText("A" + rnd.nextInt(20));
        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                HalykOperator.savePref("nameET", nameET.getText().toString());
            }
        });
        serviceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                HalykOperator.savePref("serviceET", serviceET.getText().toString());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        nextClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClient();
            }
        });
        completeClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeClient();
            }
        });
    }

    private void nextClient() {
        HalykOperator.ref.child("users").child(currentUser.fireId).child("action").setValue("NEXT");
        nextClient.setEnabled(false);
    }

    private void completeClient() {
        HalykOperator.ref.child("users").child(currentUser.fireId).child("action").setValue("READY");
        completeClient.setEnabled(false);
    }

    ValueEventListener vel;

    private void login() {
        if (currentUser != null) {
            HalykOperator.ref.child("users").child(currentUser.fireId).removeEventListener(vel);
            currentUser.loggedIn = false;
            HalykOperator.ref.child("users").child(currentUser.fireId).setValue(currentUser);
            currentUser = null;
            login.setText("Login");
        } else {
            String name = nameET.getText().toString();
            services = serviceET.getText().toString().replaceAll(" ", "").split(",");
            currentUser = new User();
            currentUser.name = name;
            currentUser.windowNo = windowET.getText().toString();
            currentUser.services = new ArrayList<String>(Arrays.asList(services));
            Firebase db_ref = HalykOperator.ref.child("users").push();
            currentUser.fireId = db_ref.getKey();
            nextClient.setEnabled(true);
            completeClient.setEnabled(false);
            db_ref.setValue(currentUser);
            Log.e(TAG, "login: id=" + db_ref.getKey());
            vel = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User changedUser = dataSnapshot.getValue(User.class);
                    if (changedUser.currentTicketId != null &&
                            !changedUser.currentTicketId.equals(currentUser.currentTicketId)) {
                        currentUser.currentTicketId = changedUser.currentTicketId;
                        updateScreen();
                        if (currentUser.currentTicketId.equals("")) {
                            nextClient.setEnabled(true);
                            completeClient.setEnabled(false);
                        } else {
                            nextClient.setEnabled(false);
                            completeClient.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            };
            HalykOperator.ref.child("users").child(currentUser.fireId).addValueEventListener(vel);
            login.setText("Logout");
        }
    }

    private void updateScreen() {
        currentTicketTV.setText("Текущий клиент: " + currentUser.currentTicketId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (currentUser != null) {
            currentUser.loggedIn = false;
            HalykOperator.ref.child("users").child(currentUser.fireId).setValue(currentUser);
            currentUser = null;
        }
    }
}
