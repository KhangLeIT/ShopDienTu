<?php
include "connect.php";
$iduser = $_POST['iduser'];
$query = 'SELECT * FROM `thongbaodonhang` where iduser ='.$iduser.' ORDER BY idthongbao DESC ';

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
		'message' => 'Khong Thanh Cong'
	];
}
print_r(json_encode($arr));


?>