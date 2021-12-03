package by.romanovich.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //элементы меню активити в этом фрагменте
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_about, container, false);

    }
    // убераем пункты тоолбара
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.action_about);
        if (item != null) {
            item.setVisible(false);
        }
        MenuItem item1 = menu.findItem(R.id.menu_add);
        if (item1 != null) {
            item1.setVisible(false);
        }
    }
}