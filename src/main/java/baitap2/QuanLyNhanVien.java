
/** Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuanLyNhanVien {
    private ArrayList<NhanVien> danhSachNhanVien;
    private Scanner scanner; // Nên giữ nguyên là Scanner scanner

    public QuanLyNhanVien(Scanner scanner) {
        this.danhSachNhanVien = new ArrayList<>();
        this.scanner = scanner;
    }
    private int readIntInput(String prompt) {
        int value = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                if (value < 0) {
                    System.out.println("Loi: Gia tri khong the la so am. Vui long nhap lai.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Loi: Gia tri phai la mot so nguyen. Vui long nhap lai.");
                scanner.nextLine(); // Xóa bỏ input không hợp lệ khỏi bộ đệm
            }
        }
        scanner.nextLine(); // Tiêu thụ ký tự xuống dòng còn lại
        return value;
    }
    private double readDoubleInput(String prompt) {
        double value = -1.0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print(prompt);
                value = scanner.nextDouble();
                if (value < 0) {
                    System.out.println("Loi: Gia tri khong the la so am. Vui long nhap lai.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Loi: Gia tri phai la mot so. Vui long nhap lai.");
                scanner.nextLine(); // Xóa bỏ input không hợp lệ khỏi bộ đệm
            }
        }
        scanner.nextLine(); // Tiêu thụ ký tự xuống dòng còn lại
        return value;
    }
    public boolean themNhanVien() {
        System.out.println("\n--- Them Nhan Vien Moi ---");

        System.out.print("Nhap ho ten: ");
        String hoTen = scanner.nextLine(); // Đọc cả dòng cho tên

        // Sử dụng phương thức hỗ trợ để đọc lương cơ bản
        double luongCoBan = readDoubleInput("Nhap luong co ban: ");
        int loaiNhanVien;
        while (true) {
            loaiNhanVien = readIntInput("Loai nhan vien (1: Van phong, 2: San xuat): ");
            if (loaiNhanVien == 1 || loaiNhanVien == 2) {
                break; // Thoát vòng lặp nếu nhập đúng loại
            } else {
                System.out.println("Loai nhan vien khong hop le. Vui long nhap 1 hoac 2.");
            }
        }
        NhanVien nv = null; // Khởi tạo null
        switch (loaiNhanVien) {
            case 1: // Nhan Vien Van Phong
                // Sử dụng phương thức hỗ trợ để đọc số ngày làm việc
                int soNgayLamViec = readIntInput("Nhap so ngay lam viec: ");
                nv = new NhanVienVanPhong(hoTen, luongCoBan, soNgayLamViec);
                break;
            case 2: // Nhan Vien San Xuat
                // Sử dụng phương thức hỗ trợ để đọc số sản phẩm
                int soSanPham = readIntInput("Nhap so san pham: ");
                nv = new NhanVienSanXuat(hoTen, luongCoBan, soSanPham);
                break;
            default:
                // Trường hợp này hiếm khi xảy ra do vòng lặp kiểm tra loaiNhanVien đã bao quát
                System.out.println("Loai nhan vien khong hop le. Khong the them nhan vien.");
                return false; // Trả về false nếu không tạo được nhân viên
        }
        if (nv != null) {
            danhSachNhanVien.add(nv);
            return true; // Trả về true nếu thêm thành công
        }
        return false; // Trường hợp không tạo được (nhưng đã xử lý ở default)
    }
    
    public void inDanhSachNhanVien() {
        if (danhSachNhanVien.isEmpty()) {
            System.out.println("\nDanh sach nhan vien hien dang rong.");
            return;
        }
        System.out.println("\n--- THONG TIN CHI TIET CUA TAT CA NHAN VIEN ---");
        for (NhanVien nv : danhSachNhanVien) {
            System.out.println("--------------------");
            nv.hienThiThongTin(); // Gọi phương thức hiển thị thông tin của từng đối tượng
        }
        System.out.println("--------------------");
    }
}