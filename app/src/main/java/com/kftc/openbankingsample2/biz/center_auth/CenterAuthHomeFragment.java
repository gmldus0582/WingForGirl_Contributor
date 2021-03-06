package com.kftc.openbankingsample2.biz.center_auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.api.CenterAuthAPIFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.account_balance.CenterAuthAPIAccountBalanceFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.account_transaction.CenterAuthAPIAccountTransactionRequestFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.donate_activity.donate_activity;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_auto.CenterAuthAPITransferAutoFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_result.CenterAuthAPITransferResultFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_withdraw.CenterAuthAPITransferWithdrawFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.user_me.CenterAuthAPIUserMeRequestFragment;
import com.kftc.openbankingsample2.biz.center_auth.api.transfer_self_withdraw.CenterAuthAPITransferSelfWithdrawFragment;

import com.kftc.openbankingsample2.biz.center_auth.api.user_me.CenterAuthAPIUserMeResultFragment;
import com.kftc.openbankingsample2.biz.center_auth.auth.CenterAuthFragment;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.biz.main.HomeFragment;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.util.TwoString;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.KmUtilMoneyEditText;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.android.material.bottomsheet.BottomSheetDialog;


/**
 * ???????????? ????????????
 */
public class CenterAuthHomeFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;
    private View view2;
    private View view3;
    private View view4;
    public static String coin2;
    public static int sum2;
    public static String inp2;


    // data
    private Bundle args;
    private String cntrAccountType;
    private String transferPurpose;

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
        view = inflater.inflate(R.layout.fragment_center_auth_home, container, false);
        initView();
        return view;
    }

    private void initView() {
        coin2= super.coin;
        sum2 = super.sum;
        inp2 = super.inp;
        TextView txtCoin = view.findViewById(R.id.textBalance);
        TextView txtSum = view.findViewById(R.id.textSum);
        txtCoin.setText(coin2);
        txtSum.setText(String.valueOf(sum2)+"???");

        view2 = getLayoutInflater().inflate(R.layout.fragment_center_auth_api_transfer_withdraw,null,false);
        view3 = getLayoutInflater().inflate(R.layout.fragment_center_auth_api_transfer_self_withdraw,null,false);
        view4 = getLayoutInflater().inflate(R.layout.fragment_donateactivity,null,false);

        // ????????????
        //view.findViewById(R.id.btnAuthToken).setOnClickListener(v -> startFragment(CenterAuthFragment.class, args, R.string.fragment_id_center_auth));

        // API ??????
        // view.findViewById(R.id.btnAPICallMenu).setOnClickListener(v -> startFragment(CenterAuthAPIFragment.class, args, R.string.fragment_id_center_api_call));
//        //????????????
//        view.findViewById(R.id.btnInqrUserInfoPage).setOnClickListener(v -> startFragment(CenterAuthAPIUserMeRequestFragment.class, args, R.string.fragment_id_api_call_userme));

        // ????????????
        //view.findViewById(R.id.btnInqrBlncPage).setOnClickListener(v -> startFragment(CenterAuthAPIAccountBalanceFragment.class, args, R.string.fragment_id_api_call_balance));

        // ??????????????????
        //view.findViewById(R.id.btnInqrTranRecPage).setOnClickListener(v -> startFragment(CenterAuthAPIAccountTransactionRequestFragment.class, args, R.string.fragment_id_api_call_transaction));

        //?????? ??????
        EditText etBankTranId = view2.findViewById(R.id.etBankTranId);
        TextView bal = view.findViewById(R.id.textBalance);
        view.findViewById(R.id.btnTrnsWDPage).setOnClickListener(v ->{
            setRandomBankTranId(etBankTranId);
            startFragment(CenterAuthAPITransferWithdrawFragment.class, args, R.string.fragment_id_api_call_withdraw);
            //bal.setText("0???");
            

        });

        //?????????
        TextView dial = view.findViewById(R.id.inputMoney);
            view.findViewById(R.id.one).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");

                }
                dial.append("1");
            });
            view.findViewById(R.id.two).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }
                dial.append("2");
            });
            view.findViewById(R.id.three).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }
                dial.append("3");
            });
            view.findViewById(R.id.four).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }

                dial.append("4");
            });
            view.findViewById(R.id.five).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }

                dial.append("5");
            });
            view.findViewById(R.id.six).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }

                dial.append("6");
            });
            view.findViewById(R.id.seven).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }

                dial.append("7");
            });
            view.findViewById(R.id.eight).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }

                dial.append("8");
            });
            view.findViewById(R.id.nine).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }

                dial.append("9");
            });
            view.findViewById(R.id.zero).setOnClickListener(v ->{
                if(dial.getText().equals("?????? ?????? ??????")){
                    dial.setText("");
                }

                dial.append("0");
            });
        view.findViewById(R.id.reset).setOnClickListener(v ->{
            dial.setText(" ");
        });
        view.findViewById(R.id.del).setOnClickListener(v ->{
            String dialDel = dial.getText().toString();
            dial.setText(dialDel.substring(0,dialDel.length()-1));
        });



        //????????????
        //TextView coinTran = view3.findViewById(R.id.moneyTranAmt);
        EditText etBankTranSelfId = view3.findViewById(R.id.etBankTranId);

        view.findViewById(R.id.btnSelfWithdraw).setOnClickListener(v -> {
            startFragment(CenterAuthAPITransferSelfWithdrawFragment.class, args, R.string.fragment_id_api_call_self_withdraw);
//            View dialogView = getLayoutInflater().inflate(R.layout.fragment_center_auth_api_transfer_self_withdraw, null);
//            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity.getApplicationContext());
//            bottomSheetDialog.setContentView(dialogView);
//            bottomSheetDialog.show();
            inp2 = dial.getText().toString();
            super.inp = inp2;
            //coinTran.setText("123");
            setRandomBankTranId(etBankTranSelfId);
        });

        //????????????

        view.findViewById(R.id.btnActivity).setOnClickListener(v->startFragment(donate_activity.class, args, R.string.fragment_donate_activity));

        // ??????????????????
        //view.findViewById(R.id.btnTransferResult).setOnClickListener(v -> startFragment(CenterAuthAPITransferResultFragment.class, args, R.string.fragment_id_api_call_transfer_result));
        //view.findViewById(R.id.btnAutoTrnsWDPage).setOnClickListener(v -> startFragment(CenterAuthAPITransferAutoFragment.class, args, R.string.fragment_id_api_call_auto));
    }

    @Override
    public void onBackPressed() {
        startFragment(HomeFragment.class, args, R.string.fragment_id_home);
    }
}