/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author lap top
 */
public class QuanLyHinhHoc {
    private ArrayList<HinhHoc> danhSachHinhHoc;
    private Scanner scanner;

    public QuanLyHinhHoc(Scanner scanner) {
        this.danhSachHinhHoc = new ArrayList<>();
        this.scanner = scanner;
    }
    public boolean themHinhHoc() {
        System.out.println("\n--- Them Hinh Hoc Moi ---");

        System.out.print("Loai hinh (1: Hinh tron, 2: Hinh chu nhat): ");
        int loaiHinh = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        HinhHoc hinh = null;

        switch (loaiHinh) {
            case 1: // Hinh Tron
                System.out.print("Nhap ban kinh: ");
                double banKinh = scanner.nextDouble();
                hinh = new HinhTron(banKinh);
                break;
            case 2: // Hinh Chu Nhat
                System.out.print("Nhap chieu dai: ");
                double chieuDai = scanner.nextDouble();
                System.out.print("Nhap chieu rong: ");
                double chieuRong = scanner.nextDouble();
                hinh = new HinhChuNhat(chieuDai, chieuRong);
                break;
            default:
                System.out.println("Loai hinh khong hop le.");
                return false;
        }
        scanner.nextLine(); 

        if (hinh != null) {
            danhSachHinhHoc.add(hinh);
            return true;
        }
        return false;
    }

    public void inThongTinHinhHoc() {
        if (danhSachHinhHoc.isEmpty()) {
            System.out.println("\nDanh sach hinh hoc hien dang rong.");
            return;
        }
        System.out.println("\n--- THONG TIN CAC HINH HOC ---");
        for (int i = 0; i < danhSachHinhHoc.size(); i++) {
            HinhHoc hinh = danhSachHinhHoc.get(i);
            System.out.println("--------------------");
            System.out.println("Hinh " + (i + 1) + ":");
            System.out.println("  Dien tich: " + String.format("%.2f", hinh.tinhDienTich()));
            System.out.println("  Chu vi: " + String.format("%.2f", hinh.tinhChuVi()));
        }
    }
}
