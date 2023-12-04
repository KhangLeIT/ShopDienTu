package com.example.cuahangdientu.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.R;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BeginActivity extends AppCompatActivity {
    private TextView txtChaUser, txtAppName;
    private AppCompatButton btnSignIn, btnSignUp;

    private ApiStore apiStore;
    private final CompositeDisposable  compositeDisposable = new CompositeDisposable();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        AnhXaView();
        progressBar.setVisibility(View.GONE);
        ActionButton();

        Typeface typeface = ResourcesCompat.getCachedFont(this, R.font.blacklist);
        txtAppName.setTypeface(typeface);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        txtAppName.setAnimation(anim);
        if(Paper.book().read("email")!= null && Paper.book().read("pass") != null)
        {
            btnSignUp.setVisibility(View.GONE);
            btnSignIn.setVisibility(View.GONE);
            if(Paper.book().read("NameUser") != null)
            {
                String xinChaoUser = "Xin Chào !! \n " + Paper.book().read("NameUser") + "\n Chúc Bạn Ngày Mới Tốt Lành";
                txtChaUser.setText(xinChaoUser);
                txtChaUser.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(Paper.book().read("email")!= null && Paper.book().read("pass") != null)
                {
                    DangNhapUser(Paper.book().read("email"),Paper.book().read("pass"));
                }
            }
        }.start();

    }

    private void ActionButton() {

        btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        });


    }

    private void DangNhapUser( String email, String pass) {
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

                                Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(this, userStoreModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        },throwable -> Log.e(TAG,  throwable.getMessage())
                )
        );

    }

    private void AnhXaView() {
        Paper.init(this);
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);

        txtChaUser = findViewById(R.id.begin_ChaoUser);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtAppName = findViewById(R.id.begin_appName);

        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}