package data;

public class CardData {
    private String titleText; // заголовок
    private String noteDetails; // описание
    private int picture; // изображение
    private boolean ready; // флажок
    public CardData(String titleText, String noteDetails, int picture, boolean ready){
        this.titleText = titleText;
        this.noteDetails=noteDetails;
        this.picture=picture;
        this.ready=ready;
    }
    public String getTitleText() {
        return titleText;
    }
    public String getNoteDetails() {
        return noteDetails;
    }
    public int getPicture() {
        return picture;
    }
    public boolean isReady() {
        return ready;
    }
}