package com.itculturalfestival.smartcampus.ui.main;

import com.itculturalfestival.smartcampus.entity.News;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenterImpl;
import com.vegen.smartcampus.baseframework.network.DisposableHolder;
import com.vegen.smartcampus.baseframework.network.HttpError;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.itculturalfestival.smartcampus.Constant.*;

/**
 * Created by vegen on 2018/3/6.
 */

public class NewsPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter  {

    public NewsPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void getNewsList(String url) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                Document document = Jsoup.connect(url).get();
                e.onNext(document);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Document>() {
                    @Override
                    public void accept(Document document) throws Exception {
                        List<News> newsList = new ArrayList<>();        // 新闻列表

                        Element body = document.body();
                        Element photoList = body.getElementById("ListNews").getElementById("leftbox").getElementById("photolist");

                        // NEWS_TYPE_FOCUS 要闻
                        newsList.addAll(getTagNews(photoList, "xxyw", NEWS_TYPE_FOCUS));
                        // NEWS_TYPE_COMPREHENSIVE 综合
                        newsList.addAll(getTagNews(photoList, "zhxw", NEWS_TYPE_COMPREHENSIVE));
                        // NEWS_TYPE_FLASH 快讯
                        newsList.addAll(getTagNews(photoList, "xykx", NEWS_TYPE_FLASH));
                        // NEWS_TYPE_OTHER 其他
                        newsList.addAll(getTagNews(photoList, "otherPic", NEWS_TYPE_OTHER));

                        if (mView != null) {
                            mView.showNewsList(newsList);
                            mView.hideLoading();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView != null) {
                            mView.showMessage(HttpError.getErrorMessage(throwable));
                            mView.hideLoading();
                        }
                    }
                });

        mHttpLinkers.add(new DisposableHolder(disposable));
    }

    private List<News> getTagNews(Element photoList, String tag, String type){
        List<News> newsList = new ArrayList<>();
        Element focusNews = photoList.getElementById(tag);
        Elements focusLinks = focusNews.getElementsByTag("div").select("#photodiv");
        String text = "";
        for (Element link : focusLinks) {
            Elements photoLink = link.getElementById("photo").getElementsByTag("a");
            Element newsElement = photoLink.get(0);
            String title = newsElement.attr("title");
            String url = newsElement.attr("href");
            String pic = newsElement.getElementsByTag("img").get(0).attr("src");
            String date = link.getElementById("title").ownText();

            text += title + "\n" + url + "\n" + pic + "\n" + date.substring(1, date.length() - 1) + "\n";

            News news = new News(type, title, pic, url, date.substring(1, date.length() - 1));
            newsList.add(news);
        }
        LogUtils.e("NewsPresenter", type + "\n" + text);
        return newsList;
    }
}
