<?php
include "connect.php";

$query = 'SELECT `sanpham`.`idsp`, `sanpham`.tensp, COUNT(`soluongmua`) AS tong FROM `chitietdonhang` INNER JOIN `sanpham` ON sanpham.idsp = `chitietdonhang`.idsp  GROUP BY `sanpham`.`idsp`  LIMIT 0,10';
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