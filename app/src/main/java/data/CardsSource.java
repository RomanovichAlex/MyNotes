package data;

public interface CardsSource {
    CardData getCardData(int position);
    int size();
}
