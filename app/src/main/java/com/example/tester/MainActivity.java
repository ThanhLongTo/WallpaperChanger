package com.example.tester;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    boolean switcherOn1 = false;
    boolean switcherOn2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final LottieAnimationView switchButton1 = findViewById(R.id.switcher1);
        switchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switcherOn1) {
                    switchButton1.setMinAndMaxProgress(0.5f, 1.0f);
                    switchButton1.playAnimation();
                    switcherOn1 = false;
                } else {
                    switchButton1.setMinAndMaxProgress(0.0f, 0.5f);
                    switchButton1.playAnimation();
                    switcherOn1 = true;
                }
            }
        });

        final LottieAnimationView switchButton2 = findViewById(R.id.switcher2);
        switchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switcherOn2) {
                    switchButton2.setMinAndMaxProgress(0.5f, 1.0f);
                    switchButton2.playAnimation();
                    switcherOn2 = false;
                } else {
                    switchButton2.setMinAndMaxProgress(0.0f, 0.5f);
                    switchButton2.playAnimation();
                    switcherOn2 = true;
                }
            }
        });

        ImageView optionBtn = findViewById(R.id.option);
        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        Button unplashEditBtn = findViewById(R.id.unplashEdit);
        unplashEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent picture_intent = new Intent(MainActivity.this,UnplashActivity.class);
                startActivity(picture_intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });


    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.rearrange) {
                    Toast.makeText(MainActivity.this, "Rearrange here", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.share) {
                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

        });
        popup.show();
    }
}