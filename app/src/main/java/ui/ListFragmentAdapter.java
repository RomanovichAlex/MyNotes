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
import by.romanovich.mynotes.R;
import data.CardData;
import data.CardsSource;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder> {
    private final static String TAG = "ListFragmentAdapter";
    private CardsSource dataSource;
    private Fragment fragment;
    private OnItemClickListener itemClickListener; // Слушатель будет устанавливаться извне
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
    public ListFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
// Создаём новый элемент пользовательского интерфейса
// через Inflater
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        Log.d(TAG, "onCreateViewHolder");
// Здесь можно установить всякие параметры
        return new ViewHolder(v);
    }
    // Заменить данные в пользовательском интерфейсе
// Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull ListFragmentAdapter.ViewHolder viewHolder, int i) {
// Получить элемент из источника данных (БД, интернет...)
// Вынести на экран используя ViewHolder
        viewHolder.setData(dataSource.getCardData(i));
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
    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    // Этот класс хранит связь между данными и элементами View
// Сложные данные могут потребовать несколько View на
// один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView noteDetails;
        private AppCompatImageView image;
        private CheckBox ready;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.title);
            noteDetails = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.imageView);
            ready = itemView.findViewById(R.id.ready);

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

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View v) {
                itemView.showContextMenu(10,10);
                return true;
            }
        });
    }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                fragment.registerForContextMenu(itemView);
            }
        }


        public void setData(CardData cardData){
            titleText.setText(cardData.getTitleText());
            noteDetails.setText(cardData.getNoteDetails());
            ready.setChecked(cardData.isReady());
            image.setImageResource(cardData.getPicture());
        }
    }
}

