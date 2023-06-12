package com.example.apptinder.Account_Setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AcountSaveActivity extends AppCompatActivity {
    private CircleImageView profileIv;
    public EditText name , textfile;
    private Spinner showgender, ageSp;
    private TextView savesetting;

    private static final int Storage_Permission_Code = 200;
    private static final int Image_From_Gallery_Code = 1001;

    private String[] storagePermission;

    Uri imageUri;

    private SessionManager sessionManager;

    private void bindingView() {
        showgender = findViewById(R.id.spinner_gender_edit);
        ageSp = findViewById(R.id.ageSp);
        profileIv = findViewById(R.id.image_edit);
        name = findViewById(R.id.edit_name);
        textfile = findViewById(R.id.edit_description);
        savesetting = findViewById(R.id.savesetting);
        saveshowgender();
    }

    private void bindingAction() {

        profileIv.setOnClickListener(this::setonclickprofile);
        savesetting.setOnClickListener(this::onclicksetsave);

        showgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ageSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedAge = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onclicksetsave(View view) {
        save();
        Intent intent =new Intent(AcountSaveActivity.this,ListSettingAcountActivity.class);
        startActivity(intent);
    }

    private void setonclickprofile(View view) {
        pickFromGallery();
    }

    private void saveshowgender() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        showgender.setAdapter(adapter);

        List<String> ageList = new ArrayList<>();
        for (int i = 16; i <= 200; i++) {
            ageList.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ageList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSp.setAdapter(adapter1);
    }

    // Hàm chọn ảnh từ thư viện
    private void pickFromGallery() {
        if (!checkStoragePermission()) {
            requestStoragePermission();
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, Image_From_Gallery_Code);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // Kiểm tra quyền truy cập vào bộ nhớ
    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    // Yêu cầu cấp quyền truy cập vào bộ nhớ
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, Storage_Permission_Code);
    }

    // Xử lý kết quả yêu cầu cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Storage_Permission_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "Storage permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // Xử lý kết quả khi chọn ảnh từ thư viện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Image_From_Gallery_Code) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String imagePath = cursor.getString(columnIndex);

                        // Hiển thị ảnh đã chọn lên ImageView
                        loadImageFromPath(imagePath);

                        cursor.close();
                    }
                }
            }
        }
    }

    // Hiển thị ảnh từ đường dẫn và đặt vào ImageView
    private void loadImageFromPath(String imagePath) {
        File imgFile = new File(imagePath);

        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            profileIv.setImageBitmap(bitmap);
            imageUri = Uri.fromFile(imgFile); // Lưu đường dẫn ảnh để sử dụng sau này
        }
    }

    // Lấy đường dẫn ảnh từ Uri
    private String getImagePathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
    }

    private void save() {
        sessionManager = new SessionManager(getApplicationContext());
        String nameFile = name.getText().toString();
        String avatar = "";
        String age = "12";
        Boolean gender = true;
        String text = textfile.getText().toString();


        if (imageUri != null) {
            avatar = getImagePathFromUri(imageUri); // Lấy đường dẫn thực tế của ảnh từ imageUri
        }

        DBcontext db = DBcontext.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();

        sessionManager = new SessionManager(getApplicationContext());
        int useId = sessionManager.getUserId();

        // Tạo đối tượng User và cập nhật thông tin
        User user = new User();
        user.setUsername(nameFile);
        user.setAvatar(avatar);
        user.setAge(Integer.parseInt(age));
        user.setGender(gender);
        user.setText(text);

        userDao.updateUser(useId, user.getUsername(), user.getAvatar(), user.getAge(), user.getGender(), user.getText());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_save);
        bindingView();
        bindingAction();
        storagePermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    }
}