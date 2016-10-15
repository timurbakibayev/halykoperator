package kz.sagrad.halykoperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText nameET;
    EditText serviceET;
    Button login;
    Button nextClient;
    Button completeClient;
    TextView waitingTV;
    TextView currentTicketTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameET = (EditText)findViewById(R.id.nameET);
        serviceET = (EditText) findViewById(R.id.servicesET);
        login = (Button) findViewById(R.id.login);
        nextClient = (Button) findViewById(R.id.nextClient);
        completeClient = (Button) findViewById(R.id.completeClient);
        waitingTV = (TextView) findViewById(R.id.waitingTV);
        currentTicketTV = (TextView) findViewById(R.id.currentTicketTV);
        Random rnd = new Random();
        nameET.setText(HalykOperator.sharedPref.getString("nameET","SAGRAD_" + rnd.nextInt(20)));
        serviceET.setText(HalykOperator.sharedPref.getString("serviceET","0,1,2"));
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
    }
}
