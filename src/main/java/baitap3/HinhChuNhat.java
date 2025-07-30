/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap3;

/**
 *
 * @author lap top
 */
public class HinhChuNhat implements HinhHoc {
    private double chieuDai; 
    private double chieuRong;

    public HinhChuNhat(double chieuDai, double chieuRong) {
        this.chieuDai = chieuDai;
        this.chieuRong = chieuRong;
    }

    public double getChieuDai() {return chieuDai;}

    public void setChieuDai(double chieuDai) {this.chieuDai = chieuDai;}

    public double getChieuRong() {return chieuRong;}

    public void setChieuRong(double chieuRong) {this.chieuRong = chieuRong;    }

    @Override
    public double tinhDienTich() {
        return chieuDai * chieuRong;
    }

    @Override
    public double tinhChuVi() {
        return 2 * (chieuDai + chieuRong);
    }
}
