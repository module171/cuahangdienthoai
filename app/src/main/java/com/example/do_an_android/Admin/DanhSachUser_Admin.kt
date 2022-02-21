package com.example.do_an_android.Admin

import com.example.do_an_android.Model.LoaiSanPham
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.do_an_android.R
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.android.volley.RequestQueue
import kotlin.jvm.Synchronized
import com.android.volley.toolbox.Volley
import com.example.do_an_android.Model.DienThoai
import com.bumptech.glide.Glide
import android.os.AsyncTask
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.annotation.SuppressLint
import com.example.do_an_android.Model.cart
import android.app.Activity
import android.content.Intent
import com.example.do_an_android.Activity.GioHang
import com.android.volley.toolbox.StringRequest
import com.android.volley.VolleyError
import kotlin.Throws
import com.android.volley.AuthFailureError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.do_an_android.Admin.DanhSachSP_Admin
import com.example.do_an_android.Admin.DanhSachUser_Admin
import com.example.do_an_android.Admin.DanhSachLoaiSP_Admin
import com.example.do_an_android.Activity.ThongTinCaNhan
import com.example.do_an_android.Activity.TrangChu
import com.example.do_an_android.Activity.ThemSP
import android.widget.AdapterView.OnItemClickListener
import com.example.do_an_android.Activity.ChiTietSanPham
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.example.do_an_android.Admin.Admin
import com.example.do_an_android.Activity.ThemLoaiSP
import com.example.do_an_android.Activity.DangNhap
import android.view.WindowManager
import android.content.SharedPreferences
import com.example.do_an_android.Activity.DatHang
import com.example.do_an_android.Activity.DanhSachSP
import com.example.do_an_android.Activity.DangKy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_android.Activity.TimKiemSP
import androidx.viewpager.widget.ViewPager
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.example.do_an_android.Model.User
import com.example.do_an_android.Util.*
import java.util.ArrayList

class DanhSachUser_Admin : AppCompatActivity() {
    var trove: ImageView? = null
    var chitiet: TextView? = null
    var listView: ListView? = null
    var userArrayList = ArrayList<User>()
    var adapter: UserAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_sach_user_admin)
        listView = findViewById(R.id.mylistview_DanhsachUserAdmin)
        adapter = UserAdapter(this, userArrayList)
        listView.setAdapter(adapter)
        getdata()
        trove = findViewById<View>(R.id.IV_BackToHome) as ImageView
        trove!!.setOnClickListener {
            val intent = Intent(applicationContext, Admin::class.java)
            finish()
            startActivity(intent)
        }

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "" + userArrayList.get(i).getIdproduct(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
//                int idpro = userArrayList.get(i).getIdproduct();
//                intent.putExtra("idproduct", userArrayList.get(i).getIdproduct());
//                startActivity(intent);
//            }
//        });
    }

    fun getdata() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest =
            StringRequest(Request.Method.GET, Server.getalluser, { response: String? ->
                userArrayList.clear()
                var id = 0
                var name = ""
                var birthday = ""
                var phone = ""
                var email = ""
                var gender = ""
                var pass = ""
                var type = 0
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        id = jsonObject.getInt("iduser")
                        name = jsonObject.getString("nameuser")
                        birthday = jsonObject.getString("birthday")
                        phone = jsonObject.getString("phone")
                        email = jsonObject.getString("email")
                        gender = jsonObject.getString("gender")
                        pass = jsonObject.getString("password")
                        type = jsonObject.getInt("typeuser")
                        userArrayList.add(
                            User(
                                id,
                                name,
                                birthday,
                                phone,
                                email,
                                gender,
                                pass,
                                type
                            )
                        )
                    }
                    adapter!!.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error: VolleyError ->
                Toast.makeText(
                    applicationContext,
                    "Lỗi $error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, TrangChu::class.java))
    }
}