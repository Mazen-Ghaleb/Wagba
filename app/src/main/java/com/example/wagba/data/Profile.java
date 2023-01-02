package com.example.wagba.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class Profile {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name ="id")
    private String mId;

    @NonNull
    @ColumnInfo(name = "firstName")
    private String mFirstName;

    @NonNull
    @ColumnInfo(name = "lastName")
    private String mLastName;

    @NonNull
    @ColumnInfo(name = "email")
    private String mEmail;

    @NonNull
    @ColumnInfo(name = "birthdate")
    private String mBirthdate;

    @NonNull
    @ColumnInfo(name = "gender")
    private String mGender;

    public Profile(Profile profile){
        this.mId = profile.getId();
        this.mFirstName = profile.getFirstName();
        this.mLastName = profile.getLastName();
        this.mEmail = profile.getEmail();
        this.mBirthdate = profile.getBirthdate();
        this.mGender = profile.getGender();
    }

    public Profile(@NonNull String mId,@NonNull String mFirstName,@NonNull String mLastName,@NonNull String mEmail,@NonNull String mBirthdate,@NonNull String mGender) {
        this.mId = mId;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mBirthdate = mBirthdate;
        this.mGender = mGender;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getFirstName() {
        return mFirstName;
    }

    @NonNull
    public String getLastName() {
        return mLastName;
    }

    @NonNull
    public String getEmail() {
        return mEmail;
    }

    @NonNull
    public String getBirthdate() {
        return mBirthdate;
    }

    @NonNull
    public String getGender() {
        return mGender;
    }

    public void setId(@NonNull String mId) {
        this.mId = mId;
    }

    public void setFirstName(@NonNull String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setLastName(@NonNull String mLastName) {
        this.mLastName = mLastName;
    }

    public void setEmail(@NonNull String mEmail) {
        this.mEmail = mEmail;
    }

    public void setBirthdate(@NonNull String mBirthdate) {
        this.mBirthdate = mBirthdate;
    }

    public void setGender(@NonNull String mGender) {
        this.mGender = mGender;
    }
}
