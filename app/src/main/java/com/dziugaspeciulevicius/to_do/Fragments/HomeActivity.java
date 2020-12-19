//package com.dziugaspeciulevicius.to_do.Activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toolbar;
//
//import com.bumptech.glide.Glide;
//import com.dziugaspeciulevicius.to_do.R;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class HomeActivity extends AppCompatActivity {
//
//    private Toolbar toolbar;    // adding a custom toolbar
//    private RecyclerView recyclerView;
//    private FloatingActionButton floatingActionButton;
//
//
//
//    private DatabaseReference reference;
//    private FirebaseAuth mAuth;
//    private FirebaseUser mUser;
//    private String onlineUserID;
//
//    private ProgressDialog loader;
//
//    private String key = "";
//    private String task;
//    private String description;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home);
//
//
//        // hide top action bar
//        try {
//            this.getSupportActionBar().hide();
//        } catch (NullPointerException e){}
//
//
//
//
//        // custom toolbar (breaks the app, idk why)
////        toolbar = findViewById(R.id.homeToolbar);
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setTitle("Home");
//
//        recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        // get user and it's tasks
//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();
//        onlineUserID = mUser.getUid();
//        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);
//
//        // create to-do round + button action
//        floatingActionButton = findViewById(R.id.floating_create_button);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addTask();
//            }
//        });
//
//
//        // Initialize and Assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        // Set Home selected
//        bottomNavigationView.setSelectedItemId(R.id.home);
//        // Perform ItemSelectedListener
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.home:
//                        return true;
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    private void showAllUserData() {
//        Intent intent = getIntent();
//        String user_name =  intent.getStringExtra("name");
//        String user_email =  intent.getStringExtra("email");
//        String user_password =  intent.getStringExtra("password");
//
//        nameInput.getEditText().setText(user_name);
//        emailInput.getEditText().setText(user_email);
//        passwordInput.getEditText().setText(user_password);
//
//    }
//
//    // add task action
//    private void addTask() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        // what does inflater do exactly? Does not really work without it
//        LayoutInflater inflater = LayoutInflater.from(this);
//
//        View myView = inflater.inflate(R.layout.input_box, null);
//
//        dialog.setView(myView);
//
////        AlertDialog alertDialog = dialog.create();
//        final AlertDialog alertDialog = dialog.create();
//        alertDialog.setCancelable(false);
//        dialog.show();
//
//        final EditText task = myView.findViewById(R.id.task);
//        final EditText additionalInformation = myView.findViewById(R.id.additional_information);
//        Button save = myView.findViewById(R.id.buttonSave);
//        Button cancel = myView.findViewById(R.id.buttonCancel);
//
//        cancel.setOnClickListener((v) -> {alertDialog.dismiss();});
//
//        save.setOnClickListener((v) -> {
//            String mTask = task.getText().toString().trim();
//            String mDescription = additionalInformation.getText().toString().trim();
//            String id = reference.push().getKey();
//            String date = DateFormat.getDateInstance().format(new Date());
//
//            if (TextUtils.isEmpty(mTask)) {
//                task.setError("Task is required");
//                return;
//            } else {
//                loader.setMessage("Sending data");
//                loader.setCanceledOnTouchOutside(false);
//                loader.show();
//
//                // we need to create an object of a model class
//                Model model = new Model(mTask, mDescription, id, date);
//                reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(HomeActivity.this, "To-do has been inserted", Toast.LENGTH_SHORT).show();
//                            loader.dismiss();
//                        } else {
//                            String error = task.getException().toString();
//                            Toast.makeText(HomeActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
//                            loader.dismiss();
//                        }
//                    }
//                });
//            }
//
//            alertDialog.dismiss();
//        });
//
//    alertDialog.show();
//    }
//
//
//
//}
