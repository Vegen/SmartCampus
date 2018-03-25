package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.entity.Employment;
import com.itculturalfestival.smartcampus.ui.main.home.ArticleDetailContract;
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

/**
 * Created by vegen on 2018/3/24.
 */

public class EmploymentArticleDetailPresenter extends BasePresenterImpl<EmploymentListContract.View> implements EmploymentListContract.Presenter {

    public EmploymentArticleDetailPresenter(EmploymentListContract.View view) {
        super(view);
    }

    @Override
    public void getEmploymentList(String url, int type) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<Document>) e -> {
            Document document = Jsoup.connect(url).get();
            e.onNext(document);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(document -> {
                    Element body = document.body();

                    if (mView != null) {
                        mView.showEmploymentList(filterData(body, type));
                        mView.hideLoading(false);
                        if (type == Constant.FAIR) mView.loadMoreEnd(true);
                    }
                }, throwable -> {
                    if (mView != null) {
                        mView.showMessage(HttpError.getErrorMessage(throwable));
                        mView.hideLoading(true);
                    }
                });

        mHttpLinkers.add(new DisposableHolder(disposable));
    }

    private List<Employment> filterData(Element body, int type) {
        List<Employment> employments = new ArrayList<>();
        switch (type) {
            case Constant.NOTICE:// 招聘公告
                Elements infoList = body.select(".infoList");
                for (Element info : infoList){
                    String title = info.select(".span7").first().getElementsByTag("a").first().ownText().trim();
                    String content = "";
                    String tips = info.select(".span1").first().ownText().trim();
                    String time = info.select(".span4").first().ownText().trim();
                    String nextUrl = info.select(".span7").first().getElementsByTag("a").attr("href").trim();
                    Employment employment = new Employment(title, content, tips, time, nextUrl);
                    employments.add(employment);
                }
                break;
            case Constant.FAIR:// 招聘会
                Elements infoList_jobfairList = body.getElementsByClass("infoList jobfairList");
                for (Element info : infoList_jobfairList){
                    String title = info.select(".span1").first().getElementsByTag("a").first().attr("title").trim();
                    String content = info.select(".span3").first().ownText().trim();
                    String tips = info.select(".span4").first().ownText().trim();
                    String time = info.select(".span5").first().ownText().trim();
                    String nextUrl = info.select(".span1").first().getElementsByTag("a").attr("href").trim();
                    Employment employment = new Employment(title, content, tips, time, nextUrl);
                    employments.add(employment);
                }
                break;
            case Constant.CONFERENCE:// 宣讲会
                Elements infoList_teachinList = body.getElementsByClass("infoList teachinList");
                for (Element info : infoList_teachinList){
                    String title = info.select(".span1").first().getElementsByTag("a").first().attr("title").trim();
                    String content = info.select(".span2").first().ownText().trim()
                            + info.select(".span5").first().ownText().trim()
                            + info.select(".span4").first().ownText().trim();
                    String tips = "";
                    String time = info.select(".span5").first().ownText().trim();
                    String nextUrl = info.select(".span1").first().getElementsByTag("a").attr("href").trim();
                    Employment employment = new Employment(title, content, tips, time, nextUrl);
                    employments.add(employment);
                }
                break;
            case Constant.JOB:// 岗位信息
                Elements infoList_job = body.select(".infoList");
                for (Element info : infoList_job){
                    String title = info.select(".span2").first().getElementsByTag("a").first().ownText().trim();
                    String content = info.select(".span1").first().getElementsByTag("a").first().attr("title").trim()
                              + "("+ info.select(".span3").last().ownText() + ")";
                    String tips = info.select(".span3").first().ownText();
                    String time = info.select(".span4").first().ownText().trim();
                    String nextUrl = info.select(".span1").first().getElementsByTag("a").attr("href").trim();
                    Employment employment = new Employment(title, content, tips, time, nextUrl);
                    employments.add(employment);
                }
                break;
            default:
                Elements newsList = body.select(".newsList");
                for (Element info : newsList){
                    String title = info.select(".span1").first().getElementsByTag("a").first().ownText().trim();
                    String content = "";
                    String tips = "";
                    String time = info.select(".span2").first().ownText().trim();
                    String nextUrl = info.select(".span1").first().getElementsByTag("a").attr("href").trim();
                    Employment employment = new Employment(title, content, tips, time, nextUrl);
                    employments.add(employment);
                }
                break;
        }
        return employments;
    }

}
