/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap4;

import java.util.Scanner;

/**
 *
 * @author lap top
 */
public class MenuChucNang {

    // Hiển thị các lựa chọn cho người dùng
    public void hienThiMenu() {
        System.out.println("===== MENU QUAN LY SACH =====");
        System.out.println("1. Hien thi tat ca sach");
        System.out.println("2. Tim sach theo ten");
        System.out.println("3. Thong ke sach theo nam xuat ban");
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon");
    }

    // Lấy lựa chọn của người dùng từ bàn phím
    public int layLuaChon(Scanner scanner) {
        int luaChon = -1; // Giá trị mặc định
        try {
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ ký tự Enter còn lại sau khi nhập số
        } catch (java.util.InputMismatchException e) {
            System.out.println("Loi:");
            scanner.nextLine(); // Đọc bỏ dòng nhập sai
        }
        return luaChon;
    }

    // Lấy từ khóa tìm kiếm từ người dùng (vì tìm kiếm cần input string)
    public String layTuKhoaTimKiem(Scanner scanner) {
        System.out.print("tim kiem: ");
        return scanner.nextLine();
    }
}
