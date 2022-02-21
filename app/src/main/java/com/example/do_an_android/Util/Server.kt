package com.example.do_an_android.Util

import com.example.do_an_android.Model.LoaiSanPham
import android.widget.BaseAdapter
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.do_an_android.R
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.android.volley.RequestQueue
import com.example.do_an_android.Util.VolleySingleton
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
import com.example.do_an_android.Util.DienthoaiAdapter
import com.example.do_an_android.Activity.TrangChu
import com.example.do_an_android.Activity.ThemSP
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import com.example.do_an_android.Activity.ChiTietSanPham
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.example.do_an_android.Admin.Admin
import com.example.do_an_android.Util.UserAdapter
import com.example.do_an_android.Util.LoaiAdapter
import com.example.do_an_android.Activity.ThemLoaiSP
import android.widget.EditText
import com.example.do_an_android.Util.CheckConnection
import com.example.do_an_android.Activity.DangNhap
import android.view.WindowManager
import android.content.SharedPreferences
import com.example.do_an_android.Activity.DatHang
import com.example.do_an_android.Util.itemInCartAdapter
import com.example.do_an_android.Activity.DanhSachSP
import android.widget.CheckBox
import com.example.do_an_android.Activity.DangKy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_android.Activity.TimKiemSP
import androidx.viewpager.widget.ViewPager
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.LinearLayout
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable

object Server {
    var localhost = "localhost"
    var connectdb = "http://192.168.1.8:88/API/connectdb.php"
    var registeruser = "http://192.168.1.8:88/API/registeruser.php"
    var login = "http://192.168.1.8:88/API/login.php"
    var addproduct = "http://192.168.1.8:88/API/addproduct.php"
    var addloaisp = "http://192.168.1.8:88/API/addloaisp.php"
    var addHD = "http://192.168.1.8:88/API/addhoadon.php"
    var getAllProduct = "http://192.168.1.8:88/API/getspall.php"
    var getproductbyid = "http://192.168.1.8:88/API/getproductbyid.php"
    var searchproductbyname = "http://192.168.1.8:88/API/searchsp.php"
    var getuserbyemail = "http://192.168.1.8:88/API/getuserbyemail.php"
    var getalluser = "http://192.168.1.8:88/API/getuserall.php"
    var getallloai = "http://192.168.1.8:88/API/getloaiall.php"
    var updateItemInCart = "http://192.168.1.8:88/API/updateItemInCart.php"
    var getproductcart = "http://192.168.1.8:88/API/getcart.php"
    var getUserByEmail = "http://192.168.1.8:88/API/getuserbyemail.php"
    var getidorder = "http://192.168.1.8:88/API/getIdOrder.php"
    var insertItemToCart = "http://192.168.1.8:88/API/insertItemToCart.php"
    var deletedItemInCart = "http://192.168.1.8:88/API/deletedItemInCart.php"
    var updateinfo = "http://192.168.1.8:88/API/updateinfo.php"
    var insertdathang = "http://192.168.1.8:88/API/insertdathang.php"
    var updateorder = "http://192.168.1.8:88/API/updateorder.php"
    var insert = "http://192.168.1.8:88/API/insert.php"
}