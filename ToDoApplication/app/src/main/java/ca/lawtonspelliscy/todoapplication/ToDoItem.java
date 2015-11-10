package ca.lawtonspelliscy.todoapplication;

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
public class ToDoItem {


    private String mSubject;
    private String mDescription;
    private boolean mComplete;

    public ToDoItem(String subject, String description) {
        this.mDescription = description;
        this.mSubject = subject;
        this.mComplete =false;
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

    public void setComplete(boolean complete) {
        this.mComplete = complete;
    }
}