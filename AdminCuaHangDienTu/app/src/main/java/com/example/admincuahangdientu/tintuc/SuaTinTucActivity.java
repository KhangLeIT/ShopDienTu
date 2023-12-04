package com.example.admincuahangdientu.tintuc;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

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

import com.bumptech.glide.Glide;
import com.example.admincuahangdientu.MainActivity;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.model.MessageModel;
import com.example.admincuahangdientu.model.TinTuc;
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

public class SuaTinTucActivity extends AppCompatActivity {
    private Spinner spinnerDM;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;
    private List<LoaiSp> mangLoaiSp = new ArrayList<>();
    private AppCompatButton btnAdd, btnKtraLink;
    private int loaisp = 1;

    private TextView errorTieude, errorHinh, errorNoidung,errorIdsp;
    private ImageView openHinhAnh, img;
    private EditText hinhanh, tieude, noidung, idsp;
    private String mediaPath;

    private FrameLayout progressBar;
    private ScrollView scrollView;
    private Toolbar toolbar;

    boolean checkChangeImg = false;
    String noiDungUpdate = "";
    String tieuDeTT, noiDungTT, hinhAnhTT;
    int idSpTT = 0;
    private TinTuc editTT = DbQuery.select_edit_tintuc;
    private String anhDelete;
    boolean changeTD, changeND, changeIMG, changeIdSp, changeIddm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tin_tuc);
        AnhXaView();
        ActionToobar();
        loaisp = editTT.getIddm();
        DbQuery.tintuc_edit_change_YesOrNo = false;
        progressBar.setVisibility(View.GONE);
        setGoneTextError();
        setTongTinEdit();
        SpinnerDanhMuc();
        ActionButton();
    }

    private void setTongTinEdit() {
        anhDelete = editTT.getHinhanh();

        tieude.setText(editTT.getTieude());
        hinhanh.setText(editTT.getHinhanh());
        Glide.with(getApplicationContext()).load(Utils.BASE_URL+"../Hinhanh/"+editTT.getHinhanh()).into(img);
        img.setVisibility(View.VISIBLE);
        int id = editTT.getIdsp();
        String idOfSp = String.valueOf(id);
        if(id == 0){
            idOfSp = "";
        }
        idsp.setText(idOfSp);
        noidung.setText(editTT.getNoidung());
    }

    private void ActionButton() {
        openHinhAnh.setOnClickListener(view -> {
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(2048)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        btnAdd.setOnClickListener(view -> {
            if(checkTextEmptySP())
            {
                if(checkLengthTv()){
                    if(addThongTinUpdate()){
                        actionUpdateTinTuc();
                        if(checkChangeImg){
                            progressBar.setVisibility(View.VISIBLE);
                            uploadMultipleFiles();
                            deleteAnhStoreSp(anhDelete);
                        }
                    }
                }
            }
        });


    }

    private boolean addThongTinUpdate() {
        changeIdSp = false; changeIMG = false; changeND = false; changeTD = false;
        checkChangeImg = false; changeIddm = false;
        noiDungUpdate = " `id` = " + String.valueOf(editTT.getId());
        String editTieuDe, editNoiDung, editHinhanh;
        editTieuDe = editTT.getTieude();
        editNoiDung = editTT.getNoidung();
        editHinhanh = editTT.getHinhanh();
        int editIdSp = editTT.getIdsp();
        int editIddm = editTT.getIddm();
        int dem =0;
        if(!editTieuDe.equals(tieuDeTT)){
            noiDungUpdate = noiDungUpdate + " , `tieude` = '"+tieuDeTT+"' ";
            changeTD = true;
            dem++;
        }
        if(!editNoiDung.equals(noiDungTT)){
            noiDungUpdate = noiDungUpdate + " , `noidung` = '"+noiDungTT+"' ";
            changeND = true;
            dem++;
        }
        if(!editHinhanh.equals(hinhAnhTT)){
            checkChangeImg = true;
            changeIMG = true;
            noiDungUpdate = noiDungUpdate + " , `hinhanh` = '"+hinhAnhTT+"' ";
            dem++;
        }
        if(editIdSp != idSpTT){
            changeIdSp = true;
            noiDungUpdate = noiDungUpdate + " , `idsp` = '"+ String.valueOf(idSpTT)+"' ";
            dem++;
        }
        if(editIddm != loaisp){
            changeIddm = true;
            noiDungUpdate = noiDungUpdate + " , `iddm` = '"+ String.valueOf(loaisp) + "' ";
            dem++;
        }
        //Toast.makeText(this, "ediddm "+ String.valueOf(editIddm) + "// loaisp "+ String.valueOf(loaisp), Toast.LENGTH_SHORT).show();
        return dem !=0;

    }

    private void actionUpdateTinTuc() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.updateTinTuc("update",editTT.getId(),noiDungUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                DbQuery.tintuc_edit_change_YesOrNo = true;
                                if(changeTD) DbQuery.select_edit_tintuc.setTieude(tieuDeTT);
                                if(changeIMG) DbQuery.select_edit_tintuc.setHinhanh(hinhAnhTT);
                                if(changeIdSp) DbQuery.select_edit_tintuc.setIdsp(idSpTT);
                                if(changeND) DbQuery.select_edit_tintuc.setNoidung(noiDungTT);
                                if(changeIddm) DbQuery.select_edit_tintuc.setIddm(loaisp);
                                Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if(!checkChangeImg) finish();
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                ));
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
        if(TextUtils.isEmpty(idsp.getText().toString().trim())){
            idSpTT = 0;
        }
        else {
            idSpTT = Integer.parseInt(idsp.getText().toString().trim());
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

    private void deleteAnhStoreSp( String img) {

        compositeDisposable.add(apiStore.deleteAnhStore("deleteHinh", img)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
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
                                spinnerDM.setSelection(loaisp-1);

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