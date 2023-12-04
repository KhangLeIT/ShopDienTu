package com.example.admincuahangdientu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.sanpham.DanhMucSanPhamActivity;
import com.example.admincuahangdientu.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignInAdminActivity extends AppCompatActivity {

    private Button signInB;
    private EditText edEmail, edPass;
    private ProgressBar progressBar;
    private APIadminStore apiStore;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_admin);

        AnhXaView();
        signInB.setOnClickListener(view -> {
            signInAdmin();
        });
    }

    private void signInAdmin() {
        String tk = edEmail.getText().toString().trim();
        String mk = edPass.getText().toString().trim();
        if(TextUtils.isEmpty(tk)){
            Toast.makeText(this, "tài khoản trống", Toast.LENGTH_SHORT).show();
        }
        else if( TextUtils.isEmpty(mk)){
            Toast.makeText(this, "mật khẩu trống", Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            compositeDisposable.add(apiStore.sigInAdmin(tk, mk)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            adminModel -> {
                                if (adminModel.isSuccess()) {
                                    Toast.makeText(this, adminModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(it);
                                }
                                else {
                                    Toast.makeText(this, adminModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            },
                            throwable -> {
                                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                    )
            );
        }
    }

    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        signInB = findViewById(R.id.loginB);
        edEmail = findViewById(R.id.dn_email);
        edPass = findViewById(R.id.dn_password);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
}