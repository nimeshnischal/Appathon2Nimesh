package com.nimeshnischal.appathon2nimesh;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OptionsActivity extends AppCompatActivity {
    static final String SELECTED_BUTTON_ID = "selected_button_id";
    static int selectedButtonId = Utilities.NONE_SELECTED;
    static int randomOrQuest = -1;

    @BindView(R.id.triviaButton)
    Button triviaButton;
    @BindView(R.id.mathButton)
    Button mathButton;
    @BindView(R.id.dateButton)
    Button dateButton;
    @BindView(R.id.yearButton)
    Button yearButton;
    @BindView(R.id.numberInput)
    EditText numberInput;
    @BindView(R.id.submit)
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NMZ_LOG", "Activity Started.");
        setContentView(R.layout.activity_options);
        randomOrQuest = getIntent().getIntExtra(Utilities.RANDOM_OR_QUEST, -1);
        ButterKnife.bind(this);
        Log.d("NMZ_LOG", getIntent().toString());

        setLayout();
        //goToA2.setText(randomOrQuest+"");
        if (savedInstanceState != null) {
            Log.d("NMZ_LOG", savedInstanceState.toString());
            int selection = savedInstanceState.getInt(SELECTED_BUTTON_ID);
            switch (selection) {
                case Utilities.TRIVIA:
                    markTriviaButton();
                    break;
                case Utilities.MATH:
                    markMathButton();
                    break;
                case Utilities.DATE:
                    markDateButton();
                    break;
                case Utilities.YEAR:
                    markYearButton();
                    break;
                default:
                    deselectAllButtons();
                    selectedButtonId = Utilities.NONE_SELECTED;
            }
        }
        setEditTextHint();
        setActionBarTitle();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_BUTTON_ID, selectedButtonId);
    }

    void setLayout() {
        if (randomOrQuest == Utilities.RANDOM) {
            numberInput.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);
        }
    }

    private void setActionBarTitle() {
        switch (randomOrQuest) {
            case Utilities.RANDOM:
                setTitle("Random Facts");
                break;
            case Utilities.QUEST:
                setTitle("Quest Facts");
        }
    }


    @OnClick(R.id.submit)
    void submit() {
        Log.d("NMZ_LOG", "Button Clicked.");
        if (selectedButtonId != Utilities.NONE_SELECTED) {
            Intent intent = new Intent(OptionsActivity.this, DisplayActivity.class);
            String url = "";
            String input = numberInput.getText().toString().trim();
            switch (selectedButtonId) {
                case Utilities.TRIVIA:
                    if(!validateInputAsInt(input)) {
                        showInvalidInputMessage();
                        return;
                    }
                    url = Utilities.QUEST_TRIVIA_URL;
                    url = url.replace("*", input);
                    break;
                case Utilities.DATE:
                    if(!validateInputAsDate(input)){
                        showInvalidInputMessage();
                        return;
                    }
                    url = Utilities.QUEST_DATE_URL;
                    url = url.replace("*", input);
                    break;
                case Utilities.MATH:
                    if(!validateInputAsInt(input)) {
                        showInvalidInputMessage();
                        return;
                    }
                    url = Utilities.QUEST_MATH_URL;
                    url = url.replace("*", input);
                    break;
                case Utilities.YEAR:
                    if(!validateInputAsInt(input)) {
                        showInvalidInputMessage();
                        return;
                    }
                    url = Utilities.QUEST_YEAR_URL;
                    url = url.replace("*", input);
                    break;
            }
            intent.putExtra(Utilities.URL, url);
            openDisplayActivity(intent);
        } else {
            Toast.makeText(OptionsActivity.this, "Select an option first!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validateInputAsDate(String input) {
        if(input.split("/").length >2){
            return false;
        }
        try {
            String month = input.split("/")[0];
            if(month.startsWith("-"))
                month = month.substring(1);
            String day = input.split("/")[1];
            if(day.startsWith("-"))
                day = day.substring(1);
            return month.matches("[0-9]+") && day.matches("[0-9]+");
        }catch (Exception e){
            return false;
        }

    }

    private boolean validateInputAsInt(String input) {
        if(input.startsWith("-")) {
            input = input.substring(1);
        }
        return input.matches("[0-9]+");
    }

    private void showInvalidInputMessage() {
        numberInput.setHighlightColor(Color.RED);
        Toast.makeText(OptionsActivity.this, "Invalid input!", Toast.LENGTH_LONG).show();
    }

    private void setDisplayAcivityIntentForRandom(int option) {
        Intent intent = new Intent(OptionsActivity.this, DisplayActivity.class);
        String url = "";
        switch (option) {
            case Utilities.TRIVIA:
                url = Utilities.RANDOM_TRIVIA_URL;
                break;
            case Utilities.DATE:
                url = Utilities.RANDOM_DATE_URL;
                break;
            case Utilities.MATH:
                url = Utilities.RANDOM_MATH_URL;
                break;
            case Utilities.YEAR:
                url = Utilities.RANDOM_YEAR_URL;
                break;
        }
        Log.d("NMZ_LOG", "Before storing in intent, url: "+ url);
        intent.putExtra(Utilities.URL, url);
        openDisplayActivity(intent);
    }

    void openDisplayActivity(Intent intent) {
        Log.d("NMZ_LOG", "Before opening intent: "+ intent.getStringExtra(Utilities.URL));
        startActivity(intent);
    }

    @OnClick(R.id.triviaButton)
    void markTriviaButton() {
        if (randomOrQuest == Utilities.QUEST) {
            deselectAllButtons();
            if (selectedButtonId == Utilities.TRIVIA) {
                selectedButtonId = Utilities.NONE_SELECTED;
            } else {
                selectButton(triviaButton);
                selectedButtonId = Utilities.TRIVIA;
            }
            setEditTextHint();
        } else {
            setDisplayAcivityIntentForRandom(Utilities.TRIVIA);
        }
    }


    @OnClick(R.id.mathButton)
    void markMathButton() {
        if (randomOrQuest == Utilities.QUEST) {
            deselectAllButtons();
            if (selectedButtonId == Utilities.MATH) {
                selectedButtonId = Utilities.NONE_SELECTED;
            } else {
                selectButton(mathButton);
                selectedButtonId = Utilities.MATH;
            }
            setEditTextHint();
        } else {
            setDisplayAcivityIntentForRandom(Utilities.MATH);
        }
    }

    @OnClick(R.id.dateButton)
    void markDateButton() {
        if (randomOrQuest == Utilities.QUEST) {
            deselectAllButtons();
            if (selectedButtonId == Utilities.DATE) {
                selectedButtonId = Utilities.NONE_SELECTED;
            } else {
                selectButton(dateButton);
                selectedButtonId = Utilities.DATE;
            }
            setEditTextHint();
        } else {
            setDisplayAcivityIntentForRandom(Utilities.DATE);
        }
    }

    @OnClick(R.id.yearButton)
    void markYearButton() {
        if (randomOrQuest == Utilities.QUEST) {
            deselectAllButtons();
            if (selectedButtonId == Utilities.YEAR) {
                selectedButtonId = Utilities.NONE_SELECTED;
            } else {
                selectButton(yearButton);
                selectedButtonId = Utilities.YEAR;
            }
            setEditTextHint();
        } else {
            setDisplayAcivityIntentForRandom(Utilities.YEAR);
        }
    }

    private void deselectAllButtons() {
        triviaButton.setBackgroundResource(R.drawable.default_button_gradient);
        mathButton.setBackgroundResource(R.drawable.default_button_gradient);
        dateButton.setBackgroundResource(R.drawable.default_button_gradient);
        yearButton.setBackgroundResource(R.drawable.default_button_gradient);
    }

    private void selectButton(Button button) {
        button.setBackgroundResource(R.drawable.selected_button_gradient);
    }

    public void setEditTextHint() {
        String hint = "";
        switch (selectedButtonId) {
            case Utilities.TRIVIA:
                hint = "e.g.: 2000";
                break;
            case Utilities.MATH:
                hint = "e.g.: 150";
                break;
            case Utilities.DATE:
                hint = "e.g.: 12/31 (MM/DD)";
                break;
            case Utilities.YEAR:
                hint = "e.g.: 2017";
                break;
            default:
                hint = "Select any option.";
        }
        numberInput.setHint(hint);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        selectedButtonId = Utilities.NONE_SELECTED;
    }


}
