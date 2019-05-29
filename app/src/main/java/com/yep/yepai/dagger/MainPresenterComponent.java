package com.yep.yepai.dagger;

import com.yep.yepai.ui.MainPresenter;

import dagger.Component;

/**
 * MainPresenter 的组件
 * @author chaochaowu
 */
@Component(modules = {FaceppServiceModule.class})
public interface MainPresenterComponent {
    void inject(MainPresenter mainPresenter);
}
