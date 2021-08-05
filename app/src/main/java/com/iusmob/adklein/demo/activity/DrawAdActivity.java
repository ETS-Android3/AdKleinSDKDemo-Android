package com.iusmob.adklein.demo.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iusmob.adklein.ad.AdKleinDrawAd;
import com.iusmob.adklein.ad.AdKleinDrawAdListener;
import com.iusmob.adklein.ad.AdKleinDrawVodAdListener;
import com.iusmob.adklein.ad.AdKleinError;
import com.iusmob.adklein.demo.DemoConstants;
import com.iusmob.adklein.demo.R;
import com.iusmob.adklein.demo.adapter.DrawVodAdAdapter;
import com.iusmob.adklein.demo.entity.DrawVodAdBean;
import com.iusmob.adklein.demo.utils.ToastUtils;
import com.iusmob.adklein.demo.widget.KleinSmartRefreshLayout;
import com.iusmob.adklein.demo.widget.ViewPagerLayoutManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class DrawAdActivity extends AppCompatActivity implements AdKleinDrawAdListener,
        OnRefreshLoadMoreListener {
    private KleinSmartRefreshLayout refreshLayout;
    private DrawVodAdAdapter mAdapter;
    private RecyclerView rvList;
    private List<DrawVodAdBean> dataList = new ArrayList<>();
    private int refreshType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_ad);
        initView();
        initData();
    }

    private void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL));
        mAdapter = new DrawVodAdAdapter();
        rvList.setAdapter(mAdapter);
    }

    private void initData() {
        kleinDraw = new AdKleinDrawAd(this,
                DemoConstants.DRAW_ID,
                this,
                getScreenWidth(),
                0.0F);
        refreshLayout.autoRefresh();
    }

    @Override
    public void onAdLoaded(List<AdKleinDrawVodAdListener> list) {
        for (int i = 0; i < list.size(); i++) {
            int index = i * 3;
            AdKleinDrawVodAdListener data = list.get(i);
            if (index <= dataList.size()) {
                dataList.add(index, new DrawVodAdBean(data));
            } else {
                dataList.add(new DrawVodAdBean(data));
            }
        }
        mAdapter.addData(dataList);
        refreshLayout.finish(refreshType, true, false);
    }

    @Override
    public void onError(final AdKleinError adKleinError) {
        ToastUtils.toast(this, "draw ad error" +
                adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
        refreshLayout.finish(refreshType, false, false);
    }

    @Override
    public void onAdShow() {
        ToastUtils.toast(this, "draw ad show", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdClicked() {
        ToastUtils.toast(this, "draw ad clicked",
                Toast.LENGTH_SHORT);
    }

    @Override
    public void onRenderSuccess(final View view, final float v, final float v1) {
    }

    @Override
    public void onRenderFail(final AdKleinError adKleinError) {
        ToastUtils.toast(this, "draw ad render fail" +
                        " " + adKleinError.getErrorMsg() + " " + adKleinError.getErrorCode(),
                Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdClose() {
        ToastUtils.toast(this, "draw ad close", Toast.LENGTH_SHORT);
    }

    private float getScreenWidth() {
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        return (float) screenSize.x;
    }

    private AdKleinDrawAd kleinDraw;

    private void mockNormalDataRequest() {
        int itemCount = mAdapter.getItemCount();
        for (int i = 0; i < 9; i++) {
            dataList.add(new DrawVodAdBean("Normal Data Page"));
            mAdapter.notifyItemInserted(itemCount + i);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshType = KleinSmartRefreshLayout.TYPE_LOAD_MORE;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshType = KleinSmartRefreshLayout.TYPE_FRESH;
        dataList.clear();
        mAdapter.clearData();
        loadData();
    }

    /**
     * 加载数据和广告
     */
    private void loadData() {
        dataList.clear();
        mockNormalDataRequest();
        kleinDraw.load(DemoConstants.AD_COUNT);
    }
}