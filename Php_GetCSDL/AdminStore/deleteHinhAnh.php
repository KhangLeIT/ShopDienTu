<?php

include "connect.php";
$image = $_POST["image"];
$target_dir = "../Hinhanh/";


unlink("".$target_dir."/".$image ."");



?>