<?php
//header("Content-Type: application/json");
include "connect.php";

$action = $_POST['action'];

$arr = [
			'success' => false,
			'message' => 'error'	
		];


switch ($action) {
	case "getDonHang":
		getDonHang();
		break;
	case "timkiem":
		timkiemIDdonHang();
		break;
	case "getUser":
		getUser();
		break;
	case "updateTrangThai":
		updateTrangThai();
		break;
	case "thongbaoUser":
		thongBaoDonHang();
		break;

	default:
		defaut();
		break;
}

function getDonHang(){
	include "connect.php";

	$thoigian = $_POST['thoigian'];
	$trangthai = $_POST['trangthai'];
	$sapxep = $_POST['sapxep'];

	$query = 'SELECT * FROM `donhang` WHERE DATEDIFF(CURDATE(), `ngaytaodon`) '.$thoigian.' AND `trangthai` = '.$trangthai.' ORDER BY `id` '.$sapxep.'';
	$data = mysqli_query($conn, $query);

	$result = array();

	if($data == true)
	{
		
		while($row = mysqli_fetch_assoc($data))
		{
		   $truyvan = 'SELECT * FROM `chitietdonhang` INNER JOIN `sanpham` ON chitietdonhang.idsp = sanpham.idsp INNER JOIN `donhang` ON chitietdonhang.iddonhang = donhang.id WHERE chitietdonhang.iddonhang = "'.$row['id'].'" ';
			$data1 = mysqli_query($conn, $truyvan);

			$item = array();
			while($row1 = mysqli_fetch_assoc($data1))
			{
				$item[] = $row1;
			}

			$row['item'] = $item;
			$result[] = ($row);

		}

		// code...
		if (!empty($result)) {

			$arr = [
				'success' => true,
				'message' => 'Thanh Cong',
				'result'  => $result	
			];	

		}else{
			$arr = [
				'success' => false,
				'message' => 'Khong Thanh Cong',
				'result'  => $result	
			];
		}
		print_r(json_encode($arr));

	}
	else{
		$arr = [
			'success' => false,
			'message' => 'error'	
		];

		print_r(json_encode($arr));
	}
}

function timkiemIDdonHang(){
	include "connect.php";
	$id = $_POST['id'];

	$query = 'SELECT * FROM `donhang` WHERE `id` = '.$id.'';
	$data = mysqli_query($conn, $query);

	$result = array();

	if($data == true)
	{
		
		while($row = mysqli_fetch_assoc($data))
		{
		   $truyvan = 'SELECT * FROM `chitietdonhang` INNER JOIN `sanpham` ON chitietdonhang.idsp = sanpham.idsp INNER JOIN `donhang` ON chitietdonhang.iddonhang = donhang.id WHERE chitietdonhang.iddonhang = "'.$row['id'].'"';
			$data1 = mysqli_query($conn, $truyvan);

			$item = array();
			while($row1 = mysqli_fetch_assoc($data1))
			{
				$item[] = $row1;
			}

			$row['item'] = $item;
			$result[] = ($row);

		}

		// code...
		if (!empty($result)) {

			$arr = [
				'success' => true,
				'message' => 'Thanh Cong',
				'result'  => $result	
			];	

		}else{
			$arr = [
				'success' => false,
				'message' => 'Khong Thanh Cong',
				'result'  => $result	
			];
		}
		print_r(json_encode($arr));

	}
	else{
		$arr = [
			'success' => false,
			'message' => 'error'	
		];

		print_r(json_encode($arr));
	}

}

function getUser(){
	include "connect.php";
	$iduser = $_POST['iduser'];


	$query = "SELECT * FROM `userstore` WHERE `iduser` =".$iduser."";
	$data = mysqli_query($conn, $query);
	$result = array();
	while ($row = mysqli_fetch_assoc($data)) {
		$result[] = ($row);
		// code...
	}

	if (!empty($result)) {

		$arr = [
			'success' => true,
			'message' => 'Thanh Cong',
			'result'  => $result	
		];	
	}else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong',
			'result'  => $result	
		];
	}
	print_r(json_encode($arr));

}

function updateTrangThai(){
	include "connect.php";
	$id = $_POST['id'];
	$trangthai = $_POST['trangthai'];
	$ngayhoanthanh = $_POST['ngayhoanthanh'];

	$query = 'UPDATE `donhang` SET `trangthai`= '.$trangthai.', `ngayhoanthanh` = '.$ngayhoanthanh.' WHERE `id` = '.$id.' ' ;
	$data = mysqli_query($conn, $query);

	if($data == true){
		$arr = [
			'success' => true,
			'message' => 'Thanh Cong'
		];	
	}else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'
		];
	}
	print_r(json_encode($arr));

}

function thongBaoDonHang(){
	include "connect.php";
	$iduser = $_POST['iduser'];
	$noidung = $_POST['noidung'];
	$iddonhang = $_POST['iddonhang'];

	$query = 'INSERT INTO `thongbaodonhang`(`iduser`, `noidungthongbao`, `iddonhang`, `trangthaidoc`) VALUES ('.$iduser.',"'.$noidung.'",'.$iddonhang.', 0)';
	$data = mysqli_query($conn, $query);

	if($data == true){
		$arr = [
			'success' => true,
			'message' => 'Thanh Cong'
		];	
	}else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'
		];
	}
	print_r(json_encode($arr));
}

function defaut(){
	print_r(json_encode($arr));
}

?>