package com.itculturalfestival.smartcampus.ui.main.home;

import com.itculturalfestival.smartcampus.network.Url;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenterImpl;
import com.vegen.smartcampus.baseframework.network.DisposableHolder;
import com.vegen.smartcampus.baseframework.network.HttpError;
import com.vegen.smartcampus.baseframework.utils.SystemUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vegen on 2018/3/22.
 */

public class ArticleDetailPresenter extends BasePresenterImpl<ArticleDetailContract.View> implements ArticleDetailContract.Presenter  {

    public ArticleDetailPresenter(ArticleDetailContract.View view) {
        super(view);
    }

    @Override
    public void getNewsContent(String url) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<Document>) e -> {
            Document document = Jsoup.connect(url).get();
            e.onNext(document);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(document -> {
                    Element body = document.body();
                    Elements links = body.select("a[href]"); //带有href属性的a元素

                    for (Element element : links){
                        element.attr("href", "");
                    }

                    Element author = body.select("#author").first();
                    Element contentbody = body.select("#contentbody").first();
                    Element showtag = body.select("#showtag").last();

                    if (mView != null) {
                        mView.showNewsContent("</br>" + (author.html() + contentbody.html() + showtag.html())
                                .replace("font-size: 10.5pt", "font-size: 16.5pt")
                                .replace("FONT-SIZE: 10.5pt", "FONT-SIZE: 16.5pt")
                                .replace("src=\"/files", "src=\"" + Url.ROOT_URL + "/files")
                                .replace("href=\"\"", "")
                                .replace("【点击：", "【点击：" + SystemUtils.getRandom(5, 30))
                                .replace("发布时间", "</br>发布时间"));
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
}
