<?php

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
	unlink("".$target_dir."/".$hinhanh."");
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




?>