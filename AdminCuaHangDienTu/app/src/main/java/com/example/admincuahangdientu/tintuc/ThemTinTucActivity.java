package com.example.admincuahangdientu.tintuc;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admincuahangdientu.MainActivity;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.model.MessageModel;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTinTucActivity extends AppCompatActivity {
    private Spinner spinnerDM;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;
    private List<LoaiSp> mangLoaiSp = new ArrayList<>();
    private AppCompatButton btnAdd, btnKtraLink;
    private int loaisp = 0;

    private TextView errorTieude, errorHinh, errorNoidung,errorIdsp;
    private ImageView openHinhAnh, img;
    private EditText hinhanh, tieude, noidung, idsp;
    private String mediaPath;

    private FrameLayout progressBar;
    private ScrollView scrollView;
    private Toolbar toolbar;

    String tieuDeTT, noiDungTT, hinhAnhTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tin_tuc);

        AnhXaView();
        ActionToobar();
        progressBar.setVisibility(View.GONE);
        setGoneTextError();
        SpinnerDanhMuc();

        ActionButton();
    }

    private void ActionButton() {
        btnAdd.setOnClickListener(view -> {
            if(checkTextEmptySP()){
                if(checkLengthTv()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Bạn Muốn Thêm Tin Tức Này?");
                    builder.setMessage("\nTiêu đề: " + tieude.getText().toString().trim());
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addTinTuc();
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });
        openHinhAnh.setOnClickListener(view -> {
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(2048)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

    }

    private void addTinTuc() {
        progressBar.setVisibility(View.VISIBLE);
        String idSpTT = idsp.getText().toString().trim();
        if(TextUtils.isEmpty(idSpTT)){
            idSpTT = "0";
        }
        String thoigian = DbQuery.getNgayGio();
        compositeDisposable.add(apiStore.themTinTucMoi("add",tieuDeTT,hinhAnhTT,noiDungTT,loaisp,Integer.parseInt(idSpTT),thoigian)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                uploadMultipleFiles();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "no connect with server " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "no connect with server " + throwable.getMessage());
                        }
                )
        );
    }

    private boolean checkLengthTv() {
        setGoneTextError();
        int dem = 0;
        if(tieuDeTT.length()<=10)
        {
            setTextError(errorTieude, "tiêu đề quá ngắn");
            dem++;
        }
        if(noiDungTT.length()<=10){
            setTextError(errorNoidung, " nội dung quá ngắn");
            dem++;
        }

        return dem == 0;
    }

    private boolean checkTextEmptySP() {
        setGoneTextError();
        tieuDeTT = tieude.getText().toString().trim();
        noiDungTT = noidung.getText().toString().trim();
        hinhAnhTT = hinhanh.getText().toString().trim();
        int dem = 0;
        if(TextUtils.isEmpty(tieuDeTT)){
            setTextError(errorTieude, "tiêu đề trống");
            dem++;
        }
        if(TextUtils.isEmpty(noiDungTT)){
            setTextError(errorNoidung, "nội dung trống");
            dem++;
        }
        if(TextUtils.isEmpty(hinhAnhTT)){
            setTextError(errorHinh, "hình ảnh trống");
            dem++;
        }

        return dem == 0;
    }

    private void setTextError(TextView txt, String str)
    {
        txt.setText(str);
        txt.setVisibility(View.VISIBLE);
    }


    private void setGoneTextError() {
        errorIdsp.setVisibility(View.GONE);
        errorNoidung.setVisibility(View.GONE);
        errorTieude.setVisibility(View.GONE);
        errorHinh.setVisibility(View.GONE);
    }

    private void ActionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm Tin Tức");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);

        spinnerDM = findViewById(R.id.add_danhmuc);
        btnAdd = findViewById(R.id.btnAddTT);
        openHinhAnh = findViewById(R.id.addTT_openImage);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.scrollView2);
        toolbar = findViewById(R.id.toolbar);

        img = findViewById(R.id.addTT_img);

        tieude = findViewById(R.id.addTT_tieude);
        hinhanh = findViewById(R.id.addTT_edHinhanh);
        noidung = findViewById(R.id.addTT_noidung);
        idsp = findViewById(R.id.addTT_edIdsp);

        errorTieude = findViewById(R.id.txtErrorTieuDe);
        errorHinh = findViewById(R.id.txtErrorHinhanh);
        errorNoidung = findViewById(R.id.txtErrorNoidung);
        errorIdsp = findViewById(R.id.txtErrorIdsp);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null){
            mediaPath = data.getDataString();
            if(!TextUtils.isEmpty(mediaPath)){
                Uri uri = Uri.parse(mediaPath);
                File file = new File(getPath(uri));
                hinhanh.setText(file.getName());
                img.setImageURI(uri);
                img.setVisibility(View.VISIBLE);
            }

        }
        // uploadMultipleFiles();
        super.onActivityResult(requestCode, resultCode, data);

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

    private void SpinnerDanhMuc(){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
        compositeDisposable.add(apiStore.getLoaiSp("getloaisp")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess())
                            {
                                mangLoaiSp = loaiSpModel.getResult();
                                List<String> ldm = new ArrayList<>();
                                for(int i = 0;i<mangLoaiSp.size();i++){
                                    ldm.add(mangLoaiSp.get(i).getTensanpham());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_danhmucsp, ldm);
                                spinnerDM.setAdapter(adapter);
                                spinnerDM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        loaisp = i+1;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                                btnAdd.setVisibility(View.VISIBLE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );



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
                        progressBar.setVisibility(View.GONE);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                        //Toast.makeText(getApplicationContext(), "hinh ok!", Toast.LENGTH_SHORT).show();
                    } else {


                        //Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", serverResponse.toString());
                }
            }
            @Override
            public void onFailure(Call < MessageModel > call, Throwable t) {
                Log.d("loi", t.getMessage());
            }
        });
    }

}