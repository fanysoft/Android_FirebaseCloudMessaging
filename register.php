<?php

  $servername = "" ;
  $username = "";
  $pass = "";
  $db="";

  if (isset($_GET["Token"])) {

		   $_uv_Token=$_GET["Token"];

		   $conn = mysqli_connect($servername, $username, $pass, $db) or die("Error connecting");

		   $q="INSERT INTO firebase_users (Token) VALUES ( '$_uv_Token') ON DUPLICATE KEY UPDATE Token = '$_uv_Token';";
       echo $q;

       mysqli_query($conn,$q) or die(mysqli_error($conn));

       mysqli_close($conn);

  	}else{
      echo "to token value";
    }


?>
