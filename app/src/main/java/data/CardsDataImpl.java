package data;

import android.content.res.Resources;
import android.content.res.TypedArray;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import by.romanovich.mynotes.R;

public class CardsDataImpl implements CardsSource {

    private List<CardData> dataSource;
    private Resources resource; // ресурсы приложения

    public CardsDataImpl(Resources resources) {
        dataSource = new ArrayList<>(4);
        this.resource = resources;
    }
    public CardsSource init(CardsSourceResponse cardsSourceResponse){
// строки заголовков из ресурсов
        String[] titles = resource.getStringArray(R.array.nameOfTheNote);
// строки описаний из ресурсов
        String[] descriptions = resource.getStringArray(R.array.fullNote);
// изображения
        int[] pictures = getImageArray();
// заполнение источника данных
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new CardData(titles[i], descriptions[i], pictures[i],
                    false, Calendar.getInstance().getTime()));
        }

        if (cardsSourceResponse != null){
            cardsSourceResponse.initialized(this);
        }

        return this;
    }
// Механизм вытаскивания идентификаторов картинок
// https://stackoverflow.com/questions/5347107/creating-integer-array-of-resource-ids
    private int[] getImageArray(){
        TypedArray pictures = resource.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        pictures.recycle();
        return answer;
    }
    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size(){
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        dataSource.set(position, cardData); }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }
}
