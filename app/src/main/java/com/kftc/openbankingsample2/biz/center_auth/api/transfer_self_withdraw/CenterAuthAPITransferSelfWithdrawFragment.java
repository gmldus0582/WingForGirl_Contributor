package com.kftc.openbankingsample2.biz.center_auth.api.transfer_self_withdraw;

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
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthHomeFragment;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
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

public class CenterAuthAPITransferSelfWithdrawFragment extends AbstractCenterAuthMainFragment {
    private Context context;

    // view
    private View view;
    private View view2;
    public static String inp3;

    // data
    private Bundle args;
    private String cntrAccountType;
    private String transferPurpose;
    public static int sum2=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();

        if (args == null) args = new Bundle();
        sum2 = super.sum;


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_center_auth_api_transfer_self_withdraw, container, false);
        initView();
        return view;
    }

    void initView() {



        // access_token : ?????? ?????? ????????? ???????????? ?????? ??????
        inp3 = super.inp;
        TextView no = view.findViewById(R.id.moneyTranAmt);
        no.setText(inp3);
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getCenterAuthAccessToken(Scope.TRANSFER));

        // access_token : ?????? ???????????? ??????
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.TRANSFER));

        // ????????????????????????(20??????)
        EditText etBankTranId = view.findViewById(R.id.etBankTranId);
        setRandomBankTranId(etBankTranId);
        view.findViewById(R.id.btnGenBankTranId).setOnClickListener(v -> setRandomBankTranId(etBankTranId));

        // ?????? ??????/?????? ?????? : ???????????? ??????
        List<TwoString> cntrAccountTypeMenuList = new ArrayList<>();
        cntrAccountTypeMenuList.add(new TwoString("??????", "N"));
//        cntrAccountTypeMenuList.add(new TwoString("C(??????)", "C"));
        AppCompatSpinner spCntrAccountType = view.findViewById(R.id.spCntrAccountType);
        ArrayAdapter<TwoString> cntrAccountTypeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, cntrAccountTypeMenuList);
        spCntrAccountType.setAdapter(cntrAccountTypeAdapter);
        spCntrAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = cntrAccountTypeAdapter.getItem(position);
                if (twoString != null) {
                    cntrAccountType = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ?????? ??????/?????? ?????? : ?????????
        EditText etCntrAccountNum = view.findViewById(R.id.etCntrAccountNum);
        etCntrAccountNum.setText(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM));

        // ????????????????????????
        EditText etDpsPrintContent = view.findViewById(R.id.etDpsPrintContent);

        // ????????????????????? : ?????? ????????? ?????? ??????
        EditText etFintechUseNum = view.findViewById(R.id.etFintechUseNum);
        etFintechUseNum.setText(Utils.getSavedValue(CenterAuthConst.CENTER_AUTH_FINTECH_USE_NUM));

        // ????????????????????? : ?????? ???????????? ??????
        View.OnClickListener onClickListener = v -> showAccountDialog(etFintechUseNum);
        view.findViewById(R.id.btnSelectFintechUseNum).setOnClickListener(onClickListener);

        // ????????????


        TextView moneyTranAmt = view.findViewById(R.id.moneyTranAmt);

        // ????????????
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // ??????????????????
        EditText etReqClientName = view.findViewById(R.id.etReqClientName);
        etReqClientName.setText(Utils.getSavedValueOrDefault(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_NAME, etReqClientName.getText().toString()));

        // ?????????????????? ???????????? ????????????
        EditText etReqClientBankCode = view.findViewById(R.id.etReqClientBankCode);
        etReqClientBankCode.setText(Utils.getSavedValueOrDefault(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_BANK_CODE, etReqClientBankCode.getText().toString()));

        // ???????????? ????????????
        EditText etReqClientAccountNum = view.findViewById(R.id.etReqClientAccountNum);
        etReqClientAccountNum.setText(Utils.getSavedValueOrDefault(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_ACCOUNT_NUM, etReqClientAccountNum.getText().toString()));

        // ???????????? ????????????
        EditText etReqClientNum = view.findViewById(R.id.etReqClientNum);

        // ????????????
        List<TwoString> transferPurposeMenuList = new ArrayList<>();
        transferPurposeMenuList.add(new TwoString("????????? ??????", "TR"));
