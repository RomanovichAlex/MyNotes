package by.romanovich.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new NoteFragment()).commit();


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
// Инициализируем Toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_about:
                    openAboutFragment();
                    drawer.closeDrawers();
                    return true;
                    case R.id.action_exit:
                        questionBeforeLeaving();
                        return true;
            }
            return false;
        });
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
                openAboutFragment();
                return true;
            case R.id.action_exit:
                questionBeforeLeaving();
                return true;
            case R.id.menu_add:
                Toast.makeText(this, R.string.ADD_Note, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_clear:
                Toast.makeText(this, R.string.Clear_Note, Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void questionBeforeLeaving() {
        new AlertDialog.Builder(this)
                .setTitle("Вы точно хотите выйти из приложения?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Досвидания!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Вот и ладненько!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragmentContainer, new AboutFragment(), null).commit();
    }
}