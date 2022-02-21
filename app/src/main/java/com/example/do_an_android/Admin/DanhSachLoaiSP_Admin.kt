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
import com.example.do_an_android.Util.*
import java.util.ArrayList

class DanhSachLoaiSP_Admin : AppCompatActivity() {
    var trove: ImageView? = null
    var them: ImageView? = null
    var chitiet: TextView? = null
    var listView: ListView? = null
    var adapter: LoaiAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_sach_loai_sp_admin)
        listView = findViewById(R.id.mylistview_DanhsachLoaispAdmin)
        adapter = LoaiAdapter(this, loaiArrayList)
        listView?.setAdapter(adapter)
        getdata()
        them = findViewById<View>(R.id.themloaisp) as ImageView
        them!!.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext, ThemLoaiSP::class.java))
        }
        trove = findViewById<View>(R.id.IV_BackToHome) as ImageView
        trove!!.setOnClickListener {
            val intent = Intent(applicationContext, Admin::class.java)
            finish()
            startActivity(intent)
        }
        //        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "" + loaiArrayList.get(i).getIdproduct(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
//                int idpro = dienthoaiArrayList.get(i).getIdproduct();
//                intent.putExtra("idproduct", dienthoaiArrayList.get(i).getIdproduct());
//                startActivity(intent);
//            }
//        });
    }

    fun getdata() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest = StringRequest(Request.Method.GET, Server.getallloai, { response ->
            loaiArrayList.clear()
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val idtype_admin = jsonObject.getInt("idtype")
                    val nametype_admin = jsonObject.getString("nametype")
                    val loai = LoaiSanPham(idtype_admin, nametype_admin)
                    loaiArrayList.add(loai)
                    //                        Toast.makeText(getApplicationContext(), "" + dienthoaiArrayList.size(), Toast.LENGTH_SHORT).show();
                    adapter!!.notifyDataSetChanged()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }) { error -> Toast.makeText(applicationContext, "Lỗi $error", Toast.LENGTH_SHORT).show() }
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(applicationContext, Admin::class.java))
    }

    companion object {
        var loaiArrayList = ArrayList<LoaiSanPham>()
    }
}