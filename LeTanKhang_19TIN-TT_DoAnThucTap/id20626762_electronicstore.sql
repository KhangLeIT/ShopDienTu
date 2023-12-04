-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost:3306
-- Thời gian đã tạo: Th7 09, 2023 lúc 06:54 AM
-- Phiên bản máy phục vụ: 10.5.20-MariaDB
-- Phiên bản PHP: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `id20626762_electronicstore`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `adminstore`
--

CREATE TABLE `adminstore` (
  `id` int(11) NOT NULL,
  `tenadmin` varchar(100) NOT NULL,
  `sdtadmin` varchar(100) NOT NULL,
  `taikhoan` varchar(100) NOT NULL,
  `matkhau` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `adminstore`
--

INSERT INTO `adminstore` (`id`, `tenadmin`, `sdtadmin`, `taikhoan`, `matkhau`) VALUES
(1, 'Khang Le', '0788949502', 'Admin', '123123');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `iddonhang` int(11) NOT NULL,
  `idsp` int(11) NOT NULL,
  `soluongmua` int(11) NOT NULL,
  `gia` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`iddonhang`, `idsp`, `soluongmua`, `gia`) VALUES
(44, 13, 1, 24590000),
(44, 11, 2, 11960000),
(45, 13, 2, 49180000),
(45, 12, 1, 5990000),
(45, 8, 3, 27570000),
(46, 7, 2, 4200000),
(46, 1, 3, 11610000),
(46, 9, 1, 4444000),
(47, 13, 1, 24590000),
(47, 9, 3, 13332000),
(47, 7, 2, 4200000),
(48, 13, 3, 73770000),
(48, 9, 2, 8888000),
(48, 12, 1, 5990000),
(49, 40, 1, 500000),
(50, 40, 1, 500000),
(50, 13, 1, 24590000),
(51, 40, 1, 500000),
(51, 13, 1, 24590000),
(52, 12, 1, 5990000),
(52, 13, 1, 24590000),
(52, 11, 1, 5980000),
(53, 8, 1, 9190000),
(53, 7, 3, 6300000),
(54, 5, 1, 17990000),
(54, 12, 3, 17970000),
(55, 13, 2, 49180000),
(55, 9, 3, 13332000),
(55, 7, 1, 2100000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmuctintuc`
--

CREATE TABLE `danhmuctintuc` (
  `id` int(11) NOT NULL,
  `tendm` varchar(255) NOT NULL,
  `hinhanh` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `danhmuctintuc`
--

INSERT INTO `danhmuctintuc` (`id`, `tendm`, `hinhanh`) VALUES
(1, 'điện thoại', ''),
(2, 'laptop', ''),
(3, 'đồng hồ', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `diachi` text NOT NULL,
  `sodienthoai` text NOT NULL,
  `soluong` int(11) NOT NULL,
  `tongtien` float NOT NULL,
  `trangthai` int(11) NOT NULL,
  `ngaytaodon` datetime NOT NULL,
  `ngayhoanthanh` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`id`, `iduser`, `diachi`, `sodienthoai`, `soluong`, `tongtien`, `trangthai`, `ngaytaodon`, `ngayhoanthanh`) VALUES
(44, 27, 'demo', '0788949502', 2, 36550000, 3, '2023-05-21 14:37:26', '2023-05-21 16:58:08'),
(45, 1, 'đây là địa chỉ nè', '0788949502', 3, 82740000, 1, '2023-05-21 18:40:47', NULL),
(46, 1, 'địa chỉ admin nè', '0788949502', 3, 20254000, 2, '2023-05-21 18:42:53', NULL),
(47, 19, 'admun', '0788949502', 3, 42122000, 3, '2023-05-24 10:51:02', '2023-05-26 11:59:01'),
(48, 29, 'can tho', '0788949502', 3, 88648000, 0, '2023-06-09 13:53:27', NULL),
(49, 29, 'hyyggg', '0788949502', 1, 500000, 3, '2023-06-09 14:02:33', '2023-06-09 14:08:45'),
(50, 30, 'gg', '0788949502', 2, 25090000, 0, '2023-06-10 15:20:55', NULL),
(51, 30, 'gg', '0788949502', 2, 25090000, 0, '2023-06-10 15:21:03', NULL),
(52, 19, 'dem địa chỉ giao hàng', '0788949502', 3, 36560000, 3, '2023-06-18 14:09:33', '2023-06-18 16:08:23'),
(53, 19, 'demo 2', '0788949502', 2, 15490000, -1, '2023-06-18 14:09:55', '2023-06-18 16:06:44'),
(54, 31, 'demo', '0789456321', 2, 35960000, -1, '2023-06-22 17:32:30', '2023-06-22 17:32:52'),
(55, 32, 'Demo test', '0788949502', 3, 64612000, 3, '2023-07-07 09:25:48', '2023-07-07 09:33:54');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `iddm` int(11) NOT NULL,
  `tensanpham` varchar(100) NOT NULL,
  `hinhanh` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`iddm`, `tensanpham`, `hinhanh`) VALUES
(1, 'Điện Thoại', 'icon-phone.png'),
(2, 'Laptop', 'icon-laptop.png'),
(3, 'Đông Hồ', 'icon-wath.png'),
(4, 'Tai Nghe', 'icon-tainghe.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `idsp` int(11) NOT NULL,
  `tensp` varchar(250) NOT NULL,
  `hinhanhsp` text NOT NULL,
  `motasp` text NOT NULL,
  `thongtinsp` text NOT NULL,
  `giabansp` float NOT NULL,
  `iddm` int(11) NOT NULL,
  `slco` int(11) NOT NULL,
  `linkvideo` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`idsp`, `tensp`, `hinhanhsp`, `motasp`, `thongtinsp`, `giabansp`, `iddm`, `slco`, `linkvideo`) VALUES
(1, 'Điện thoại realme C55 6GB', 'realme-c35-vang-thumb-200x200.jpg', 'mo tai sp dien thoai id 1', 'thong tin dien thoai co id 1', 3870000, 1, 5, NULL),
(3, 'Điện thoại Samsung Galaxy A33 5G 6GB', 'samsung-galaxy-a33-5g-200x200.jpg', 'Mô Tả Sp id 3', 'Thông tin sp id 3', 6680000, 1, 10, 'nswesAQ0MGM'),
(4, 'Điện thoại iPhone 14 512GB', 'iPhone-14-plus-thumb-den-600x600.jpg', 'mo ta sp tip theo', 'ip 14 pro max', 27990000, 1, 11, 'jUHQQfWbjtk'),
(5, 'Điện thoại iPhone 12 256GB', 'iphone-12-do-new-600x600-600x600.jpg', 'mo ta ip 12', '256gb ip 12', 17990000, 1, 11, 'O_Kj9TJDR-o'),
(6, 'Điện thoại Samsung Galaxy A34 5G 256GB', 'samsung-galaxy-a34-5g-bac-thumb-600x600.jpg', 'samsung mo ta', 'thong tin samsung', 9190000, 1, 11, '1Zx1Av_FRTs'),
(7, 'Điện thoại Xiaomi Redmi A1', 'xiaomi-redmi-a1-thumb-200x200.jpg', 'mo ta san pham redmi', 'thong tin ne', 2100000, 1, 11, NULL),
(8, 'Điện thoại Xiaomi Redmi Note 12 Pro 5G', 'xiaomi-redmi-note-12-pro-xanh-thumb-600x600.jpg', 'mo ta san pham xiaomi', 'thong tin dien thoai xiaomi redmi', 9190000, 1, 10, 'aoivOb4lamY'),
(9, 'Điện thoại Samsung Galaxy A14 6GB', 'samsung-galaxy-a14-black-thumb-600x600.jpg', 'mo ta sp dien samsung', 'thong tin ne', 4444000, 1, 10, 'NULL'),
(11, 'OPPO A57 128GB', 'oppo-a57-den-thumb-600x600.jpg', 'Màn hình: IPS LCD6.56\"HD+\r\nHệ điều hành: Android 12\r\nCamera sau: Chính 13 MP & Phụ 2 MP\r\nCamera trước: 8 MP\r\nChip: MediaTek Helio G35\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM - Hỗ trợ 4G\r\nPin, Sạc: 5000 mAh33 W', 'OPPO đã bổ sung thêm vào dòng sản phẩm OPPO A giá rẻ một thiết bị mới có tên OPPO A57 128GB. Khác với mẫu A57 5G đã được ra mắt trước đó, điện thoại dòng A mới có màn hình HD+, camera chính 13 MP và pin 5000 mAh', 5980000, 1, 11, NULL),
(12, 'Điện Thoại realme C55 8GB ', 'realme-c55-den-thumb-200x200.jpg', 'Màn hình: IPS LCD6.72\"Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 64 MP & Phụ 2 MP\r\nCamera trước: 8 MP\r\nChip: MediaTek Helio G88\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM - Hỗ trợ 4G\r\nPin, Sạc: 5000 mAh33 W', 'realme C55 8GB mẫu điện thoại mới đến từ nhà realme, sở hữu bộ thông số khá ấn tượng. Máy mang trong mình một thiết kế đẹp mắt, hiệu năng ổn định cùng khả năng chụp ảnh chi tiết hứa hẹn sẽ không làm bạn thất vọng.', 5990000, 1, 10, 'TEceK2DDqFY'),
(13, 'Điện thoại iPhone 14 Plus 256GB', 'iPhone-14-thumb-do-600x600.jpg', 'Màn hình: OLED6.7\"Super Retina XDR\r\nHệ điều hành: iOS 16 \r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A15 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 1 Nano SIM & 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 4325 mAh20 W', 'iPhone 14 Plus 256GB vừa được Apple cho ra mắt với một diện mạo sang chảnh về thiết kế, vượt trội về hiệu năng và quay phim chụp ảnh chuyên nghiệp. Đây hứa hẹn sẽ là dòng sản phẩm mới nổi và sẽ thu hút được nhiều khách hàng săn đón trong thời gian tới.', 5500000, 1, 11, 'bml0r_KxON0'),
(40, 'apple watch', 'IMG_20230609_140034534.jpg', 'đồnh hemo.    test', 'đồg hồ thông tin', 500000, 3, 10, NULL),
(41, 'đồng hồ demo test', 'IMG_20230707_092909154.jpg', 'đồng hồ demo', 'đồng hồ demo test', 500000, 3, 10, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongbaodonhang`
--

CREATE TABLE `thongbaodonhang` (
  `idthongbao` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `noidungthongbao` text NOT NULL,
  `iddonhang` int(11) NOT NULL,
  `trangthaidoc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thongbaodonhang`
--

INSERT INTO `thongbaodonhang` (`idthongbao`, `iduser`, `noidungthongbao`, `iddonhang`, `trangthaidoc`) VALUES
(1, 27, 'day la demotest', 44, 0),
(2, 27, 'Đơn Hàng 44 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc 2023-05-21 15:57:40', 44, 0),
(3, 27, 'Đơn Hàng 44 Của Bạn Đang Được Giao Vào Lúc CN, 21-05-2023,15:59:18', 44, 0),
(4, 27, 'Đơn Hàng 44 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc CN, 16:01:32, 21-05-2023', 44, 0),
(5, 27, 'Đơn Hàng 44 Của Bạn Đang Được Giao Vào Lúc  16:02:45, CN, 21-05-2023', 44, 0),
(6, 27, 'Đơn Hàng 44 Của Bạn Được Nhân Viên Xác Nhận Hòa Thành Vào Lúc  16:58:08 - CN, 21-05-2023', 44, 0),
(7, 1, 'Đơn Hàng 45 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc  18:41:21 - CN, 21-05-2023', 45, 0),
(8, 1, 'Đơn Hàng 46 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc  18:43:21 - CN, 21-05-2023', 46, 0),
(9, 1, 'Đơn Hàng 46 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  18:43:51 - CN, 21-05-2023', 46, 0),
(10, 19, 'Đơn Hàng 47 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc  10:51:21 - T.4, 24-05-2023', 47, 0),
(11, 19, 'Đơn Hàng 47 Của Bạn Được Nhân Viên Xác Nhận Hòa Thành Vào Lúc  10:52:49 - T.4, 24-05-2023', 47, 0),
(12, 19, 'Đơn Hàng 47 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  13:38:43 - T.4, 24-05-2023', 47, 0),
(13, 19, 'Đơn Hàng 47 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  11:49:12 - T.6, 26-05-2023', 47, 0),
(14, 19, 'Đơn Hàng 47 Của Bạn Đang Ở Trạng Thá Chờ Duyệt Vào lúc  11:58:36 - T.6, 26-05-2023', 47, 0),
(15, 19, 'Đơn Hàng 47 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  11:58:52 - T.6, 26-05-2023', 47, 0),
(16, 29, 'Đơn Hàng 49 Của Bạn Đang Ở Trạng Thá Chờ Duyệt Vào lúc  14:05:23 - T.6, 09-06-2023', 49, 0),
(17, 29, 'Đơn Hàng 49 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc  14:05:36 - T.6, 09-06-2023', 49, 0),
(18, 29, 'Đơn Hàng 49 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  14:06:19 - T.6, 09-06-2023', 49, 0),
(19, 29, 'Đơn Hàng 49 Của Bạn Đang Ở Trạng Thá Chờ Duyệt Vào lúc  14:06:55 - T.6, 09-06-2023', 49, 0),
(20, 29, 'Đơn Hàng 49 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc  14:07:40 - T.6, 09-06-2023', 49, 0),
(21, 29, 'Đơn Hàng 49 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  14:08:25 - T.6, 09-06-2023', 49, 0),
(22, 29, 'Đơn Hàng 49 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  14:08:38 - T.6, 09-06-2023', 49, 0),
(23, 19, 'Đơn Hàng 52 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  16:01:51 - CN, 18-06-2023', 52, 0),
(24, 32, 'Đơn Hàng 55 Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc  09:27:00 - T.6, 07-07-2023', 55, 0),
(25, 32, 'Đơn Hàng 55 Của Bạn Đang Được Giao Cho Shipper Vào Lúc  09:33:40 - T.6, 07-07-2023', 55, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tintuc`
--

CREATE TABLE `tintuc` (
  `id` int(11) NOT NULL,
  `tieude` varchar(250) NOT NULL,
  `hinhanh` varchar(250) NOT NULL,
  `noidung` text NOT NULL,
  `iddm` int(11) NOT NULL,
  `idsp` int(11) DEFAULT NULL,
  `luotxem` int(11) NOT NULL,
  `thoigianthem` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tintuc`
--

INSERT INTO `tintuc` (`id`, `tieude`, `hinhanh`, `noidung`, `iddm`, `idsp`, `luotxem`, `thoigianthem`) VALUES
(1, 'Phát mê với điện thoại mỏng nhẹ mà giá quá rẻ chỉ còn 5.49 triệu', 'xiaomi-13-lite-35-290423-074257-800-resize.jpg', 'Xiaomi 13 Lite chính thức sẽ là mẫu điện thoại được giới thiệu và mở bán tại thị trường Việt Nam trong phân khúc tầm trung vừa được Xiaomi cho ra mắt, hiện nay máy đang được đánh giá là rất nổi trội trong phân khúc khi sở hữu con chip mới từ nhà Qualcomm mang tên Snapdragon 7 Gen 1, kèm theo đó là sạc nhanh 67 W cùng hệ thống camera được sản xuất từ nhà Sony vô cùng xịn sò.\nThiết kế với diện mạo đầy sang trọng\nVề thiết kế thì Xiaomi 13 Lite sở hữu một diện mạo hết sức trẻ trung và hiện đại, mặt lưng được bo cong hai cạnh cùng cụm camera thiết kế theo dạng tròn giúp tạo nên một cái nhìn tổng thể đầy mềm mại và uyển chuyển. Điểm đáng chú ý lần này là cụm camera dạng viên thuốc chứa bên trong hai camera, điều này đem tới một điểm mới mẻ về phần màn hình giúp người dùng có thể dễ dàng nhận diện.\n \nKhác với Xiaomi 12, mẫu điện thoại Xiaomi 13 Lite này sẽ được khoác lên mình bộ áo mới mẻ với những màu sắc vô cùng trẻ trung và đầy tính sang trọng, điều này vừa giúp tăng tính thẩm mỹ cũng như đem lại cho máy một điểm nhận diện sản phẩm hết sức đặc biệt.\nẤn tượng với chipset thế hệ mới\nVề thông số cấu hình của Xiaomi 13 Lite thì máy sẽ được tích hợp con chip Snapdragon 7 Gen 1, đây được xem là mẫu chip khá mới và sở hữu một hiệu năng hết sức mạnh mẽ, nhờ đó mà Xiaomi 13 Lite trở thành mẫu điện thoại dùng để chơi game được quan tâm bậc nhất trong giai đoạn đầu năm 2023.\n \nSở hữu hệ thống camera tân tiến đến từ nhà Sony\nTheo như thông tin hãng cung cấp thì Xiaomi 13 Lite sẽ được trang bị bộ 3 camera ở phần mặt sau, trong đó camera chính sẽ là cảm biến Sony IMX766 với độ phân giải 50 MP, kèm theo camera góc siêu rộng 8 MP và camera macro 2 MP giúp chụp hình với trường ảnh rộng hơn hay lấy nét những chi tiết ở tiêu cự gần.\n \nĐiều đặc biệt mà Xiaomi 13 Lite có được là máy sở hữu tận 2 camera ở mặt trước với độ phân giải là 32 MP và 8 MP giúp cho những bức ảnh thu lại đều đạt chất lượng ảnh cực kỳ cao. Một trong số đó là cảm biến góc siêu rộng giúp mang đến góc chụp lớn hơn để thu trọn khung cảnh hay chủ thể xung quanh bạn vào bức hình, rất phù hợp cho những trường hợp dùng để selfie nhóm.\nSử dụng công nghệ màn hình cao cấp\nCông nghệ màn hình mà hãng điện thoại Xiaomi sử dụng cho chiếc máy này là AMOLED với kích thước 6.55 inch, nhờ đó mà người dùng có thể chiêm ngưỡng nội dung được tốt hơn với khung hình lớn cùng màu sắc được tái tạo tươi mới và màu đen sâu cho ra cảm giác chân thực hơn.\n \nNgoài ra thì máy còn được hỗ trợ tần số quét 120 Hz giúp mang lại trải nghiệm vuốt chạm cực kỳ mượt mà, những nội dung từ trò chơi cho đến video ở định dạng FPS cao cũng đều được tái hiện một cách mượt mà.\nDuy trì năng lượng vừa đủ cho một ngày sử dụng liên tục\nỞ phiên bản 13 Lite này thì máy được trang bị một viên pin có dung lượng là 4500 mAh với tổng thời lượng dùng mang lại là cực kỳ lâu dài, đối với những tác vụ như: Nhắn tin, nghe gọi, xem phim, lướt web hay kể cả chơi game thì máy vẫn có thể đồng hành cùng bạn xuyên suốt một ngày mà không cần phải bận tâm về vấn đề sạc pin.\n \nNgoài ra thì đây còn là mẫu điện thoại sạc siêu nhanh với tổng công suất tối đa mà hãng hỗ trợ là 67 W, điều này sẽ giúp rút ngắn thời gian sạc của điện thoại để làm mọi trải nghiệm của bạn trở nên liền mạch hơn khi không phải mất quá lâu để đợi thiết bị nạp đầy dung lượng. \nSo sánh thông số kỹ thuật giữa Xiaomi 13 và Xiaomi 13 Pro\nDưới đây là bảng tổng hợp các danh mục về thông số kỹ thuật của bộ đôi Xiaomi 13 series vừa được công bố:\nThuộc tính	Xiaomi 13 Lite	Xiaomi 13\nMàn hình	AMOLED 6.55 inch	AMOLED 6.36 inch\nChip xử lý	Snapdragon 7 Gen 1 8 nhân	Snapdragon 8 Gen 2 8 nhân\nCamera sau	Chính 50 MP & Phụ 8 MP, 2 MP	Chính 50 MP & Phụ 12 MP, 10 MP\nCamera trước	Chính 32 MP & Phụ 8 MP	32 MP\nRAM 	8 GB	8 GB\nDung lượng pin	4500 mAh và sạc siêu nhanh 67 W	4500 mAh và sạc siêu nhanh 67 W\nMàu sắc	Xanh dương và đen	Xanh lá và đen\nVideo đánh giá chi tiết Xiaomi 13 Lite tại Thế Giới Di Động\nĐể biết thêm thông tin chi tiết về những trải nghiệm thực tế trên chiếc Xiaomi 13 Lite thì người dùng có thể tham khảo video đánh giá tại đây:\nCâu hỏi thường gặp trước khi mua Xiaomi 13 Lite là gì?\nXiaomi 13 Lite có được hỗ trợ tinh chỉnh ống kính từ Leica không?\nBởi đây có lẽ là phiên bản mà Xiaomi muốn mang đến cho người dùng thêm nhiều lựa chọn hơn ở phân khúc điện thoại tầm trung, vì thế để tối ưu được giá thành một cách tốt nhất thì phiên bản này sẽ không được hỗ trợ tinh chỉnh đến từ nhà Leica. Tuy nhiên với thông số ống kính chất lượng như vậy nên máy hoàn toàn có thể đem đến cho bạn những bức ảnh cực kỳ sắc nét.\nXiaomi 13 Lite hỗ trợ bao nhiêu khe SIM?\nXiaomi 13 Lite có thể sử dụng 2 khe SIM nano, thế nhưng máy lại không được hỗ trợ khe gắn thẻ Micro SD để mở rộng bộ nhớ lưu trữ. Vì vậy, dung lượng bộ nhớ tối đa của máy là 256 GB đúng theo con số mà hãng đã cung cấp, bấy nhiêu là đủ để bạn có thể tải xuống rất nhiều ứng dụng yêu thích cũng như thoải mái tải tệp xuống để lưu trữ các nội dung mà bạn muốn.\nSự khác biệt giữa Xiaomi 13 Lite và Xiaomi 13 là gì?\nTuy cả hai đều thuộc dòng điện thoại thế hệ 13 thế nhưng vẻ ngoài giữa hai máy gần như không có sự tương đồng về thiết kế, đối với Xiaomi 13 Lite thì máy được tạo hình bo cong với lối tạo hình cụm camera to tròn độc đáo, còn Xiaomi 13 lại sở hữu một vẻ ngoài vuông vức hơn cùng cụm camera ô vuông nổi bật kèm theo dòng chữ Leica ấn tượng.\nXiaomi 13 Lite hứa hẹn sẽ trở thành mẫu điện thoại đột phá về mọi mặt cho năm 2023. Với những thông số hết sức ấn tượng như trên thì đây quả thực là cái tên rất đáng để cân nhắc dành cho những bạn mong muốn tìm mua cho mình một thiết bị sở hữu mức giá tốt, có thể đáp ứng tốt cho việc chơi game hay hỗ trợ nhiếp ảnh chuyên nghiệp.\n\n', 1, 10, 60, '2023-05-01'),
(2, 'Đánh giá iPhone 14 Plus sau 6 tháng: Hiệu năng được cải thiện, pin dùng lâu hơn, camera chụp vẫn đẹp', 'iphone14plus-31-_2559x1440-800-resize.jpg', 'Nhắc đến iPhone 14 series thì iPhone 14 Plus là cái tên khá đặc biệt. Lý do là bởi phiên bản Plus đã không xuất hiện từ sau thế hệ iPhone 8 series. Ở thời điểm hiện tại, iPhone 14 Plus vẫn là chiếc điện thoại nổi bật với màn hình lớn như phiên bản Pro Max nhưng mức giá lại hợp lý hơn nhiều. Vậy sau 6 tháng kể từ ngày ra mắt liệu iPhone 14 Plus hiệu năng còn mạnh mẽ như trước? iPhone 14 Plus pin dùng trong bao lâu? iPhone 14 Plus camera chụp ảnh ra sao? Cùng mình đánh giá iPhone 14 Plus sau 6 tháng để tìm câu trả lời nha!\r\n\r\niPhone 14 Plus hiệu năng vẫn mạnh mẽ với Apple A15 Bionic\r\n\r\nVề cấu hình của iPhone 14 Plus, Apple đã trang bị cho máy con chip Apple A15 Bionic với 5 nhân đồ họa và vi xử lý này đã từng xuất hiện trên iPhone 13 Pro Series. Tính đến thời điểm hiện tại thì A15 Bionic vẫn thuộc TOP những vi xử lý mạnh mẽ nhất dành cho các thiết bị di động. Nhờ đó mà iPhone 14 Plus hoàn toàn có thể cân được các tác vụ nặng như chơi game đồ họa cao hay edit video.\r\nBên cạnh con chip Apple A15 Bionic, iPhone 14 Plus cũng được trang bị dung lượng RAM 6 GB và bộ nhớ trong tối đa lên đến 512 GB. Chính vì thế mà máy có thể hoạt động mượt mà và tránh tình trạng giật lag khi sử dụng thời gian dài. Đồng thời, thiết bị cũng đáp ứng tốt nhu cầu lưu trữ dữ liệu của người dùng.\r\n- Chấm điểm hiệu năng iPhone 14 Plus sau 6 tháng\r\nĐể cho các bạn có cái nhìn cụ thể nhất về hiệu năng của iPhone 14 Plus sau 6 tháng ra mắt, mình sẽ sử dụng các phần mềm chấm điểm chuyên dụng như AnTuTu Benchmark và 3DMark Wild Life. Điều kiện để mình chấm điểm cụ thể như sau:\r\n•	Pin của máy phải từ 90 - 100% (pin dưới 90% sẽ ảnh hưởng đến hiệu năng và kết quả).\r\n•	Không vừa sạc pin vừa chấm điểm.\r\n•	Chấm 3 lần liên tục và lấy kết quả trung bình sau 3 lần chấm.\r\n\r\nĐầu tiên ở bài test AnTuTu Benchmark thì iPhone 14 Plus sau 6 tháng vẫn cho kết quả nhỉnh hơn hẳn so với thời điểm máy mới ra mắt. Cụ thể, iPhone 14 Plus chạy iOS 16.4.1 đạt được là 797.409 điểm, cao hơn nhiều so với con số 771.483 điểm ở iOS 16.1.1. Điều này phần nào cho chúng ta thấy được khả năng xử lý các tác vụ nặng của iPhone 14 Plus được cải thiện nhiều hơn qua nhiều bản cập nhật phần mềm.\r\nCuối cùng là bài test nặng đô nhất trong phần chấm điểm hiệu năng ngày hôm nay - Wild Life Extreme Stress Test (bài test này sẽ tự động lặp lại liên tục 20 lần). Các bạn có thể xem kết quả bên dưới đây và chú ý giúp mình mục Stability (mức độ ổn định) nha!\r\n- 3DMark Wild Life Extreme Stress Test\r\n•	iOS 16.1.1: Mức độ ổn định 65.8%.\r\n•	iOS 16.4.1: Mức độ ổn định 95.0%.\r\n\r\nQua kết quả trên, chúng ta có thể dễ dàng thấy được mức độ ổn định của iPhone 14 Plus chạy iOS 16.4.1 tốt hơn rất nhiều so với bản iOS cũ, cụ thể là 95% so với 65.8%. Điều đó cũng chứng tỏ rằng iPhone 14 Plus nói riêng và các mẫu iPhone khác nói chung thường có độ ổn định tốt hơn sau nhiều bản cập nhật iOS. Nhìn chung, dù iPhone 14 Plus đã ra mắt được 6 tháng nhưng máy vẫn hoàn toàn cân được các tác vụ nặng như edit video hay chơi các tựa game nặng về mặt đồ họa như: Genshin Impact, Call of Duty Mobile,...\r\nKhông chỉ thế, khả năng kiểm soát nhiệt độ cũng như mức tiêu thụ pin của iPhone 14 Plus cũng được cải thiện tốt hơn so với thời điểm máy mới ra mắt. Cụ thể, iPhone 14 Plus chỉ mất 4% pin sau khi hoàn thành bài test, con số này ở 6 tháng trước là 7%. Qua đó, minh tin rằng Apple đã rất cố gắng trong việc tối ưu về mặt hiệu năng cho iPhone 14 Plus, đồng thời cũng đảm bảo cho khả năng tiêu thụ điện của máy nằm ở mức tốt.\r\n\r\n', 1, 13, 21, '2023-05-03'),
(8, 'Samsung Galaxy Z Fold4 512GB có lẽ là cái tên dành được nhiều sự chú ý đến từ sự kiện Unpacked thường niên của Samsung, bởi máy sở hữu màn hình lớn cùng cơ chế gấp gọn giúp người dùng có thể dễ dàng mang theo.', 'IMG_20230618_152108988.jpg', 'Vẻ ngoài thời thượng chuẩn chỉnh flagship\n\nSamsung Galaxy Z Fold4 512GB được hoàn thiện với ngoại hình khá giống với một chiếc máy tính bảng, sử dụng cơ chế gập hiện đại giúp cho người dùng có thể tùy biến thay đổi Galaxy Z Fold4 thành chiếc smartphone hay tablet một cách linh hoạt tùy vào nhu cầu sử dụng.\nĐộ bền của máy đã được kiểm chứng qua nhiều bài đánh giá khắt khe khác nhau, nên Samsung tự tin cho biết Galaxy Z Fold4 có tuổi thọ sử dụng và số lần gập gia tăng rất nhiều so với thế hệ tiền nhiệm. Từ đó người dùng có thể an tâm hơn trong việc mua sắm và sử dụng máy trong một thời gian lâu dài.\nNgoài ra với đặc tính ánh kim trên Armor Aluminum cũng giúp Galaxy Z Fold4 trở nên sáng bóng và bắt mắt hơn, làm toát lên một diện mạo cao cấp sang trọng khi cầm nắm sử dụng trên tay.\n\nNhiếp ảnh chuyên nghiệp với bộ 3 camera cao cấp\n\nỞ phía mặt lưng, máy sẽ được tích hợp 3 camera với camera chính có độ phân giải 50 MP, camera góc siêu rộng 12 MP và một cảm biến tele 10 MP. Với chất lượng ảnh thu lại có độ sắc nét cao giúp Galaxy Z Fold4 có thể trở thành một chiếc camera phone xịn sò để ghi lại những khoảnh khắc đẹp xung quanh bạn.\nĐi kèm với đó sẽ là những tính năng như: Góc siêu rộng, chuyên nghiệp, zoom quang học 3X hay thậm chí là thu phóng chuẩn không gian lên đến 30 lần. Không đơn thuần là một trợ thủ phục vụ cho việc ghi chép mà máy còn có thể đóng vai trò như một một chiếc máy ảnh tiện lợi đối với các bạn phóng viên hay vlogger.\nBên trong màn hình lớn sẽ là camera ẩn có độ phân giải 4 MP giúp cho người dùng có thể vừa sử dụng máy cho công việc và vừa có thể video call với bạn bè. Hơn thế nữa, ở phần màn hình phụ còn có thêm camera 10 MP để các tác vụ chụp nhanh trở nên dễ dàng hơn cho dù thiết bị có đang gặp hay không đi chăng nữa.\n\nMàn hình rộng lớn cùng màu sắc hiển thị sinh động\n\nGalaxy Z Fold4 sẽ được trang bị trên máy một tấm nền Dynamic AMOLED 2X, mang đến khả năng tái hiện màu sắc sinh động rực rỡ hay thể hiện màu đen sâu giúp người dùng có thể trải nghiệm nội dung một cách chân thực. Dù người dùng có nhìn máy từ góc độ nào đi chăng nữa thì hình ảnh cũng sẽ không có sự thay đổi quá nhiều.', 1, 0, 3, '2023-06-18'),
(9, 'Tai nghe Bluetooth True Wireless Samsung Galaxy Buds 2 Pro R510N', 'IMG_20230618_152434120.jpg', 'Tháng 08/2022, nhà Samsung cho ra mắt sản phẩm mới là tai nghe Bluetooth True Wireless Samsung Galaxy Buds 2 Pro R510N. Đây là phiên bản kế nhiệm của chiếc tai nghe Samsung Galaxy Buds Pro (ra mắt vào tháng 01/2021). Sản phẩm mới này hứa hẹn sẽ bổ sung nhiều tính năng hấp dẫn cho người dùng.\nThiết kế gọn nhẹ, màu sắc sang chảnh\nĐiều mình chú ý đầu tiên chính là tổng quan thiết kế của bộ tai nghe này, toàn bộ hộp sạc và tai nghe đều được bao phủ bởi màu trắng trang nhã, thanh lịch. Phần logo Samsung in ở mặt trên hộp sạc càng làm thiết kế trở nên tinh tế hơn. Với mình, đây không chỉ là một chiếc tai nghe mà nó còn là một món phụ kiện thời trang sang chảnh.\n\nCảm giác khi cầm hộp sạc rất vừa tay, nó nằm gọn trong lòng bàn tay mình. Khi mình sắm thêm một case đựng nữa thì đây chính là một combo hoàn hảo. Mình có thể thoải mái treo móc bất kỳ đâu, balo hay túi xách đều được, hơn nữa còn bảo vệ dock sạc khỏi những vết xước hoặc dấu vân tay khi sử dụng. Tất nhiên, nếu không có case đựng, bạn vẫn hoàn toàn dễ dàng cất giữ và mang theo vô cùng thuận tiện.\n\nKhi mở hộp sạc ra, mình có thể dễ dàng mở bằng một tay và nó cho mình cảm giác phần bản lề chắc chắn, tiếng “tách” khi đóng hộp nghe khá vui tai.\n\nTrong hộp tai nghe khi mua về, mình nhận được tổng cộng 3 cặp đệm tai. Trong đó, có 1 cặp được gắn sẵn lên tai nghe và 2 cặp rời đi kèm. Mỗi cặp đệm tai là một kích cỡ khác nhau, tương ứng với 3 cỡ S - M - L. Do vậy, khá dễ dàng để lựa chọn được cặp đệm tai phù hợp với khuôn tai của mình. Ngoài ra, khi cho bạn bè mượn cũng có thể đổi cặp đệm có kích cỡ khác vừa vặn với bạn mình hơn.\n\nVề tai nghe, theo mình so sánh thì phiên bản Buds 2 Pro này đã được nhà sản xuất tối ưu về mặt kích thước, giúp nó nhỏ gọn hơn phiên bản tiền nhiệm Buds Pro kha khá. Do vậy, khi đeo, mình thấy vừa vặn với tai hơn, không bị cấn tai, kết hợp với nút đệm tai silicone mềm mại nên đeo lâu cỡ 3 - 4 tiếng liên tục mà mình vẫn thoải mái.\n\nTai nghe Bluetooth kết nối nhanh, đường truyền ổn định\nTheo thông số từ nhà sản xuất, chiếc tai nghe Samsung này sử dụng chuẩn kết nối Bluetooth 5.3, đây là công nghệ kết nối không dây tiên tiến nhất tại thời điểm ra mắt. Vì vậy, tốc độ kết nối theo mình cảm nhận là khá nhanh. Mình chỉ vừa mở hộp sạc lên, tai nghe đã tự động kết nối với điện thoại ngay (Lưu ý: Mình đã từng kết nối tai nghe với chiếc điện thoại này rồi và trong tình huống trên, điện thoại mình đang bật Bluetooth).\n\nTuy nhiên, với những bạn lần đầu sử dụng, cần thực hiện vài thao tác để kết nối như sau: Đầu tiên, khi lấy tai nghe ra khỏi hộp sạc, 2 bên tai sẽ tự động được kết nối. Sau đó, bạn bật Bluetooth của thiết bị cần kết nối, dò và tìm tai nghe trên màn hình. Cuối cùng, chọn “Connect” là xong.', 4, 0, 0, '2023-06-18'),
(10, 'Loạt laptop MSI giá tốt tại Thế Giới Di Động', 'IMG_20230706_190142184.jpg', 'Đại diện hệ thống Thế Giới Di Động cho biết, laptop MSI được nhiều người sử dụng nhờ phần cứng mạnh mẽ, thiết kế thẩm mỹ, dải sản phẩm đa dạng với nhiều tính năng phụ trợ hữu ích. \"Chương trình ưu đãi của MSI và Thế Giới Di Động mang đến cơ hội để người dùng mua một chiếc máy ưng ý, phục vụ cho việc học tập, giải trí\", đại diện nói.\n\nMột số model đáng chú ý hiện có tại cửa hàng như Thin GF63 12VE (460VN). Máy trang bị vi xử lý Intel Core i5 12450H, tốc độ ép xung đối đa lên đến 4.4 GHz kết hợp cùng card rời Nvidia, giúp tác vụ mượt mà, từ chơi game đến thiết kế. Dòng laptop RTX 40 series còn có tính năng MUX Switch giúp xuất hình trực tiếp từ card đồ họa lên màn hình.', 2, 0, 2, '2023-07-06'),
(11, 'Nghệ thuật kêt hợp đá quý của Hublot', 'IMG_20230706_190513790.jpg', 'Không chỉ làm chủ vật liệu chế tác và chơi đùa cùng màu sắc, Hublot còn chú trọng kỹ năng kết hợp đá quý trên từng chiếc đồng hồ.\nBộ vỏ được xem là cầu nối tương tác giữa người dùng và đồng hồ, bảo vệ phần máy cơ, mặt số khỏi tác động ngoại lực hay va chạm ngoài ý muốn. Bộ phận này còn đảm bảo khả năng chống nước tối ưu, tăng tính thẩm mỹ cao trên diện tích rất nhỏ.\n\nQuá trình hoàn thiện bộ vỏ đòi hỏi chuyên môn cao cùng nhiều kỹ thuật khó. Ngoài đánh bóng, phay xước, phun cát hay chải satin, khảm nạm đá quý là một trong những kỹ nghệ đặc biệt, phản ánh giá trị đồng hồ và tài năng nghệ nhân.\n\nHơn 40 năm qua, Hublot chỉ dùng đá quý chất lượng cao, nguồn gốc tự nhiên và trải qua quy trình kiểm định khắt khe. Kim cương, hồng ngọc, ngọc bích hay sapphire màu phải đảm bảo yếu tố: độ chính xác mặt cắt, trong suốt, màu sắc và kích cỡ sau cắt mài.\n\nNghệ nhân sẽ đính thủ công đá quý lên bộ vỏ, dây đeo hay toàn bộ mặt số, chú trọng thao tác chuẩn xác. \"Nhờ liên tục thử nghiệm bằng công cụ chuyên dụng (một số loại đặc biệt do chúng tôi thiết kế), tất cả đá được sử dụng đều đảm bảo mức độ tinh xảo bậc nhất\", đại diện Hublot nói.', 3, 0, 0, '2023-07-06');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tintuchinhanh`
--

CREATE TABLE `tintuchinhanh` (
  `idTT` int(11) NOT NULL,
  `hinhanh` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tintuchinhanh`
--

INSERT INTO `tintuchinhanh` (`idTT`, `hinhanh`) VALUES
(1, 'xiaomi-redmi-a1-thumb-200x200.jpg'),
(1, 'xiaomi-redmi-note-12-pro-xanh-thumb-600x600.jpg'),
(2, 'iphone-14-max-256gb-080922-113836.jpg'),
(2, 'iphone-14-max-256gb-080922-113835.jpg'),
(2, 'iphone-14-max-256gb-080922-113841.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `userstore`
--

CREATE TABLE `userstore` (
  `iduser` int(11) NOT NULL,
  `tenuser` varchar(100) NOT NULL,
  `anhdaidien` text NOT NULL,
  `email` text NOT NULL,
  `sdt` text NOT NULL,
  `pass` text NOT NULL,
  `gioitinh` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `userstore`
--

INSERT INTO `userstore` (`iduser`, `tenuser`, `anhdaidien`, `email`, `sdt`, `pass`, `gioitinh`) VALUES
(1, 'Admin - Khang Lê', '', 'lekhangmobile.tm59@gmail.com', '0788949502', '123123', 1),
(5, 'khang le', '', 'lekhangmobile1.tm59@gmail.com', '0788949502', '123123', 1),
(6, 'khang le 12', '', 'lekhangmobile12.tm59@gmail.com', '0788949502', '123123', 1),
(7, 'leeeeee', '', 'lekhangmobile.tm599@gmail.com', '012345667', '123123', 1),
(8, 'Tui Là Khang', '', 'khangtanle.tm59@gmail.com', '09090909090', '123123', 1),
(9, 'khang le', '', 'lekhangmobile.tm55@gmail.com', '123', '123123', 1),
(11, 'Khang Tester', '', 'lekhang1512@gmail.com', '0788949502', '123123', 1),
(12, 'Khang Tester Empty', '', 'khangtesterempty@gmail.com', '1231231321', '123123', 1),
(13, 'KhangTester', '', 'khangle1512@gmail.com', '0788949502', '123123', 1),
(14, 'Khang Test App', '', 'khanglee@gmail.com', '0788949502', '123123', 1),
(17, 'namene', '', 'emaine@gmail.com', '0788949502', '123123', 1),
(18, 'khangTestSignIn', '', 'khangtestsignin@gmail.com', '0788949502', '123123', 1),
(19, 'khang profile', '', 'khangle.tm59@gmail.com', '0788949502', '123123', 1),
(20, 'Nu Ne', '', 'nune@gmail.com', '0788912345', '123123', 0),
(21, 'Test Nữ Nè', '', 'testnune@gmail.com', '0789456321', '123123', 1),
(22, 'Nu', '', 'testnutiepne@gmail.com', '0789654321', '123123', 1),
(23, 'nunu', '', '123123@gmail.com', '0789654123', '123123', 1),
(24, 'giới tính', '', 'gioitinh@gmail.com', '0789465312', '123123', 0),
(25, 'khangle', '', 'khangle1512.tm59@gmail.com', '0788949502', '123123', 1),
(26, 'khangle1512', '', 'khangle.1512@gmail.com', '0788949502', '123123', 1),
(27, 'testdky', '', 'testdky@gmail.com', '0788949502', '123123', 1),
(28, 'Tao Thang Day', '', 'mbietbomaylaai0@gmail.com', '0329855895', '123456', 1),
(29, 'khang', '', 'lekhang.tm59@gmail.c', '0788949502', '123123', 1),
(30, 'float Tester', '', 'testapp.tm59@gmail.com', '0788949502', '123123', 1),
(31, 'khang', '', 'le.tm59@gmail.com', '0789456321', '123123', 1),
(32, 'khang', '', 'lekhang.tm15122001@gmail.com', '0788949502', '123123', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `uudaihot`
--

CREATE TABLE `uudaihot` (
  `id` int(11) NOT NULL,
  `tieude` varchar(250) NOT NULL,
  `hinhanh` varchar(250) NOT NULL,
  `noidung` text NOT NULL,
  `idsp` int(11) DEFAULT NULL,
  `sale` float DEFAULT NULL,
  `slsale` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `uudaihot`
--

INSERT INTO `uudaihot` (`id`, `tieude`, `hinhanh`, `noidung`, `idsp`, `sale`, `slsale`) VALUES
(1, 'siêu sale OPPO A57 ', 'a57-720-220-720x220.jpg', 'Giá Cực Rẻ\nOppo A57 siêu sale giá cực rẻ \nGiảm 900K VNĐ cho khách hàng\nSố Lượng Có Hạn', 11, 900000, 5),
(2, 'Giá Quá Rẻ - Realme C55 series - Trả Góp %', 'realme-C55-720-220-720x220-2.jpg', 'trả góp 0%\r\nGiảm tới 900k cho khách hàng', 12, 900000, 5),
(3, 'Redmi Note 12 - Trả Góp 0% - Tặng sim trị giá 1TR VNĐ', 'Redmi-note12-720-220-720x220.jpg', 'Trả Góp 0%\r\nTặng Sim 1TR VNĐ', 10, 0, 5);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `adminstore`
--
ALTER TABLE `adminstore`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `danhmuctintuc`
--
ALTER TABLE `danhmuctintuc`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`iddm`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`idsp`);

--
-- Chỉ mục cho bảng `thongbaodonhang`
--
ALTER TABLE `thongbaodonhang`
  ADD PRIMARY KEY (`idthongbao`);

--
-- Chỉ mục cho bảng `tintuc`
--
ALTER TABLE `tintuc`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `userstore`
--
ALTER TABLE `userstore`
  ADD PRIMARY KEY (`iduser`);

--
-- Chỉ mục cho bảng `uudaihot`
--
ALTER TABLE `uudaihot`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `adminstore`
--
ALTER TABLE `adminstore`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `danhmuctintuc`
--
ALTER TABLE `danhmuctintuc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `iddm` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `idsp` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT cho bảng `thongbaodonhang`
--
ALTER TABLE `thongbaodonhang`
  MODIFY `idthongbao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT cho bảng `tintuc`
--
ALTER TABLE `tintuc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `userstore`
--
ALTER TABLE `userstore`
  MODIFY `iduser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `uudaihot`
--
ALTER TABLE `uudaihot`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
