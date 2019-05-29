package com.yep.yepai.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yep.yepai.R;
import com.yep.yepai.tencentChat.Utils;
import com.yep.yepai.adapter.FacesInfoAdapter;
import com.yep.yepai.bean.FaceppBean;
import com.yep.yepai.dagger.DaggerMainActivityComponent;
import com.yep.yepai.dagger.MainPresenterModule;
import com.yep.yepai.eventbus.FaceEvent;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.yep.yepai.util.PathUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 * @author chaochaowu
 */
public class FacePlus extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "FacePlus";
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;

    private static final int CHOOSE_PHOTO=0;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.progressBar)
    ProgressBarCircularIndeterminate progressBar;
    @BindView(R.id.button)
    ButtonRectangle button;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button2)
    ButtonRectangle button2;


    File mTmpFile;
    Uri imageUri;
    Bitmap photo = null;
    public File file = null;

    @Inject
    MainPresenter mPresenter;

    FacesInfoAdapter mAdapter;
    private List<FaceppBean.FacesBean> faces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.face_plus);
        ButterKnife.bind(this);
        DaggerMainActivityComponent.builder()
                .mainPresenterModule(new MainPresenterModule(this))
                .build()
                .inject(this);
        faces = new ArrayList<>();
        faces.add(new FaceppBean.FacesBean());
        mAdapter = new FacesInfoAdapter(this,faces,photo);
        mAdapter.setListener(new FacesInfoAdapter.onItemClickListener() {
            @Override
            public void onItemClicked(FaceppBean.FacesBean face, TextView tvBeauty) {
                gotoDetailActivity(face,tvBeauty);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.button)
    public void onButtonClicked() {
        takePhoto();
    }

    @OnClick(R.id.button2)
    public void onButtonClicked2() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void gotoDetailActivity(FaceppBean.FacesBean face, TextView tvBeauty) {
        if (face.getAttributes() == null){
            return;
        }
        Intent intent = new Intent(this,DetailActivity.class);
        android.support.v4.util.Pair<View, String> image = new android.support.v4.util.Pair(imageView, "image");
        android.support.v4.util.Pair<View, String> beauty = new android.support.v4.util.Pair(tvBeauty, "beauty");
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, image,beauty);
        EventBus.getDefault().postSticky(new FaceEvent(photo,face));
        startActivity(intent,optionsCompat.toBundle());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        return;
                    }
                }
                takePhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    photo = BitmapFactory.decodeFile(mTmpFile.getAbsolutePath(), options);
                    int bitmapDegree = Utils.getBitmapDegree(mTmpFile.getAbsolutePath());
                    if(bitmapDegree != 0){
                        photo = Utils.rotateBitmapByDegree(this.photo, bitmapDegree);
                    }
                    displayPhoto(this.photo);
                    mAdapter.setPhoto(this.photo);
                    mPresenter.getDetectResultFromServer(this.photo);
                    break;
                case CHOOSE_PHOTO:
                    BitmapFactory.Options options2 = new BitmapFactory.Options();
                    options2.inSampleSize = 2;
                    Uri uri = data.getData();
                    Context context = getBaseContext();
                    try {
                        String filePath = PathUtil.getPath(context, uri);
                        file = new File(filePath);
                    }catch (Exception e){
                        System.out.println("error");
                    }
                    photo = BitmapFactory.decodeFile(file.getAbsolutePath(), options2);
                    displayPhoto(this.photo);
                    mAdapter.setPhoto(this.photo);
                    mPresenter.getDetectResultFromServer(this.photo);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void takePhoto() {
        if (!Utils.checkAndRequestPermission(this, PERMISSIONS_REQUEST_CODE)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/img";
        if (new File(path).exists()) {
            try {
                new File(path).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @SuppressLint("SimpleDateFormat")
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mTmpFile = new File(path, filename + ".jpg");
        mTmpFile.getParentFile().mkdirs();
        String authority = getPackageName() + ".provider";
        System.out.println("authority="+ authority);
        System.out.println("mTmpFile="+ mTmpFile);
        imageUri = FileProvider.getUriForFile(this, authority, mTmpFile);
        System.out.println("aimageUri="+imageUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void displayPhoto(Bitmap photo) {
        Glide.with(this).load(photo).into(imageView);
    }

    @Override
    public void displayFaceInfo(List<FaceppBean.FacesBean> faces) {
        this.faces.clear();
        if (faces == null) {
            this.faces.add(new FaceppBean.FacesBean());
            Toast.makeText(this,"未检测到面部信息", Toast.LENGTH_LONG).show();
        }else {
            this.faces.addAll(faces);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        button.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

}
