<?php 
header("Content-Type:text/html;charset=utf-8");

$con = mysql_connect("localhost", "rkskekfk2020", "Tjdgkr23!");

if(!$con) {
	die('Could not connect:' .mysql_error());
}

mysql_select_db("rkskekfk2020_jeju", $con);
mysql_query("set names utf-8");
mysql_query("set session character_set_connection=utf8;");
mysql_query("set session character_set_results=utf8;");
mysql_query("set session character_set_client=utf8;");

$sql = "SELECT user_idx, user_email, user_name, user_phone, user_type, user_image FROM user WHERE user_email = '$_POST[user_email]' AND user_pass = '$_POST[user_pass]'";


if(!($query = mysql_query($sql, $con))) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
	$result->user_info = mysql_fetch_object($query);
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>