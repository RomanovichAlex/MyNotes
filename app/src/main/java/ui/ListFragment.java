package ui;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import by.romanovich.mynotes.MainActivity;
import by.romanovich.mynotes.Navigation;
import by.romanovich.mynotes.R;
import by.romanovich.mynotes.observe.Observer;
import by.romanovich.mynotes.observe.Publisher;
import data.CardData;
import data.CardsSource;
import data.CardsSourceImpl;


public class ListFragment extends Fragment {

    private CardsSource data;
    private ListFragmentAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    // признак, что при повторном открытии фрагмента
// (возврате из фрагмента, добавляющего запись)
// надо прыгнуть на последнюю запись
    private boolean moveToLastPosition;




    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Получим источник данных для списка
// Поскольку onCreateView запускается каждый раз
// при возврате в фрагмент, данные надо создавать один раз
        data = new CardsSourceImpl(getResources()).init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container,
                false);
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                navigation.addFragment(CardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
// это сигнал, чтобы вызванный метод onCreateView
// перепрыгнул на конец списка
                        moveToLastPosition = true;
                    }
                });
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
        adapter = new ListFragmentAdapter(data, this);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                null));
        recyclerView.addItemDecoration(itemDecoration);

        // Установим слушателя
        adapter.SetOnItemClickListener(new ListFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Позиция - %d",
                        position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch(item.getItemId()) {
            case R.id.action_update:
                navigation.addFragment(CardFragment.newInstance(data.getCardData(position)),
                        true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(position, cardData);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


}