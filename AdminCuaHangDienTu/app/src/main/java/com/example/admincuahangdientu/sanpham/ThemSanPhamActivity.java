package com.example.admincuahangdientu.sanpham;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admincuahangdientu.MainActivity;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.YoutubeActivity;
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

public class ThemSanPhamActivity extends AppCompatActivity {
    private Spinner spinnerDM;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;
    private List<LoaiSp> mangLoaiSp = new ArrayList<>();
    private AppCompatButton btnAdd, btnKtraLink;
    private int loaisp = 0;

    private TextView errorTen, errorHinh, errorGia, errorSl, errorMota, errorLink, errorThongtin;
    private ImageView openHinhAnh, img;
    private EditText hinhanh, solco, tensp, giaban, mota, thongtin, linkvideo;
    private String mediaPath;

    private FrameLayout progressBar;
    private ScrollView scrollView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

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
                    builder.setTitle("Bạn Muốn Thêm Sản Phẩm Này?");
                    builder.setMessage("\nTên: " + tensp.getText().toString().trim()+ "\nGiá bán: " +giaban.getText().toString().trim());
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addSanPham();
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

        btnKtraLink.setOnClickListener(view ->{
            if(!linkvideo.getText().toString().trim().isEmpty()){
                DbQuery.linkVideo_check = linkvideo.getText().toString().trim();
                Intent i = new Intent(getApplicationContext(), YoutubeActivity.class);
                startActivity(i);
            }
            else{
                setTextError(errorLink, "Link video trống");
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

    private boolean checkLengthTv(){
        String ten, mta, thtin, link;

        link = linkvideo.getText().toString().trim();
        ten = tensp.getText().toString().trim();
        mta = mota.getText().toString().trim();
        thtin = thongtin.getText().toString().trim();
        int dem = 0;
        if(ten.length() <=10){
            setTextError(errorTen, "tên sản phẩm quá ngắn");
            dem++;
        }
        if(link.length()<=10){
            setTextError(errorLink,"Link Youtube không hợp lê");
        }
        if(mta.length()<=10)
        {
            setTextError(errorMota, "mô tả quá ngắn");
            dem++;
        }
        if(thtin.length()<=10){
            setTextError(errorThongtin, "thông tin quá ngắn");
            dem++;
        }
        if(dem == 0)
        {
            setGoneTextError();
            return true;
        }
        else{
            return false;
        }
    }

    private void setGoneTextError(){

        errorTen.setVisibility(View.GONE);
        errorHinh.setVisibility(View.GONE);
        errorGia.setVisibility(View.GONE);
        errorSl.setVisibility(View.GONE);
        errorMota.setVisibility(View.GONE);
        errorLink.setVisibility(View.GONE);
        errorThongtin.setVisibility(View.GONE);
    }


    private boolean checkTextEmptySP(){
        String ten,hinh,mta, link, thtin, gia, sl;

        ten = tensp.getText().toString().trim();
        hinh = hinhanh.getText().toString().trim();
        gia = giaban.getText().toString().trim();
        sl = solco.getText().toString().trim();
        mta = mota.getText().toString().trim();
        link = linkvideo.getText().toString().trim();
        thtin = thongtin.getText().toString().trim();

        int dem = 0;
        if(TextUtils.isEmpty(ten)){
           setTextError(errorTen, "tên sản phẩm trống");
            dem++;
        }
        if(TextUtils.isEmpty(hinh))
        {
            setTextError(errorHinh, "hình ảnh trống");
            dem++;
        }
        if(TextUtils.isEmpty(gia))
        {
            setTextError(errorGia, "gia sản phẩm trống");
            dem++;
        }
        if(TextUtils.isEmpty(sl))
        {
            setTextError(errorSl,"số lượng trống");
            dem++;
        }
        if(TextUtils.isEmpty(mta))
        {
            setTextError(errorMota, "mô tả trống");
            dem++;
        }
        if(TextUtils.isEmpty(thtin))
        {
            setTextError(errorThongtin, "thông tin trống");
            dem++;
        }

        if(dem == 0){
            setGoneTextError();
            return true;
        }
        else{
            return false;
        }

    }
    private void setTextError(TextView txt, String str)
    {
        txt.setText(str);
        txt.setVisibility(View.VISIBLE);
    }

    private void addSanPham() {
        progressBar.setVisibility(View.VISIBLE);
        String ten,hinh,mta, link, thtin;
        float gia;
        int sl;
        ten = tensp.getText().toString().trim();
        hinh = hinhanh.getText().toString().trim();
        gia = Float.parseFloat(giaban.getText().toString().trim());
        sl = Integer.parseInt(solco.getText().toString().trim());
        mta = mota.getText().toString().trim();
        link = linkvideo.getText().toString().trim();
        thtin = thongtin.getText().toString().trim();
        String linksp;
        if(TextUtils.isEmpty(link)){
            linksp ="NULL";
        }
        else{
            linksp = "'"+link+"'";
        }

        compositeDisposable.add(apiStore.themsanpham("add",ten,hinh,mta,thtin,gia,loaisp,sl,linksp)
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

    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        spinnerDM = findViewById(R.id.add_danhmuc);
        btnAdd = findViewById(R.id.btnAddSp);

        openHinhAnh = findViewById(R.id.addsp_openImage);
        hinhanh = findViewById(R.id.addsp_edHinhanh);
        tensp = findViewById(R.id.addsp_tensp);
        giaban = findViewById(R.id.addsp_giasp);
        solco = findViewById(R.id.addsp_slco);
        mota = findViewById(R.id.addsp_mota);
        linkvideo = findViewById(R.id.addsp_linkvideo);
        thongtin = findViewById(R.id.addsp_thongtin);

        progressBar = findViewById(R.id.progressBar);

        errorTen = findViewById(R.id.txtErrorTensp);
        errorHinh = findViewById(R.id.txtErrorHinhanh);
        errorGia = findViewById(R.id.txtErrorGiaban);
        errorSl = findViewById(R.id.txtErrorSl);
        errorMota = findViewById(R.id.txtErrorMota);
        errorLink = findViewById(R.id.txtErrorLinkvideo);
        errorThongtin = findViewById(R.id.txtErrorThongtin);

        btnKtraLink = findViewById(R.id.addsp_ktravideo);
        scrollView = findViewById(R.id.scrollView2);

        toolbar = findViewById(R.id.toolbar);

        img = findViewById(R.id.addsp_img);


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
    // Uploading Image/Video
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


}