package com.example.admincuahangdientu.sanpham;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admincuahangdientu.QuanLySanPhamFragment;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.model.MessageModel;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.text.ParsePosition;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaDanhMucActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;
    private AppCompatButton btnAdd;
    private EditText hinhanh, tenDM;
    private ImageView openImg, img;
    private Toolbar toolbar;
    String mediaPath;
    private LoaiSp editDM = DbQuery.select_edit_danhmuc;
    String tendanhmuc, hinhdanhmuc;
    String querySet ="";

    boolean chageImg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_danh_muc);
        AnhXaView();
        ActionToobar();
        ActionButton();
        setDataDanhMuc();

    }

    private void setDataDanhMuc() {
        tenDM.setText(editDM.getTensanpham());
        Glide.with(this).load(Utils.BASE_URL +"../Hinhanh/"+ editDM.getHinhanh()).into(img);
        hinhanh.setText(editDM.getHinhanh());
    }

    private void ActionButton() {
        openImg.setOnClickListener(view -> {
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(2048)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        btnAdd.setOnClickListener(view -> {
            if(TextUtils.isEmpty(tenDM.getText().toString().trim())){
                tenDM.setError("tên danh mục trống");
            }
            else if(TextUtils.isEmpty(hinhanh.getText().toString().trim())){
                tenDM.setError("hình ảnh trống");
            }
            else {
                SuaDanhMuc("editDanhMuc");
            }
        });
    }

    private void SuaDanhMuc(String str) {
        tendanhmuc = editDM.getTensanpham();
        hinhdanhmuc = editDM.getHinhanh();
        String tenDMuc = tenDM.getText().toString().trim();
        String hinhImg = hinhanh.getText().toString().trim();

        if(!tenDMuc.equals(tendanhmuc)){
            querySet = querySet + ",tensanpham = '"+tenDMuc+"'";
        }
        if(!hinhdanhmuc.equals(hinhImg)){
            querySet = querySet + ", hinhanh = '"+hinhImg+"'";
            chageImg = true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(tenDMuc.equals(tendanhmuc) && hinhImg.equals(hinhdanhmuc)){
            builder.setTitle("Danh Mục chưa edit?");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }
        else {
            builder.setTitle("Bạn Muốn Sửa Danh mục Này?");
            builder.setMessage("\nTên: " + tenDMuc);
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    updateDanhMuc();
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

        }
        builder.show();

    }

    private void updateDanhMuc() {
        int iddm = editDM.getId();
        compositeDisposable.add(apiStore.updateDanhMuc("update",iddm,querySet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                if(chageImg){
                                    uploadMultipleFiles();
                                    deleteAnhStoreDM(hinhdanhmuc);
                                }
                            }

                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );

    }


    private void deleteAnhStoreDM( String img) {
        compositeDisposable.add(apiStore.deleteAnhStore("deleteHinh", img)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                Toast.makeText(this, "delete hinh", Toast.LENGTH_SHORT).show();
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private void ActionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm Sản Phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null){
            mediaPath = data.getDataString();
            // External sdcard location
            if(!TextUtils.isEmpty(mediaPath)){
                Uri uri = Uri.parse(mediaPath);
                File file = new File(getPath(uri));
                hinhanh.setText(file.getName());
                img.setImageURI(uri);
                img.setVisibility(View.VISIBLE);
            }
            else {
                img.setVisibility(View.GONE);
                hinhanh.setText("");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        toolbar = findViewById(R.id.toolbar);
        btnAdd = findViewById(R.id.btnAddDM);
        hinhanh = findViewById(R.id.addDM_edHinhanh);
        tenDM = findViewById(R.id.addDM_tenDM);
        openImg = findViewById(R.id.addDM_openImage);

        img = findViewById(R.id.addDM_img);
    }

    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri,null, null, null, null);
        if(cursor == null){
            result = uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return  result;
    }

    private void uploadMultipleFiles() {
        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));

        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        Call<MessageModel> call = apiStore.updatefile(fileToUpload1);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        //Toast.makeText(getApplicationContext(), "hinh ok!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.v("Response", serverResponse.getMessage());
                        //Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onResponse: khong thanh cong");
                }
            }
            @Override
            public void onFailure(Call < MessageModel > call, Throwable t) {
                Log.d("loi", t.getMessage());
            }
        });
    }

}