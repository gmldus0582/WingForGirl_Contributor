package com.kftc.openbankingsample2.biz.center_auth.api.account_balance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.CircularArray;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_withdraw.CenterAuthAPITransferWithdrawFragment;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.biz.main.HomeFragment;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.BankAccount;
import com.kftc.openbankingsample2.common.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ์์ก์กฐํ
 */
public class CenterAuthAPIAccountBalanceFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_center_auth_api_account_balance, container, false);
        initView();

        return view;
    }

    void initView() {

        // access_token : ๊ฐ์ฅ ์ต๊ทผ ํ?ํฐ์ผ๋ก ๊ธฐ๋ณธ ์ค์?
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getCenterAuthAccessToken(Scope.INQUIRY));

        // access_token : ๊ธฐ์กด ํ?ํฐ์์ ์?ํ
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.INQUIRY));

        // ์ํ๊ฑฐ๋๊ณ?์?๋ฒํธ(20์๋ฆฌ)
        EditText etBankTranId = view.findViewById(R.id.etBankTranId);
        setRandomBankTranId(etBankTranId);
        view.findViewById(R.id.btnGenBankTranId).setOnClickListener(v -> setRandomBankTranId(etBankTranId));

        // ํํํฌ์ด์ฉ๋ฒํธ : ์ต๊ทผ ๊ณ์ข๋ก ๊ธฐ๋ณธ ์ค์?
        EditText etFintechUseNum = view.findViewById(R.id.etFintechUseNum);
        etFintechUseNum.setText(Utils.getSavedValue(CenterAuthConst.CENTER_AUTH_FINTECH_USE_NUM));

        // ํํํฌ์ด์ฉ๋ฒํธ : ๊ธฐ์กด ๊ณ์ข์์ ์?ํ
        //View.OnClickListener onClickListener = v -> showAccountDialog(etFintechUseNum);


        view.findViewById(R.id.btnSelectFintechUseNum).setOnClickListener(v->{
            showAccountDialog(etFintechUseNum);
        });





        // ๊ฑฐ๋์ผ์
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // ์์ก์กฐํ ์์ฒญ
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {
            startFragment(HomeFragment.class, args, R.string.fragment_id_home);
            //์ง์?๋ด์ฉ ์?์ฅ
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken);
            String fintechUseNum = etFintechUseNum.getText().toString();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_FINTECH_USE_NUM, fintechUseNum);

            // ์์ฒญ์?๋ฌธ
//            HashMap<String, String> paramMap = new HashMap<>();
//            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
//            paramMap.put("fintech_use_num", fintechUseNum);
//            paramMap.put("tran_dtime", etTranDtime.getText().toString());
//
//            showProgress();
//            CenterAuthApiRetrofitAdapter.getInstance()
//                    .accountBalanceFinNum("Bearer " + accessToken, paramMap)
//                    .enqueue(super.handleResponse("balance_amt", "์์ก"));

        });

        // ์ทจ์
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }
}
