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
import androidx.appcompat.app.ActionBarDrawerToggle;
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
                        finish();
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
                finish();
                return true;
            case R.id.menu_add:

                return true;
            case R.id.menu_clear:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.fragmentContainer, new AboutFragment()).commit();
    }
}
