
<?php

//header("Content-Type: application/json");
include "connect.php";

$iduser = $_POST['iduser'];
$diachi = $_POST['diachi'];
$sodienthoai = $_POST['sodienthoai'];
$soluong = $_POST['soluong'];
$tongtien = $_POST['tongtien'];
$ngaytaodon = $_POST['ngaytaodon'];
$chitiet = $_POST['chitiet'];


$query = 'INSERT INTO `donhang`(`iduser`, `diachi`, `sodienthoai`, `soluong`, `tongtien`, `trangthai`, `ngaytaodon`,`ngayhoanthanh` ) VALUES ("'.$iduser.'","'.$diachi.'","'.$sodienthoai.'","'.$soluong.'","'.$tongtien.'", 0 , "'.$ngaytaodon.'", NULL)';


$data = mysqli_query($conn, $query);

if($data == true)
{
	$query = 'SELECT id AS iddonhang from `donhang` WHERE `iduser` = '.$iduser.' ORDER BY id DESC LIMIT 1';
	$data = mysqli_query($conn, $query);
	//$result = array();
	while ($row = mysqli_fetch_assoc($data)) {
		$iddonhang = ($row);
	}
	if(!empty($iddonhang))
	{
		//co don hang
		$chitiet = json_decode($chitiet, true);
		foreach ($chitiet as $key => $value) {
			$truyvan ='INSERT INTO `chitietdonhang`(`iddonhang`, `idsp`, `soluongmua`, `gia`) VALUES ("'.$iddonhang["iddonhang"].'","'.$value["idsp"].'","'.$value["soluong"].'","'.$value["giabansp"].'")';

			$data = mysqli_query($conn, $truyvan);
			
		}
		if($data == true)
		{
			$arr = [
				'success' => true,
				'message' => 'Thanh Cong'
			];	

		}
		else
		{
			$arr = [
				'success' => false,
				'message' => 'khong Thanh Cong'
			];	

		}
		print_r(json_encode($arr));

	}
	

}
else{
	$arr = [
		'success' => false,
		'message' => 'data flase'
	];
	print_r(json_encode($arr));
}



?> 