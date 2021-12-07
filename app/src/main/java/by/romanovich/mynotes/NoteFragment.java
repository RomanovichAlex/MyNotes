package by.romanovich.mynotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {



    static final String ARG_INDEX = "index";

    // TODO: Rename and change types of parameters
    private Note note;
    public NoteFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param note Index of note.
     * @return A new instance of fragment NoteFragment.
     */

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы
    public static NoteFragment newInstance(Note note){
        NoteFragment noteFragment = new NoteFragment();
        // Передача параметра через бандл
        Bundle args = new Bundle();
        // Создание фрагмента
        args.putParcelable(ARG_INDEX, note);
        noteFragment.setArguments(args);
        return noteFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            note = getArguments().getParcelable(ARG_INDEX);
        }
    }

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //элементы меню активити в этом фрагменте
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
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

    // Этот метод вызывается, когда макет экрана создан и готов к отображению информации. Создаем список заметок
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (note == null){
            return;
        }
        EditText eT = view.findViewById(R.id.noteTitle);
        eT.setText(note.getTitleText());


        Bundle arguments = getArguments();
        // Аргументы могут быть null (как в случае с методом Activity getIntent())
        // поэтому обязательно проверяем на null
        if (arguments != null) {
            int index = arguments.getInt(ARG_INDEX);
            // найдем в root view нужный EditText
            EditText editTextDescription = view.findViewById(R.id.noteDetails);
            // Получим из ресурсов массив описания заметок
            String[] descriptions = getResources().getStringArray(R.array.fullNote);
            // Возьмем нужное описание и отобразим в EditText
            editTextDescription.setText(descriptions[index]);
        }
    }
}