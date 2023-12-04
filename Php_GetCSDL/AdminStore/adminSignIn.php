<?php

include "connect.php";
$taikhoan = $_POST['taikhoan'];
$matkhau = $_POST['matkhau'];

$query = 'SELECT `id`, `tenadmin`, `sdtadmin`  FROM `adminstore` WHERE `taikhoan` = "'.$taikhoan.'" AND `matkhau`= "'.$matkhau.'" LIMIT 1';
$data = mysqli_query($conn, $query);

$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}	

if (!empty($result)) {
	$arr = [
			'success' => true,
			'message' => 'Thành Công',
			'result' => $result
		];	
}
else
{
	$arr = [
			'success' => false,
			'message' => 'đăng nhập thất bại'
	
		];
}

print_r(json_encode($arr));

?>