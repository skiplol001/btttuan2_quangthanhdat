/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap4;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author lap top
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        // Tạo một đối tượng Scanner để đọc input từ người dùng, dùng chung cho toàn bộ ứng dụng
        // Tạo đối tượng quản lý sách (chứa danh sách sách và các chức năng xử lý sách)
        QuanLySach quanLySach = new QuanLySach();

        // Tạo đối tượng menu chức năng (chỉ để hiển thị menu và lấy lựa chọn)
        MenuChucNang menu = new MenuChucNang();

        int luaChon;
        do {
            menu.hienThiMenu(); // Hiển thị menu cho người dùng
            luaChon = menu.layLuaChon(scanner); // Lấy lựa chọn của người dùng

            switch (luaChon) {
                case 1:
                    quanLySach.hienThiTatCaSach();
                    break;
                case 2:
                    String tuKhoa = menu.layTuKhoaTimKiem(scanner); // Lấy từ khóa từ người dùng
                    quanLySach.timSachTheoTen(tuKhoa); // Gọi chức năng tìm kiếm
                    break;
                case 3:
                    quanLySach.thongKeSachTheoNamXuatBan();
                    break;
                case 0:
                    System.out.println("Đang thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.\n");
            }
        } while (luaChon != 0); // Lặp lại cho đến khi người dùng chọn 0

        scanner.close(); // Đóng Scanner khi chương trình kết thúc
    }
}
