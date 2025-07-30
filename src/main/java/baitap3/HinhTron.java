/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap3;

/**
 *
 * @author lap top
 */
public class HinhTron implements HinhHoc {
    private double banKinh; // Radius of the circle

    public HinhTron(double banKinh) {this.banKinh = banKinh;}
   
    public double getBanKinh() {return banKinh;}
    public void setBanKinh(double banKinh) {this.banKinh = banKinh;}

    @Override
    public double tinhDienTich() {return Math.PI * banKinh * banKinh;}

    @Override
    public double tinhChuVi() {return 2 * Math.PI * banKinh;}
}