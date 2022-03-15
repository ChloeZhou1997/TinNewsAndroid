package com.laioffer.tinnews.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.laioffer.tinnews.TinNewsApplication;
import com.laioffer.tinnews.database.TinNewsDatabase;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;
    private final TinNewsDatabase database;

    public NewsRepository() {
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = TinNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });

        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        Log.d("test", "1");
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                Log.d("test", "3");
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        Log.d("test", "2");
        return everyThingLiveData;
    }

    public LiveData<Boolean> favoriteNews(Article article) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Log.d("Tim", Thread.currentThread().getName() + " 1");
        new FavoriteAsyncTask(database, result).execute(article);
        Log.d("Tim", Thread.currentThread().getName() + " 2");
        return result;
    }

    public LiveData<List<Article>> getAllSavedArticles() {
//        Option 1
//        MutableLiveData<List<Article>> articles = new MutableLiveData<>();
//        AsyncTask.execute(() -> {
//            List<Article> articleList = database.articleDao().getNoLiveDataAllArticles();
//            articles.postValue(articleList);
//        });
//        Option 2
        return database.articleDao().getAllArticles();
    }

    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

    private static class FavoriteAsyncTask extends AsyncTask<Article, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Tim", Thread.currentThread().getName() + " 3");
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("Tim", Thread.currentThread().getName() + " 4");
        }
        @Override
        protected Boolean doInBackground(Article... articles) {
            Log.d("Tim", Thread.currentThread().getName() + " 5");
            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
            Log.d("Tim", Thread.currentThread().getName() + " 6");
        }

        private final TinNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinNewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }
    }
}
