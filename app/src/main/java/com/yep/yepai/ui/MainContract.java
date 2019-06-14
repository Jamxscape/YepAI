package com.yep.yepai.ui;

import android.graphics.Bitmap;

import com.yep.yepai.bean.FaceppBean;

import java.util.List;

/**
 * All rights belong to YEP@author chaochaowu
 */
public interface MainContract {

    interface View{

        void showProgress();
        void hideProgress();
        void displayPhoto(Bitmap photo);
        void displayFaceInfo(List<FaceppBean.FacesBean> faces);

    }

    interface Presenter{
        void getDetectResultFromServer(Bitmap photo);
    }
}
