package baitap2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void hienThiMenu() {
        System.out.println("\n===== MENU QUAN LY NHAN VIEN =====");
        System.out.println("1. Them nhan vien moi");
        System.out.println("2. In danh sach nhan vien");
        System.out.println("0. Thoat chuong trinh");
        System.out.print("Nhap lua chon cua ban: ");
    }

    public static void main(String[] args) {
        // Sử dụng try-with-resources để tự động đóng Scanner
        try (Scanner scanner = new Scanner(System.in)) {
            // Tạo một đối tượng QuanLyNhanVien, truyền đối tượng 'scanner' đã khởi tạo
            QuanLyNhanVien qlnv = new QuanLyNhanVien(scanner); // <--- FIX IS HERE
            int luaChon;
            do {
                hienThiMenu(); // Hiển thị menu chức năng
                try {
                    luaChon = scanner.nextInt();
                    scanner.nextLine(); // Tiêu thụ ký tự xuống dòng sau nextInt()
                    switch (luaChon) {
                        case 1: // Thêm nhân viên
                            // Gọi phương thức themNhanVien từ đối tượng QuanLyNhanVien
                            if (qlnv.themNhanVien()) {
                                System.out.println("Them nhan vien vao danh sach thanh cong!");
                            }
                            // Phương thức themNhanVien đã tự in thông báo lỗi nếu có
                            break;
                        case 2: // In danh sách nhân viên
                            // Gọi phương thức inDanhSachNhanVien từ đối tượng QuanLyNhanVien
                            qlnv.inDanhSachNhanVien();
                            break;
                        case 0: // Thoát chương trình
                            System.out.println("Dang thoat chuong trinh. Tam biet!");
                            break;
                        default: // Lựa chọn không hợp lệ
                            System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Loi: Lua chon phai la mot so nguyen. Vui long nhap lai.");
                    scanner.nextLine(); // Xóa bỏ input không hợp lệ khỏi bộ đệm
                    luaChon = -1; // Đặt giá trị không hợp lệ để tiếp tục vòng lặp
                }

            } while (luaChon != 0); // Vòng lặp tiếp tục cho đến khi người dùng chọn 0
        }
    }
}