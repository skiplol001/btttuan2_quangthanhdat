/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap4;

/**
 *
 * @author lap top
 */
public class Sach {
    private String maSach;
    private String tenSach;
    private String tacGia;
    private int namXuatBan;
    public Sach(String maSach, String tenSach, String tacGia, int namXuatBan) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.namXuatBan = namXuatBan;
    }
    public String getMaSach() {return maSach;}
    public String getTenSach() {return tenSach;}
    public String getTacGia() {return tacGia;}
    public int getNamXuatBan() {return namXuatBan;} 
}