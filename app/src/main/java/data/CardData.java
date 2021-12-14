package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class CardData implements Parcelable {
    private final String title; // заголовок
    private final String description; // описание
    private final int picture; // изображение
    private final boolean ready; // флажок
    private final Date date; // дата


    public CardData(String title, String description, int picture, boolean ready,Date date){
        this.title = title;
        this.description=description;
        this.picture=picture;
        this.ready=ready;
        this.date = date;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
        ready = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
        dest.writeByte((byte) (ready ? 1 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getPicture() {
        return picture;
    }
    public boolean isReady() {
        return ready;
    }

    public Date getDate() { return date; }
}