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
        System.out.println("===== MENU QUẢN LÝ SÁCH =====");
        System.out.println("1. Hiển thị tất cả sách");
        System.out.println("2. Tìm sách theo tên");
        System.out.println("3. Thống kê sách theo năm xuất bản");
        System.out.println("0. Thoát");
        System.out.print("Nhập lựa chọn của bạn: ");
    }

    // Lấy lựa chọn của người dùng từ bàn phím
    public int layLuaChon(Scanner scanner) {
        int luaChon = -1; // Giá trị mặc định
        try {
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ ký tự Enter còn lại sau khi nhập số
        } catch (java.util.InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập một số hợp lệ.");
            scanner.nextLine(); // Đọc bỏ dòng nhập sai
        }
        return luaChon;
    }

    // Lấy từ khóa tìm kiếm từ người dùng (vì tìm kiếm cần input string)
    public String layTuKhoaTimKiem(Scanner scanner) {
        System.out.print("Nhập từ khóa tên sách cần tìm: ");
        return scanner.nextLine();
    }
}
