package ca.lawtonspelliscy.todoapplication.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lawton on 15-11-07.
 * Simple class to contain whatever is need in each item in the to-do list
 * currently contains whether complete, subject and description
 *
 * Will eventually contain:
 * Date
 * priority
 * completion
 *
 */
public class ToDoItem implements Parcelable{


    private String mSubject;
    private String mDescription;
    private boolean mComplete;
    private int mRowid;

    /**
     * Simple object that contains the subject, description and completion state of a to-do item.
     * The completion state defaults to false.
     * @param subject The subject of the to-do item
     * @param description The description of the to-do item
     */
    public ToDoItem(String subject, String description) {
        this.mDescription = description;
        this.mSubject = subject;
        this.mComplete =false;
    }




    public int getRowid() {
        return mRowid;
    }

    public void setRowid(int mRowid) {
        this.mRowid = mRowid;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        this.mSubject = subject;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean isComplete() {
        return mComplete;
    }

    public int getComplete() {
        return mComplete ?1:0;
    }

    public void setComplete(boolean complete) {
        this.mComplete = complete;
    }

    public static final Parcelable.Creator<ToDoItem> CREATOR =
            new Parcelable.Creator<ToDoItem>(){

                @Override
                public ToDoItem createFromParcel(Parcel source) {
                    return new ToDoItem(source);
                }

                @Override
                public ToDoItem[] newArray(int size) {
                    return new ToDoItem[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDescription);
        dest.writeString(mSubject);
        dest.writeInt(this.getComplete()); //caste boolean to integer
        dest.writeInt(mRowid);

    }

    /**
     * make sure to include all member variables in same order as they are in @writeToParcel
     */
    private ToDoItem(Parcel in) {

        mDescription = in.readString();
        mSubject = in.readString();
        mComplete = in.readInt() == 1;
        mRowid = in.readInt();

    }
}
