package by.romanovich.mynotes;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class NameOfTheNoteFragment extends Fragment {
    EditText titleText;

    private static final String CURRENT_NOTE = "CurrentNote";
    // Текущая позиция (выбранная заметка)
    private int currentPosition = 0;

    // При создании фрагмента Создаём макет экрана и отображения названиия заметок
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_name_of_the_note, container, false);
        return root;
    }

    // Этот метод вызывается, когда макет экрана создан и готов к отображению информации. Создаем список заметок
    @Override
    public void onViewCreated (@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }
        initList(view);

    // отображения открытого ранее списка в ландшафтной ориентации
        if (isLandscape()) {
        showLandDescription(currentPosition);
    }
}
    //Передаем layot, создаём список заметок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String [] notes = getResources().getStringArray(R.array.nameOfTheNote);
        //Чтение  названий заметок
        titleText = (EditText) view.findViewById(R.id.titleText);
        // В этом цикле создаём элемент TextView, заполняем его значениями и добавляем на экран.
        for (int i=0; i<notes.length;i++) {
            String note=notes[i];
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int position=i;
            tv.setOnClickListener(v -> {
                currentPosition=position;
                showDescription(position);
            });
        }
    }

    private void showDescription(int index) {
        if (isLandscape()) {
            showLandDescription(index);
        } else {
            showPortDescription(index);
        }
    }

    // Показываем описание в портретной ориентации
    private void showPortDescription(int index) {
        NoteFragment noteFragment = NoteFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // добавляем фрагмент через add
        fragmentTransaction.add(R.id.fragmentContainer, noteFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    // Показываем описание в ландшафтной ориентации
    private void showLandDescription(int index) {
        // Создаём новый фрагмент с текущей позицией для вывода герба
        NoteFragment detail = NoteFragment.newInstance(index);
        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.NoteFragment, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
}
