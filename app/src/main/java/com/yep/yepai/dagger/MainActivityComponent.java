package com.yep.yepai.dagger;

import com.yep.yepai.ui.FacePlus;

import dagger.Component;


/**
 * MainActivity 的组件
 * @author chaochaowu
 */
@Component(modules = {MainPresenterModule.class})
public interface MainActivityComponent {
    void inject(FacePlus mainActivity);
}
