<?php
//header("Content-Type: application/json");
include "connect.php";

$action = $_POST['action'];

$arr = [
			'success' => false,
			'message' => 'error',	
		];


switch ($action) {
	case "add":
		themSanPhamMoi();
		break;
	case "delete":
		deleteSanPham();
		break;
	case "backdelete":
		backSanPhamDelete();
		break;
	case "update":
		updateSanPham();
		break;
	case "deleteHinh":
		deleteHinhAnh();
		break;

	default:
		defaut();
		break;
}


function themSanPhamMoi(){

	include "connect.php";
	$tensp = $_POST['tensp'];
	$hinhanh = $_POST['hinhanh'];
	$mota = $_POST['mota'];
	$thongtin = $_POST['thongtin'];
	$giaban = $_POST['giaban'];
	$iddm = $_POST['iddm'];
	$slco = $_POST['slco'];
	$linkvideo = $_POST['linkvideo'];

	$query = 'INSERT INTO `sanpham`(`tensp`, `hinhanhsp`, `motasp`, `thongtinsp`, `giabansp`, `iddm`, `slco`, `linkvideo`) VALUES ("'.$tensp.'","'.$hinhanh.'","'.$mota.'","'.$thongtin.'","'.$giaban.'","'.$iddm.'","'.$slco.'",'.$linkvideo.')';
	$data = mysqli_query($conn, $query);

	if ($data == true) {

		$arr = [
			'success' => true,
			'message' => 'Thanh Cong',
				
		];	
	}else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong',	
		];
	}

	print_r(json_encode($arr));
}

function deleteSanPham()
{
	include "connect.php";
	$idsp = $_POST['idsp'];
	$target_dir = "../Hinhanh/";

	$query = "SELECT `hinhanhsp` FROM `sanpham` WHERE idsp =".$idsp." LIMIT 1";

	$data = mysqli_query($conn, $query);
	$result = array();

	while ($row = mysqli_fetch_assoc($data)) {
		$result = ($row);
		// code...
	}



	if (!empty($result)) {

		$hinhanh = $result['hinhanhsp'];
		// echo $hinhanh;
        // di chuyen hinh anh sang backup
        $source_file = '../Hinhanh/'.$hinhanh.'';
        $destination_path = '../backupHinhanh/';
        rename($source_file, $destination_path . pathinfo($source_file, PATHINFO_BASENAME));
        
	   // xoa san pham
		$queryDelete = "DELETE FROM `sanpham` WHERE idsp = ".$idsp."";
		$data = mysqli_query($conn, $queryDelete);
		if($data == true)
		{
			$arr = [
			'success' => true,
			'message' => 'Thanh Cong'
			];
		}
		else{
			$arr = [
			'success' => false,
			'message' => 'error sql'
			
			];
		}
			
	}else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'	
		];
	}
	print_r(json_encode($arr));
}

function backSanPhamDelete(){
	include "connect.php";
	$idsp = $_POST['idsp'];
	$tensp = $_POST['tensp'];
	$hinhanh = $_POST['hinhanh'];
	$mota = $_POST['mota'];
	$thongtin = $_POST['thongtin'];
	$giaban = $_POST['giaban'];
	$iddm = $_POST['iddm'];
	$slco = $_POST['slco'];
	$linkvideo = $_POST['linkvideo'];

	$query = 'INSERT INTO `sanpham`(`idsp`,`tensp`, `hinhanhsp`, `motasp`, `thongtinsp`, `giabansp`, `iddm`, `slco`, `linkvideo`) VALUES ("'.$idsp.'","'.$tensp.'","'.$hinhanh.'","'.$mota.'","'.$thongtin.'","'.$giaban.'","'.$iddm.'","'.$slco.'","'.$linkvideo.'")';
	$data = mysqli_query($conn, $query);

	if ($data == true) {
		// echo $hinhanh;
        // di chuyen hinh anh sang backup
        $source_file = '../backupHinhanh/'.$hinhanh.'';
        $destination_path = '../Hinhanh/';
        rename($source_file, $destination_path . pathinfo($source_file, PATHINFO_BASENAME));


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

function updateSanPham()
{
	include "connect.php";
	$idsp = $_POST['idsp'];
	$noidungupdate = $_POST['noidungupdate'];

	$query = 'UPDATE `sanpham` SET '.$noidungupdate.' WHERE idsp = '.$idsp.'';
	$data = mysqli_query($conn, $query);

	if ($data == true) {

		$arr = [
			'success' => true,
			'message' => 'thanh cong'
		];
		
	}
	else{
		$arr = [
			'success' => false,
			'message' => 'Khong Thanh Cong'	
		];
	}
	print_r(json_encode($arr));
}


function deleteHinhAnh(){
	include "connect.php";
	$hinhanh = $_POST['hinhanh'];
	
	if(unlink("../Hinhanh/".$hinhanh."")){
    	$arr = [
            'success' => true,
            'message' => 'Thanh Cong xoa anh'	
            ];
	}
	else{
	    $arr = [
            'success' => false,
            'message' => 'Error'	
            ];
	}

	print_r(json_encode($arr));
}

function defaut(){
	print_r(json_encode($arr));
}


?>