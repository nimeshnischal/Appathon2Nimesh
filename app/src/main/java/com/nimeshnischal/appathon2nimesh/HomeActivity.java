package com.nimeshnischal.appathon2nimesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.randomButton)
    Button randomButton;
    @BindView(R.id.questButton)
    Button questButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
    }

    @OnClick(R.id.randomButton)
    void openOptionsActivity(){
        Intent intent = new Intent(HomeActivity.this, OptionsActivity.class);
        intent.putExtra(Utilities.RANDOM_OR_QUEST, Utilities.RANDOM);
        startActivity(intent);
    }

    @OnClick(R.id.questButton)
    void openOptionsActivity2(){
        Intent intent = new Intent(HomeActivity.this, OptionsActivity.class);
        intent.putExtra(Utilities.RANDOM_OR_QUEST, Utilities.QUEST);
        startActivity(intent);
    }
}
