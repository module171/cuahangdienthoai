package com.example.do_an_android.Util

import com.example.do_an_android.Model.LoaiSanPham
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.do_an_android.R
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.example.do_an_android.Activity.ChiTietSanPham
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.example.do_an_android.Admin.Admin
import com.example.do_an_android.Util.UserAdapter
import com.example.do_an_android.Util.LoaiAdapter
import com.example.do_an_android.Activity.ThemLoaiSP
import com.example.do_an_android.Util.CheckConnection
import com.example.do_an_android.Activity.DangNhap
import android.view.WindowManager
import android.content.SharedPreferences
import com.example.do_an_android.Activity.DatHang
import com.example.do_an_android.Util.itemInCartAdapter
import com.example.do_an_android.Activity.DanhSachSP
import com.example.do_an_android.Activity.DangKy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_android.Activity.TimKiemSP
import androidx.viewpager.widget.ViewPager
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.*
import com.android.volley.Response
import java.text.DecimalFormat
import java.util.ArrayList
import java.util.HashMap

class itemInCartAdapter(var context: Context, var arraygiohang: ArrayList<cart>) : BaseAdapter() {
    override fun getCount(): Int {
        return arraygiohang.size
    }

    override fun getItem(i: Int): cart {
        return arraygiohang[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    inner class ViewHolder {
        var title: TextView? = null
        var subtitle: TextView? = null
        var motasanpham: TextView? = null
        var them: TextView? = null
        var giam: TextView? = null
        var soluong: TextView? = null
        var tongtien: TextView? = null
        var xoa: TextView? = null

        //public final TextView soluong;
        var iddetail: String? = null
        var giasp: String? = null
        var tong: String? = null
        var hinh: ImageView? = null
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        var viewHolder: ViewHolder? = null
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.cart_item, null)
            viewHolder!!.title = view.findViewById(R.id.tenSanPham)
            viewHolder.subtitle = view.findViewById(R.id.giaTienSanPham)
            viewHolder.them = view.findViewById(R.id.tang)
            viewHolder.soluong = view.findViewById(R.id.soLuongSanPham)
            viewHolder.hinh = view.findViewById(R.id.hinhSanPham)
            val finalViewHolder = viewHolder
            viewHolder.tongtien = view.findViewById(R.id.tongTienSanPham)
            viewHolder!!.them?.setOnClickListener(View.OnClickListener {
                tangsosp(finalViewHolder)
                (context as Activity).finish()
                context.startActivity(Intent(context, GioHang::class.java))
            })
            viewHolder.giam = view.findViewById(R.id.giam)
            viewHolder?.giam?.setOnClickListener(View.OnClickListener {
                giamsosp(finalViewHolder)
                (context as Activity).finish()
                context.startActivity(Intent(context, GioHang::class.java))
            })
            viewHolder.xoa = view.findViewById(R.id.xoaKhoiGioHang)
            viewHolder?.xoa?.setOnClickListener(View.OnClickListener {
                xoasp(finalViewHolder)
                (context as Activity).finish()
                context.startActivity(Intent(context, GioHang::class.java))
            })

            //viewHolder.motasanpham = view.findViewById(R.id.mota_sanpham);
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val sanpham = getItem(i)
        viewHolder!!.title!!.text = sanpham.nameproduct + ""
        val formatter = DecimalFormat("###,###,###")
        viewHolder.subtitle!!.text = formatter.format(sanpham.price.toLong()) + "" + " VNĐ"
        viewHolder.iddetail = sanpham.idcart.toString()
        viewHolder.soluong!!.text = sanpham.sum.toString()
        viewHolder.giasp = sanpham.price.toString()
        viewHolder.tongtien!!.text = formatter.format(sanpham.totalprice.toLong()) + "" + " VNĐ"
        viewHolder.tong = sanpham.totalprice.toString()
        Glide.with(context).load(sanpham.hinh).into(viewHolder.hinh!!)
        //viewHolder.motasanpham.setText(sanpham.getBnt());
        return view
    }

    private fun tangsosp(viewHolder: ViewHolder?) {
        var sl: Int = viewHolder!!.soluong!!.text as Int
        sl += 1
        val t = "" + sl
        viewHolder.soluong!!.text = t
        val id = viewHolder.iddetail
        updateSoLuong(id, t)
        val tt = (sl * viewHolder.giasp!!.toInt()).toString()
        viewHolder.tongtien!!.text = "$tt VNĐ"
    }

    private fun giamsosp(viewHolder: ViewHolder?) {
        var sl: Int = viewHolder!!.soluong!!.text as Int
        if (sl == 1) {
            return
        } else {
            sl -= 1
            val t = "" + sl
            viewHolder.soluong!!.text = t
            val id = viewHolder.iddetail
            updateSoLuong(id, t)
            val tt = (sl * viewHolder.giasp!!.toInt()).toString()
            viewHolder.tongtien!!.text = "$tt VNĐ"
        }
    }

    private fun xoasp(viewHolder: ViewHolder?) {
        val id = viewHolder!!.iddetail
        deleteItemInCart(id)
    }

    private fun updateSoLuong(id: String?, sum: String) {
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.updateItemInCart,
            Response.Listener { response ->
                if (response == "Done") {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    context,
                    "Lỗi $error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val param = HashMap<String, String>()
                param["iddetail"] = id!!
                param["sum"] = sum
                return param
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun deleteItemInCart(id: String?) {
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Server.deletedItemInCart,
            Response.Listener { response ->
                if (response == "Done") {
                    Toast.makeText(context, "Xoa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xoa thất bại", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    context,
                    "Lỗi 2 $error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val param = HashMap<String, String>()
                param["iddetail"] = id!!
                return param
            }
        }
        requestQueue.add(stringRequest)
    }
}