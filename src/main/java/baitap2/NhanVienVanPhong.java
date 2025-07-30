/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap2;

/**
 *
 * @author lap top
 */
public class NhanVienVanPhong extends NhanVien {
    private final int soNgayLamViec;

    public NhanVienVanPhong(String hoTen, double luongCoBan, int soNgayLamViec) {
        super(hoTen, luongCoBan);
        this.soNgayLamViec = soNgayLamViec;
    }

    @Override
    public double tinhLuong() {
        return luongCoBan + soNgayLamViec * 100000; // Lương = luongCoBan + soNgayLamViec * 100.000
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("So ngay lam viec: " + soNgayLamViec);
    }
}
