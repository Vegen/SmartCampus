package com.itculturalfestival.smartcampus.ui.main.home;

import com.itculturalfestival.smartcampus.entity.News;
import com.itculturalfestival.smartcampus.entity.NewsList;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenterImpl;
import com.vegen.smartcampus.baseframework.network.DisposableHolder;
import com.vegen.smartcampus.baseframework.network.HttpError;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.itculturalfestival.smartcampus.Constant.NEWS_TYPE_COMPREHENSIVE;
import static com.itculturalfestival.smartcampus.Constant.NEWS_TYPE_FLASH;
import static com.itculturalfestival.smartcampus.Constant.NEWS_TYPE_FOCUS;
import static com.itculturalfestival.smartcampus.Constant.NEWS_TYPE_OTHER;

/**
 * Created by vegen on 2018/3/21.
 */

public class MoreNewsPresenter extends BasePresenterImpl<MoreNewsContract.View> implements MoreNewsContract.Presenter {

    public MoreNewsPresenter(MoreNewsContract.View view) {
        super(view);
    }

    @Override
    public void getNewsList(String url, int newsType) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<Document>) e -> {
            Document document = Jsoup.connect(url).get();
            e.onNext(document);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(document -> {
                    Element body = document.body();
                    // 下一页的参数
                    String __VIEWSTATE = body.select("#__VIEWSTATE").first().attr("value").trim();
                    String __VIEWSTATEGENERATOR = body.select("#__VIEWSTATEGENERATOR").first().attr("value").trim();
                    String __EVENTVALIDATION = body.select("#__EVENTVALIDATION").first().attr("value").trim();
                    Map<String, String> newsForm = new HashMap<>();
                    newsForm.put("__VIEWSTATE", __VIEWSTATE);
                    newsForm.put("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR);
                    newsForm.put("__EVENTVALIDATION", __EVENTVALIDATION);
                    // 本页的数据
                    Element photoList = body.getElementById("ListNews").getElementById("leftbox").getElementById("photolist");
                    List<News> newsList = getTagNews(photoList, newsType);

                    if (mView != null) {
                        mView.nextNewsListForm(newsForm);
                        mView.showNewsList(newsList);
                        mView.hideLoading();
                    }
                }, throwable -> {
                    if (mView != null) {
                        mView.showMessage(HttpError.getErrorMessage(throwable));
                        mView.hideLoading();
                    }
                });

        mHttpLinkers.add(new DisposableHolder(disposable));
    }

    private List<News> getTagNews(Element photoList, int type){
        List<News> newsList = new ArrayList<>();
        Elements photodiv = photoList.getElementsByTag("div").select("#photodiv");
        String text = "";
        for (Element link : photodiv) {
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
        LogUtils.e("HomePresenter", type + "\n" + text);
        return newsList;
    }

    @Override
    public void getNewsList(int page, String url, int newsType, String view_state, String view_state_generator, String event_validation) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<Document>) e -> {
            Connection connection = Jsoup.connect(url);
            connection.data("__EVENTARGUMENT", "");
            connection.data("__EVENTTARGET", "PageNavigator1$LnkBtnNext");
            connection.data("__EVENTVALIDATION", event_validation);
            connection.data("__VIEWSTATE", view_state);
            connection.data("__VIEWSTATEGENERATOR", view_state_generator);
            connection.data("Date", "30");
            connection.data("tags", "");
            Document document = connection.post();
            e.onNext(document);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(document -> {
                    Element body = document.body();
                    // 下一页的参数
                    String __VIEWSTATE = body.select("#__VIEWSTATE").first().attr("value").trim();
                    String __VIEWSTATEGENERATOR = body.select("#__VIEWSTATEGENERATOR").first().attr("value").trim();
                    String __EVENTVALIDATION = body.select("#__EVENTVALIDATION").first().attr("value").trim();
                    Map<String, String> newsForm = new HashMap<>();
                    newsForm.put("__VIEWSTATE", __VIEWSTATE);
                    newsForm.put("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR);
                    newsForm.put("__EVENTVALIDATION", __EVENTVALIDATION);
                    // 本页的数据
                    Element photoList = body.getElementById("ListNews").getElementById("leftbox").getElementById("photolist");
                    List<News> newsList = getTagNews(photoList, newsType);

                    if (mView != null) {
                        mView.nextNewsListForm(newsForm);
                        mView.showNewsList(newsList);
                        mView.hideLoading();
                    }
                }, throwable -> {
                    if (mView != null) {
                        mView.showMessage(HttpError.getErrorMessage(throwable));
                        mView.hideLoading();
                        mView.loadMoreFail();
                    }
                });

        mHttpLinkers.add(new DisposableHolder(disposable));
    }
}
