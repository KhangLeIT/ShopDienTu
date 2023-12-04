package com.example.cuahangdientu.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.ErrorAdapter;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtUserName, txtSDT, txtEmail, txtPass, txtConfirmPass;
    private Button btnSignUp;
    private ApiStore apiStore;
    private TextView tvCheckText;
    private ProgressBar progressBar;
    private RadioButton cbNam, cbNu;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CountryCodePicker ccp;
    private ImageView backB;
    private List<String> listError;
    private ErrorAdapter errorAdapter;
    private RecyclerView rec_error;

    private int gioiTinh = 1;
    private boolean select_GioiTinh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AnhXaView();
        ActionButton();
    }

    private void ActionButton() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkTextEmpty())
                {
                    if(checkText()){
                        progressBar.setVisibility(View.VISIBLE);
                        setSupportMess("Vui Lòng Chờ");
                        tvCheckText.setTextColor(Color.BLUE);
                        ActionDangKyUser();
                    }
                }

            }
        });
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean checkSDT()
    {
        ccp.registerCarrierNumberEditText(txtSDT);
        return ccp.isValidFullNumber();
    }
    private boolean checkText()
    {
        tvCheckText.setTextColor(Color.RED);
        listError = new ArrayList<>();
        int dem = 0;
        if(!checkEmail(txtEmail.getText().toString().trim())){
            listError.add("email chưa đúng");
            dem ++;
        }
        if(!checkSDT()) {
            listError.add("số điện thoại chưa đúng");
            dem++;
        }
        if(txtPass.getText().toString().trim().compareTo(txtConfirmPass.getText().toString().trim()) !=0)
        {
            listError.add("mật khẩu chưa khớp");
            dem++;
        }
        if(txtPass.getText().toString().trim().length()<6||txtPass.getText().toString().trim().length()>10)
        {
            listError.add("độ dài mật khẩu từ 6 đến 10");
            dem++;
        }
        if(txtEmail.getText().toString().trim().contains(" "))
        {
            listError.add("Email không chứa khoản cách");
            dem++;
        }
        if(txtPass.getText().toString().trim().contains(" "))
        {
            listError.add("mật khẩu không chứa khoản cách");
            dem++;
        }
        if(dem == 0) return true;
        else {
            setListError();
            setSupportMess(String.valueOf(listError.size()));
            return false;
        }
    }


    private boolean checkEmail(String email)
    {
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void setSupportMess(String str)
    {
        tvCheckText.setText(str);
        tvCheckText.setTextColor(Color.RED);
        tvCheckText.setVisibility(View.VISIBLE);
    }

    private boolean checkTextEmpty()
    {
        tvCheckText.setTextColor(Color.RED);
        int dem = 0;
        listError = new ArrayList<>();
        String name = txtUserName.getText().toString().trim();
        String sdt = txtSDT.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String pass = txtPass.getText().toString().trim();
        String con_pass = txtConfirmPass.getText().toString().trim();
        if(name.isEmpty())
        {
            listError.add("Chưa Nhập Tên Của Bạn");
            dem ++;
        }
        if(email.isEmpty())
        {

            listError.add("Chưa Nhập Email");
            dem ++;
        }
        if(pass.isEmpty())
        {
            listError.add("Chưa Nhập mật khẩu");
            dem ++;
        }
        if(con_pass.isEmpty())
        {
            listError.add("Chưa Nhập Lại Mật Khẩu");

            dem ++;
        }
        if(sdt.isEmpty())
        {
            listError.add("Chưa Nhập Số Điện Thoại");
            dem ++;
        }
        if(!select_GioiTinh)
        {
            listError.add("bạn chưa chọn giới tính");
            dem++;
        }

        if(dem==0)
        {
            return true;
        }
        else
        {
            setListError();
            setSupportMess(String.valueOf(listError.size()));
            return false;
        }


    }
    private void setListError()
    {
        errorAdapter = new ErrorAdapter(getApplicationContext(), listError);
        rec_error.setAdapter(errorAdapter);
    }

    private void ActionDangKyUser() {
        String name = txtUserName.getText().toString().trim();
        String sdt = txtSDT.getText().toString().trim();
        String email = txtEmail.getText().toString().trim().toLowerCase();

        String pass = txtPass.getText().toString().trim();
        String con_pass = txtConfirmPass.getText().toString().trim();
        compositeDisposable.add(apiStore.dangKyUser(email, pass, sdt, name,gioiTinh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userStoreModel -> {
                            if (userStoreModel.isSuccess())
                            {
                                Paper.book().write("email", email);
                                Paper.book().write("pass", pass);
                                Paper.book().write("NameUser", name);
                                Toast.makeText(this, "đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, BeginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                setSupportMess(userStoreModel.getMessage());
                                progressBar.setVisibility(View.GONE);
                            }

                        },throwable -> Log.e(TAG,  throwable.getMessage())
                )
        );
    }

    private void AnhXaView() {
        Paper.init(this);
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        btnSignUp = findViewById(R.id.signupB);
        txtUserName = findViewById(R.id.dk_username);
        txtSDT = findViewById(R.id.dk_sdt);
        txtEmail = findViewById(R.id.dk_emailID);
        txtPass = findViewById(R.id.dk_password);
        txtConfirmPass = findViewById(R.id.dk_confirm_pass);
        backB = findViewById(R.id.backB);

        ccp = findViewById(R.id.countryCodePicker);
        tvCheckText = findViewById(R.id.dn_checkTextMess);
        progressBar = findViewById(R.id.progressBar);

        tvCheckText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        listError = new ArrayList<>();
        rec_error = findViewById(R.id.rec_error);

        cbNam = findViewById(R.id.dk_cbNam);
        cbNu= findViewById(R.id.dk_cbNu);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rec_error.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            SignUpActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked =((RadioButton) view).isChecked();
        switch (view.getId()){

            case R.id.dk_cbNu:
                if(checked){
                    gioiTinh = 0;
                    select_GioiTinh = true;
                }
                break;
            case R.id.dk_cbNam:
                if(checked){
                    gioiTinh = 1;
                    select_GioiTinh = true;
                }
                break;
        }

    }
}