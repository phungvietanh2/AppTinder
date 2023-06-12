package com.example.apptinder.Start;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.MainActivity;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.io.File;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SaveSettingAcountActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radio1, raido2;
    private TextView date;
    private ImageView imageDate;
    private DatePickerDialog picker;
    private CircleImageView avata;

    private int day;
    private int month;
    private int year;
    private Button save;
    private Boolean gender;

    private EditText tieusu;

    private static final int Storage_Permission_Code = 200;
    private static final int Image_From_Gallery_Code = 1001;

    private String[] storagePermission;

    Uri imageUri;

    private void bindingView() {
        radioGroup = findViewById(R.id.radioGroup);
        date = findViewById(R.id.datechoice);
        imageDate = findViewById(R.id.imageDate);
        avata = findViewById(R.id.avataacount1);
        save = findViewById(R.id.save12);
        tieusu = findViewById(R.id.tieusu);
    }

    private void bindingAction() {
        radioGroup.setOnCheckedChangeListener(this::onClickRadio);
        imageDate.setOnClickListener(this::onClickDate);
        save.setOnClickListener(this::onClickSave);
        avata.setOnClickListener(this::onclickAvata);
    }

    private void onclickAvata(View view) {
        pickFromGallery();
    }

    //chon anh tu thu vien
    private void pickFromGallery() {
        if (!checkStoragePermission()) {
            requestStoragePermission();
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, Image_From_Gallery_Code);
        }
    }


    // Kiểm tra quyền truy cập vào bộ nhớ
    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    // Yêu cầu cấp quyền truy cập vào bộ nhớ
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, Storage_Permission_Code);
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
            avata.setImageBitmap(bitmap);
            imageUri = Uri.fromFile(imgFile); // Lưu đường dẫn ảnh để sử dụng sau này
        }
    }

    private void bindingData() {
        gender = true;
    }

    private void onClickSave(View view) {
        save();
    }

    private void onClickDate(View view) {
        final Calendar cldr = Calendar.getInstance();
        day = cldr.get(Calendar.DAY_OF_MONTH);
        month = cldr.get(Calendar.MONTH);
        year = cldr.get(Calendar.YEAR);

        picker = new DatePickerDialog(SaveSettingAcountActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;
                        String selectedDate = day + "/" + (month + 1) + "/" + year;
                        date.setText(selectedDate);
                    }
                }, year, month, day);
        picker.show();
    }

    private void onClickRadio(RadioGroup radioGroup, int checkedId) {
        radio1 = findViewById(R.id.radioButton1);
        raido2 = findViewById(R.id.radioButton2);

        // Kiểm tra RadioButton nào được chọn
        if (checkedId == R.id.radioButton1) {
            radio1.setChecked(true);
            raido2.setChecked(false);
            gender = true;
        } else if (checkedId == R.id.radioButton2) {
            radio1.setChecked(false);
            raido2.setChecked(true);
            gender = false;
        }
    }

    private void save() {

        Calendar currentCalendar = Calendar.getInstance();
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        int currentYear = currentCalendar.get(Calendar.YEAR);

        int age = currentYear - year;
        if (currentMonth < month + 1 || (currentMonth == month + 1 && currentDay < day)) {
            age--;
        }

        DBcontext db = DBcontext.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        int useId = sessionManager.getUserId();

        String text = tieusu.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
        } else if (age < 16) {
            Toast.makeText(this, "Bạn phải đủ 16 tuổi trở lên", Toast.LENGTH_SHORT).show();
        } else {
            String avatar = getImagePathFromUri(imageUri);
            User user = new User();
            user.setAvatar(avatar);
            user.setAge(age);
            user.setGender(gender);
            user.setText(text);

            userDao.updateUser1(useId, user.getAvatar(), user.getAge(), user.getGender(), user.getText());

            Intent intent = new Intent(SaveSettingAcountActivity.this, MainActivity.class);
            startActivity(intent);

        }


    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_setting_acount);
        bindingView();
        bindingAction();
        bindingData();
    }
}
