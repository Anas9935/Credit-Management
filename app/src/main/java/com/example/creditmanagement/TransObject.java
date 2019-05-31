package com.example.creditmanagement;

class TransObject {
    private final int transFrom;
    private final int transTo;
    private final long transTime;
    private final int traansamt;

    public TransObject(int frm,int to,int time,int amt){
        transFrom=frm;
        transTo=to;
        transTime=time;
        traansamt=amt;
    }

    public int getTransFrom() {
        return transFrom;
    }

    public int getTransTo() {
        return transTo;
    }

    public long getTransTime() {
        return transTime;
    }

    public void setTid() {
    }

    public int getTraansamt() {
        return traansamt;
    }
}
