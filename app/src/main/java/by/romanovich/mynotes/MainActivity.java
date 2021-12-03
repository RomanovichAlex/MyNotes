package by.romanovich.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
    }


    //Создаём меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id.fragmentContainer, new AboutFragment()).commit();
                return true;
            case R.id.action_exit:
                finish();
                return true;
            case R.id.menu_add:

                return true;
            case R.id.menu_clear:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