//        transferPurposeMenuList.add(new TwoString("ST(??????)", "ST"));
//        transferPurposeMenuList.add(new TwoString("RC(??????)", "RC"));
        AppCompatSpinner spTransferPurpose = view.findViewById(R.id.spTransferPurpose);
        ArrayAdapter<TwoString> transferPurposeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, transferPurposeMenuList);
        spTransferPurpose.setAdapter(transferPurposeAdapter);
        spTransferPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = transferPurposeAdapter.getItem(position);
                if (twoString != null) {
                    transferPurpose = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ??????????????????
        EditText etSubFrncName = view.findViewById(R.id.etSubFrncName);

        // ?????????????????????
        EditText etSubFrncNum = view.findViewById(R.id.etSubFrncNum);

        // ??????????????? ?????????????????????
        EditText etSubFrncBusinessNum = view.findViewById(R.id.etSubFrncBusinessNum);

        // ????????????????????????
        EditText etRecvClientName = view.findViewById(R.id.etRecvClientName);

        // ????????????????????????
        EditText etRecvClientBankCode = view.findViewById(R.id.etRecvClientBankCode);

        // ????????????????????????
        EditText etRecvClientAccountNum = view.findViewById(R.id.etRecvClientAccountNum);

        // ???????????? ??????
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // ???????????? ??????
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken);
            String cntrAccountNum = etCntrAccountNum.getText().toString();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_CNTR_ACCOUNT_NUM, cntrAccountNum);
            String fintechUseNum = etFintechUseNum.getText().toString();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_FINTECH_USE_NUM, fintechUseNum);
            String reqClientName = etReqClientName.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_NAME, reqClientName);
            String reqClientBankCode = etReqClientBankCode.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_BANK_CODE, reqClientBankCode);
            String reqClientAccountNum = etReqClientAccountNum.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_REQ_CLIENT_ACCOUNT_NUM, reqClientAccountNum);


            // ????????????
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
            paramMap.put("cntr_account_type", cntrAccountType);
            paramMap.put("cntr_account_num", etCntrAccountNum.getText().toString());
            paramMap.put("dps_print_content", etDpsPrintContent.getText().toString());
            paramMap.put("fintech_use_num", fintechUseNum);
            paramMap.put("tran_amt", moneyTranAmt.getText().toString());     // ??????(,)??? ???????????? ??????
            paramMap.put("tran_dtime", etTranDtime.getText().toString());
            paramMap.put("req_client_name", reqClientName);
            paramMap.put("req_client_bank_code", reqClientBankCode);
            paramMap.put("req_client_account_num", reqClientAccountNum);
            paramMap.put("req_client_num", etReqClientNum.getText().toString());
            paramMap.put("transfer_purpose", transferPurpose);
            paramMap.put("sub_frnc_name", etSubFrncName.getText().toString());
            paramMap.put("sub_frnc_num", etSubFrncNum.getText().toString());
            paramMap.put("sub_frnc_bussiness_num", etSubFrncBusinessNum.getText().toString());
            paramMap.put("recv_client_name", etRecvClientName.getText().toString());
            paramMap.put("recv_client_bank_code", etRecvClientBankCode.getText().toString());
            paramMap.put("recv_client_account_num", etRecvClientAccountNum.getText().toString());

            showProgress();
            CenterAuthApiRetrofitAdapter.getInstance()
                    .transferWithdrawFinNum("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("tran_amt", "??????"));
            String strCoin = moneyTranAmt.getText().toString();
            int i  = Integer.parseInt(strCoin);
            sum2=sum2+i;
            super.sum = sum2;
            startFragment(CenterAuthHomeFragment.class, args, R.string.fragment_id_center);
        });

        // ??????
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());

    }
}
