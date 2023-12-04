<?php

//header("Content-Type: application/json");
include "connect.php";
$action = $_POST["action"];


switch ($action) {
	case 0:
		getTinTucMoi();
		break;
	case 1:
		getChiTietTinTuc();
		break;
	case 2:
		updateLuotXem();
		break;
	case 3:
		gethinhanhtintuc();
		break;
    case 4:
		getsanphamtheotintuc();
		break;
	case 5:
		getTinTucHot();
		break;
	case 6:
		loadtintuctheodanhmuc();
		break;
	default:
		ErorrSQL();
		break;
}

function getTinTucMoi()
{
	include "connect.php";
	$query = 'SELECT * FROM `tintuc` ORDER BY `id` DESC LIMIT 0,10';
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


function getChiTietTinTuc()
{
	$idtintuc = $_POST["idtintuc"];
	include "connect.php";
	$query = 'SELECT * FROM `tintuc` where `id` = "'.$idtintuc.'" LIMIT 1';
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


function getTinTucHot()
{
	
	include "connect.php";
	$query = 'SELECT * FROM `tintuc` WHERE DATEDIFF(CURDATE(), `thoigianthem`) <8 ORDER BY `luotxem` DESC LIMIT 0,10';
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


function gethinhanhtintuc()
{
	$idtintuc = $_POST["idtintuc"];
	include "connect.php";

	$query = 'SELECT * FROM `tintuchinhanh` WHERE `idTT` = "'.$idtintuc.'"';
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

function getsanphamtheotintuc()
{
	$idsp = $_POST["idsp"];
	include "connect.php";
	$query = 'SELECT * FROM `sanpham` WHERE `idsp` = "'.$idsp.'" LIMIT 1';
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

function updateLuotXem()
{
	$idtintuc = $_POST["idtintuc"];
	include "connect.php";
	$query = 'UPDATE `tintuc` SET `luotxem`= `luotxem` + 1 WHERE `id` = "'.$idtintuc.'"' ;
	
	$data = mysqli_query($conn, $query);
	if ($data == true) {
		$arr = [
			'success' => true,
			'message' => 'Thanh Cong',
		];
	}
	else
	{
		$arr = [
			'success' => false,
			'message' => 'error',
			
		];
		
	}
    print_r(json_encode($arr));
}


function ErorrSQL()
{
	$arr = [
			'success' => false,
			'message' => 'Loi',	
		];

	print_r(json_encode($arr));
}

?> 