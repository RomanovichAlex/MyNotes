package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.zip.Inflater;

import by.romanovich.mynotes.R;
import data.CardData;
import data.CardsSource;
import data.CardsSourceImpl;


public class ListFragment extends Fragment {

    private CardsSource data;
    private ListFragmentAdapter adapter;
    private RecyclerView recyclerView;


    public static ListFragment newInstance() {
        return new ListFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container,
                false);
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        data = new CardsSourceImpl(getResources()).init();
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    // убераем пункты тоолбара
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                data.addCardData(new CardData("Заголовок " + data.size(),
                        "Описание " + data.size(),
                        R.drawable.nature1,
                        false));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.menu_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
// Получим источник данных для списка
        data = new CardsSourceImpl(getResources()).init();
        initRecyclerView();
    }

    private void initRecyclerView(){
// Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);
// Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

// Установим адаптер
        adapter = new ListFragmentAdapter(data);
        recyclerView.setAdapter(adapter);
        // Установим слушателя
        adapter.SetOnItemClickListener(new ListFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Позиция - %d",
                        position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}