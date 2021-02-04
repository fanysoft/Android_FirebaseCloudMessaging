<?php


	function send_notification ($tokens, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $tokens,
			 'data' => $message
			);

		$headers = array(
			'Authorization:key = YOUR KEY',
			'Content-Type: application/json'
			);

	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}


	$servername = "" ;
	$username = "";
	$pass = "";
	$db="";

	$conn = mysqli_connect($servername, $username, $pass, $db);

	$sql = " Select Token From firebase_users";

	$result = mysqli_query($conn,$sql);
	$tokens = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$tokens[] = $row["Token"];
		}
	}

	mysqli_close($conn);


	?>


	<br><br>
	<center>
	<form method="post" name="pokus">
	<font face=arial size=1>
	<input type="hidden" name="odeslano" value="ano">
	<tr>
		<td colspan=4 valign=center width=260><div class=bezne><font color=silver><center><br>
		<textarea cols="150" style="font-family: Arial; font-size: 11pt; color=green;"  rows="2" name="subjekt"></textarea><br>
	  </select>
	  <br> <br>
		<input type="submit" value="Send to Firebase Cloud Messaging" style="font-family: Arial; font-size: 11pt; color=green;"></center>
	</td>
	</tr>
	</form>

<?php

	$odeslano = $_POST["odeslano"];


	if ($odeslano=="ano"){
			$subjekt = $_POST["subjekt"];
			$message = array("message" => $subjekt);

			$message_status = send_notification($tokens, $message);
			echo "<br><br>";
			echo $message_status;
	}


?>
