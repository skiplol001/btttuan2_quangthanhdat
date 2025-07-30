/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaptuan2.baitap1;

import java.util.Scanner;

/**
 *
 * @author lap top
 */
import java.util.Scanner; // Đảm bảo đã import Scanner

public class phuongthuc_sp extends SanPham {

    // Hàm tạo có tham số
    public phuongthuc_sp(String ma, String ten, Double gia) {
        super(ma, ten, gia);
    }

    // Hàm tạo mặc định
    public phuongthuc_sp() {
        super("SP-00", "San pham khong ton tai", 0.0);
    }

    public static phuongthuc_sp taoSanPhamTuNhapLieu(Scanner inputScanner) {
        String maSP;
        String tenSP;
        Double giaSP;

        System.out.println(
                "\n--- Nhap thong tin san pham ---");

        System.out.print(
                "Nhap ma san pham (nhan Enter de dung mac dinh 'SP-00'): ");
        maSP = inputScanner.nextLine().trim(); //loại bỏ khoảng trắng thừa ở đầu/cuối

        if (maSP.isEmpty()) {
            maSP = "SP-00";
        }

        System.out.print(
                "Nhap ma san pham (nhan Enter de dung mac dinh 'San pham khong ton tai'): ");
        tenSP = inputScanner.nextLine().trim();

        if (tenSP.isEmpty()) {
            tenSP = "San pham khong ton tai";
        }

        // Nhập Giá sản phẩm (có thể phức tạp hơn một chút với số)
        System.out.print(
                "Nhap ma san pham (nhan Enter de dung mac dinh 0.0): ");
        String giaStr = inputScanner.nextLine().trim();

        if (giaStr.isEmpty()) {
            giaSP = 0.0;
        } else {
            try {
                giaSP = Double.valueOf(giaStr);
            } catch (NumberFormatException e) {
                giaSP = 0.0;
            }
        }

        // Tạo và trả về đối tượng phuongthuc_sp với các giá trị đã xử lý
        return new phuongthuc_sp(maSP, tenSP, giaSP);

    }

}
