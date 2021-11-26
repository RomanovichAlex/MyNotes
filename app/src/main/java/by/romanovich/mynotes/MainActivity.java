package by.romanovich.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Constants {

    private Button btnPlus;

    private EditText titleText;

    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаём фрагмент
        NameOfTheNoteFragment nameOfTheNoteFragment = new NameOfTheNoteFragment();
        //Вызываем фрагмент менеджер
        getSupportFragmentManager()
                //Открываем транзакцию
                .beginTransaction()
                //Заменяем элемент на фрагмент
                .replace(R.id.fragmentContainer, nameOfTheNoteFragment)
                .commit();

        account = new Account();

        initViews();
    }

    private void initViews() {
        titleText = findViewById(R.id.titleText);

        titleText.setOnClickListener(view -> {
            String tT = titleText.getText().toString();
            populateAccount();
            openNotesActivityForResult();
        });

        btnPlus = (Button) findViewById(R.id.buttonPlus);
        btnPlus.setOnClickListener(view -> {
            populateAccount();
            openNotesActivityForResult();
        });
    }

    private void openNotesActivityForResult() {
        Intent runNotes = new Intent(MainActivity.this, NotesActivity.class);
        runNotes.putExtra(YOUR_ACCOUNT, account);
        someActivityResultLauncher.launch(runNotes);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        setNotesResult(data);
                    }
                }
            });


    private void setNotesResult(Intent data) {
        account = data.getParcelableExtra(YOUR_ACCOUNT);
        populateViews();
    }

    private void populateViews() {
        titleText.setText(account.getTitleText());
    }

    private void populateAccount() {
        account.setTitleText(titleText.getText().toString());
    }
}