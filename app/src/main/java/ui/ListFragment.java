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
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import data.CardsSourceFirebaseImpl;
import data.CardsSourceResponse;


public class ListFragment extends Fragment {

    private static final int MY_DEFAULT_DURATION = 1000;
    private CardsSource data;
    private ListFragmentAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    // признак, что при повторном открытии фрагмента
// (возврате из фрагмента, добавляющего запись)
// надо прыгнуть на последнюю запись
    private boolean moveToFirstPosition;

    public static ListFragment newInstance() {
        return new ListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container,
                false);
        // Получим источник данных для списка
        initView(view);
        setHasOptionsMenu(true);
        data = new CardsSourceFirebaseImpl().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) ||
                super.onOptionsItemSelected(item);
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
        return onItemSelected(item.getItemId()) ||
        super.onContextItemSelected(item);
    }


    private boolean onItemSelected(int menuItemId){
        switch (menuItemId){
            case R.id.menu_add:
                navigation.addFragment(CardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
// это сигнал, чтобы вызванный метод onCreateView
// перепрыгнул на начало списка
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(CardFragment.newInstance(data.getCardData(updatePosition)
                ), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(updatePosition, cardData);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteCardData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.menu_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
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

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
// Получим источник данных для списка
        initRecyclerView();
    }

    private void initRecyclerView(){
// Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

// Установим адаптер
        adapter = new ListFragmentAdapter(this);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0){
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }


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