package com.wlmtest.forecasttestjava.view.forecast;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays.Forecast5DaysFragment;
import com.wlmtest.forecasttestjava.view.forecast.tabs.today.ForecastTodayFragment;

public class ForecastTabAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public ForecastTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ForecastTodayFragment();
            case 1:
                return new Forecast5DaysFragment();
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}