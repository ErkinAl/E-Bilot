package com.example.e_bilot;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

// DENİZ BİLGİN, ERKİN ALKAN
// This is the second fundamental class of the app
public class User implements Parcelable {
    private String userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int age;
    private boolean isMale;

    public User(){

    }

    // DENİZ BİLGİN, ERKİN ALKAN
    public User(Parcel in) {
        userId = in.readString();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        password = in.readString();
        age = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isMale = in.readBoolean();
        }
    }

    // DENİZ BİLGİN, ERKİN ALKAN
    @NonNull
    @Override
    public String toString() {
        return this.userId + " - " + this.name + " named user.";
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // DENİZ BİLGİN, ERKİN ALKAN
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    // DENİZ BİLGİN, ERKİN ALKAN
    @Override
    public int describeContents() {
        return 0;
    }

    // DENİZ BİLGİN, ERKİN ALKAN
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(age);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(isMale);
        }
    }
}
