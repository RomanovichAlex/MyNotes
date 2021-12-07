package by.romanovich.mynotes;


import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String titleText;
    private String noteDetails;

    protected Note(Parcel in) {
        titleText = in.readString();
        noteDetails = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titleText);
        dest.writeString(noteDetails);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public static Creator<Note> getCREATOR() {
        return CREATOR;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getNoteDetails() {
        return noteDetails;
    }

    public void setNoteDetails(String noteDetails) {
        this.noteDetails = noteDetails;
    }


    public Note(String titleText, String noteDetails) {
        this.titleText = titleText;
        this.noteDetails = noteDetails;
    }

}
