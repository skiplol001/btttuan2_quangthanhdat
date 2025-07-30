/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaptuan2.baitap1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author lap top
 */
public class ham_main {

    public static void hienThiMenu() {
        System.out.println("\n--- MENU QUAN LY SAN PHAM ---");
        System.out.println("1. Nhap thong tin san pham moi");
        System.out.println("2. Hien thi tat ca san pham");
        System.out.println("3. San pham gia cao nhat");
        System.out.println("0. Thoat chuong trinh");
        System.out.print("Vui long nhap lua chon: ");
    }

    public static void main(String[] args) {
        List<phuongthuc_sp> danhSachSanPham = new ArrayList<>();
        try (Scanner luachon = new Scanner(System.in)) { // Đổi tên luachon thành mainScanner để thống nhất và dễ hiểu
            int luaChon; // Biến để lưu lựa chọn của người dùng
            do {
                hienThiMenu();
                luaChon = luachon.nextInt();
                luachon.nextLine();
                switch (luaChon) {
                    case 1 -> {
                        phuongthuc_sp spMoi = phuongthuc_sp.taoSanPhamTuNhapLieu(luachon);
                        danhSachSanPham.add(spMoi);
                        System.out.println("da them san pham nay");
                    }
                    case 2 -> {
                        if (danhSachSanPham.isEmpty()) {
                            System.out.println("Danh sach san pham hien dang rong.");
                        } else {
                            Collections.sort(danhSachSanPham, Comparator.comparingDouble(phuongthuc_sp::getGia).reversed());
                            System.out.println("\n--- DANH SACH SAN PHAM (Sap xep theo gia giam dan) ---");
                            for (int i = 0; i < danhSachSanPham.size(); i++) {
                                System.out.println((i + 1) + ". " + danhSachSanPham.get(i));
                            }
                        }
                    }
                     case 3 -> {
                            // Find and display the most expensive product
                            if (danhSachSanPham.isEmpty()) {
                                System.out.println("Danh sach san pham hien dang rong.");
                            } else {
                                phuongthuc_sp sanPhamDatNhat = null;
                                double giaCaoNhat = -1.0;

                                for (phuongthuc_sp sp : danhSachSanPham) {
                                    if (sp.getGia() > giaCaoNhat) {
                                        giaCaoNhat = sp.getGia();
                                        sanPhamDatNhat = sp;
                                    }
                                }
                                System.out.println("\n--- SAN PHAM CO GIA TRI CAO NHAT ---");
                                if (sanPhamDatNhat != null) {
                                    System.out.println(sanPhamDatNhat);
                                }
                            }
                     }
                    case 0 ->
                        System.out.println("thoat chuong trinh...");
                    default ->
                        System.out.println("khong co lua chon nay");
                }
            } while (luaChon != 0);
        }
    }
}
