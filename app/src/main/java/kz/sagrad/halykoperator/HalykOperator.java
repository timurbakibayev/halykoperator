package kz.sagrad.halykoperator;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HalykOperator extends Application {

    private FirebaseAuth.AuthStateListener mAuthListener;
    public static Firebase ref;
    public static FirebaseAuth mAuth;
    public static FirebaseUser user;
    public static SharedPreferences sharedPref;

    public static String TAG = "HalykOperator.java";
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        ref = new Firebase("https://dqueue-4be01.firebaseio.com/");
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                HalykOperator.user = user;
                if (user != null) {
                    // User is signed in
                    Log.e(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    ref.child("users/"+user.getUid()+"/email").setValue(user.getEmail().toString());
                    ref.child("users/"+user.getUid()+"/id").setValue(user.getUid());
                } else {
                    // User is signed out
                    Log.e(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);

    }

}
