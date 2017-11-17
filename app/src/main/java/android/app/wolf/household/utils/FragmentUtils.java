package android.app.wolf.household.utils;

import android.app.wolf.household.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by lh on 2017/10/11.
 * <p>
 * 功能作用：用户点击按钮时切换fragment
 * <p>
 * 修改记录：
 */
public class FragmentUtils {

    public static void judgeToShow(FragmentManager fragmentManager, Fragment fragment) {

        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment f : fragments) {
            if (!f.isHidden()) {
                fragmentManager.beginTransaction()
                        .hide(f)
                        .commit();
            }
        }

        if (!fragment.isAdded()) { //如果fragment没有被添加
            fragmentManager.beginTransaction()
                    .add(R.id.main_fragmentlayout, fragment) //添加这个fragment
                    .commit(); //提交
        } else { //如果fragment已经被添加
            fragmentManager.beginTransaction()
                    .show(fragment) //展示这个fragment
                    .commit(); //提交
        }
    }

}
