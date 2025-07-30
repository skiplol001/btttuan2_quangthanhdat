/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap2;

/**
 *
 * @author lap top
 */
public class NhanVienSanXuat extends NhanVien {
    private final int soSanPham;

    public NhanVienSanXuat(String hoTen, double luongCoBan, int soSanPham) {
        super(hoTen, luongCoBan);
        this.soSanPham = soSanPham;
    }

    @Override
    public double tinhLuong() {
        return luongCoBan + soSanPham * 20000; // Lương = luongCoBan + soSanPham * 20000
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("So san pham: " + soSanPham);
    }
}
