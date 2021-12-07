package by.romanovich.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class NameOfTheNoteFragment extends Fragment {

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
        initList(view);
}

    //Передаем layot, создаём список заметок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String [] notes = getResources().getStringArray(R.array.nameOfTheNote);
        // В этом цикле создаём элемент TextView, заполняем его значениями и добавляем на экран.
        for (int i=0; i<notes.length;i++) {
            String titleText=notes[i];
            TextView tv = new TextView(getContext());
            tv.setText(titleText);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final String position="Note";
            tv.setOnClickListener(v -> {
                Note currentNote = new Note(position,titleText);
                showDescription(currentNote);
            });
        }
    }

    // Показываем описание в портретной ориентации
    private void showDescription(Note note) {
        NoteFragment noteFragment = NoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // добавляем фрагмент через add
        fragmentTransaction.add(R.id.fragmentContainer, noteFragment.newInstance(note))
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
