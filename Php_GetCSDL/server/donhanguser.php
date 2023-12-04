<?php

//header("Content-Type: application/json");
include "connect.php";


$trangthai = $_POST['trangthai'] ;

if($trangthai == 3)
{
	getDonHangHoanThanh();
}
else{
	getDonHangAll();
}

function getDonHangHoanThanh()
{
    include "connect.php";
    $iduser = $_POST['iduser'];
	$query = 'SELECT * FROM `donhang` WHERE `iduser` = "'.$iduser.'" AND `trangthai` = 3 ORDER BY id DESC ';
	$data = mysqli_query($conn, $query);
	$result = array();

	while($row = mysqli_fetch_assoc($data))
	{
	    $truyvan = 'SELECT * FROM `chitietdonhang` INNER JOIN `sanpham` ON chitietdonhang.idsp = sanpham.idsp INNER JOIN `donhang` ON chitietdonhang.iddonhang = donhang.id WHERE chitietdonhang.iddonhang = "'.$row['id'].'" AND donhang.trangthai = 3';
		$data1 = mysqli_query($conn, $truyvan);

		$item = array();
		while($row1 = mysqli_fetch_assoc($data1))
		{
			$item[] = $row1;
		}

		$row['item'] = $item;
		$result[] = ($row);

	}
	if(!empty($result))
	{
		$arr = [
			'success' => true,
			'message' => 'thành công',
			'result' => $result
		];	
	}
	else
	{
		$arr = [
			'success' => false,
		];	

	}

	print_r(json_encode($arr));
}

function getDonHangAll(){
    include "connect.php";
    $iduser = $_POST['iduser'];
	$query = 'SELECT * FROM `donhang` WHERE `iduser` = "'.$iduser.'" AND `trangthai` < 3 ORDER BY id DESC ';
	$data = mysqli_query($conn, $query);
	$result = array();

	while($row = mysqli_fetch_assoc($data))
	{
	   $truyvan = 'SELECT * FROM `chitietdonhang` INNER JOIN `sanpham` ON chitietdonhang.idsp = sanpham.idsp INNER JOIN `donhang` ON chitietdonhang.iddonhang = donhang.id WHERE chitietdonhang.iddonhang = "'.$row['id'].'" AND donhang.trangthai < 3';
		$data1 = mysqli_query($conn, $truyvan);

		$item = array();
		while($row1 = mysqli_fetch_assoc($data1))
		{
			$item[] = $row1;
		}

		$row['item'] = $item;
		$result[] = ($row);

	}
	if(!empty($result))
	{
		$arr = [
			'success' => true,
			'message' => 'thành công',
			'result' => $result
		];	
	}
	else
	{
		$arr = [
			'success' => false,
		];	

	}

	print_r(json_encode($arr));
}



?> 