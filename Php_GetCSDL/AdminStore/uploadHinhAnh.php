<?php

include "connect.php";
$target_dir = "../Hinhanh/";
$query = "SELECT max(idsp) as idsp from sanpham";
$data = mysqli_query($conn, $query);
$result = array();

while($row = mysqli_fetch_assoc($data)){
	$result = ($row);
}

$target_file_name = $target_dir.basename($_FILES["file"]["name"]);
$name = basename($_FILES["file"]["name"]);
$response = array();


if(isset($_FILES["file"]))
{
	if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name))
	{
		$arr =[
			'success' => true,
			'message' =>"thanh cong",
			'name' => $name
		];

	}
	else{
		$arr =[
			'success' => false,
			'message' =>"ko thanh cong"
		];

	}
}
else{
	$arr =[
			'success' => false,
			'message' =>"Lỗi"
		];
}

echo json_encode($arr);
?>