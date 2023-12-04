<?php
//header("Content-Type: application/json");
include "connect.php";

$timkiem = $_POST['timkiem'] ;
$sapxep = $_POST['sapxep'] ;

if(!empty(trim($timkiem)))
{
    $query = "SELECT * FROM `sanpham` WHERE `tensp` LIKE RTRIM('%".trim($timkiem)."%') ORDER BY `giabansp` ".$sapxep."";
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
}
else
{
    $arr = [
    			'success' => false,
    		];
    
}



?> 