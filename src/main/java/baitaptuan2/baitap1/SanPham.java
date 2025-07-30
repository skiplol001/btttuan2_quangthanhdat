/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package baitaptuan2.baitap1;

/**
 *
 * @author lap top
 */
public class SanPham {

    public String ma;
    public String getMa() {return ma;}
    public void setMa(String ma) {this.ma = ma;}
    
    public String ten;
    public String getTen() {return ten;}
    public void setTen(String ten) {this.ten = ten;}
    
    public Double gia;
    public Double getGia() {return gia;}
    public void setGia(Double gia) {this.gia = gia;}
    
     public SanPham(String ma, String ten, Double gia) {
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
    }
    @Override 
    public String toString() {
        return "Ma san pham: " + ma + ", Ten san pham: " + ten + ", Gia: " + gia;
    }
}
