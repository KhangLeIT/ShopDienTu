<?php

include "connect.php";

$query = 'SELECT *, SUM(tongtien) as tongtienthang, MONTH(`ngayhoanthanh`) as thang FROM `donhang` WHERE MONTH(`ngayhoanthanh`) >= 0  GROUP BY YEAR(`ngayhoanthanh`), MONTH(`ngayhoanthanh`)';
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