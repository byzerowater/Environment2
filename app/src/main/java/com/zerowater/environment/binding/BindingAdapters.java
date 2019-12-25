package com.zerowater.environment.binding;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.zerowater.environment.ui._base.BaseViewModelAdapter;

import java.util.List;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-24.
 * Zero Ltd
 * byzerowater@gmail.com
 * BindingAdapters
 */
public class BindingAdapters {
    /**
     * 뷰 가시성 상태 설정
     *
     * @param view View
     * @param gone true 미노출, false 노출
     */
    @BindingAdapter("visibleGone")
    public static void setVisibleGone(final View view, final boolean gone) {
        view.setVisibility(gone ? View.GONE : View.VISIBLE);
    }


    /**
     * RecyclerViewBindingAdapters
     */
    @InverseBindingMethods({
            @InverseBindingMethod(type = RecyclerView.class,
                    attribute = "android:recyclerViewAdapter"),
            @InverseBindingMethod(type = RecyclerView.class,
                    attribute = "android:supportsChangeAnimations"),
            @InverseBindingMethod(type = RecyclerView.class,
                    attribute = "android:itemAnimator"),
            @InverseBindingMethod(type = RecyclerView.class,
                    attribute = "android:bindData")
    })
    public static class RecyclerViewBindingAdapters {

        /**
         * 어답터 설정
         *
         * @param recyclerView RecyclerView
         * @param adapter      설정할 어답터
         */
        @BindingAdapter("android:recyclerViewAdapter")
        public static void setRecyclerViewAdapter(final RecyclerView recyclerView, final RecyclerView.Adapter adapter) {
            if (adapter != null) {
                recyclerView.setAdapter(adapter);
            }
        }

        /**
         * 애니메이션 지원 여부 설정
         *
         * @param recyclerView             RecyclerView
         * @param supportsChangeAnimations true 애니메이션 지원, false 애니메이션 미지원.
         */
        @BindingAdapter("android:supportsChangeAnimations")
        public static void setSupportsChangeAnimations(final RecyclerView recyclerView, boolean supportsChangeAnimations) {
            Timber.i("supportsChangeAnimations %s", supportsChangeAnimations);
            RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
            if (animator != null && animator instanceof SimpleItemAnimator) {
                Timber.i("SimpleItemAnimator %s", supportsChangeAnimations);
                ((SimpleItemAnimator) animator).setSupportsChangeAnimations(supportsChangeAnimations);
            }
        }

        /**
         * 애니메이션 설정
         *
         * @param recyclerView    RecyclerView
         * @param useItemAnimator true DefaultItemAnimator 설정, false 미설정
         */
        @BindingAdapter("android:itemAnimator")
        public static void setItemAnimator(final RecyclerView recyclerView, boolean useItemAnimator) {
            Timber.i("useItemAnimator %s", useItemAnimator);
            if (useItemAnimator) {
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            } else {
                recyclerView.setItemAnimator(null);
            }
        }

        /**
         * 아이템 설정
         *
         * @param recyclerView RecyclerView
         * @param adapter      RecyclerView.Adapter
         * @param dataList     설정할 아이템
         */
        @BindingAdapter({"android:recyclerViewAdapter", "android:bindData"})
        public static void bindData(RecyclerView recyclerView, RecyclerView.Adapter adapter, List dataList) {
            RecyclerView.Adapter oldAapater = recyclerView.getAdapter();
            if (oldAapater != null) {
                adapter = oldAapater;
            } else {
                recyclerView.setAdapter(adapter);
            }
            if (adapter != null && adapter instanceof BaseViewModelAdapter) {
                ((BaseViewModelAdapter) adapter).dispatchUpdatesTo(dataList);
            }
        }

    }

    /**
     * TabLayoutBindingAdapters
     */
    @InverseBindingMethods({
            @InverseBindingMethod(type = TabLayout.class,
                    attribute = "android:setupWithViewPager")
    })
    public static class TabLayoutBindingAdapters {

        /**
         * ViewPager 연결
         *
         * @param tabLayout TabLayout
         * @param viewPager 연결할 ViewPager
         */
        @BindingAdapter("android:setupWithViewPager")
        public static void setupWithViewPager(final TabLayout tabLayout, final ViewPager viewPager) {
            if (viewPager != null) {
                tabLayout.setupWithViewPager(viewPager);
            }
        }
    }

    /**
     * ViewPagerBindingAdapters
     */
    @InverseBindingMethods({
            @InverseBindingMethod(type = ViewPager.class,
                    attribute = "android:viewPagerAdapter"),
            @InverseBindingMethod(type = ViewPager.class,
                    attribute = "android:offscreenPageLimit"),
            @InverseBindingMethod(type = ViewPager.class,
                    attribute = "android:currentPosition",
                    event = "android:currentPositionAttrChanged")
    })
    public static class ViewPagerBindingAdapters {

        /**
         * 어답터 설정
         *
         * @param viewPager    ViewPager
         * @param pagerAdapter 설정할 어답터
         */
        @BindingAdapter("android:viewPagerAdapter")
        public static void setViewPagerAdapter(final ViewPager viewPager, final PagerAdapter pagerAdapter) {
            if (pagerAdapter != null) {
                viewPager.setAdapter(pagerAdapter);
            }
        }

        /**
         * 유지해야하는 페이지 수 설정
         *
         * @param viewPager ViewPager
         * @param limit     유지해야하는 페이지 수
         */
        @BindingAdapter("android:offscreenPageLimit")
        public static void setOffscreenPageLimit(final ViewPager viewPager, final int limit) {
            viewPager.setOffscreenPageLimit(limit);
        }

        /**
         * 현재 보여지는 페이지 정보 가져오기
         *
         * @param viewPager              ViewPager
         * @param inverseBindingListener 현재 페이지 정보 리스너
         */
        @BindingAdapter(value = {"android:currentPositionAttrChanged"}, requireAll = false)
        public static void onPageChangeCallback(final ViewPager viewPager, InverseBindingListener inverseBindingListener) {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    inverseBindingListener.onChange();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        /**
         * 현재 보여지는 페이지 위치 가져오기
         *
         * @param viewPager ViewPager
         * @return 현재 보여지는 페이지 위치
         */
        @InverseBindingAdapter(attribute = "android:currentPosition", event = "android:currentPositionAttrChanged")
        public static int getCurrentPosition(final ViewPager viewPager) {
            return viewPager.getCurrentItem();
        }

        /**
         * 보여질 페이지 위치 설정
         *
         * @param viewPager ViewPager
         * @param position  보여질 페이지 위치
         */
        @BindingAdapter("android:currentPosition")
        public static void setCurrentPosition(final ViewPager viewPager, final int position) {
            viewPager.setCurrentItem(position, false);
        }
    }

    /**
     * NestedScrollViewViewBindingAdapters
     */
    @InverseBindingMethods({
            @InverseBindingMethod(type = NestedScrollView.class,
                    attribute = "openKeyboard")
    })
    public static class NestedScrollViewViewBindingAdapters {
        /**
         * 키보드 상태에 따라 스크롤 설정
         *
         * @param nestedScrollView NestedScrollView
         * @param isOpen           키보드 열린 상태
         */
        @BindingAdapter("openKeyboard")
        public static void setOpenKeyboard(NestedScrollView nestedScrollView, boolean isOpen) {
            if (nestedScrollView != null) {
                if (isOpen) {
                    nestedScrollView.fullScroll(View.FOCUS_DOWN);
                }
            }
        }
    }

}
