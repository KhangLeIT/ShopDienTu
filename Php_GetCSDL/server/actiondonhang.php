<?php

$action = $_POST['action'];

	

switch ($action) {
	case 0:
		userHuyDonHang();
		break;
	case 2:
		userXacNhanDaNhanDon();
		break;

	default:
		defaut();
		break;
}

function userHuyDonHang()
{
	include "connect.php";
	$iduser = $_POST['iduser'];
	$iddonhang = $_POST["iddonhang"];
	$date = $_POST["date"];

	$query = 'UPDATE `donhang` SET `trangthai` = -1, `ngayhoanthanh` = "'.$date.'"  WHERE `iduser` = "'.$iduser.'" AND  `id` = "'.$iddonhang.'" ' ;
	$data = mysqli_query($conn, $query);

	if ($data == true) {

		$arr = [
			'success' => true,
			'message' => 'Thanh Cong'	
		];	
	}else{
		$arr = [
			'success' => false,
			'message' => 'error'	
		];
		
	}
	print_r(json_encode($arr));
}

function userXacNhanDaNhanDon()
{
	include "connect.php";
	$iduser = $_POST['iduser'];
	$iddonhang = $_POST["iddonhang"];
	$date = $_POST["date"];

	$query = 'UPDATE `donhang` SET `trangthai` = 3, `ngayhoanthanh` = "'.$date.'"  WHERE `iduser` = "'.$iduser.'" AND  `id` = "'.$iddonhang.'" ' ;
	$data = mysqli_query($conn, $query);

	if ($data == true) {

		$arr = [
			'success' => true,
			'message' => 'Thanh Cong'	
		];	
	}else{
		$arr = [
			'success' => false,
			'message' => 'error'	
		];
		
	}
	print_r(json_encode($arr));
}

function defaut()
{
	$arr = [
			'success' => false,
			'message' => 'error'	
		];

	print_r(json_encode($arr));
	
}

?>