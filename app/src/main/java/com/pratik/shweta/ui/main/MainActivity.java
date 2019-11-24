package com.pratik.shweta.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.pratik.shweta.BaseActivity;
import com.pratik.shweta.R;
import com.pratik.shweta.SessionManager;
import com.pratik.shweta.ui.main.profile.ProfileFragment;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testFragmentTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mainMenuInflater = getMenuInflater();
        mainMenuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    private void testFragmentTransaction(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,new ProfileFragment())
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                sessionManager.logOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
