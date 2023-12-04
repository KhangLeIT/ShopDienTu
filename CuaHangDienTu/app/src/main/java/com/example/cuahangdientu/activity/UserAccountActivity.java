package com.example.cuahangdientu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.SanPhamTheoDMucAdapter;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;
import com.hbb20.CountryCodePicker;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserAccountActivity extends AppCompatActivity {
    private EditText name, phone;
    private LinearLayout editB;
    private Button cancelB, saveB;
    private TextView profileUserName, txtError;
    private LinearLayout button_layout;
    private String nameStr, phoneStr;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private CountryCodePicker ccp;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        AnhXaView();
        disableEditing();
        ActionUser();
        AcctionToobar();
        txtError.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        ccp.setVisibility(View.GONE);

    }

    private void ActionUser() {
        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEditing();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableEditing();
            }
        });

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    nameStr = name.getText().toString();
                    phoneStr = phone.getText().toString();
                    int idUser = Utils.user_current.getIduser();
                    saveData(idUser,phoneStr,nameStr);
                }
            }
        });
    }

    private void AcctionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(DbQuery.select_ten_danhmuc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveData(int iduser, String sdt, String tenuser) {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.updateuser(iduser,sdt, tenuser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userStoreModel -> {
                            if(userStoreModel.isSuccess())
                            {
                                disableEditing();
                                setDataUser(sdt, tenuser);
                                Paper.book().delete("NameUser");
                                Paper.book().write("NameUser", tenuser);
                                progressBar.setVisibility(View.GONE);

                            }else{
                                Toast.makeText(this, " no connected", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
        );
    }

    private void setDataUser(String sdt, String tenuser) {
        Utils.user_current.setSdt(sdt);
        Utils.user_current.setTenuser(tenuser);

        //
        name.setText(tenuser);
        phone.setText(sdt);
        profileUserName.setText(tenuser);
    }

    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        Paper.init(this);

        toolbar = findViewById(R.id.toolbar);
        name = findViewById(R.id.profile_name);
        phone = findViewById(R.id.profile_phone);
        editB = findViewById(R.id.profile_edit);
        cancelB = findViewById(R.id.profile_cancel);
        saveB = findViewById(R.id.profile_save);
        profileUserName = findViewById(R.id.profile_tenuser);
        progressBar = findViewById(R.id.progressBar);
        txtError = findViewById(R.id.txtError);
        ccp = findViewById(R.id.countryCodePicker);

        button_layout = findViewById(R.id.profile_button_layout);
    }

    private void setTxtError(String str)
    {
        txtError.setText(str);
        txtError.setVisibility(View.VISIBLE);
    }

    private void disableEditing()
    {
        name.setEnabled(false);
        phone.setEnabled(false);
        ccp.setEnabled(false);
        name.setTextColor(Color.GRAY);
        phone.setTextColor(Color.GRAY);

        button_layout.setVisibility(View.GONE);
        txtError.setVisibility(View.GONE);
        ccp.setVisibility(View.GONE);

        name.setText(Utils.user_current.getTenuser());


        if (Utils.user_current.getSdt() != null)
        {
            phone.setText(Utils.user_current.getSdt());
        }
        String profileName = Utils.user_current.getTenuser();
        profileUserName.setText(profileName);

    }
    private boolean checkSDT()
    {
        ccp.registerCarrierNumberEditText(phone);
        return ccp.isValidFullNumber();
    }
    private boolean validate(){

        nameStr = name.getText().toString().trim();
        phoneStr = phone.getText().toString().trim();
        if(nameStr.isEmpty())
        {
            setTxtError("tên bạn để trống");
            return false;
        }
        else if(phoneStr.isEmpty()){
            setTxtError("số điện thoại bạn để trống");
            return false;
        }
        else if(!checkSDT())
        {
            setTxtError("số điện thoại không đúng");
            return false;
        }
        return true;
    }

    private void enableEditing()
    {
        name.setEnabled(true);
        name.setTextColor(Color.BLACK);
        phone.setTextColor(Color.BLACK);
        phone.setEnabled(true);
        ccp.setVisibility(View.VISIBLE);
        button_layout.setVisibility(View.VISIBLE);

    }
}