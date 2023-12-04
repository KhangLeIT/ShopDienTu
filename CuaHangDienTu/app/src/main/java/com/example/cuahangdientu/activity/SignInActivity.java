package com.example.cuahangdientu.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignInActivity extends AppCompatActivity {
    private Button signInB;
    private TextView signUpB, tvCheckText;
    private EditText edEmail, edPass;
    private ProgressBar progressBar;
    private ImageView backB;
    private ApiStore apiStore;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        AnhXaView();
        ActionButton();

        if(DbQuery.mangGioHang != null)
        {
            DbQuery.mangGioHang.clear();
        }


    }
    private void setSupportMess(String str)
    {
        tvCheckText.setText(str);
        tvCheckText.setTextColor(Color.RED);
        tvCheckText.setVisibility(View.VISIBLE);
    }


    private boolean checkText()
    {
        if(edEmail.getText().toString().isEmpty())
        {
            setSupportMess("Vui Lòng Nhập Email");
            edEmail.findFocus();
            return false;
        }
        else if(edPass.getText().toString().isEmpty())
        {
            setSupportMess("Vui Lòng Nhập Mật Khẩu");
            edPass.findFocus();
            return false;
        }
        else if(edEmail.getText().toString().contains(" "))
        {
            setSupportMess("Vui Lòng Không Nhập Khoản Cách");
            edEmail.findFocus();
            return false;
        }
        else if(edPass.getText().toString().contains(" "))
        {
            setSupportMess("Vui Lòng Không Nhập Khoản Cách");
            edPass.findFocus();
            return false;
        }
        else if(!checkEmail(edEmail.getText().toString().trim()))
        {
            setSupportMess("Email Chưa Đúng");
            return false;
        }
        else if(edPass.getText().toString().trim().length() < 6)
        {
            setSupportMess("Mật Khẩu phải từ 6 kí tự đến 10 kí tự");
            edPass.findFocus();
            return false;
        }
        tvCheckText.setVisibility(View.GONE);
        return true;

    }
    private boolean checkEmail(String email)
    {
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void ActionButton() {
           signInB.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  if(checkText())
                  {

                      String email = edEmail.getText().toString().trim().toLowerCase();
                      String pass = edPass.getText().toString().trim();
                      DangNhapUser(email, pass);
                  }
               }
           });

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXaView() {
        Paper.init(this);
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);

        signInB = findViewById(R.id.loginB);
        signUpB = findViewById(R.id.signupB);
        edEmail = findViewById(R.id.dn_email);
        edPass = findViewById(R.id.dn_password);
        backB = findViewById(R.id.backB);
        tvCheckText = findViewById(R.id.dn_checkTextMess);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        tvCheckText.setVisibility(View.GONE);


        if(Paper.book().read("email")!= null && Paper.book().read("pass") != null)
        {
            DangNhapUser(Paper.book().read("email"),Paper.book().read("pass"));
        }
    }

    private void DangNhapUser( String email, String pass) {
        setSupportMess("Vui Lòng Chờ");
        tvCheckText.setTextColor(Color.BLUE);
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.dangnhapUser(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userStoreModel -> {
                            if (userStoreModel.isSuccess())
                            {
                                Paper.book().write("email", email);
                                Paper.book().write("pass", pass);
                                Utils.user_current = userStoreModel.getResult().get(0);
                                Paper.book().write("NameUser", Utils.user_current.getTenuser());

                                Intent intent = new Intent(SignInActivity.this, BeginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                progressBar.setVisibility(View.GONE);
                                setSupportMess(userStoreModel.getMessage());
                            }

                        },throwable -> Log.e(TAG,  throwable.getMessage())
                )
        );

    }


}