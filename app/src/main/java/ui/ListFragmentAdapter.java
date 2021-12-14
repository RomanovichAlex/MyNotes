package ui;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import by.romanovich.mynotes.R;
import data.CardData;
import data.CardsSource;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder> {
    private final static String TAG = "ListFragmentAdapter";
    private CardsSource dataSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;
    private int menuPosition;

    // Передаём в конструктор источник данных
// В нашем случае это массив, но может быть и запрос к БД
    public ListFragmentAdapter(CardsSource dataSource, Fragment fragment) {
      this.dataSource = dataSource;
      this.fragment = fragment;
   }
    // Создать новый элемент пользовательского интерфейса
// Запускается менеджером
    @NonNull
    @Override
    public ListFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
// Создаём новый элемент пользовательского интерфейса
// через Inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Log.d(TAG, "onCreateViewHolder");
// Здесь можно установить всякие параметры
        return new ViewHolder(view);
    }
    // Заменить данные в пользовательском интерфейсе
// Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull ListFragmentAdapter.ViewHolder holder, int position) {
// Получить элемент из источника данных (БД, интернет...)
// Вынести на экран используя ViewHolder
        holder.setData(dataSource.getCardData(position));
        Log.d(TAG, "onBindViewHolder");
    }
    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }
    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public int getMenuPosition() {
        return menuPosition;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    // Этот класс хранит связь между данными и элементами View
// Сложные данные могут потребовать несколько View на
// один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private AppCompatImageView image;
        private CheckBox ready;
        private TextView date;



        public ViewHolder(@NonNull  final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
            ready = itemView.findViewById(R.id.ready);
            date = itemView.findViewById(R.id.date);

            registerContextMenu(itemView);


// Обработчик нажатий на картинке
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
// Обработчик долгих нажатий на картинке
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View v) {
                menuPosition = getLayoutPosition();
                itemView.showContextMenu(10,10);
                return true;
            }
        });
    }


        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }


        public void setData(CardData cardData){
            title.setText(cardData.getTitle());
            description.setText(cardData.getDescription());
            ready.setChecked(cardData.isReady());
            image.setImageResource(cardData.getPicture());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(cardData.getDate()));

        }

    }
}

