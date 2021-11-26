package by.romanovich.mynotes;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

public class NameOfTheNoteFragment extends Fragment {
    EditText titleText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_name_of_the_note, container, false);
        return root;

    }

    // Создаём макет экрана и отображения названиия заметок
    @Override
    public void onViewCreated (@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }
    //Передаем layot
    private void initList(View view) {
        LinearLayout layoutViev = (LinearLayout) view;
        //Чтение  названий заметок
        titleText = (EditText) view.findViewById(R.id.titleText);



            //дабывает контекст из нашей активити

        }
    }
