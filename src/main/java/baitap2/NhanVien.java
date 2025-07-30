/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap2;

/**
 *
 * @author lap top
 */
public abstract class NhanVien {
    protected String hoTen;
    protected double luongCoBan;

    public NhanVien(String hoTen, double luongCoBan) {
        this.hoTen = hoTen;
        this.luongCoBan = luongCoBan;
    }

    // Phương thức trừu tượng để tính lương
    public abstract double tinhLuong();

    // Phương thức hiển thị thông tin
    public void hienThiThongTin() {
        System.out.println("Ho ten: " + hoTen);
        System.out.println("Luong co ban: " + String.format("%.2f", luongCoBan));
        System.out.println("Luong thuc te: " + String.format("%.2f", tinhLuong()));
    }
}
