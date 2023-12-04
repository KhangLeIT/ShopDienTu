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
		addTinTucMoi();
		break;
	case "getTinTuc":
		loadtintuctheodanhmuc();
		break;
	case "delete":
		deleteTinTuc();
		break;
	case "backDelete":
		backTinTucDelete();
		break;
	case "update":
		updateTinTuc();
		break;
	default:
		defaut();
		break;

}

function addTinTucMoi(){
	include "connect.php";

	$tieude = $_POST['tieude'];
	$hinhanh = $_POST['hinhanh'];
	$noidung = $_POST['noidung'];
	$iddm = $_POST['iddm'];
	$idsp = $_POST['idsp'];
	$thoigian = $_POST['thoigian'];

	$query = 'INSERT INTO `tintuc`(`tieude`, `hinhanh`, `noidung`, `iddm`, `idsp`, `luotxem`, `thoigianthem`) VALUES ("'.$tieude.'","'.$hinhanh.'","'.$noidung.'","'.$iddm.'","'.$idsp.'","0","'.$thoigian.'")';
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

function loadtintuctheodanhmuc()
{
	include "connect.php";

	$page = $_POST['page'];
	$iddm = $_POST['iddm'];

	$total = 5; // lấy 5 sp 1 lần
	$pos = ($page-1) * $total; // 0,5 5,5

	$query = 'SELECT * FROM `tintuc` WHERE `iddm` = '.$iddm.' LIMIT '.$pos.','.$total.'';
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
				
		];
	}
	print_r(json_encode($arr));
}


function deleteTinTuc()
{
	include "connect.php";
	$idTT = $_POST['idTT'];
	$target_dir = "../Hinhanh/";

	$query = "SELECT `hinhanh` FROM `tintuc` WHERE id =".$idTT." LIMIT 1";

	$data = mysqli_query($conn, $query);
	$result = array();

	while ($row = mysqli_fetch_assoc($data)) {
		$result = ($row);
		// code...
	}



	if (!empty($result)) {

		$hinhanh = $result['hinhanh'];
		// echo $hinhanh;
        // di chuyen hinh anh sang backup
        $source_file = '../Hinhanh/'.$hinhanh.'';
        $destination_path = '../backupHinhanh/';
        rename($source_file, $destination_path . pathinfo($source_file, PATHINFO_BASENAME));
        
	   // xoa san pham
		$queryDelete = "DELETE FROM `tintuc` WHERE id = ".$idTT."";
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

function backTinTucDelete(){
	include "connect.php";
	$id = $_POST['id'];
	$tieude = $_POST['tieude'];
	$hinhanh = $_POST['hinhanh'];
	$noidung = $_POST['noidung'];
	$iddm = $_POST['iddm'];
	$idsp = $_POST['idsp'];
	$luotxem = $_POST['luotxem'];
	$thoigian = $_POST['thoigian'];

	$query = 'INSERT INTO `tintuc`(`id`,`tieude`, `hinhanh`, `noidung`, `iddm`, `idsp`, `luotxem`, `thoigianthem`) VALUES ("'.$id.'","'.$tieude.'","'.$hinhanh.'","'.$noidung.'","'.$iddm.'","'.$idsp.'","'.$luotxem.'","'.$thoigian.'")';

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

function updateTinTuc(){
	include "connect.php";
	$idTT = $_POST['idTT'];
	$noidungupdate = $_POST['noidungupdate'];

	$query = 'UPDATE `tintuc` SET  '.$noidungupdate.' WHERE id = '.$idTT.'';
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



function defaut(){
	print_r(json_encode($arr));
}


?>