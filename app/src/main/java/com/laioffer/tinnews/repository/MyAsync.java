package com.laioffer.tinnews.repository;

public abstract class MyAsync<Param, Progress, Result>{
    private Result movetoBackgorund(Param... params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doInBackground(params);
            }
        }).start();
        return null;
    }
    public abstract Result doInBackground(Param... params);

    public void onPreExecute() {

    }

    public void onProgressUpdate(Progress... progresses) {

    }

    public void onPostExecute(Result result) {

    }

    public MyAsync<Param, Progress, Result> execute(Param... params) {
        onPreExecute();
        movetoBackgorund(params);
        onPostExecute(null);
        return this;
    }
}


