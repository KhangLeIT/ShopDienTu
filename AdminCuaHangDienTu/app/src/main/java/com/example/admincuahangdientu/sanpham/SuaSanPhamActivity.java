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
import android.os.Environment;
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
import com.example.admincuahangdientu.YoutubeActivity;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.model.MessageModel;
import com.example.admincuahangdientu.model.SanPham;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaSanPhamActivity extends AppCompatActivity {
    private Spinner spinnerDM;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;
    private List<LoaiSp> mangLoaiSp = new ArrayList<>();
    private AppCompatButton btnSua, btnKtraLink;
    private int loaisp = 0;

    private TextView errorTen, errorHinh, errorGia, errorSl, errorMota, errorLink, errorThongtin;
    private ImageView openHinhAnh, img;
    private EditText hinhanh, solco, tensp, giaban, mota, thongtin, linkvideo;
    private String mediaPath;

    private FrameLayout progressBar;
    private ScrollView scrollView;
    private Toolbar toolbar;

    private SanPham spEdit = DbQuery.select_edit_sanpham;
    String strHinh, strTen, strMota, strThongtin, strLink;
    int strSL;
    float strGia;
    int strIddm;

    String ten,hinh,mta, link, thtin, gia, sl;

    String noidungUpdate = "SET ";
    boolean anhUpdate = false;
    private boolean changeImg, changeTenSp, changeGia, changeSL, changeMoTa, changeLink, changeThongTin, changeIdDM ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        AnhXaView();
        ActionToobar();
        DbQuery.sanpham_edit_change_YesOrNo = false;
        progressBar.setVisibility(View.GONE);
        setGoneTextError();
        loaisp = spEdit.getIddm();
        SpinnerDanhMuc();
        ActionButton();
        setThongTinSanPham();

    }

    private void setThongTinSanPham() {
        strIddm = spEdit.getIddm();
        strHinh = spEdit.getHinhanhsp();
        strTen = spEdit.getTensp();
        strMota = spEdit.getMotasp();
        strThongtin = spEdit.getThongtinsp();
        if(spEdit.getLinkvideo() == null){
            spEdit.setLinkvideo("");
        }
        if(spEdit.getLinkvideo().equals("NULL"))spEdit.setLinkvideo("");
        strLink = spEdit.getLinkvideo();
        strGia = spEdit.getGiabansp();
        strSL = spEdit.getSlco();

        tensp.setText(strTen);
        hinhanh.setText(strHinh);
        Glide.with(getApplicationContext()).load(Utils.BASE_URL+"../Hinhanh/"+strHinh).into(img);

        DecimalFormat formatter = new DecimalFormat("#########");
        giaban.setText(String.valueOf(formatter.format(strGia)));
        solco.setText( String.valueOf(strSL));

        mota.setText(strMota);
        linkvideo.setText(strLink);
        thongtin.setText(strThongtin);


    }



    private void ActionButton() {
        btnSua.setOnClickListener(view -> {
            if(checkTextEmptySP()){
                if(checkLengthTv()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Bạn Muốn Sửa Sản Phẩm Này?");
                    builder.setMessage("\nTên: " + tensp.getText().toString().trim()+ "\nGiá bán: " +giaban.getText().toString().trim());
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(noiDungTextUpdate()){
                                UpdateSanPham();
                                if(anhUpdate) {
                                    uploadMultipleFiles();
                                    String imgDelete = spEdit.getHinhanhsp();
                                    deleteAnhStoreSp(imgDelete);

                                }

                            }
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

    private void deleteAnhStoreSp( String img) {
        compositeDisposable.add(apiStore.deleteAnhStore("deleteHinh", img)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                finish();
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private void UpdateSanPham() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.updatesanpham("update",spEdit.getIdsp(),noidungUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                if(changeIdDM) DbQuery.select_edit_sanpham.setIddm(loaisp);
                                if(changeTenSp) DbQuery.select_edit_sanpham.setTensp(ten);
                                if(changeImg) DbQuery.select_edit_sanpham.setHinhanhsp(hinh);
                                if(changeGia) DbQuery.select_edit_sanpham.setGiabansp(Integer.parseInt(gia));
                                if(changeSL) DbQuery.select_edit_sanpham.setSlco(Integer.parseInt(sl));
                                if(changeMoTa) DbQuery.select_edit_sanpham.setMotasp(mta);
                                if(changeLink) DbQuery.select_edit_sanpham.setLinkvideo(link);
                                if(changeThongTin) DbQuery.select_edit_sanpham.setThongtinsp(thtin);
                                DbQuery.sanpham_edit_change_YesOrNo = true;
                                progressBar.setVisibility(View.GONE);
                                if(!anhUpdate) finish();
                                Toast.makeText(this, "đã sửa sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private boolean noiDungTextUpdate() {
        anhUpdate = false;changeGia = false; changeImg = false; changeTenSp = false;
        changeSL = false;changeMoTa =false;changeLink = false;changeThongTin = false;
        changeIdDM = false;
        noidungUpdate = "`idsp` = '" + spEdit.getIdsp() +"' ";


        ten = tensp.getText().toString().trim();
        hinh = hinhanh.getText().toString().trim();
        gia = giaban.getText().toString().trim();
        sl = solco.getText().toString().trim();
        mta = mota.getText().toString().trim();
        link = linkvideo.getText().toString().trim();
        thtin = thongtin.getText().toString().trim();

        int dem = 0;
        if(!ten.equals(strTen)){
            changeTenSp = true;
            noidungUpdate = noidungUpdate + " ,`tensp` ='"+ten+"' ";
            dem ++;
        }
        if(!hinh.equals(strHinh)){
            changeImg = true;
            noidungUpdate = noidungUpdate + " ,`hinhanhsp` ='"+hinh+"' ";
            anhUpdate = true;
            dem ++;
        }
        if(!mta.equals(strMota)){
            changeMoTa= true;
            noidungUpdate = noidungUpdate + " ,`motasp` ='"+mta+"' ";
            dem ++;
        }
        if(!thtin.equals(strThongtin)){
            changeThongTin = true;
            noidungUpdate = noidungUpdate + " ,`thongtinsp` ='"+thtin+"' ";
            dem ++;
        }
        if(strGia != Float.parseFloat(gia)){
            changeGia = true;
            noidungUpdate = noidungUpdate + " ,`giabansp` ='"+gia+"' ";
            dem ++;
        }
        if(!link.equals(strLink)){
            changeLink = true;
            if(link.isEmpty()){
                noidungUpdate = noidungUpdate + " ,`linkvideo` = NULL ";
            }
            else{
                noidungUpdate = noidungUpdate + " ,`linkvideo` ='"+link+"' ";
            }
            dem ++;

        }
        if(strSL != Integer.parseInt(sl)){
            changeSL = true;
            noidungUpdate = noidungUpdate + " ,`slco` ='"+sl+"' ";
            dem ++;
        }
        if(loaisp != strIddm){
            changeIdDM = true;
            noidungUpdate = noidungUpdate + " ,`iddm` ='"+loaisp+"' ";
            dem ++;
        }

        Log.e("noidungupdate", noidungUpdate);
        return dem != 0;
    }


    private void ActionToobar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Thêm Sản Phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean checkLengthTv(){
        setGoneTextError();
        String ten, mta, thtin, link;

        link = linkvideo.getText().toString().trim();
        ten = tensp.getText().toString().trim();
        mta = mota.getText().toString().trim();
        thtin = thongtin.getText().toString().trim();
        float gia = Float.parseFloat(giaban.getText().toString());
        int dem = 0;
        if(ten.length() <=10){
            setTextError(errorTen, "tên sản phẩm quá ngắn");
            dem++;
        }
        if(!link.isEmpty() && link.length()<=10){
            setTextError(errorLink,"Link Youtube không hợp lê");
        }
        if(mta.length()<=10)
        {
            setTextError(errorMota, "mô tả quá ngắn");
            dem++;
        }
        if(gia==0.0){
            setTextError(errorGia, "giá = 0?");
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
        setGoneTextError();
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
            if(Float.parseFloat(gia) ==0){
                setTextError(errorGia, "giá sản phẩm  = 0 ?");
                dem++;
            }
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


    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);

        spinnerDM = findViewById(R.id.add_danhmuc);
        btnSua = findViewById(R.id.btnAddSp);

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
    // Uploading Image/Video
    private void uploadMultipleFiles() {
        progressBar.setVisibility(View.VISIBLE);
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

    private void SpinnerDanhMuc(){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        btnSua.setVisibility(View.GONE);
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
                                btnSua.setVisibility(View.VISIBLE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }
}