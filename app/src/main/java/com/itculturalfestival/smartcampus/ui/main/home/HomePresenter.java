package com.itculturalfestival.smartcampus.ui.main.home;

import com.itculturalfestival.smartcampus.entity.News;
import com.itculturalfestival.smartcampus.entity.NewsList;
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
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.itculturalfestival.smartcampus.Constant.*;

/**
 * Created by vegen on 2018/3/6.
 */

public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter  {

    public HomePresenter(HomeContract.View view) {
        super(view);
    }

    @Override
    public void getNewsList(String url) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<Document>) e -> {
            Document document = Jsoup.connect(url).get();
            e.onNext(document);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(document -> {
                    Element body = document.body();
                    Element photoList = body.getElementById("ListNews").getElementById("leftbox").getElementById("photolist");

                    List<NewsList> newsListList = new ArrayList<>();        // 新闻列表
                    // NEWS_TYPE_FOCUS 要闻
                    newsListList.add(new NewsList(NEWS_TYPE_FOCUS, getTagNews(photoList, "xxyw", NEWS_TYPE_FOCUS)));
                    // NEWS_TYPE_COMPREHENSIVE 综合
                    newsListList.add(new NewsList(NEWS_TYPE_COMPREHENSIVE, getTagNews(photoList, "zhxw", NEWS_TYPE_COMPREHENSIVE)));
                    // NEWS_TYPE_FLASH 快讯
                    newsListList.add(new NewsList(NEWS_TYPE_FLASH, getTagNews(photoList, "xykx", NEWS_TYPE_FLASH)));
                    // NEWS_TYPE_OTHER 其他
                    newsListList.add(new NewsList(NEWS_TYPE_OTHER, getTagNews(photoList, "otherPic", NEWS_TYPE_OTHER)));

                    List<String> strings = new ArrayList<>();
                    Elements elements = photoList.select("div.more");
                    for (Element element : elements){
                        String id = element.getElementsByTag("a").attr("href");
                        strings.add(id);
                    }

                    if (mView != null) {
                        mView.showNewsList(newsListList);
                        mView.moreNewsClassId(strings);
                        mView.hideLoading(false);
                    }
                }, throwable -> {
                    if (mView != null) {
                        mView.showMessage(HttpError.getErrorMessage(throwable));
                        mView.hideLoading(true);
                    }
                });

        mHttpLinkers.add(new DisposableHolder(disposable));
    }

    private List<News> getTagNews(Element photoList, String tag, int type){
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
        LogUtils.e("HomePresenter", type + "\n" + text);
        return newsList;
    }

}
