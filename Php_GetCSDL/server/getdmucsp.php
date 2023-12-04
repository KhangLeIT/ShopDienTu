
<?php
//header("Content-Type: application/json");

include "connect.php";

$page = $_POST['page'];
$iddm = $_POST['iddm'];

$total = 5; // lấy 5 sp 1 lần
$pos = ($page-1) * $total; // 0,5 5,5

$query = 'SELECT * FROM `sanpham` WHERE `iddm` = '.$iddm.' order by idsp DESC LIMIT '.$pos.','.$total.'';
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

?> 