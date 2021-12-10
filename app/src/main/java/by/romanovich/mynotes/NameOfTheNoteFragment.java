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

    // Создаём список городов на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.nameOfTheNote);
        String[] fullNote = getResources().getStringArray(R.array.fullNote);
// При помощи этого объекта будем доставать элементы, спрятанные в item.xml
        LayoutInflater ltInflater = getLayoutInflater();

// В этом цикле ищем элемент TextView в item.xml,
// заполняем его значениями
// и добавляем на экран item.xml.
// Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < notes.length; i++) {
            String titleNote = notes[i];
            String fulNote = fullNote[i];
// Достаём элемент из item.xml
            View item = ltInflater.inflate(R.layout.item, layoutView, false);
            TextView tvTitleNote = item.findViewById(R.id.textView);
// Находим в этом элементе TextView
            tvTitleNote.setText(titleNote);
            layoutView.addView(item);
            final int position = i;
            tvTitleNote.setOnClickListener(v -> {
                Note currentNote = new Note(titleNote,fulNote);
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
        fragmentTransaction.add(R.id.list_container, noteFragment.newInstance(note))
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
