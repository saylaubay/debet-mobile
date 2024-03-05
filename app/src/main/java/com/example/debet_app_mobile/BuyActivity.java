package com.example.debet_app_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.debet_app_mobile.adapter.ViewPagerAdapter;
import com.example.debet_app_mobile.fragment.contract.FragmentContractAdd;
import com.example.debet_app_mobile.fragment.contract.FragmentContractList;
import com.example.debet_app_mobile.payload.ListClient;
import com.google.android.material.tabs.TabLayout;

import java.sql.Timestamp;
import java.util.List;

public class BuyActivity extends AppCompatActivity {

    TextView product, summa, percent, part;
    Spinner spinner;
    Button btn;

    ViewPager viewPager;
    TabLayout tabLayout;

    List<String> data;

    ArrayAdapter<String> adapterClient;


    List<ListClient> dataClient;

    public boolean isNetwork(){
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }catch (NullPointerException e){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
//        product = findViewById(R.id.product);
//        summa = findViewById(R.id.summa);
//        percent = findViewById(R.id.percent);
//        part = findViewById(R.id.part);
//
//        spinner = findViewById(R.id.spinner);
//        btn = findViewById(R.id.addBtn);
//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        Api api = retrofit.create(Api.class);
//
//        SharedPreferences preferences = getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);
//
//        String token = preferences.getString("token", "");
//        String role = preferences.getString("role", "");
//        String dbusername = preferences.getString("username", "");
//
//        token = "Bearer " + token;
//
//        String finalToken = token;
//
//        data = new ArrayList<>();
//        dataClient = new ArrayList<>();
//
//        ArrayList<ClientDto> clients = new ArrayList<ClientDto>();
//
//        BuyDto buyDto = new BuyDto();
//
//        System.out.println(role);
//
//
//        api.getMyClients(finalToken).enqueue(new Callback<ApiResponseClient2>() {
//            @Override
//            public void onResponse(Call<ApiResponseClient2> call, Response<ApiResponseClient2> response) {
//                System.out.println(123);
//                for (ClientDtos datum : response.body().getData()) {
//                    dataClient.add(new ListClient(datum.getId(), datum.getFirstName(), datum.getPhone()));
//                    data.add(datum.getFirstName() + " " + datum.getPhone());
//
//                    adapterClient = new ArrayAdapter<String>(BuyActivity.this, android.R.layout.simple_spinner_item, data);
//                    adapterClient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner.setAdapter(adapterClient);
//                }
//
//
////                ClientAdapter clientAdapter = new ClientAdapter(BuyActivity.this, clients);
////                listView.setAdapter(clientAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseClient2> call, Throwable t) {
//
//            }
//        });
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Long id = dataClient.get(i).getId();
//                buyDto.setClientId(id);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
////        api.getAllMyClient(token).enqueue(new Callback<ApiResponseClient>() {
////            @Override
////            public void onResponse(Call<ApiResponseClient> call, Response<ApiResponseClient> response) {
////
////                for (ClientDto datum : response.body().getData()) {
////                    dataClient.add(new ListClient(datum.getId(),datum.getFirstName(), datum.getPhone()));
////                    data.add(datum.getFirstName() + " " + datum.getPhone());
////
////                    adapterClient = new ArrayAdapter<String>(BuyActivity.this, android.R.layout.simple_spinner_item, data);
////                    adapterClient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                    spinner.setAdapter(adapterClient);
////                }
////
////
////                ClientAdapter clientAdapter = new ClientAdapter(BuyActivity.this, clients);
//////                listView.setAdapter(clientAdapter);
////            }
////
////            @Override
////            public void onFailure(Call<ApiResponseClient> call, Throwable t) {
////
////            }
////        });
////        UserDtos dbUser = new UserDtos();
//        System.out.println(dbusername);
//        api.getUserByUsername(finalToken, dbusername).enqueue(new Callback<ApiResponseUserAdd>() {
//            @Override
//            public void onResponse(Call<ApiResponseUserAdd> call, Response<ApiResponseUserAdd> response) {
//                UserDtos data = response.body().getData();
//                buyDto.setWorkerId(data.getId());
//                Toast.makeText(BuyActivity.this, "isledi", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseUserAdd> call, Throwable t) {
//
//            }
//        });
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String sProduct = product.getText().toString();
//                String s = summa.getText().toString();
//                double sSumma = Double.parseDouble(s);
//                String s1 = percent.getText().toString();
//                int sPercent = Integer.parseInt(s1);
//                String s2 = part.getText().toString();
//                int sPart = Integer.parseInt(s2);
//
//                if (sProduct.equals("") || s.equals("") || s1.equals("") || s2.equals("")) {
//                    Toast.makeText(BuyActivity.this, "Mag'liwmatlardi toliq toltirin'!!!", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    buyDto.setProductName(sProduct);
//                    buyDto.setPrice(sSumma);
//                    buyDto.setPercent(sPercent);
//                    buyDto.setPart(sPart);
//
//
//                    api.addContract(finalToken, buyDto).enqueue(new Callback<ApiResponseContract>() {
//                        @Override
//                        public void onResponse(Call<ApiResponseContract> call, Response<ApiResponseContract> response) {
//                            ApiResponseContract body = response.body();
//                            product.setText("");
//                            summa.setText("");
//                            percent.setText("");
//                            part.setText("");
//                            Toast.makeText(BuyActivity.this, "OKAY", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<ApiResponseContract> call, Throwable t) {
//                            Toast.makeText(BuyActivity.this, "111111", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//        });

        SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);

        long expireTime = preferences.getLong("expireTime", 0);
        Timestamp t = new Timestamp(System.currentTimeMillis());


        if (expireTime <= t.getTime() && expireTime != 0){

            MainActivity mainActivity=new MainActivity();
            mainActivity.a = true;

//            Toast.makeText(this, "Waqit tawsildi Calc", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();

            startActivity(getIntent());
            Intent i = new Intent(BuyActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentContractList(), "Contractlar dizimi");
        adapter.addFragment(new FragmentContractAdd(), "Contract qosiw");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}