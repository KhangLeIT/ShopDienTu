<?php
//header("Content-Type: application/json");
include "connect.php";
$query = "SELECT * FROM `loaisanpham`";
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

?> 