<?php

//header("Content-Type: application/json");
include "connect.php";
$action = $_POST["action"];


switch ($action) {
	case 0:
		getUuDaiHot();
		break;
    case 1:
		getSanPhamUuDai();
		break;
	default:
		ErorrSQL();
		break;
}

function getUuDaiHot()
{
	include "connect.php";
	$query = 'SELECT * FROM `uudaihot` ORDER BY `id` DESC LIMIT 0,5';
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

function getSanPhamUuDai()
{
	

	include "connect.php";
	$idsp = $_POST["idsp"];

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

function ErorrSQL()
{
	$arr = [
			'success' => false,
			'message' => 'Loi',	
		];

	print_r(json_encode($arr));
}

?> 