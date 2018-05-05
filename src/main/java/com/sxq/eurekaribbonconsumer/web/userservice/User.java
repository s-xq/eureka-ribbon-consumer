package com.sxq.eurekaribbonconsumer.web.userservice;

public class User {
    private int mId;
    private String mName;
    private int mAge;
    private String mTimeStamp;


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        mTimeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return String.format("[User] mId=%s, mName=%s, mAge=%s,mTimeStamp=%s",
                String.valueOf(mId),
                String.valueOf(mName),
                String.valueOf(mAge),
                String.valueOf(mTimeStamp));

    }
}